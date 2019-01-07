package es.uned.controladores;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uned.menus.Menu;
import es.uned.servicios.ServicioInterface;
import es.uned.servicios.exception.ServicioYaIniciadoException;

/**
 * Controlador abstracto que encapsula la mayoría de funcionalidad básica que
 * será reutilizada por cada una de las implementaciones específicas.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public abstract class AbstractControlador implements ControladorInterface {
	/**
	 * Agrupa todos los servicios asociados a este controlador asociándole un nombre
	 * arbitrario a cada uno.
	 */
	protected Map<String, ServicioInterface> servicios;

	/**
	 * El menú asociado a este controlador y que permitirá al usuario interactuar
	 * con él.
	 */
	protected Menu menu;

	/**
	 * El stream de salida que se usará si el controlador necesita avisar de algo al
	 * usuario.
	 */
	protected PrintStream out;

	public AbstractControlador() {
		super();

		this.servicios = new HashMap<>();
	}

	public AbstractControlador(PrintStream out) {
		this();

		this.out = out;
	}

	@Override
	public boolean iniciarServicios() {
		for (ServicioInterface s : this.servicios.values()) {
			try {
				if (!s.iniciar()) {
					return false;
				}
			} catch (ServicioYaIniciadoException e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Añade un servicio a los usados por el controlador
	 * 
	 * @param s Servicio a añadir
	 * @return boolean
	 */
	protected boolean addServicio(String nombre, ServicioInterface s) {
		return this.servicios.put(nombre, s) != null;
	}

	/**
	 * Añade un menú a este controlador
	 * 
	 * @param s Servicio del menú a añadir
	 * @return boolean
	 */
	protected boolean addMenu(Menu s) {
		this.menu = s;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mostrarMenu() {
		this.menu.mostrar();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ServicioInterface> getServicios() {
		return (List<ServicioInterface>) this.servicios.values();
	}

	/**
	 * Devuelve un servicio asociado a una clase
	 * 
	 * @return El servicio asociado, si existe
	 */
	protected ServicioInterface getServicio(String s) {
		return this.servicios.get(s);
	}
}
