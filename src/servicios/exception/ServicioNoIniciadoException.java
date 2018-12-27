package servicios.exception;

/**
 * Excepción lanzada cuando un servicio no está iniciado y se requiere que esté
 * en marcha
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class ServicioNoIniciadoException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServicioNoIniciadoException(String mess) {
		super(mess);
	}
}
