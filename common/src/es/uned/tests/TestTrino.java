/**
 * 
 */
package es.uned.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.uned.common.Trino;
import es.uned.common.TrinoInterface;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
class TestTrino {
	private ArrayList<TrinoInterface> trinos;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		this.trinos = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			this.trinos.add(new Trino("Trino número " + i, "Nick " + i));
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	@Test
	void test() {
		for (int i = 0; i < 100; i++) {
			assertEquals("Nick " + i, this.trinos.get(i).ObtenerNickPropietario(), "Comprobando nick trino #" + i);
			assertEquals("Trino número " + i, this.trinos.get(i).ObtenerTrino(), "Comprobando texto trino #" + i);
		}
	}

}
