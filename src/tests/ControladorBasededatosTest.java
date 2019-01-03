package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.Basededatos;

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
		this.datos = new Basededatos(System.out);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		this.datos.iniciarServicios();
	}
}
