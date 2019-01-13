package es.uned.usuario;

import es.uned.common.rmi.ControladorRegistro;
import es.uned.usuario.controladores.Usuario;

public class UsuarioMain {
	public static void main(String[] args) {
		System.out.print("Preparando RMI... ");

		try {
			ControladorRegistro con = new ControladorRegistro();
			System.out.println("[OK]");

			System.out.print("Iniciando sistema de usuario... ");
			Usuario u = new Usuario(System.out, con);
			System.out.println("[OK]");

			u.mostrarMenu();

			con.limpiarObjeto(u);
			System.exit(0);
		} catch (Exception e) {
			System.out.println(" [ERROR]");
			System.out.println("Ha ocurrido un error, cancelando...");
			e.printStackTrace();

			System.exit(1);
		}
	}
}
