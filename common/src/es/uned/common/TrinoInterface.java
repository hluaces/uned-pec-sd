package es.uned.common;

/**
 * Interfaz de un Trino creada para mantener la orientación a objetos a la hora
 * de trabajar con Trinos.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface TrinoInterface {
	public String ObtenerTrino();

	public String ObtenerNickPropietario();

	public long ObtenerTimestamp();

	@Override
	public String toString();
}
