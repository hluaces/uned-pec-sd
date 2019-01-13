package es.uned.servidor.servicios;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.Trino;
import es.uned.common.TrinoInterface;
import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.rmi.ControladorRegistro;
import es.uned.common.servicios.AbstractServicio;
import es.uned.common.servicios.CallbackUsuarioInterface;
import es.uned.common.servicios.ServicioAutenticacionInterface;
import es.uned.common.servicios.ServicioGestorInterface;

/**
 * Implementación de la interfaz del ServicioGestorInterface
 * 
 * @see ServicioGestorInterface
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class ServicioGestorImpl extends AbstractServicio implements ServicioGestorInterface {
	/**
	 * El controlador del registro que será utilizado para localizar los servicios
	 * RMI necesarios
	 */
	private ControladorRegistro c;

	/**
	 * Registra los callbacks de usuario
	 */
	private Map<String, CallbackUsuarioInterface> callbacks;

	/**
	 * Registra los trinos que se han enviado pero cuyos usuarios no estaban online
	 */
	private Map<String, List<TrinoInterface>> mensajesOffline;

	/**
	 * Crea una nueva instancia del servicio, facilitándole un controlador del
	 * registro.
	 * 
	 * @param c
	 */
	public ServicioGestorImpl(ControladorRegistro c) {
		super();

		this.c = c;
		this.callbacks = new HashMap<>();
		this.mensajesOffline = new HashMap<>();
	}

	/**
	 * Intenta localizar un controlador de base de datos en el registro y
	 * posteriormente lo devuelve.
	 * 
	 * @return
	 */
	private BasededatosInterface getControladorDatos() throws RemoteException {
		return (BasededatosInterface) this.c.buscarObjeto(BasededatosInterface.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUsuarios() throws RemoteException {
		return new ArrayList<String>(this.getControladorDatos().getUsuarios().keySet());
	}

	/**
	 * Devuelve el texto que se usará para mostrar un trino por pantalla
	 * 
	 * @param t Trino a mostrar
	 * @return Mensaje que representa al trino
	 */
	private String getTextoTrino(TrinoInterface t) {
		return t.ObtenerNickPropietario() + "#" + t.ObtenerTrino();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean enviarTrino(String nick, String trino) throws RemoteException {
		TrinoInterface t = new Trino(trino, nick);

		if (!this.getControladorDatos().addTrino(t)) {
			return false;
		}

		for (String s : this.getSeguidores(nick)) {
			CallbackUsuarioInterface c = this.callbacks.get(s);

			if (c != null) {
				c.write(this.getTextoTrino(t));
			} else {
				if (null == this.mensajesOffline.get(s)) {
					this.mensajesOffline.put(s, new ArrayList<>());
				}

				this.mensajesOffline.get(s).add(t);
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean mostrarMensajesOffline(String usuario) throws RemoteException {
		CallbackUsuarioInterface callback = this.callbacks.get(usuario);

		if (callback == null) {
			return false;
		}

		if (null == this.mensajesOffline.get(usuario)) {
			return true;
		}

		for (TrinoInterface t : this.mensajesOffline.get(usuario)) {
			callback.write(this.getTextoTrino(t));
		}

		this.mensajesOffline.get(usuario).clear();
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addSeguidor(String nick, String seguidor) throws RemoteException {
		if (this.getControladorDatos().esSeguidor(nick, seguidor)) {
			return false;
		}

		return this.getControladorDatos().addSeguidor(nick, seguidor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeSeguidor(String nick, String seguidor) throws RemoteException {
		if (!this.getControladorDatos().esSeguidor(nick, seguidor)) {
			return false;
		}

		return this.getControladorDatos().removeSeguidor(nick, seguidor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSeguidores(String nick) throws RemoteException {
		return this.getControladorDatos().getSeguidores(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSeguidos(String nick) throws RemoteException {
		return this.getControladorDatos().getSeguidos(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _iniciar() {
		try {
			this.getControladorDatos();
			return true;
		} catch (RemoteException e) {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _parar() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addCallbackUsuario(String usuario, CallbackUsuarioInterface c) throws RemoteException {
		this.callbacks.put(usuario, c);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeCallbackUsuario(String usuario) throws RemoteException {
		return this.callbacks.remove(usuario) != null;
	}
}
