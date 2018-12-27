package servicios.exception;

/**
 * Excepción lanzada al intentar iniciar un servicio ya iniciado
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class ServicioYaIniciadoException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServicioYaIniciadoException(String message) {
		super(message);
	}
}
