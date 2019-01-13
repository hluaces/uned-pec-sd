package es.uned.common.servicios;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz que representa un callback de usuario, que no es más que un
 * mecanismo para poder enviar texto a un usuario desde el servidor.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface CallbackUsuarioInterface extends Remote {
	/**
	 * Envía un texto al terminal del usuario.
	 * 
	 * @param tx Texto a enviar
	 */
	public void write(String tx) throws RemoteException;
}
