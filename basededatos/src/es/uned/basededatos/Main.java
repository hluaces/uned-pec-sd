package es.uned.basededatos;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import es.uned.basededatos.controladores.Basededatos;
import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.controladores.RmiControladores;

/**
 * Método "main" del controlador de base de datos, que lo inicialia y prepara
 * para el funcionamiento de RMI
 * 
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
public class Main {

	private static Basededatos datos;

	public static void main(String[] args) {
		datos = new Basededatos(System.out);

		System.out.println("Arrancando controlador de base de datos");

		System.out.print("Iniciando servicios...");
		datos.iniciarServicios();
		System.out.println(" [OK]");

		System.out.print("Preparando RMI...");

		try {
			BasededatosInterface b = (BasededatosInterface) UnicastRemoteObject.exportObject(datos,
					RmiControladores.BASEDEDATOS_PORT);

			Registry r = LocateRegistry.createRegistry(9999);

			r.rebind(RmiControladores.BASEDEDATOS_RMI, b);

			System.out.println(" [OK]");
			System.out.println("Iniciando menú de controlador");
			datos.mostrarMenu();

			r.unbind(RmiControladores.BASEDEDATOS_RMI);
			UnicastRemoteObject.unexportObject(datos, true);

		} catch (Exception e) {
			System.out.println(" [ERROR]");
			System.out.println("Ha ocurrido un error, cancelando...");
			e.printStackTrace();

			System.exit(1);
		}
	}

}
