package servicios.menu;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import servicios.AbstractServicio;
import servicios.EstadoServicio;
import servicios.exception.ServicioYaIniciadoException;

/**
 * Este servicio auxiliar permite mostrar menús que ejecutarán callbacks
 * previamente definidos.
 * 
 * Se usará para que los controladores puedan definir uno y ofrecer así algún
 * mecanismo de interacción con el mismo al usuario.
 * 
 * Los menús tienen asociados un conjunto de opciones y ofrecerán al usuario los
 * mecanismos necesarios para interactuar con ellas.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public class ServicioMenu extends AbstractServicio implements ServicioMenuInterface {
	/**
	 * La clase que se usará para recibir eventos de teclado
	 */
	private Scanner scanner;

	/**
	 * El conjunto de opciones asociado a este menú
	 */
	private List<OpcionMenuInterface> opciones;

	/**
	 * El stream de salida que se usará para mostrar los mensajes del menú
	 */
	private PrintStream out;

	public ServicioMenu(PrintStream out) {
		super();
		this.out = out;
		this.opciones = new ArrayList<>();
		this.scanner = new Scanner(System.in);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _iniciar() {
		return true;
	}

	@Override
	public boolean iniciar() throws ServicioYaIniciadoException {
		boolean parar = false;
		boolean activada = false;

		if (!super.iniciar()) {
			return false;
		}

		this.mostrarOpciones();

		// Bucle principal del servicio
		while (!parar) {
			try {
				activada = false;
				int opcion = this.scanner.nextInt();

				// Para eliminar el '\n\r' restante
				this.scanner.nextLine();

				// Escaneamos una opción y buscamos entre las asociadas al menú
				// cual corresponde con ese número
				for (OpcionMenuInterface o : this.opciones) {
					if (!o.activar(opcion)) {
						continue;
					}

					// Hemos activado una opción
					activada = true;

					// Si la opción es final se detendrá el menú
					parar = o.isFinal();
					break;
				}

				if (!activada) {
					this.mostrarMensajeFallo();
				}

				if (parar) {
					break;
				}
			} catch (InputMismatchException e) {
				this.mostrarMensajeFallo();
			} finally {
				if (!parar) {
					this.mostrarOpciones();
				}
			}
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _parar() {
		this.scanner.close();
		this.scanner = null;
		this.opciones = new ArrayList<>();

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpcionMenuInterface addOpcion(String textoOpcion, CallbackOpcionInterface callback)
			throws ServicioYaIniciadoException {
		if (this.getEstado() == EstadoServicio.EN_EJECUCION) {
			throw new ServicioYaIniciadoException("No se pueden añadir opciones en un servicio de menú no iniciado");
		}

		OpcionMenuInterface o = new OpcionMenu(this.out, this.scanner).setNumeroOpcion(this.opciones.size() + 1)
				.setTextoOpcion(textoOpcion).setCallback(callback);

		this.opciones.add(o);
		return o;
	}

	/**
	 * Método auxiliar, sobrecargable, que se llama para mostrar un mensaje en el
	 * stream de salida que indica que se ha elegido una opción incorrecta
	 */
	protected void mostrarMensajeFallo() {
		this.out.println("Opción no encontrada");
	}

	/**
	 * Muestra todas las opciones del menú con sus teclas de acceso
	 */
	protected void mostrarOpciones() {
		for (OpcionMenuInterface o : this.opciones) {
			o.mostrar();
		}
	}

	public static void main(String[] argc) {
		ServicioMenuInterface s = new ServicioMenu(System.out);

		try {
			s.addOpcion("Imprime 'hola mundo'", new CallbackOpcionInterface() {
				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					out.println("HOLA MUNDO");
					return true;
				}
			});
			s.addOpcion("Imprime 'asd'", new CallbackOpcionInterface() {

				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					out.println("asd");
					return true;
				}
			});
			s.addOpcion("Salir", new CallbackOpcionInterface() {

				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					out.println("Adios!!!");
					return true;
				}

			}).setFinal(true);

			s.iniciar();
		} catch (ServicioYaIniciadoException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Fin de programa");
		}

	}
}
