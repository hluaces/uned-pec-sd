package es.uned.usuario.controladores;

import java.io.PrintStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import es.uned.common.DatosUsuarioInterface;
import es.uned.common.controladores.AbstractControlador;
import es.uned.common.controladores.ServidorInterface;
import es.uned.common.controladores.UsuarioInterface;
import es.uned.common.menu.exception.ParametroCallbackNoValido;
import es.uned.common.menus.CallbackOpcionInterface;
import es.uned.common.menus.Menu;
import es.uned.common.menus.OpcionMenuInterface;
import es.uned.common.menus.ParametroOpcionInterface;
import es.uned.common.menus.ParametroOpcionValidadorInterface;
import es.uned.common.rmi.ControladorRegistro;
import es.uned.common.servicios.CallbackUsuarioInterface;
import es.uned.common.servicios.exception.AutenticacionExcepcion;
import es.uned.common.servicios.exception.UsuarioNoExisteException;
import es.uned.usuario.servicios.CallbackUsuarioImpl;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
final public class Usuario extends AbstractControlador implements UsuarioInterface, Remote {
	/**
	 * El identificador de sesión del usuario
	 */
	private int sesion;

	/**
	 * El objeto que será encargado de mostrar los mensajes del controlador al
	 * usuario.
	 */
	private PrintStream out;

	/**
	 * El controlador de servidor que será utilizado por el usuario para comunicarse
	 * por RMI con el sistema.
	 */
	private ServidorInterface s;

	/**
	 * Caché de datos del usuario actualmente identificado
	 */
	private DatosUsuarioInterface u;

	/**
	 * El controlador de registro que se usará para intentar conectarse al servidor.
	 */
	private ControladorRegistro c;

	/**
	 * El callback usado para enviar información a este controlador.
	 */
	private CallbackUsuarioInterface callback;

	/**
	 * Crea un nuevo controlador de usuario.
	 * 
	 * @param out El stream que será utilizado para mostrar mensajes al usuario.
	 * @param s   El objeto ServidorInterface utilizado para comunicarse remotamente
	 *            con el sistema
	 */
	public Usuario(PrintStream out, ControladorRegistro c) throws RemoteException {
		super();

		this.out = out;
		this.sesion = 0;
		this.c = c;

		this.prepararMenu();

		this.callback = new CallbackUsuarioImpl(out);
		this.callback = (CallbackUsuarioInterface) c.exportarObjeto(this.callback);
	}

	/**
	 * Intenta reconectarse al servidor
	 * 
	 * @return boolean
	 */
	private boolean conectarse() {
		try {
			this.s = (ServidorInterface) this.c.buscarObjeto(ServidorInterface.class);
			return this.s != null;
		} catch (RemoteException e) {
			return false;
		}
	}

	/**
	 * Devuelve una instancia del servidor asociado a este usuario (intenta
	 * reconectarse si no existiese)
	 * 
	 * @return La instancia del servidor o null
	 */
	private ServidorInterface getServidor() {
		return this.conectarse() ? this.s : null;
	}

	/**
	 * Reinicializa los datos de usuario
	 */
	private void logout() {
		this.u = null;
		this.sesion = 0;
	}

	/**
	 * Prepara el menú asociado a este controlador.
	 * 
	 * Nótese que este controlador tiene dos menús distintos en función de si el
	 * usuario está logeado o no.
	 * 
	 * Esta función determina si es el caso, modificando el menú asociado al
	 * controlador.
	 */
	private void prepararMenu() {
		try {
			this.addMenu(new Menu(System.out));

			if (this.getServidor() != null) {
				this.u = this.s.getDatosSesion(this.sesion);
			}

			if (u == null) {
				this.prepararMenuAnonimo();
			} else {
				this.prepararMenuLogeado();
			}
		} catch (AutenticacionExcepcion | RemoteException e) {
			if (this.sesion != 0) {
				out.println("Error al conectar con el servicio de autenticación. Cerrando sesión.");
				out.println(e.getMessage());
				this.logout();
			} else {
				this.prepararMenuAnonimo();
			}

		}

	}

