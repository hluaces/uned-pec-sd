package common;

/**
 * Interfaz de un Trino creada para mantener la orientación a objetos.
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
