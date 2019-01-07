package es.uned.common.controladores;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.TrinoInterface;
import es.uned.common.servicios.ServicioDatosInterface;

/**
 * Interfaz que expone las funciones públicas del controlador de base de datos.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public interface BasededatosInterface extends Remote {

	/**
	 * Wrapper del controlador de base de datos para el método isConectado del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#isConectado(String)
	 */
	public boolean isConectado(String nick) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método addConectado del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#addConectado(String)
	 */
	public boolean addConectado(String nick) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método removeConectado del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeConectado(String)
	 */
	public boolean removeConectado(String nick) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getConectados del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getConectados()
	 */
	public Set<String> getConectados() throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método addUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#addUsuario(DatosUsuarioInterface)
	 */
	public DatosUsuarioInterface addUsuario(DatosUsuarioInterface datos) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método hasUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#hasUsuario(String)
	 */
	public boolean hasUsuario(String nick) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getUsuario(String)
	 */
	public DatosUsuarioInterface getUsuario(String nick) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método removeUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeUsuario(String)
	 */
	public DatosUsuarioInterface removeUsuario(String nick) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getUsuarios del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getUsuarios()
	 */
	public Map<String, DatosUsuarioInterface> getUsuarios() throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getUsuarios del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#addSeguidor(String, String)
	 */
	public boolean addSeguidor(String seguido, String seguidor) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método removeSeguidor del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeSeguidor(String, String)
	 */
	public boolean removeSeguidor(String seguido, String seguidor) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método esSeguidor del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#esSeguidor(String, String)
	 */
	public boolean esSeguidor(String seguido, String seguidor) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getSeguidores del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getSeguidores(String)
	 */
	public Set<String> getSeguidores(String seguido) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getSeguidos del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getSeguidos(String)
	 */
	public Set<String> getSeguidos(String seguidor) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getTrinos del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getTrinos()
	 */
	public Map<String, List<TrinoInterface>> getTrinos() throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getTrinosUsuario del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#getTrinosUsuario(String)
	 */
	public List<TrinoInterface> getTrinosUsuario(String usuario) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método addTrino del servicio
	 * homónimo
	 * 
	 * @see ServicioDatosInterface#addTrino(TrinoInterface)
	 */
	public boolean addTrino(TrinoInterface trino) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método removeTrino del
	 * servicio homónimo
	 * 
	 * @see ServicioDatosInterface#removeTrino(TrinoInterface)
	 */
	public boolean removeTrino(TrinoInterface trino) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método hasTrino del servicio
	 * homónimo
	 * 
	 * @see ServicioDatosInterface#hasTrino(TrinoInterface)
	 */
	public boolean hasTrino(TrinoInterface trino) throws RemoteException;

	/**
	 * Wrapper del controlador de base de datos para el método getTrino del servicio
	 * homónimo
	 * 
	 * @see ServicioDatosInterface#getTrino(String, String)
	 */
	public TrinoInterface getTrino(String usuario, String textoTrino) throws RemoteException;
}
