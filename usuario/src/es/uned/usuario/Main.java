package es.uned.usuario;

import java.rmi.RemoteException;

import es.uned.common.DatosUsuario;
import es.uned.common.controladores.BasededatosInterface;
import es.uned.common.rmi.ControladorRegistro;

public class Main {
	public static void main(String[] args) {
		try {
			ControladorRegistro r = new ControladorRegistro();

			BasededatosInterface datos = (BasededatosInterface) r.buscarObjeto(BasededatosInterface.class);

			datos.addUsuario(new DatosUsuario("pepe", "pepito", "123123"));

			for (String user : datos.getUsuarios().keySet()) {
				System.out.println(user);
			}

			System.exit(0);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
