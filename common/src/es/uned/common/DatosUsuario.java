package es.uned.common;

import java.io.Serializable;

import es.uned.common.servicios.CallbackUsuarioInterface;

/**
 * Clase que representa a un usuario del sistema. No confundir con el
 * controlador "Usuario" que se encarga de administrar los servicios de usuario.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public final class DatosUsuario implements DatosUsuarioInterface, Serializable {
	private static final long serialVersionUID = -1139206633143592204L;
	private String nombre, nick, password;
	private CallbackUsuarioInterface c;

	/**
	 * Crea un nuevo conjunto de datos de usuario
	 * 
	 * @param nombre   Nombre del usuario
	 * @param nick     Su nick
	 * @param password Contraseña
	 */
	public DatosUsuario(String nombre, String nick, String password) {
		super();
		this.nombre = nombre;
		this.nick = nick;
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNombre() {
		return nombre;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNick() {
		return nick;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CallbackUsuarioInterface getCallback() {
		return this.c;
	}
}
