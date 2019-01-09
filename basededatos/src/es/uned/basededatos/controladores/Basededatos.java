package es.uned.basededatos.controladores;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import es.uned.basededatos.servicios.ServicioDatosImpl;
import es.uned.common.DatosUsuarioInterface;
import es.uned.common.TrinoInterface;
import es.uned.common.controladores.AbstractControlador;
import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.menus.CallbackOpcionInterface;
import es.uned.common.menus.Menu;
import es.uned.common.menus.ParametroOpcionInterface;
import es.uned.common.rmi.ControladorRegistro;
import es.uned.common.servicios.ServicioDatosInterface;
import es.uned.common.servicios.exception.ServicioYaIniciadoException;

/**
 * Esta entidad es la encargada de almacenar todos los datos del sistema:
 * Usuarios, Seguidores, Trinos, ...
 * 
 * Sólo la entidad Servidor puede consumir el servicio Datos que suministra esta
 * entidad.zz
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final public class Basededatos extends AbstractControlador implements BasededatosInterface {
	private static String SERVICIO_DATOS = "datos";

	public Basededatos(PrintStream out) {
		super(out);

		this.addServicio(Basededatos.SERVICIO_DATOS, new ServicioDatosImpl());
		this.crearMenu();
	}

	/**
	 * Inicializa el menú usado por el controlador.
	 */
	protected void crearMenu() {
		Menu menu = new Menu(this.out);

		try {
			menu.addOpcion("Información de la Base de Datos.", new CallbackOpcionInterface() {
				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					out.println("Mostrando identificador RMI");
					out.println(ControladorRegistro.getRmiUri(Basededatos.this));
					return true;
				}
			});
			menu.addOpcion("Listar usuarios registrados.", new CallbackOpcionInterface() {
				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					return listarUsuariosRegistrados(out);
				}
			});
			menu.addOpcion("Listar trinos.", new CallbackOpcionInterface() {
				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					return listarTrinos(out);
				}
			});
			menu.addOpcion("Salir.", new CallbackOpcionInterface() {

				@Override
				public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
					out.println("Saliendo del sistema de base de datos.");
					return true;
				}
			}).setFinal(true);

			this.addMenu(menu);
		} catch (ServicioYaIniciadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Muestra por un stream de salida los us uarios registados
	 * 
	 * @param out Stream de salida a usar
	 * @return boolean
	 */
	protected boolean listarUsuariosRegistrados(PrintStream out) {
		if (this.getUsuarios().entrySet().isEmpty()) {
			out.println("Todavía no hay usuarios registrados.");
			return true;
		}

		for (Entry<String, DatosUsuarioInterface> s : this.getUsuarios().entrySet()) {
			out.println(" * " + s.getKey());
		}

		return true;
	}

	/**
	 * Lista los trinos creados en la base de datos
	 * 
	 * @param out Stream de salida a usar
	 * @return boolean
	 */
	protected boolean listarTrinos(PrintStream out) {
		if (this.getTrinos().entrySet().isEmpty()) {
			out.println("Todavía no hay trinos en el sistema.");
			return true;
		}

		for (Entry<String, List<TrinoInterface>> s : this.getTrinos().entrySet()) {
			for (TrinoInterface t : s.getValue()) {
				out.println(" * " + t.ObtenerNickPropietario() + ": " + t.ObtenerTrino());
			}
		}
		return true;
	}

	/**
	 * Devuelve la instancia del servicio de base de datos asociado a este
	 * controlador
	 * 
	 * @return la instancia asociada
	 */
	protected ServicioDatosInterface getServicioDatos() {
		return (ServicioDatosInterface) this.getServicio(Basededatos.SERVICIO_DATOS);
	}

	/**
	 * Determina si un usuario está conectado
	 * 
	 * @param nick nick del usuario a comprobar
	 * @return true si está conectado, false en caso contrario
	 */
	@Override
	public boolean isConectado(String nick) throws RemoteException {
		return this.getServicioDatos().isConectado(nick);
	}

	/**
	 * Añade un nick de usuario a la lista de conectadosusuario
	 * 
	 * @param nick nick del usuario a comprobar
	 * @return true si está conectado, falso en caso contrario
	 */
	@Override
	public boolean addConectado(String nick) {
		return this.getServicioDatos().addConectado(nick);
	}

	/**
	 * Elimina un nick de usuario de la lista de conectados
	 * 
	 * @param nick usuario a eliminar de la lista de conectados
	 * @return true si se elimina correctamente, falso en caso contrario
	 */
	@Override
	public boolean removeConectado(String nick) {
		return this.getServicioDatos().removeConectado(nick);
	}

	/**
	 * Devuelve una lista con los nicks de todos los usuarios conectados
	 * 
	 * @return lista de todos los usuarios conectados
	 */
	@Override
	public Set<String> getConectados() {
		return this.getServicioDatos().getConectados();
	}

	/**
	 * Intenta crear un nuevo usuario a partir de una clase de DatosUsuario.
	 * 
	 * @param datos Datos del nuevo usuario a crear
	 * @return Datos del usuario creado
	 */
	@Override
	public DatosUsuarioInterface addUsuario(DatosUsuarioInterface datos) {
		return this.getServicioDatos().addUsuario(datos);
	}

	/**
	 * Determina si un usuario con un nick dado ya está registrado
	 * 
	 * @param nick Nick del usuario a comprobar
	 * @return true si ya existe o false si no
	 */
	@Override
	public boolean hasUsuario(String nick) {
		return this.getServicioDatos().hasUsuario(nick);
	}

	/**
	 * Devuelve los datos de usuario asociados a un nick
	 * 
	 * @param nick Nick del usuario del que queremos los datos
	 * @return Datos de usuario asociados a ese nick, o "null"
	 */
	@Override
	public DatosUsuarioInterface getUsuario(String nick) {
		return this.getServicioDatos().getUsuario(nick);
	}

	/**
	 * Elimina un usuario registrado
	 * 
	 * @param nick nick del usuario a borrar
	 * @return Datos del usuario borrado o null, si no había ningún usuario con ese
	 *         nombre
	 */
	@Override
	public DatosUsuarioInterface removeUsuario(String nick) {
		return this.getServicioDatos().removeUsuario(nick);
	}

	/**
	 * Devuelve un Map con todos los usuarios registrados
	 * 
	 * @return Map que relaciona los nicks con los datos de todos los usuarios
	 *         registrados
	 */
	@Override
	public Map<String, DatosUsuarioInterface> getUsuarios() {
		return this.getServicioDatos().getUsuarios();
	}

	/**
	 * Añade un nuevo seguidor a un usuario
	 * 
	 * @param seguido  Nick del usuario al que queremos seguir
	 * @param seguidor Nick del usuario que va a seguirle
	 * @return True si tiene éxito
	 */
	@Override
	public boolean addSeguidor(String seguido, String seguidor) {
		return this.getServicioDatos().addSeguidor(seguido, seguidor);
	}

	/**
	 * Intenta eliminar un seguidor de un usuario
	 * 
	 * @param seguido  Nick del usuario al que el usuario sigue
	 * @param seguidor Nick del seguidor que queremos eliminar
	 * @return booleans
	 */
	@Override
	public boolean removeSeguidor(String seguido, String seguidor) {
		return this.getServicioDatos().removeSeguidor(seguido, seguidor);
	}

	/**
	 * Determina si un usuario es seguidor de otro
	 * 
	 * @param seguido  Nick del usuario para el que queremos comprobar el seguidor
	 * @param seguidor Nick del usuario que queremos comprobar si sigue al anterior
	 * @return true si tiene éxito
	 */
	@Override
	public boolean esSeguidor(String seguido, String seguidor) {
		return this.getServicioDatos().esSeguidor(seguido, seguidor);
	}

	/**
	 * Devuelve una lista con todos los seguidores de un usuario
	 * 
	 * @param seguido Nick del usuario del que queremos conocer los seguidores
	 * @return Lista con los nombres de los seguidores
	 */
	@Override
	public Set<String> getSeguidores(String seguido) {
		return this.getServicioDatos().getSeguidores(seguido);
	}

	/**
	 * Devuelve una lista con todos los usuarios a los que sigue otro usuario
	 * 
	 * @param seguidor Nick del usuario cuyos seguidos queremos conocer
	 * @return Lista con los seguidos del usuario
	 */
	@Override
	public Set<String> getSeguidos(String seguidor) {
		return this.getServicioDatos().getSeguidos(seguidor);
	}

	/**
	 * Devuelve una copia de todos los trinos en la base de datos en forma de Map
	 * cuya key es el nick del propietario.
	 * 
	 * @return Map con todos los trinos de la base de datos
	 */
	@Override
	public Map<String, List<TrinoInterface>> getTrinos() {
		return this.getServicioDatos().getTrinos();
	}

	/**
	 * Devuelve una lista con todos los trinos de un usuario cuyo nick pasemos como
	 * parámetro.
	 * 
	 * @param usuario Nick del usuario cuyos trinos queremos
	 * @return Lista de los trinos del usuario
	 */
	@Override
	public List<TrinoInterface> getTrinosUsuario(String usuario) {
		return this.getServicioDatos().getTrinosUsuario(usuario);
	}

	/**
	 * Añade un trino a la base de datos
	 * 
	 * @param trino Trino a añadir
	 * @return true si tiene éxito, false en caso contrario
	 */
	@Override
	public boolean addTrino(TrinoInterface trino) {
		return this.getServicioDatos().addTrino(trino);
	}

	/**
	 * Borra un trino de la base de datos. Si existe más de una copia, solo
	 * eliminará uno.
	 * 
	 * @param trino Trino a eliminar
	 * @return true si tiene éxito, false en caso contrario
	 */
	@Override
	public boolean removeTrino(TrinoInterface trino) {
		return this.getServicioDatos().removeTrino(trino);
	}

	/**
	 * Determina si un trino dado existe
	 * 
	 * @param trino El trino a comprobar
	 * @return true si existe, false en caso contrario
	 */
	@Override
	public boolean hasTrino(TrinoInterface trino) {
		return this.getServicioDatos().hasTrino(trino);
	}

	/**
	 * Devuelve un trino en base a su texto, si éste existe.
	 * 
	 * En el caso de que haya más de uno que coincida, solo se devolverá uno.
	 * 
	 * @param usuario    Nick del usuario que escribió el trino
	 * @param textoTrino Texto del trino
	 * @return Objeto del trino o null.
	 */
	@Override
	public TrinoInterface getTrino(String usuario, String textoTrino) {
		return this.getServicioDatos().getTrino(usuario, textoTrino);
	}

}
