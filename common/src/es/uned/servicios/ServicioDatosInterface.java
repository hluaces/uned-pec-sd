package es.uned.servicios;

import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.TrinoInterface;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface ServicioDatosInterface extends ServicioInterface {
	/**
	 * Determina si un usuario está conectado
	 * 
	 * @param nick nick del usuario a comprobar
	 * @return true si está conectado, false en caso contrario
	 */
	public boolean isConectado(String nick);

	/**
	 * Añade un nick de usuario a la lista de conectados
	 * 
	 * @param nick nick del usuario a comprobar
	 * @return true si está conectado, falso en caso contrario
	 */
	public boolean addConectado(String nick);

	/**
	 * Elimina un nick de usuario de la lista de conectados
	 * 
	 * @param nick usuario a eliminar de la lista de conectados
	 * @return true si se elimina correctamente, falso en caso contrario
	 */
	public boolean removeConectado(String nick);

	/**
	 * Devuelve una lista con los nicks de todos los usuarios conectados
	 * 
	 * @return lista de todos los usuarios conectados
	 */
	public Set<String> getConectados();

	/**
	 * Intenta crear un nuevo usuario a partir de una clase de DatosUsuario.
	 * 
	 * @param datos Datos del nuevo usuario a crear
	 * @return Datos del usuario creado
	 */
	public DatosUsuarioInterface addUsuario(DatosUsuarioInterface datos);

	/**
	 * Determina si un usuario con un nick dado ya está registrado
	 * 
	 * @param nick Nick del usuario a comprobar
	 * @return true si ya existe o false si no
	 */
	public boolean hasUsuario(String nick);

	/**
	 * Devuelve los datos de usuario asociados a un nick
	 * 
	 * @param nick Nick del usuario del que queremos los datos
	 * @return Datos de usuario asociados a ese nick, o "null"
	 */
	public DatosUsuarioInterface getUsuario(String nick);

	/**
	 * Elimina un usuario registrado
	 * 
	 * @param nick nick del usuario a borrar
	 * @return Datos del usuario borrado o null, si no había ningún usuario con ese
	 *         nombre
	 */
	public DatosUsuarioInterface removeUsuario(String nick);

	/**
	 * Devuelve un Map con todos los usuarios registrados
	 * 
	 * @return Map que relaciona los nicks con los datos de todos los usuarios
	 *         registrados
	 */
	public Map<String, DatosUsuarioInterface> getUsuarios();

	/**
	 * Añade un nuevo seguidor a un usuario
	 * 
	 * @param seguido  Nick del usuario al que queremos seguir
	 * @param seguidor Nick del usuario que va a seguirle
	 * @return True si tiene éxito
	 */
	public boolean addSeguidor(String seguido, String seguidor);

	/**
	 * Intenta eliminar un seguidor de un usuario
	 * 
	 * @param seguido  Nick del usuario al que el usuario sigue
	 * @param seguidor Nick del seguidor que queremos eliminar
	 * @return booleans
	 */
	public boolean removeSeguidor(String seguido, String seguidor);

	/**
	 * Determina si un usuario es seguidor de otro
	 * 
	 * @param seguido  Nick del usuario para el que queremos comprobar el seguidor
	 * @param seguidor Nick del usuario que queremos comprobar si sigue al anterior
	 * @return true si tiene éxito
	 */
	public boolean esSeguidor(String seguido, String seguidor);

	/**
	 * Devuelve una lista con todos los seguidores de un usuario
	 * 
	 * @param seguido Nick del usuario del que queremos conocer los seguidores
	 * @return Lista con los nombres de los seguidores
	 */
	public Set<String> getSeguidores(String seguido);

	/**
	 * Devuelve una lista con todos los usuarios a los que sigue otro usuario
	 * 
	 * @param seguidor Nick del usuario cuyos seguidos queremos conocer
	 * @return Lista con los seguidos del usuario
	 */
	public Set<String> getSeguidos(String seguidor);

	/**
	 * Devuelve una copia de todos los trinos en la base de datos en forma de Map
	 * cuya key es el nick del propietario.
	 * 
	 * @return Map con todos los trinos de la base de datos
	 */
	public Map<String, List<TrinoInterface>> getTrinos();

	/**
	 * Devuelve una lista con todos los trinos de un usuario cuyo nick pasemos como
	 * parámetro.
	 * 
	 * @param usuario Nick del usuario cuyos trinos queremos
	 * @return Lista de los trinos del usuario
	 */
	public List<TrinoInterface> getTrinosUsuario(String usuario);

	/**
	 * Añade un trino a la base de datos
	 * 
	 * @param trino Trino a añadir
	 * @return true si tiene éxito, false en caso contrario
	 */
	public boolean addTrino(TrinoInterface trino);

	/**
	 * Borra un trino de la base de datos. Si existe más de una copia, solo
	 * eliminará uno.
	 * 
	 * @param trino Trino a eliminar
	 * @return true si tiene éxito, false en caso contrario
	 */
	public boolean removeTrino(TrinoInterface trino);

	/**
	 * Determina si un trino dado existe
	 * 
	 * @param trino El trino a comprobar
	 * @return true si existe, false en caso contrario
	 */
	public boolean hasTrino(TrinoInterface trino);

	/**
	 * Devuelve un trino en base a su texto, si éste existe.
	 * 
	 * En el caso de que haya más de uno que coincida, solo se devolverá uno.
	 * 
	 * @param usuario    Nick del usuario que escribió el trino
	 * @param textoTrino Texto del trino
	 * @return Objeto del trino o null.
	 */
	public TrinoInterface getTrino(String usuario, String textoTrino);

}
