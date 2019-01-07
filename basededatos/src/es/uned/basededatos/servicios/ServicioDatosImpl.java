package es.uned.basededatos.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.TrinoInterface;
import es.uned.servicios.AbstractServicio;
import es.uned.servicios.ServicioDatosInterface;

/**
 * Servicio Datos: Este servicio hará las funciones de una base de datos que
 * relacione Usuarios-Seguidores-Trinos. Es decir, mantendrá la lista de
 * usuarios registrados y/o conectados al sistema, junto con sus seguidores y
 * trinos; y los relacionará permitiendo operaciones típicas de consulta, añadir
 * y borrado.
 * 
 * Los dos servicios anteriores (Servicio Autenticación y Servicio Gestor) harán
 * uso de este servicio para realizar las operaciones sobre el estado de los
 * usuarios del sistema y sus seguidores. Aunque podría usarse un sistema de
 * gestión de bases de datos (SGBD) para implementar este servicio, esto haría
 * muy complejo el desarrollo de la práctica y no atendería a los objetivos de
 * la asignatura.
 * 
 * Así pues, el equipo docente recomienda para la implementación del servicio
 * las clases List y HashMap de Java.
 * 
 * Todas las operaciones de la clase son seguras y "tontas", pues no es
 * responsabilidad de la misma mantener ninguna lógica de negocio.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 *
 */
final public class ServicioDatosImpl extends AbstractServicio implements ServicioDatosInterface {
	private Map<String, DatosUsuarioInterface> registrados;
	private Set<String> conectados;
	private Map<String, Set<String>> seguidores;
	private Map<String, List<TrinoInterface>> trinos;

	public ServicioDatosImpl() {
		super();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _iniciar() {
		this.registrados = new HashMap<>();
		this.conectados = new HashSet<>();
		this.seguidores = new HashMap<>();
		this.trinos = new HashMap<>();

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean _parar() {
		this.registrados = null;
		this.conectados = null;
		this.seguidores = null;
		this.trinos = null;

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConectado(String nick) {
		return this.conectados.contains(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addConectado(String nick) {
		return this.conectados.add(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeConectado(String nick) {
		return this.conectados.remove(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getConectados() {
		return new HashSet<>(this.conectados);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DatosUsuarioInterface addUsuario(DatosUsuarioInterface datos) {
		this.registrados.put(datos.getNick(), datos);
		return datos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasUsuario(String nick) {
		return this.registrados.containsKey(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DatosUsuarioInterface getUsuario(String nick) {
		return this.registrados.get(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DatosUsuarioInterface removeUsuario(String nick) {
		return this.registrados.remove(nick);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, DatosUsuarioInterface> getUsuarios() {
		return new HashMap<>(this.registrados);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addSeguidor(String seguido, String seguidor) {
		if (!this.seguidores.containsKey(seguido)) {
			this.seguidores.put(seguido, new HashSet<>());
		}

		return this.seguidores.get(seguido).add(seguidor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeSeguidor(String seguido, String seguidor) {
		return this.seguidores.containsKey(seguido) && this.seguidores.get(seguido).remove(seguidor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean esSeguidor(String seguido, String seguidor) {
		if (!this.seguidores.containsKey(seguido)) {
			return false;
		}

		return this.seguidores.get(seguido).contains(seguidor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSeguidores(String seguido) {
		if (!this.seguidores.containsKey(seguido)) {
			this.seguidores.put(seguido, new HashSet<>());
		}

		return this.seguidores.get(seguido);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSeguidos(String seguidor) {
		Set<String> seguidos = new HashSet<>();

		for (Map.Entry<String, Set<String>> entry : this.seguidores.entrySet()) {
			if (entry.getValue().contains(seguidor)) {
				seguidos.add(entry.getKey());
			}
		}

		return seguidos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<TrinoInterface>> getTrinos() {
		return new HashMap<>(this.trinos);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TrinoInterface> getTrinosUsuario(String usuario) {
		if (!this.trinos.containsKey(usuario)) {
			return new ArrayList<>();
		}

		return this.trinos.get(usuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addTrino(TrinoInterface trino) {
		if (!this.trinos.containsKey(trino.ObtenerNickPropietario())) {
			this.trinos.put(trino.ObtenerNickPropietario(), new ArrayList<>());
		}

		return this.trinos.get(trino.ObtenerNickPropietario()).add(trino);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasTrino(TrinoInterface trino) {
		for (List<TrinoInterface> trinosUsuario : this.trinos.values()) {
			if (trinosUsuario.contains(trino)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeTrino(TrinoInterface trino) {
		if (!this.trinos.containsKey(trino.ObtenerNickPropietario())) {
			return false;
		}

		return this.trinos.get(trino.ObtenerNickPropietario()).remove(trino);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TrinoInterface getTrino(String usuario, String textoTrino) {
		if (!this.trinos.containsKey(usuario)) {
			return null;
		}

		try {
			return this.trinos.get(usuario).stream().filter(trino -> trino.ObtenerTrino().equals(textoTrino))
					.findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}

	}
}
