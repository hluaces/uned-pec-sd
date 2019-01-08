package es.uned.basededatos.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.uned.basededatos.controladores.Basededatos;
import es.uned.common.DatosUsuario;
import es.uned.common.DatosUsuarioInterface;
import es.uned.common.Trino;

public class ControladorBasededatosTest {

	protected Basededatos datos;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @author Héctor Luaces Novo <hector@luaces-novo.es>
	 */

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	public static void generarDatosDummy(Basededatos b) {
		int totalTrinos = 0;

		for (int i = 0; i < 100; i++) {
			DatosUsuarioInterface u = new DatosUsuario("Nombre_" + i, "Nick_" + i, "password_" + i);
			b.addUsuario(u);

			for (int k = 0; k < 20; k++) {
				totalTrinos++;
				b.addTrino(new Trino("Esto es el trino número " + totalTrinos, u.getNick()));
			}
		}

	}

	@BeforeEach
	void setUp() throws Exception {
		this.datos = new Basededatos(System.out);
		this.datos.iniciarServicios();

		ControladorBasededatosTest.generarDatosDummy(datos);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		this.datos.iniciarServicios();
		this.datos.mostrarMenu();
	}
}
