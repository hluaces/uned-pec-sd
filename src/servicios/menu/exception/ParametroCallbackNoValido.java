package servicios.menu.exception;

/**
 * Error disparado cuando se intenta validar un callback con un valor inválido
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final public class ParametroCallbackNoValido extends Exception {
	private static final long serialVersionUID = 1L;

	public ParametroCallbackNoValido(String msg) {
		super(msg);
	}
}
