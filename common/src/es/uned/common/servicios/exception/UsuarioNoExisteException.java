/**
 * 
 */
package es.uned.common.servicios.exception;

/**
 * Excepción lanzada cuando se intenta operar sobre un usuario que no existe.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class UsuarioNoExisteException extends Exception {
	private static final long serialVersionUID = 1L;
	private String nick;

	/**
	 * Crea una nueva excepción asociándole el nick del usuario que lanzó el error
	 * 
	 * @param nick nick del usuario que lanzó el error
	 */
	public UsuarioNoExisteException(String nick) {
		super("No existe ningún usuario con el nick '" + nick + "'.");

		this.nick = nick;
	}

	/**
	 * Devuelve el nick del usuario que lanzó el error
	 * 
	 * @return el nick del usuario que lanzó el error
	 */
	public String getNick() {
		return this.nick;
	}

}
