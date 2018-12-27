/**
 * 
 */
package servicios;

/**
 * Clase encargada de generar instancias de los principales servicios.
 * 
 * Puesto que éstos son clases "package-protected" no pueden ser instanciadas de
 * otro forma que no sea pasando por aquí, consiguiendo así un único punto de
 * entrada que ayuda a esconder los detalles de implementación de cada clase.
 * 
 * @author Héctor Luaces Novo <hector@raiolanetworks.es>
 */
public final class ServiciosFactory {
	public static ServicioGestorInterface crearGestor() {
		return new ServicioGestorImpl();
	}

	public static ServicioDatosInterface crearDatos() {
		return new ServicioDatosImpl();
	}

	public static ServicioAutenticacionInterface crearAutenticacion() {
		return new ServicioAutenticacionImpl();
	}

	public static CallbackUsuarioInterface crearCallback() {
		return new CallbackUsuarioImpl();
	}
}
