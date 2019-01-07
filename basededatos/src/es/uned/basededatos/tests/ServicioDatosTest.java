package es.uned.basededatos.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.uned.basededatos.servicios.ServicioDatosImpl;
import es.uned.common.DatosUsuario;
import es.uned.common.Trino;
import es.uned.common.TrinoInterface;
import es.uned.common.servicios.ServicioDatosInterface;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
class ServicioDatosTest {

	private ServicioDatosInterface datos;

	private final int USUARIOS = 100;
	private final int CONECTADOS = 10;
	private final int SEGUIDORES = 10;
	private final String NICK_SEGUIDO = "Nick_0";
	private final String NICK_TRINERO = "Nick_0";
	private final int TRINOS = 200;

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
		this.datos = new ServicioDatosImpl();

		this.datos.iniciar();

		// Creamos usuarios
		for (int i = 0; i < this.USUARIOS; i++) {
			this.datos.addUsuario(new DatosUsuario("Usuario " + i, "Nick_" + i, "Password_" + i));
		}

		// Conectamos a unos cuantos usuarios
		for (int i = 0; i < this.CONECTADOS; i++) {
			this.datos.addConectado("Nick_" + i);
		}

		// Creamos unos cuantos seguidores
		for (int i = 1; i < this.SEGUIDORES + 1; i++) {
			this.datos.addSeguidor(this.NICK_SEGUIDO, "Nick_" + i);
		}

		// Creamos trinos
		for (int i = 0; i < this.TRINOS - 1; i++) {
			int user = ThreadLocalRandom.current().nextInt(0, this.USUARIOS);

			this.datos.addTrino(new Trino("Trino_" + i, "Nick_" + user));
		}

		this.datos.addTrino(new Trino("Trino_99", this.NICK_TRINERO));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	private int getTotalTrinos() {
		int total = 0;

		for (List<TrinoInterface> trinosUsuario : this.datos.getTrinos().values()) {
			total += trinosUsuario.size();
		}

		return total;
	}

	@Test
	void test() {
		assertEquals(this.USUARIOS, this.datos.getUsuarios().keySet().size(), "Total registrados");
		assertEquals(this.CONECTADOS, this.datos.getConectados().size(), "Totalconectados");
		assertEquals(this.SEGUIDORES, this.datos.getSeguidores(this.NICK_SEGUIDO).size(),
				"Total seguidores de " + this.NICK_SEGUIDO);

		// Pruebas de existencia
		assertTrue("Comprobando que existe " + this.NICK_SEGUIDO, this.datos.hasUsuario(this.NICK_SEGUIDO));
		assertTrue("Comprobando que NO existe 'PacoPerez'", !this.datos.hasUsuario("PacoPerez"));

		// Usuarios conectados
		assertTrue("Comprobando que " + this.NICK_SEGUIDO + " está conectado",
				this.datos.isConectado(this.NICK_SEGUIDO));
		assertTrue("Comprobando que PacoPerez no está conectado", !this.datos.isConectado("PacoPerez"));

		// Borrado de usuarios
		this.datos.removeUsuario("Nick_90");
		assertEquals(this.USUARIOS - 1, this.datos.getUsuarios().keySet().size(), "Total registrados (tras borrado)");

		// Desconectando usuarios
		this.datos.removeConectado("Nick_2");
		assertEquals(this.CONECTADOS - 1, this.datos.getConectados().size(), "Total conectados (tras borrado)");

		// Seguidores
		assertTrue("Comprobando seguidor que sigue", this.datos.esSeguidor(this.NICK_SEGUIDO, "Nick_1"));
		assertTrue("Comprobando seguidor que no sigue", !this.datos.esSeguidor(this.NICK_SEGUIDO, "Nick_20"));
		this.datos.addSeguidor(this.NICK_SEGUIDO, "Nick_20");
		assertTrue("Comprobando seguidor que sigue ( 2 )", this.datos.esSeguidor(this.NICK_SEGUIDO, "Nick_20"));
		assertEquals(this.SEGUIDORES + 1, this.datos.getSeguidores(this.NICK_SEGUIDO).size(),
				"Comprobando total seguidores");
		assertEquals(1, this.datos.getSeguidos("Nick_20").size(), "Comprobando total seguidos para Nick_20");
		this.datos.addSeguidor("Nick_2", "Nick_20");
		this.datos.addSeguidor("Nick_2", "Nick_20");
		this.datos.addSeguidor("Nick_3", "Nick_20");
		this.datos.addSeguidor("Nick_4", "Nick_20");
		assertEquals(4, this.datos.getSeguidos("Nick_20").size(), "Comprobando total seguidos para Nick_20 (2)");
		this.datos.removeSeguidor(this.NICK_SEGUIDO, "Nick_20");
		assertEquals(3, this.datos.getSeguidos("Nick_20").size(), "Comprobando total seguidos para Nick_20 (3)");
		assertEquals(this.SEGUIDORES, this.datos.getSeguidores(this.NICK_SEGUIDO).size(),
				"Comprobando total seguidores (2)");

		// Trinos
		assertEquals(this.TRINOS, this.getTotalTrinos(), "Comprobando total inicial de trinos...");
		TrinoInterface t = this.datos.getTrino(this.NICK_TRINERO, "Trino_99");
		assertTrue("Comprobando que se recupera el Trino_99", null != t);
		assertTrue("Comprobando que el trino es de " + this.NICK_TRINERO,
				t.ObtenerNickPropietario().equals(this.NICK_TRINERO));
		this.datos.removeTrino(t);
		assertEquals(this.TRINOS - 1, this.getTotalTrinos(), "Comprobando total de trinos tras borrado");
		assertTrue("Comprobando que no se recupera el Trino_99",
				null == this.datos.getTrino(this.NICK_TRINERO, "Trino_99"));
	}
}
