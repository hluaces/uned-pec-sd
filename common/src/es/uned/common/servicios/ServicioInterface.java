package es.uned.common.servicios;

import es.uned.common.servicios.exception.ServicioNoIniciadoException;
import es.uned.common.servicios.exception.ServicioYaIniciadoException;

/**
 * Interfaz para los distintos servicios que serán utilizados por cada uno de
 * los controladores
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface ServicioInterface {
	/**
	 * Inicia el servicio
	 * 
	 * @return true si tiene éxito, false en caso contrario
	 * @throws ServicioYaIniciadoException
	 */
	public boolean iniciar() throws ServicioYaIniciadoException;

	/**
	 * Detiene el servicio (la información no tiene por qué mantenerse)
	 * 
	 * @return true si tiene éxito, false en caso contrario
	 * @throws ServicioNoIniciadoException
	 */
	public boolean parar() throws ServicioNoIniciadoException;

	/**
	 * Devuelve el estado del servicio
	 * 
	 * @return
	 */
	public EnumEstadoServicio getEstado();
}
