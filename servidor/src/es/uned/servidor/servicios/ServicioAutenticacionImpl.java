package es.uned.servidor.servicios;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.rmi.ControladorRegistro;
import es.uned.common.servicios.AbstractServicio;
import es.uned.common.servicios.ServicioAutenticacionInterface;
import es.uned.common.servicios.exception.AutenticacionExcepcion;
import es.uned.common.servicios.exception.UsuarioNoExisteException;

/**
 * Implementación del servicio de autenticación. Utiliza la inyección de
 * dependencias de un registro capaz de localizar al servicio remoto.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final public class ServicioAutenticacionImpl extends AbstractServicio implements ServicioAutenticacionInterface {
	/**
	 * Mapa de sesiones activas. Relacionará un ID de sesión (número aleatorio
	 * seguro) con un nick de usuario
	 */
	private Map<Integer, String> sesiones;

	/**
	 * Controlador del registro capaz de buscar el servicio de datos remoto
	 */
	private ControladorRegistro c;

	/**
	 * Controlador de datos con el que este servicio interactuará. Se añade mediante
	 * inyección de dependencias.
	 */
	private BasededatosInterface data;

	/**
	 * Clase auxiliar para generar los números de sesión.
	 */
	private SecureRandom s;

	public ServicioAutenticacionImpl(ControladorRegistro c) {
		super();

		this.c = c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _iniciar() {
		try {
			this.data = (BasededatosInterface) this.c.buscarObjeto(BasededatosInterface.class);

			if (this.data == null) {
				return false;
			}

			this.sesiones = new HashMap<>();
			this.s = new SecureRandom();
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _parar() {
		this.sesiones = new HashMap<>();

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean estaAutenticado(Integer session) {
		return this.sesiones.containsKey(session);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DatosUsuarioInterface getDatosSesion(Integer sesion) throws RemoteException {
		String user = this.getNickSesion(sesion);

		if (user == null) {
			return null;
		}

		return this.data.getUsuario(user);
	}

	/**
	 * Devuelve el nick de usuario asociado a una sesión, si existe
	 * 
	 * @return Nick del usuario
	 */
	protected String getNickSesion(Integer sesion) {
		if (!this.estaAutenticado(sesion)) {
			return null;
		}

		return this.sesiones.get(sesion);
	}

	/**
	 * Genera una sesión para un usuario dado y devuelve su ID.
	 * 
	 * @param usuario Nombre del usuario
	 * @return ID de sesión
	 */
	protected int generarSesion(String usuario) {
		int i;

		do {
			i = this.s.nextInt();
		} while (this.sesiones.containsKey(i));

		this.sesiones.put(i, usuario);
		return i;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int autenticar(String usuario, String password)
			throws RemoteException, UsuarioNoExisteException, AutenticacionExcepcion {
		DatosUsuarioInterface u = this.data.getUsuario(usuario);

		if (u == null) {
			throw new UsuarioNoExisteException("El usuario " + usuario + " no existe.");
		}

		if (!u.getPassword().equals(password)) {
			throw new AutenticacionExcepcion("Contraseña incorrecta");
		}

		this.data.addConectado(usuario);
		return this.generarSesion(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean salir(int sesion) throws RemoteException {
		if (!this.estaAutenticado(sesion)) {
			return false;
		}

		this.data.removeConectado(this.getNickSesion(sesion));
		this.sesiones.remove(sesion);
		return true;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean registrar(DatosUsuarioInterface datos) throws AutenticacionExcepcion, RemoteException {
		if (this.data.getTrinosUsuario(datos.getNick())) {
			throw new AutenticacionExcepcion("Ese usuario ya está registrado");
		}

		return this.data.addUsuario(datos) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUsuariosIdentificados() {
		return new ArrayList<>(this.sesiones.values());
	}
}
