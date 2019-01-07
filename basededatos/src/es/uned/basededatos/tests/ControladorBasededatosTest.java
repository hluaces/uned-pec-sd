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

class ControladorBasededatosTest {

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

	@BeforeEach
	void setUp() throws Exception {
		int totalTrinos = 0;

		this.datos = new Basededatos(System.out);
		this.datos.iniciarServicios();

		for (int i = 0; i < 100; i++) {
			DatosUsuarioInterface u = new DatosUsuario("Nombre_" + i, "Nick_" + i, "password_" + i);
			this.datos.addUsuario(u);

			for (int k = 0; k < 20; k++) {
				totalTrinos++;
				this.datos.addTrino(new Trino("Esto es el trino número " + totalTrinos, u.getNick()));
			}
		}

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
