package common;

/**
 * Interfaz que encapsula una abstracción del conjunto de datos asociados a un
 * usuario
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface DatosUsuarioInterface {
	/**
	 * Devuelve el nombre real de un usuario
	 * 
	 * @return El nombre del usuario
	 */
	public String getNombre();

	/**
	 * Establece el nombre real del usuario
	 * 
	 * @param nombre Nombre que queremos establecer al usuario
	 */
	public void setNombre(String nombre);

	/**
	 * Devuelve el password del usuario tras haber sido sometido a un cifrado
	 * irreversible.
	 * 
	 * @return El password cifrado del usuario
	 */
	public String getPassword();

	/**
	 * Establece el password del usuario al pasado como parámetro. Éste será cifrado
	 * de forma irreversible.
	 * 
	 * @param password Password que queremos establecer para el usuario
	 */
	public void setPassword(String password);

	/**
	 * Devuelve el nick del usuario.
	 * 
	 * @return Nick del usuario
	 */
	public String getNick();

	/**
	 * Establece el nick del usuario
	 * 
	 * @param nick Nuevo nick del usuario
	 */
	public void setNick(String nick);
}
