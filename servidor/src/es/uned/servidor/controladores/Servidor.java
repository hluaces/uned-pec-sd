package es.uned.servidor.controladores;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uned.common.DatosUsuario;
import es.uned.common.DatosUsuarioInterface;
import es.uned.common.controladores.AbstractControlador;
import es.uned.common.controladores.ServidorInterface;
import es.uned.common.menus.CallbackOpcionInterface;
import es.uned.common.menus.Menu;
import es.uned.common.menus.ParametroOpcionInterface;
import es.uned.common.rmi.ControladorRegistro;
import es.uned.common.servicios.EnumEstadoServicio;
import es.uned.common.servicios.ServicioAutenticacionInterface;
import es.uned.common.servicios.ServicioGestorInterface;
import es.uned.common.servicios.exception.AutenticacionExcepcion;
import es.uned.common.servicios.exception.UsuarioNoExisteException;
import es.uned.servidor.servicios.ServicioAutenticacionImpl;
import es.uned.servidor.servicios.ServicioGestorImpl;

/**
 * La entidad Servidor se encarga de controlar el proceso de autenticación de
 * los usuarios del sistema y gestión de sus mensajes (los cuales vamos a llamar
 * trinos), para ello hace uso de dos servicios:
 * 
 * - ServicioAutenticacion
 * 
 * - ServicioGestor
 * 
 * @author Hector Luaces Novo <hector@luaces-novo.es>
 * @see ServicioAutenticacionImpl
 * @see ServicioGestorImpl
 */
public class Servidor extends AbstractControlador implements ServidorInterface {
	private static String SERVICIO_GESTOR = "GESTOR";
	private static String SERVICIO_AUTH = "AUTH";

	public Servidor(PrintStream out, ControladorRegistro c) {
		super(out);

		this.addServicio(Servidor.SERVICIO_AUTH, new ServicioAutenticacionImpl(c));
		this.addServicio(Servidor.SERVICIO_GESTOR, new ServicioGestorImpl(c));
		this.crearMenu();
	}

	/**
	 * Devuelve el servicio de autenticación asociado al servidor, si existe.
	 * 
	 * @return ServicioAutenticacionInterface o null
	 * @throws AutenticacionExcepcion
	 */
	protected ServicioAutenticacionInterface getServicioAutenticacion() throws AutenticacionExcepcion {
		ServicioAutenticacionInterface a = (ServicioAutenticacionInterface) getServicio(SERVICIO_AUTH);

		if (a == null) {
			throw new AutenticacionExcepcion("El servicio de autenticación no se ha iniciado.");
		}

		return a;
	}

	/**
	 * Devuelve el servicio gestor asociado al servidor, si existe.
	 * 
	 * @return ServicioGestorInterface o null
	 * @throws RemoteException
	 */
	protected ServicioGestorInterface getServicioGestor() throws RemoteException {
		ServicioGestorInterface a = (ServicioGestorInterface) getServicio(SERVICIO_GESTOR);

		if (a == null) {
			throw new RemoteException("El servicio gestor no se ha iniciado.");
		}

		return a;
	}

	/**
	 * Inicializa el menú usado por el controlador.
	 */
	protected void crearMenu() {
		Menu menu = new Menu(this.out);

		menu.addOpcion("Información del Servidor.", new CallbackOpcionInterface() {
			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				out.println("Mostrando identificador RMI");
				out.println(ControladorRegistro.getRmiUri(Servidor.this));
				return true;
			}
		});
		menu.addOpcion("Listar usuarios logeados.", new CallbackOpcionInterface() {
			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					ServicioAutenticacionInterface auth = getServicioAutenticacion();

					if (auth == null || auth.getEstado() != EnumEstadoServicio.EN_EJECUCION) {
						out.println("El servicio de autenticación no está en ejecución");
						return true;
					}

					List<String> usuarios = auth.getUsuariosIdentificados();

					if (usuarios.size() == 0) {
						out.println("No hay usuarios logeados en este momento.");
						return true;
					}

					out.println("Lista de usuarios logeados:");
					usuarios.stream().forEach((user) -> out.println("- " + user));

					return true;
				} catch (AutenticacionExcepcion e) {
					out.println("Ha ocurrido un error de comunicación con el servidor.");
					return false;
				}
			}
		});
		menu.addOpcion("Salir.", new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				out.println("Saliendo del sistema servidor.");
				return true;
			}
		}).setFinal(true);

		this.addMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DatosUsuarioInterface getDatosSesion(Integer sesion) throws RemoteException, AutenticacionExcepcion {
		return this.getServicioAutenticacion().getDatosSesion(sesion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int autenticar(String usuario, String password)
			throws UsuarioNoExisteException, AutenticacionExcepcion, RemoteException {

		return this.getServicioAutenticacion().autenticar(usuario, password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean salir(int sesion) throws AutenticacionExcepcion, RemoteException {
		return this.getServicioAutenticacion().salir(sesion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean registrar(String nombre, String nick, String password)
			throws AutenticacionExcepcion, RemoteException {
		return this.getServicioAutenticacion().registrar(new DatosUsuario(nombre, nick, password));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUsuarios() throws RemoteException {
		return this.getServicioGestor().getUsuarios();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRegistrado(String nick) throws RemoteException, AutenticacionExcepcion {
		return this.getServicioAutenticacion().estaRegistrado(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addTrino(int sesion, String trino) throws RemoteException, AutenticacionExcepcion {
		DatosUsuarioInterface u = this.getDatosSesion(sesion);

		if (u == null) {
			throw new AutenticacionExcepcion("Tu sesión no es válida.");
		}

		return this.getServicioGestor().enviarTrino(u.getNombre(), trino);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addSeguidor(String seguido, int sesion) throws RemoteException, AutenticacionExcepcion {
		DatosUsuarioInterface u = this.getDatosSesion(sesion);

		if (u == null) {
			throw new AutenticacionExcepcion("Tu sesión no es válida.");
		}

		return this.getServicioGestor().addSeguidor(seguido, u.getNick());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeSeguidor(String seguido, int sesion) throws RemoteException, AutenticacionExcepcion {
		DatosUsuarioInterface u = this.getDatosSesion(sesion);

		if (u == null) {
			throw new AutenticacionExcepcion("Tu sesión no es válida.");
		}

		return this.getServicioGestor().removeSeguidor(seguido, u.getNick());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSeguidores(String nick) throws RemoteException {
		return this.getServicioGestor().getSeguidores(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSeguidos(String nick) throws RemoteException {
		return this.getServicioGestor().getSeguidos(nick);
	}

}
