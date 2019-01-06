package menus;

import servicios.menu.exception.ParametroCallbackNoValido;

/**
 * Las opciones pueden tener asociado un validador que se usará para determinar
 * si el valor introducido es correcto.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 * @see ParametroOpcion
 */
public interface ParametroOpcionValidadorInterface {
	/**
	 * Valida un dato arbitrario pasado al validador de parámetros
	 * 
	 * @param dato Datoa a validar
	 * @return true si es válido, false en caso contrario
	 * @throws ParametroCallbackNoValido si el parámetro no es válido
	 */
	public boolean validar(Object dato) throws ParametroCallbackNoValido;

	/**
	 * Devuelve el mensaje de error que describe por qué el dato no es válido.
	 * 
	 * @return String
	 */
	public String getMensajeError();
}
