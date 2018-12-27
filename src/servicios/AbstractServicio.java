package servicios;

import servicios.exception.ServicioNoIniciadoException;
import servicios.exception.ServicioRuntimeException;
import servicios.exception.ServicioYaIniciadoException;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public abstract class AbstractServicio implements ServicioInterface {
	protected EstadoServicio status;

	public AbstractServicio() {
		super();
		this.status = EstadoServicio.SIN_INICIAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EstadoServicio getEstado() {
		return this.status;
	}

	/**
	 * Función protegida que ejecuta el código de inicio del servicio
	 * 
	 * @return boolean
	 */
	protected boolean _iniciar() {
		throw new ServicioRuntimeException("Aún no implementado");
	}

	/**
	 * Función privada que ejecuta el código de parada del servicio
	 * 
	 * @return boolean
	 */
	protected boolean _parar() {
		throw new ServicioRuntimeException("Aún no implementado");
	}

	/**
	 * Inicia el servicio
	 * 
	 * @return true si tiene éxito, false en caso contrario
	 * @throws ServicioYaIniciadoException
	 */
	@Override
	public boolean iniciar() throws ServicioYaIniciadoException {
		if (this.status == EstadoServicio.EN_EJECUCION) {
			throw new ServicioYaIniciadoException("");
		}

		try {
			if (!this._iniciar()) {
				return false;
			}

			this.status = EstadoServicio.EN_EJECUCION;
		} catch (ServicioRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServicioRuntimeException(e.getMessage());
		}

		return true;
	}

	/**
	 * Detiene el servicio (la información no tiene por qué mantenerse)
	 * 
	 * @return true si tiene éxito, false en caso contrario
	 * @throws ServicioNoIniciadoException
	 */
	@Override
	public boolean parar() throws ServicioNoIniciadoException {
		if (this.status == EstadoServicio.DETENIDO || this.status == EstadoServicio.SIN_INICIAR) {
			throw new ServicioNoIniciadoException("");
		}

		try {
			if (!this._parar()) {
				return false;
			}

			this.status = EstadoServicio.DETENIDO;
		} catch (ServicioRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServicioRuntimeException(e.getMessage());
		}
		return true;
	}
}
