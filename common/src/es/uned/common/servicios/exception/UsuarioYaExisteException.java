package es.uned.common.servicios.exception;

/**
 * Excepción lanzada cuando se intenta crear un usuario que ya existe.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class UsuarioYaExisteException extends Exception {
	private String nick;
	private static final long serialVersionUID = 1L;

	/**
	 * Genera una nueva excepción de este tipo asignándole el nick del usuario que
	 * causó el problema
	 * 
	 * @param nick nick del usuario que causó el problema
	 */
	public UsuarioYaExisteException(String nick) {
		super("Ya existe un usuario con el nick '" + nick + "'.");
		this.nick = nick;
	}

	/**
	 * Devuelve el nick del usuario que causó el error
	 * 
	 * @return nick del usuario que causó el problema
	 */
	public String getNick() {
		return nick;
	}
}
