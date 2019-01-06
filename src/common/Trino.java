package common;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase trino proporcionada por el equipo docente.
 * 
 * Los únicos cambios realizados respecto a la clase original son la
 * documentación y la interfaz TrinoInterface, en aras de mantener la cohesión
 * con el resto de la programación de la aplicación.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class Trino implements Serializable, TrinoInterface {

	private static final long serialVersionUID = 1L;
	private String trino;
	private String nickPropietario; // Ojo no pueden haber varios usuarios con el mismo nick
	private long timestamp; // momento en el que se produce el evento (tiempo en el servidor)

	public Trino(String trino, String nickPropietario) {
		this.trino = trino;
		this.nickPropietario = nickPropietario;
		Date date = new Date();
		this.timestamp = date.getTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ObtenerTrino() {
		return (trino);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ObtenerNickPropietario() {
		return (nickPropietario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long ObtenerTimestamp() {
		return (timestamp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return (getClass().getName() + "@" + trino + "|" + nickPropietario + "|" + timestamp + "|");
	}
}