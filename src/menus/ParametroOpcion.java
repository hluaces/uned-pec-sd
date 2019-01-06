package menus;

import java.util.ArrayList;
import java.util.List;

import servicios.menu.exception.ParametroCallbackNoValido;

/**
 * Implementación de ParametroCallbackOpcionInterface
 * 
 * @see ParametroOpcionInterface
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final class ParametroOpcion implements ParametroOpcionInterface {
	/**
	 * Nombre del parámetro
	 */
	private String name;

	/**
	 * El valor que guardará este parámetro
	 */
	private Object dato;

	/**
	 * El mensaje que se mostrará al usuario para solicitar el dato
	 */
	private String mensaje;

	/**
	 * El objeto que validará el valor introducido
	 */
	private List<ParametroOpcionValidadorInterface> validadores;

	public ParametroOpcion(String name) {
		super();

		this.name = name;
		this.mensaje = "Introduce un valor para '" + this.getClass().getName() + ": ";
		this.validadores = new ArrayList<>();
	}

	public ParametroOpcion(String name, String mensaje, ParametroOpcionValidadorInterface validador) {
		this(name);

		this.mensaje = mensaje;
		this.validadores.add(validador);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getDato() {
		return this.dato;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMensaje() {
		return this.mensaje;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParametroOpcionInterface setDato(Object dato) throws ParametroCallbackNoValido {
		this.validar(dato);
		this.dato = dato;

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validar(Object dato) throws ParametroCallbackNoValido {
		for (ParametroOpcionValidadorInterface i : this.validadores) {
			if (!i.validar(dato)) {
				return false;
			}
		}
		return true;
	}
}
