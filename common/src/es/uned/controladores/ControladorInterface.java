package es.uned.controladores;

import java.util.List;

import es.uned.servicios.ServicioInterface;

/**
 * Interfaz que expone las funciones públicas de todos los controladores.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface ControladorInterface {

	/**
	 * Devuelve los servicios disponibles para el controlador
	 * 
	 * @return
	 */
	public List<ServicioInterface> getServicios();

	/**
	 * Inicia todos los servicios asociados al controlador
	 * 
	 * @return boolean
	 */
	public boolean iniciarServicios();

	/**
	 * Muestra por pantalla el menú asociado al controlador.
	 */
	public void mostrarMenu();
}
