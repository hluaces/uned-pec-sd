package es.uned.basededatos;

import java.rmi.Remote;
import es.uned.basededatos.controladores.Basededatos;
import es.uned.common.rmi.ControladorRegistro;

/**
 * Método "main" del controlador de base de datos, que lo inicialia y prepara
 * para el funcionamiento de RMI
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public class BasededatosMain {
	public static void main(String[] args) {
		Basededatos datos = new Basededatos(System.out);

		System.out.println("Arrancando controlador de base de datos");

		System.out.print("Iniciando servicios... ");
		datos.iniciarServicios();
		System.out.println("[OK]");

		System.out.print("Preparando RMI... ");

		try {
			ControladorRegistro con = new ControladorRegistro();
			System.out.println("[OK]");

			System.out.print("Exportando base de datos... ");
			con.exportarObjeto(datos);
			System.out.println("[OK]");

			datos.mostrarMenu();

			con.limpiarObjeto(datos);
		} catch (Exception e) {
			System.out.println(" [ERROR]");
			System.out.println("Ha ocurrido un error, cancelando...");
			e.printStackTrace();

			System.exit(1);
		}
	}

}
