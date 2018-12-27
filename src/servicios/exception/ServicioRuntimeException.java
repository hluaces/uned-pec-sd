package servicios.exception;

/**
 * Excepción lanzada por los servicios en errores en tiempo de ejecución
 * arbitrarios
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class ServicioRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ServicioRuntimeException(String message) {
		super(message);
	}
}
