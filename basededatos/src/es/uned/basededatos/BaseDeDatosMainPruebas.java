package es.uned.basededatos;

import java.rmi.Remote;

import es.uned.basededatos.controladores.Basededatos;
import es.uned.basededatos.tests.ControladorBasededatosTest;
import es.uned.common.rmi.ControladorRegistro;

/**
 * Método "main" dee pruebas. No usar en entornos reales.
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public class BaseDeDatosMainPruebas {

	public static void main(String[] args) {
		Basededatos datos = new Basededatos(System.out);

		System.out.println("Arrancando controlador de base de datos");
		System.out.print("Iniciando servicios... ");
		datos.iniciarServicios();
		System.out.println("[OK]");

		System.out.print("Generando datos de prueba... ");
		ControladorBasededatosTest.generarDatosDummy(datos);
		System.out.println("[OK]");

		try {
			System.out.print("Preparando RMI... ");
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
