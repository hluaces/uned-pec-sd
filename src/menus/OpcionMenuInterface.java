package menus;

import java.io.PrintStream;

/**
 * Interfaz que identifica a una opción para un servicio de menú
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface OpcionMenuInterface {

	/**
	 * Muestra la opción por pantalla.
	 * 
	 * Lo ideal es mostrar tanto la tecla de activación como un texto que
	 * identifique a la misma.
	 */
	public void mostrar();

	/**
	 * Intenta activar la opción pasándole un número que puede o no identificarla.
	 * 
	 * El método ha de activar el callback si el número la identifica.
	 * 
	 * @param numeroOpcion Número de opción que se intenta activar
	 * @return resultado del callback (true = exito)
	 */
	public boolean activar(int numeroOpcion);

	/**
	 * Setter "fluent" que establece el texto identificativo de la opción.
	 * 
	 * @return self
	 */
	public OpcionMenuInterface setTextoOpcion(String textoOpcion);

	/**
	 * Setter "fluent" que establece el número que activará la opción
	 * 
	 * @return self
	 */
	public OpcionMenuInterface setNumeroOpcion(int numeroOpcion);

	/**
	 * Setter "fluent" que establece el callback que será llamado cuando la opción
	 * se active
	 * 
	 * @return self
	 */
	public OpcionMenuInterface setCallback(CallbackOpcionInterface callback);

	/**
	 * Setter "fluent" que establece el stream de salida usado por la opción para
	 * mostrar texto
	 * 
	 * @return self
	 */
	public OpcionMenuInterface setOut(PrintStream out);

	/**
	 * Setter "fluent" que establece si la opción es final o no.
	 * 
	 * Un servicio de menú debe abortar su bucle principal tras ejecutar una opción
	 * final.
	 * 
	 * @return self
	 */
	public OpcionMenuInterface setFinal(boolean b);

	/**
	 * Adder "fluent" que añade un parámetro a la opción y devuelve la misma opción.
	 * 
	 * @param mensaje Mensaje que se mostrará para pedir el parámetro
	 * @param p       Validador del parámetro a añadir
	 * 
	 * @return Parámetro recién añadido
	 */
	public ParametroOpcionInterface addParametro(String name, String mensaje, ParametroOpcionValidadorInterface p);

	/**
	 * Getter que devuelve un parámetro cuyo nombre pasamos como parámetro, o null.
	 * 
	 * @param name Nombre del parámetro
	 * @return Parámetro o null
	 */
	public ParametroOpcionInterface getParametro(String name);

	/**
	 * Determina si la opción es final o no.
	 * 
	 * Un servicio de menú debe abortar su bucle principal tras ejecutar una opción
	 * final.
	 * 
	 * @return self
	 */
	public boolean isFinal();

}