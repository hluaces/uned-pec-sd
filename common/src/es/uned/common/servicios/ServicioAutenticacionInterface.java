package es.uned.common.servicios;

import java.rmi.RemoteException;
import java.util.List;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.servicios.exception.AutenticacionExcepcion;
import es.uned.common.servicios.exception.UsuarioNoExisteException;

/**
 * Interfaz para los servicios de autenticación, que serán responsables de
 * registrar, logear y deslogear a los clientes de la herramienta.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface ServicioAutenticacionInterface extends ServicioInterface {
	/**
	 * Determina si la sesión pasada como parámetro está identificada en el sistema
	 * 
	 * @param session Id de sesion
	 * @return boolean
	 */
	public boolean estaAutenticado(Integer session);

	/**
	 * Intenta identificar a un usuario. Si lo consigue, devolverá el ID de sesión
	 * generado.
	 * 
	 * @param usuario  Usuario a identificar
	 * @param password Contraseña del usuario
	 * @return ID del usuario
	 * @throws RemoteException          Si el servicio de base de datos no responde
	 * @throws UsuarioNoExisteException Si el usuario no existe
	 * @throws AutenticacionExcepcion   Si la contraseña no es correcta
	 */
	public int autenticar(String usuario, String password)
			throws RemoteException, UsuarioNoExisteException, AutenticacionExcepcion;

	/**
	 * Cierra una sesión pasada como parámetro.
	 * 
	 * @param sesion ID de la sesión a cerrar
	 * @return True si tiene éxito
	 * @throws RemoteException Si ocurren errores en la conexión con la base de
	 *                         datos
	 */
	public boolean salir(int sesion) throws RemoteException;

	/**
	 * Intenga registrar un usuario en el sistema.
	 * 
	 * De tener éxito se almacenarán todos sus datos en la base de datos.
	 * 
	 * Registrar un usuario no lo logea en el sistema.
	 * 
	 * @param datos Información del usuario a registrar
	 * @return True si tiene éxito
	 * @throws AutenticacionExcepcion Si el usuario ya existe
	 * @throws RemoteException        Si hay problemas de conexión con la base de
	 *                                datos
	 */
	public boolean registrar(DatosUsuarioInterface datos) throws AutenticacionExcepcion, RemoteException;

	/**
	 * Dado un ID de sesión, devuelve los datos del usuario asociado a la misma, o
	 * null
	 * 
	 * @param sesion ID de sesión para el que queremos los datos
	 * @return Datos del usuario asociado a ese ID de sesión
	 * @throws RemoteException Si hay errores de comunicación con la base de datos
	 */
	public DatosUsuarioInterface getDatosSesion(Integer sesion) throws RemoteException;

	/**
	 * Devuelve una lista con los usuarios que tienen una sesión (que no tienen que
	 * ser lo mismo que "conectados", puesto que una sesión puede dejarse olvidada).
	 * 
	 * @return Lista ade usuarios
	 */
	public List<String> getUsuariosIdentificados();
}
