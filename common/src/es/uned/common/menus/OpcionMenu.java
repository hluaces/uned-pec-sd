package es.uned.common.menus;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import es.uned.common.menu.exception.ParametroCallbackNoValido;

import java.util.Scanner;

/**
 * Clase que representa una opción de un menú.
 * 
 * En dicho servicio la opción es sin duda la clase más compleja. Las opciones
 * tienen un texto que se muestra por pantalla para identificarlas, un número
 * que las identifica para activarlas, así como una función de retorno (o
 * "callback") que será ejecutada cuando la opción se active.
 * 
 * Adicionalmente, las opciones permiten a su vez añadir un conjunto de
 * parámetros arbitrarios que le serán solicitados al usuario cuando éste la
 * active. Este comportamiento es opcional.
 * 
 * Por último, las opciones pueden considerarse "finales", es decir, que el menú
 * que la invocó saldrá de su bucle principal una vez termine la ejecución del
 * callback de la opción final.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final class OpcionMenu implements OpcionMenuInterface {
	/**
	 * Clase que sirve para identificar por pantalla la opción
	 */
	protected String textoOpcion;

	/**
	 * Número asociado a la opción que se usará para activarla en un menú
	 */
	protected int numeroOpcion;

	/**
	 * Callback que se ejecutará cuando la opción se active
	 */
	protected CallbackOpcionInterface callback;

	/**
	 * Stream de salida que se usará para mostrar texto al usuario
	 */
	protected PrintStream out;

	/**
	 * Determina si la opción es final o no
	 */
	protected boolean isFinal;

	/**
	 * Scanner usado para solicitar al usuario más datos
	 */
	protected Scanner scanner;

	protected Map<String, ParametroOpcionInterface> parametros;

	/**
	 * Crea una nueva opción de menú
	 * 
	 * @param out Stream de salida usado por la clase
	 * @param s   Scanner usado por la calse
	 */
	public OpcionMenu(PrintStream out, Scanner s) {
		super();

		this.out = out;
		this.scanner = s;
		this.parametros = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParametroOpcionInterface addParametro(String nombre, String mensaje, ParametroOpcionValidadorInterface p) {
		ParametroOpcion param = new ParametroOpcion(nombre, mensaje, p);

		this.parametros.put(nombre, param);
		return param;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParametroOpcionInterface getParametro(String nombre) {
		return this.parametros.get(nombre);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mostrar() {
		this.out.println(this.numeroOpcion + " - " + this.textoOpcion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean activar(int numeroOpcion) {
		// TODO: lanzar excepción si callback no existe
		if (numeroOpcion != this.numeroOpcion) {
			return false;
		}

		for (Entry<String, ParametroOpcionInterface> p : this.parametros.entrySet()) {
			this.out.print(p.getValue().getMensaje());

			String data = this.scanner.nextLine();

			try {
				p.getValue().setDato(data);
			} catch (ParametroCallbackNoValido e) {
				this.out.println(e.getMessage());
				return true;
			}
		}

		return callback.ejecutar(this.parametros, this.out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpcionMenuInterface setTextoOpcion(String textoOpcion) {
		this.textoOpcion = textoOpcion;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpcionMenuInterface setNumeroOpcion(int numeroOpcion) {
		this.numeroOpcion = numeroOpcion;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpcionMenuInterface setCallback(CallbackOpcionInterface callback) {
		this.callback = callback;
		return this;
	}

	@Override
	public OpcionMenuInterface setOut(PrintStream out) {
		this.out = out;

		return this;
	}

	@Override
	public OpcionMenuInterface setFinal(boolean b) {
		this.isFinal = b;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFinal() {
		return this.isFinal;
	}
}
