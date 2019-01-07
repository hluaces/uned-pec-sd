package es.uned.menus;

import java.io.PrintStream;
import java.util.Map;

/**
 * Callback utilizado por las opciones de un menú cuando se ejecutan con
 * parámetros.
 * 
 * Es principalmente una interfaz sin implementación que permite encapsular
 * funciones arbitrarias en clases anónimas, manteniendo así una fuerte cohesión
 * sin sacrificar flexibilidad ni conveniencia a la hora de programar
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface CallbackOpcionInterface {
	/**
	 * Ejecuta el callback asociado a este usuario.
	 * 
	 * @param parametros Los parámetros pasados a este callback, si existen
	 * @param out        El stream de salida que se ha de usar para mostrar
	 *                   información por pantalla
	 * @return true si la ejecución tuvo éxito
	 */
	public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out);
}
