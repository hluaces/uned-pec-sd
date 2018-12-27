package controladores;

import java.util.List;

import servicios.ServicioInterface;

/**
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
}
