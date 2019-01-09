package es.uned.common.servicios.exception;

/**
 * Excepción para lanzar en errores de identificación y registro.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final public class AutenticacionExcepcion extends Exception {
	private static final long serialVersionUID = -7108766667134652032L;

	public AutenticacionExcepcion(String s) {
		super(s);
	}
}
