package servicios.menu;

import servicios.menu.exception.ParametroCallbackNoValido;

/**
 * Las opciones de menú pueden tener 0 o más parámetros.
 * 
 * Al ejecutarse el callback, antes de llevarse éste a fruición, se solicitará
 * al usuario que introduzca cada uno de estos parámetros por teclado.
 * 
 * Los parámetros pueden tener un tipo de datos asociados y, adicionalmente, una
 * función de validación.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 *
 * @param <T> tipo de dato que almacenará éste parámetro
 */
public interface ParametroOpcionInterface {

	/**
	 * Devuelve el nombre del parámetro
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Develve el valor actual de la opción
	 * 
	 * @return Object
	 */
	public Object getDato();

	/**
	 * Intenta establecer el valor de la opción a uno pasado como parámetro,
	 * lanzando una excepción si ésta no es válida.
	 * 
	 * @throws ParametroCallbackNoValido
	 * @return self
	 */
	public ParametroOpcionInterface setDato(Object dato) throws ParametroCallbackNoValido;

	/**
	 * Devuelve el mensaje que será mostrado al usuario para presentar los datos que
	 * han de ser introducidos como parámetro
	 * 
	 * @return String
	 */
	public String getMensaje();

	/**
	 * Valida un parámetro arbitrario
	 * 
	 * @param dato
	 * @return
	 * @throws ParametroCallbackNoValido
	 */
	public boolean validar(Object dato) throws ParametroCallbackNoValido;

}