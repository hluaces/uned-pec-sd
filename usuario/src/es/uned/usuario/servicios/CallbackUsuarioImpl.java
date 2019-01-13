package es.uned.usuario.servicios;

import java.io.PrintStream;
import java.rmi.RemoteException;

import es.uned.common.servicios.CallbackUsuarioInterface;

/**
 * Implementación de CallbackUsuarioInterface
 * 
 * @see CallbackUsuarioInterface
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public class CallbackUsuarioImpl implements CallbackUsuarioInterface {
	protected PrintStream out;

	/**
	 * Crea una nueva instancia de un callback de usuario, asociándole un stream de
	 * datos que será el usado para imprimir datos por pantalla.
	 * 
	 * @param out El stream usado para enviar mensajes al usuario.
	 */
	public CallbackUsuarioImpl(PrintStream out) {
		super();
		this.out = out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String tx) throws RemoteException {
		out.println(tx);
	}
}
