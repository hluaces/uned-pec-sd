package controladores;

import java.util.List;
import java.util.Map;
import java.util.Set;

import common.DatosUsuarioInterface;
import common.TrinoInterface;
import servicios.ServicioDatosInterface;

/**
 * Interfaz que expone las funciones públicas del controlador de base de datos.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface BasededatosInterface {

	/**
	 * Wrapper del controlador de base de datos para el método isConectado del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#isConectado(String)
	 */
	public boolean isConectado(String nick);

	/**
	 * Wrapper del controlador de base de datos para el método addConectado del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#addConectado(String)
	 */
	public boolean addConectado(String nick);

	/**
	 * Wrapper del controlador de base de datos para el método removeConectado del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeConectado(String)
	 */
	public boolean removeConectado(String nick);

	/**
	 * Wrapper del controlador de base de datos para el método getConectados del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getConectados()
	 */
	public Set<String> getConectados();

	/**
	 * Wrapper del controlador de base de datos para el método addUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#addUsuario(DatosUsuarioInterface)
	 */
	public DatosUsuarioInterface addUsuario(DatosUsuarioInterface datos);

	/**
	 * Wrapper del controlador de base de datos para el método hasUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#hasUsuario(String)
	 */
	public boolean hasUsuario(String nick);

	/**
	 * Wrapper del controlador de base de datos para el método getUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getUsuario(String)
	 */
	public DatosUsuarioInterface getUsuario(String nick);

	/**
	 * Wrapper del controlador de base de datos para el método removeUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeUsuario(String)
	 */
	public DatosUsuarioInterface removeUsuario(String nick);

	/**
	 * Wrapper del controlador de base de datos para el método getUsuarios del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getUsuarios()
	 */
	public Map<String, DatosUsuarioInterface> getUsuarios();

	/**
	 * Wrapper del controlador de base de datos para el método getUsuarios del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#addSeguidor(String, String)
	 */
	public boolean addSeguidor(String seguido, String seguidor);

	/**
	 * Wrapper del controlador de base de datos para el método removeSeguidor del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeSeguidor(String, String)
	 */
	public boolean removeSeguidor(String seguido, String seguidor);

	/**
	 * Wrapper del controlador de base de datos para el método esSeguidor del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#esSeguidor(String, String)
	 */
	public boolean esSeguidor(String seguido, String seguidor);

	/**
	 * Wrapper del controlador de base de datos para el método getSeguidores del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getSeguidores(String)
	 */
	public Set<String> getSeguidores(String seguido);

	/**
	 * Wrapper del controlador de base de datos para el método getSeguidos del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getSeguidos(String)
	 */
	public Set<String> getSeguidos(String seguidor);

	/**
	 * Wrapper del controlador de base de datos para el método getTrinos del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getTrinos()
	 */
	public Map<String, List<TrinoInterface>> getTrinos();

	/**
	 * Wrapper del controlador de base de datos para el método getTrinosUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getTrinosUsuario(String)
	 */
	public List<TrinoInterface> getTrinosUsuario(String usuario);

	/**
	 * Wrapper del controlador de base de datos para el método addTrino del servicio
	 * homónimo
	 * 
	 * @see ServicioDatosInterface#addTrino(TrinoInterface)
	 */
	public boolean addTrino(TrinoInterface trino);

	/**
	 * Wrapper del controlador de base de datos para el método removeTrino del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeTrino(TrinoInterface)
	 */
	public boolean removeTrino(TrinoInterface trino);

	/**
	 * Wrapper del controlador de base de datos para el método hasTrino del servicio
	 * homónimo
	 * 
	 * @see ServicioDatosInterface#hasTrino(TrinoInterface)
	 */
	public boolean hasTrino(TrinoInterface trino);

	/**
	 * Wrapper del controlador de base de datos para el método getTrino del servicio
	 * homónimo
	 * 
	 * @see ServicioDatosInterface#getTrino(String, String)
	 */
	public TrinoInterface getTrino(String usuario, String textoTrino);
}
