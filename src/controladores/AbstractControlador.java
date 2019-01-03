package controladores;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import servicios.ServicioInterface;
import servicios.exception.ServicioYaIniciadoException;
import servicios.menu.ServicioMenu;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public abstract class AbstractControlador implements ControladorInterface {
	protected Map<String, ServicioInterface> servicios;

	protected ServicioMenu menu;

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
	protected boolean addMenu(ServicioMenu s) {
		if (!this.addServicio("menu", s)) {
			return false;
		}

		this.menu = s;
		return true;
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
