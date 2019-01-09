package es.uned.servidor;

import java.rmi.RemoteException;

import es.uned.common.rmi.ControladorRegistro;
import es.uned.servidor.controladores.Servidor;

public class ServidorMain {

	public static void main(String[] args) {
		try {
			System.out.print("Conectando con el registro... ");
			ControladorRegistro c = new ControladorRegistro();
			System.out.println("[OK]");

			System.out.print("Iniciando el controlador de servidor... ");
			Servidor s = new Servidor(System.out, c);
			System.out.println("[OK]");

			System.out.print("Iniciando servicios del controlador... ");
			if (!s.iniciarServicios()) {
				System.out.println("[ERROR]");
			} else {
				System.out.println("[OK]");
			}

			System.out.print("Exportando funcionalidad RMI... ");
			c.exportarObjeto(s);
			System.out.println("[OK]");

			s.mostrarMenu();

			c.limpiarObjeto(s);
			System.exit(0);

		} catch (RemoteException e) {
			System.out.println("[ERROR]");
			System.out.println("Ha ocurrido un error remoto: ");
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

}
