package es.uned.servidor.servicios;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.Trino;
import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.rmi.ControladorRegistro;
import es.uned.common.servicios.AbstractServicio;
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
	 * Listar usuarios. Enviar trino. Seguir a: Dejar de seguir a: Borrar trino no
	 * recibido.
	 */

	/**
	 * Crea una nueva instancia del servicio, facilitándole un controlador del
	 * registro.
	 * 
	 * @param c
	 */
	public ServicioGestorImpl(ControladorRegistro c) {
		super();

		this.c = c;
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean enviarTrino(String nick, String trino) throws RemoteException {
		return this.getControladorDatos().addTrino(new Trino(trino, nick));
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

}
