package es.uned.common.servicios;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Interfaz del servicio gestor.
 * 
 * Dicho servicio se encarga de gestionar la lógica de negocio de la aplicación:
 * envíos de trinos, seguidores, trinos a seguidores, etc.
 * 
 * Para su correcto funcionamiento necesita tener acceso a una implementación
 * del controlador de base de datos, el cual obtiene solicitando al registro RMI
 * una implementación de su interfaz.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface ServicioGestorInterface extends ServicioInterface {
	/**
	 * Devuelve la lista de usuarios del sistema
	 * 
	 * @return List<String> lista de nicks de usuario del sistema
	 * @throws RemoteException si ocurre algún error en la comunicación
	 */
	public List<String> getUsuarios() throws RemoteException;

	/**
	 * Envía un trino para un usuario logeado.
	 * 
	 * Esta función se encarga de gestionar toda la lógica asociada a tal evento,
	 * como almacenar trinos desconectados y notificar a los seguidores.
	 * 
	 * @param sesion Sesión del usuario logeado.
	 * @param trino  Trino a enviar
	 * @return boolean
	 * @throws RemoteException Si ocurren errores de comunicación
	 */
	public boolean enviarTrino(String nick, String trino) throws RemoteException;

	/**
	 * Añade un seguidor a un usuario
	 * 
	 * @param nick  Nick del usuario que va a seguir a alguien
	 * @param trino Nuevo nick del seguidor
	 * @return true si tiene éxito
	 * @throws RemoteException si hay fallos de comunicación
	 */
	public boolean addSeguidor(String nick, String seguidor) throws RemoteException;

	/**
	 * Elimina un seguidor de un usuario
	 * 
	 * @param nick     Nick del usuario al que queremos quitar el seguidor
	 * @param seguidor Nombre del seguidor a añadir
	 * @return true si tiene éxito
	 * @throws RemoteException Si hay fallos de comunicación
	 */
	public boolean removeSeguidor(String nick, String seguidor) throws RemoteException;

	/**
	 * Devuelve la lista de seguidores de un usuario.
	 * 
	 * @param nick Nick del usuario del que queremos obtener los seguidores
	 * @return Set de nicks de seguidores
	 * @throws RemoteException Si hay errores de comunicación
	 */
	public Set<String> getSeguidores(String nick) throws RemoteException;

	/**
	 * Devuelve un set de los usuarios que otro está siguiendo
	 * 
	 * @param nick Nick del usuario del que queremos conocer el set de usuarios
	 *             seguidos
	 * @return Set de usuarios seguidos
	 * @throws RemoteException Si hay errores de comunicación
	 */
	public Set<String> getSeguidos(String nick) throws RemoteException;
}
