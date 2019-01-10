package es.uned.common.controladores;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.servicios.exception.AutenticacionExcepcion;
import es.uned.common.servicios.exception.UsuarioNoExisteException;

public interface ServidorInterface extends Remote {
	/**
	 * Devuelve los datos de usuario asociados a una sesión.
	 * 
	 * Si devuelve NULL se entiende que el usuario no está identificado.
	 * 
	 * En caso contrario recibirá los datos asociados.
	 * 
	 * @param sesion Sesión para la que queremos conocer los datos
	 * @return Datos del usuario o NUL L
	 * @throws RemoteException        Errores de comunicación
	 * @throws AutenticacionExcepcion En fallos del servicio de autenticación
	 */
	public DatosUsuarioInterface getDatosSesion(Integer sesion) throws RemoteException, AutenticacionExcepcion;

	/**
	 * Identifica a un usuario con su contraseña.
	 * 
	 * Si tiene éxito devuelve el ID de sesión.
	 * 
	 * @param usuario  Usuario a identificar
	 * @param password Su contraseña
	 * @return ID de sesión
	 * @throws UsuarioNoExisteException Si el usuario no existe
	 * @throws AutenticacionExcepcion   En fallos de contraseña o si el servicio de
	 *                                  autenticación no funciona
	 * @throws RemoteException
	 */
	public int autenticar(String usuario, String password)
			throws UsuarioNoExisteException, AutenticacionExcepcion, RemoteException;

	/**
	 * Desconecta a una sesión del sistema.
	 * 
	 * @param sesion ID de sesión a desconectar
	 * @return True si tiene éxito, false en casco contrario
	 * @throws AutenticacionExcepcion Si el servicio de autenticación está caído
	 * @throws RemoteException
	 */
	public boolean salir(int sesion) throws AutenticacionExcepcion, RemoteException;

	/**
	 * Intenta registrar a un usuyario con un conjunto de datos dado.
	 * 
	 * Nótese que esto no inicia sesión.
	 * 
	 * @param nombre   Nombre del usuario
	 * @param nick     Nick del usuario
	 * @param password Contraseña del usuario
	 * @return boolean si tiene éxito
	 * @throws RemoteException
	 * @throws AutenticacionExcepcion En el caso de que el servicio de autenticación
	 *                                esté caído o haya problemas con el proceso
	 */
	public boolean registrar(String nombre, String nick, String password)
			throws AutenticacionExcepcion, RemoteException;

	/**
	 * Determina si un usuario ya está registrado con un nick.
	 * 
	 * @param nick Nick a comprobar
	 * @return boolean
	 * @throws RemoteException        Errores de comunicación
	 * @throws AutenticacionExcepcion Fallos por el servicio de autenticación
	 */
	public boolean isRegistrado(String nick) throws RemoteException, AutenticacionExcepcion;
}
