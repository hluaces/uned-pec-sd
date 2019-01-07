package es.uned.menus;

import es.uned.servicios.exception.ServicioYaIniciadoException;

/**
 * Interfaz para los menús: mecanismos que se asocian a un controlador y
 * permiten al usuario interactuar con ellos.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
interface MenuInterface {
	/**
	 * Añade una opción al menú. El propio servicio se encargará intermante de
	 * configurarla acorde a las necesidades pasadas.
	 * 
	 * Es habitual utilizar clases anónimas como callback para evitar crear muchas
	 * clases concretas especializadas
	 * 
	 * @param textoOpcion Texto que se mostrará al visualizar la opción por pantalla
	 * @param callback    Callback que se ejecutará al activar la opción
	 * @return La opción recién creada
	 * @throws ServicioYaIniciadoException Si se intenta añadir una opción a un menú
	 *                                     ya iniciado
	 */
	OpcionMenuInterface addOpcion(String textoOpcion, CallbackOpcionInterface callback)
			throws ServicioYaIniciadoException;

	/**
	 * Muestra el menú por pantalla
	 */
	public void mostrar();

}