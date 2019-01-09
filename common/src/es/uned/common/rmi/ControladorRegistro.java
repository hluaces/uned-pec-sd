package es.uned.common.rmi;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.controladores.ServidorInterface;

/**
 * Clase encargada de gestionar una única instancia de ejecución del registro
 * RMI.
 * 
 * Instanciar la clase iniciará automáticamente el registro.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final public class ControladorRegistro {
	private static int PORT = 1111;

	/**
	 * La instancia del registro que será mantenida por el controlador
	 */
	private Registry registro;

	public ControladorRegistro() throws RemoteException {
		try {
			this.registro = LocateRegistry.createRegistry(ControladorRegistro.PORT);
			this.registro.lookup("");
			System.out.println("createRegistry...");
		} catch (NotBoundException e) {
		} catch (ConnectException | ExportException e) {
			this.registro = LocateRegistry.getRegistry(ControladorRegistro.PORT);
		}
	}

	/**
	 * Devuelve la URI que usará este controlador del registro para nombrar a un
	 * objeto
	 * 
	 * @param r Objeto remoto que queremos nombrar
	 * @return URI del objeto
	 */
	public static String getRmiUri(Remote r) {
		return "rmi://localhost:" + PORT + "/" + ControladorRegistro.getName(r);
	}

	/**
	 * Devuelve el nombre que usará el registro para almacenar un objeto de una
	 * clase dada
	 * 
	 * @param c Clase del objeto a almacenar
	 * @return Nombre usado por el registro
	 */
	private static String getName(Class<?> c) {
		if (!c.isInterface()) {
			return c.getInterfaces()[0].getSimpleName();
		}

		return c.getSimpleName();
	}

	/**
	 * Devuelve el nombre que usará el registro para almacenar un objeto remoto
	 * dado.
	 * 
	 * @param r Objeto remoto del que queremos conocer el nombre
	 * @return Nombre usado por el registro para almacenarlo
	 */
	private static String getName(Remote r) {
		return ControladorRegistro.getName(r.getClass());
	}

	/**
	 * Devuelve un número de puerto libre en el registro
	 * 
	 * @return int
	 */
	private int getPuerto(Remote r) {

		String nombre = ControladorRegistro.getName(r);

		if (nombre.equals(ControladorRegistro.getName(BasededatosInterface.class))) {
			return 1112;
		}

		if (nombre.equals(ControladorRegistro.getName(ServidorInterface.class))) {
			return 1113;
		}

		return 1114;
	}

	/**
	 * Dado un objeto ExportableRMI, se encarga de exportarlo y devolver su interfaz
	 * Remote.
	 * 
	 * Adicionalmente, guardará referencias internas en aras de poder realizar
	 * tareas de mantenimiento sobre los objetos previamente exportados.
	 * 
	 * @param r    El objeto a exportar
	 * @param port El puerto deseado para levantar su servicio de escucha
	 * @return El objeto exportado
	 * @throws RemoteException
	 */
	public Remote exportarObjeto(Remote r) throws RemoteException {
		Remote ret = UnicastRemoteObject.exportObject(r, this.getPuerto(r));
		this.registro.rebind(ControladorRegistro.getName(r), ret);

		return ret;
	}

	/**
	 * Busca la implementación de una clase dada en el registro.
	 * 
	 * @param c Clase a buscar
	 * @return Objeto remoto que es de la clase solicitada
	 * @throws RemoteException
	 */
	public Remote buscarObjeto(Class<?> c) throws RemoteException {
		try {
			return this.registro.lookup(ControladorRegistro.getName(c));
		} catch (NotBoundException e) {
			return null;
		}
	}

	/**
	 * Elimina un objeto del registro.
	 * 
	 * @param r Objeto a eliminar
	 * @return boolean
	 */
	public boolean limpiarObjeto(Remote r) {
		try {
			this.registro.unbind(ControladorRegistro.getName(r));
			UnicastRemoteObject.unexportObject(r, true);
			return true;
		} catch (RemoteException | NotBoundException e) {
			return false;
		}
	}

}