	/**
	 * Crea el menú del sistema destinado a usuarios identificados
	 */
	private void prepararMenuLogeado() {
		this.menu.reset();

		this.menu.addOpcion("Información del usuario.", this.opcionInformacion());
		OpcionMenuInterface o = this.menu.addOpcion("Enviar trino.", this.opcionAddTrino());
		o.addParametro("trino", "Introduce el mensaje de tu trino: ",
				this.getValidadorString(".+", "El trino no puede estar vacío"));

		this.menu.addOpcion("Listar usuarios del sistema.", this.opcionListarUsuarios());

		o = this.menu.addOpcion("Seguir a.", this.opcionSeguir());
		o.addParametro("nick", "¿A quién quieres seguir?: ", this.getValidadorNickNoExiste());

		o = this.menu.addOpcion("Dejar de seguir a.", this.opcionNoseguir());
		o.addParametro("nick", "¿A quién quieres dejar de seguir?: ", this.getValidadorNickNoExiste());

		this.menu.addOpcion("Borrar trino a los usuarios que todavía no lo han recibido.",
				new CallbackOpcionInterface() {

					@Override
					public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
						throw new RuntimeException("Aun no implementado");
					}
				});
		this.menu.addOpcion("Salir 'Logout'.", this.opcionLogout());
	}

	/**
	 * Crea el menú del sistema destinado a usuarios no identificados
	 */
	private void prepararMenuAnonimo() {
		this.menu.reset();

		// 1- Registro de usuario
		OpcionMenuInterface o = this.menu.addOpcion("Registrar un nuevo usuario.", this.opcionRegistrarse());
		o.addParametro("nick", "¿Con qué nick quieres ser conocido?: ",
				this.getValidadorString(".+", "Tienes que introducir un nick."))
				.addValidador(this.getValidadorNickExiste());
		o.addParametro("nombre", "Nombre real: ", this.getValidadorString(".+", "Tienes que introducir un nombre"));
		o.addParametro("contraseña", "Contraseña: ",
				this.getValidadorString(".+", "Tienes que introducir una contraseña."));
		o.addParametro("contraseña2", "Verifica tu contraseña: ", this.getValidadorVerificarPassword(o));

		// 2- Login
		OpcionMenuInterface o2 = this.menu.addOpcion("Hacer login.", this.opcionLogin());
		o2.addParametro("nick", "Usuario: ", this.getValidadorString(".+", "Tienes que introducir un nick."));
		o2.addParametro("contraseña", "Contraseña: ",
				this.getValidadorString(".+", "Tienes que introducir una contraseña."));

		// 3- Salir
		this.menu.addOpcion("Salir", this.opcionQuit()).setFinal(true);
	}

	/**********************************************************************************************************************
	 * OPCIONES DEL MENÚ
	 *********************************************************************************************************************/

	/**
	 * Callback ejecutado al salir del menú
	 * 
	 * @return boolean
	 */
	private CallbackOpcionInterface opcionQuit() {
		return new CallbackOpcionInterface() {
			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {

				out.println("Cerrando el sistema.");
				return true;
			}
		};
	}

	/**
	 * Devuelve un callback de una opción que gestiona el proceso de registro de un
	 * usuario
	 * 
	 * @return la instancia del callback
	 */
	private CallbackOpcionInterface opcionRegistrarse() {
		return new CallbackOpcionInterface() {
			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					String nombre = (String) parametros.get("nombre").getDato();
					String nick = (String) parametros.get("nick").getDato();
					String password = (String) parametros.get("contraseña").getDato();

					if (getServidor() == null) {
						throw new AutenticacionExcepcion("Imposible conectarse al servidor.");
					}

					boolean ok = s.registrar(nombre, nick, password);

					if (!ok) {
						throw new AutenticacionExcepcion("Ha ocurrido un error desconocido en el proceso de registro.");
					}

					out.println("Usuario registrado con éxito. Será necesario que te identifiques.");
				} catch (AutenticacionExcepcion e) {
					out.println(e.getMessage());
				} catch (RemoteException e) {
					out.println("Error al intentar el registro: ");
					out.println(e.getMessage());
				}

				return true;
			}
		};
	}

	/**
	 * Devuelve el callback ejecutado al hacer logout.
	 * 
	 * @return boolean
	 */
	private CallbackOpcionInterface opcionLogin() {
		return new CallbackOpcionInterface() {
			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				String nick = parametros.get("nick").getDato().toString();
				String pass = parametros.get("contraseña").getDato().toString();

				try {
					if (getServidor() == null) {
						throw new RuntimeException("Imposible conectar con el servidor.");
					}

					sesion = s.autenticar(nick, pass, callback);
					u = s.getDatosSesion(sesion);

					out.println("¡Hola, " + u.getNombre() + "!");
					prepararMenuLogeado();
				} catch (UsuarioNoExisteException | AutenticacionExcepcion | RemoteException e) {
					out.println("Error al identificarte:");
					out.println(e.getMessage());
				} catch (RuntimeException e) {
					out.println("No se pudo conectar con el servidor.");
					out.println(e.getMessage());
				}

				return true;
			}
		};

	}

	/**
	 * Devuelve una instancia de un callback que lista los usuarios registrados en
	 * el sistema
	 * 
	 * @return la instancia del callback
	 */
	private CallbackOpcionInterface opcionListarUsuarios() {
		return new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					if (getServidor() == null) {
						throw new RuntimeException("Imposible conectar con el servidor.");
					}

					List<String> nicks = getServidor().getUsuarios();

					if (nicks.size() == 0) {
						out.println("No hay usuarios registrados");
						return true;
					}

					for (String nick : getServidor().getUsuarios()) {
						out.println(" - " + nick);
					}

				} catch (Exception e) {
					out.println("Ha ocurrido un error de comunicación: ");
					out.println(e.getMessage());
				}

				return true;
			}
		};
	}

	/**
	 * Callback utilizado para enviar un trino en nombre del usuario.
	 * 
	 * @return La instancia del callback
	 */
	private CallbackOpcionInterface opcionAddTrino() {
		return new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					if (!getServidor().addTrino(sesion, parametros.get("trino").getDato().toString())) {
						throw new RuntimeException("Un error desconocido impidió enviar tu trino.");
					}

					out.println("Trino enviado con éxito");
				} catch (AutenticacionExcepcion | RemoteException | RuntimeException e) {
					out.println("Error al enviar tu trino: ");
					out.println(e.getMessage());
				}

				return true;
			}
		};
	}

	/**
	 * Devuelve el callback de una opción de menú que hace un logout.
	 * 
	 * @return La instancia del callback
	 */
	private CallbackOpcionInterface opcionLogout() {
		return new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					if (getServidor() == null) {
						throw new RuntimeException("Imposible conectar con el servidor para cerrar sesión remota.");
					}

					if (!s.salir(sesion)) {
						throw new RuntimeException("Error desconocido al cerrar sesión remota.");
					}

				} catch (RemoteException | AutenticacionExcepcion e) {
					out.println("Ha ocurrido un error durante el proceso: ");
					out.println(e.getMessage());
				} finally {
					logout();
					prepararMenuAnonimo();
					out.println("Cerrando sesión...");
				}
				return true;
			}
		};
	}

	/**
	 * Callback para la opción "seguir a un usuario"
	 * 
	 * @return Nick del usuario a seguir
	 */
	private CallbackOpcionInterface opcionSeguir() {
		return new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					if (getServidor() == null) {
						throw new RuntimeException("Imposible conectar con el servidor.");
					}

					if (!getServidor().addSeguidor(parametros.get("nick").getDato().toString(), sesion)) {
						throw new RuntimeException(
								"No has podido realizar la acción. ¿Estás siguiendo ya a ese usuario?");
					}

					out.println("Operación terminada con éxito");
					return true;
				} catch (RuntimeException | RemoteException | AutenticacionExcepcion e) {
					out.println("Ha ocurrido un error durante el proceso: ");
					out.println(e.getMessage());
				}

				return true;
			}
		};
	}

	/**
	 * Callback para la opción "seguir a un usuario"
	 * 
	 * @return Nick del usuario a seguir
	 */
	private CallbackOpcionInterface opcionNoseguir() {
		return new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				try {
					if (getServidor() == null) {
						throw new RuntimeException("Imposible conectar con el servidor.");
					}

					String seguido = parametros.get("nick").getDato().toString();
					if (!getServidor().removeSeguidor(seguido, sesion)) {
						throw new RuntimeException("No has podido realizar la acción. ¿Estás siguiendo a ese usuario?");
					}

					out.println("Operación terminada con éxito");
					return true;
				} catch (RuntimeException | RemoteException | AutenticacionExcepcion e) {
					out.println("Ha ocurrido un error durante el proceso: ");
					out.println(e.getMessage());
				}

				return true;
			}
		};
	}

	/**
	 * Callback llamado para mostrar la información del usuario.
	 * 
	 * Puesto que éste controlador no ofrece servicios RMI, éstos no se muestran.
	 * 
	 * @return La instancia del callback.
	 */
	private CallbackOpcionInterface opcionInformacion() {
		return new CallbackOpcionInterface() {

			@Override
			public boolean ejecutar(Map<String, ParametroOpcionInterface> parametros, PrintStream out) {
				if (sesion == 0 || u == null) {
					out.println("El usuario no está logeado.");
					return true;
				}

				out.println("- Nombre: " + u.getNombre());
				out.println("- Nick: " + u.getNick());
				out.println("- Sesión: " + sesion);
				out.println("- URL RMI: Ninguna. El usuario no expone servicios ajenos.");

				try {
					out.println("");
					out.println("Lista de seguidores:");
					Set<String> seguidores = getServidor().getSeguidores(u.getNick());

					if (seguidores.size() == 0) {
						out.println("No tienes seguidores.");
					}
					for (String s : seguidores) {
						out.println(" - " + s);
					}

				} catch (RemoteException e) {
					out.println("Error al obtener la lista de seguidores: ");
					out.println(e.getMessage());
				}

				try {
					out.println("");
					out.println("Lista de usuarios a los que sigues: ");
					Set<String> seguidos = getServidor().getSeguidos(u.getNick());

					if (seguidos.size() == 0) {
						out.println("No estás siguiendo a nadie.");
					}
					for (String s : seguidos) {
						out.println(" - " + s);
					}

				} catch (RemoteException e) {
					out.println("Error al obtener la lista de seguidos: ");
					out.println(e.getMessage());
				}

				return true;
			}
		};
	}

	/**********************************************************************************************************************
	 * Validadores de parámetros
	 *********************************************************************************************************************/

	/**
	 * Devuelve un validador de textos arbitrarios con un regexp pasado como
	 * parámetro
	 * 
	 * @return El validador creado
	 */
	private ParametroOpcionValidadorInterface getValidadorString(String regexp, String mensaje) {
		return new ParametroOpcionValidadorInterface() {

			@Override
			public boolean validar(Object dato) throws ParametroCallbackNoValido {
				Pattern p = Pattern.compile(regexp);

				return dato != null && !dato.equals("") && p.matcher(dato.toString()).matches();
			}

			@Override
			public String getMensajeError() {
				return mensaje;
			}
		};
	}

	/**
	 * Validador de parámetro de opción que verifica si un usuario ya existe.
	 * 
	 * @return El validador generado
	 */
	private ParametroOpcionValidadorInterface getValidadorNickExiste() {
		return new ParametroOpcionValidadorInterface() {
			@Override
			public boolean validar(Object dato) throws ParametroCallbackNoValido {
				// Si no hay conexión no podemos validarlo
				if (getServidor() == null) {
					return true;
				}

				try {
					if (s.isRegistrado((String) dato)) {
						return false;
					}
				}
				// Si hay fallos los ignoramos. El proceso de registro los mostrará
				catch (Exception e) {
				}

				return true;
			}

			@Override
			public String getMensajeError() {
				return "Ese usuario ya está registrado.";
			}
		};
	}

	/**
	 * Validador de parámetro de opción que verifica si un usuario no existe.
	 * 
	 * @return El validador generado
	 */
	private ParametroOpcionValidadorInterface getValidadorNickNoExiste() {
		return new ParametroOpcionValidadorInterface() {
			@Override
			public boolean validar(Object dato) throws ParametroCallbackNoValido {
				// Si no hay conexión no podemos validarlo
				if (getServidor() == null) {
					return true;
				}

				try {
					if (!s.isRegistrado((String) dato)) {
						return false;
					}
				}
				// Si hay fallos los ignoramos. El proceso de registro los mostrará
				catch (Exception e) {
				}

				return true;
			}

			@Override
			public String getMensajeError() {
				return "Ese usuario no está registrado.";
			}
		};
	}

	/**
	 * Devuelve un validador de parámetro de opción para verificar que la contraseña
	 * introducida por segunda vez es igual a la primera.
	 * 
	 * @return El validador en cuestión
	 */
	private ParametroOpcionValidadorInterface getValidadorVerificarPassword(OpcionMenuInterface o) {
		return new ParametroOpcionValidadorInterface() {

			@Override
			public boolean validar(Object dato) throws ParametroCallbackNoValido {
				if (!(dato instanceof String)) {
					return false;
				}

				return o.getParametro("contraseña").getDato().toString().equals(dato);
			}

			@Override
			public String getMensajeError() {
				return "Las contraseñas no coinciden.";
			}
		};
	}

}
