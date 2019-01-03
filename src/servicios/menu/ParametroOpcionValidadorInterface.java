package servicios.menu;

import servicios.menu.exception.ParametroCallbackNoValido;

/**
 * Las opciones pueden tener asociado un validador que se usará para determinar
 * si el valor introducido es correcto.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 * @see ParametroOpcion
 */
public interface ParametroOpcionValidadorInterface {
	public boolean validar(Object dato) throws ParametroCallbackNoValido;

	public String getMensajeError();
}
