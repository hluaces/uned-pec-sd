package es.uned.basededatos.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.uned.basededatos.controladores.Basededatos;
import es.uned.common.DatosUsuario;
import es.uned.common.DatosUsuarioInterface;
import es.uned.common.Trino;
import es.uned.common.TrinoInterface;

/**
 * @author Héctor Luaces Novo <hector@luaces-novo.es>
 */
class BasededatosTest {
	protected Basededatos datos;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.datos = new Basededatos(System.out);
		this.datos.iniciarServicios();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.datos = null;
	}

	@Test
	void testIsConectado() {
		assertFalse(this.datos.isConectado("alskdjalsjdlajd"), "Usuario desconectado");
		this.datos.addConectado("pepe");
		assertTrue(this.datos.isConectado("pepe"), "Usario conectado");
	}

	@Test
	void testConectados() {
		assertTrue(this.datos.addConectado("pepe"), "Conectando usuario");
		assertFalse(this.datos.addConectado("pepe"), "Conectando mismo usuario");
		assertTrue(this.datos.addConectado("antonio"), "Conectando usuario");
		assertTrue(this.datos.addConectado("luis"), "Conectando usuario");
		assertEquals(3, this.datos.getConectados().size(), "Hay 3 conectados");
		assertTrue(this.datos.removeConectado("pepe"), "Eliminamos un conectado");
		assertEquals(2, this.datos.getConectados().size(), "Hay 2 conectados");
		assertFalse(this.datos.removeConectado("pepe"), "Eliminamos un conectado que no existe");
		assertEquals(2, this.datos.getConectados().size(), "Hay 2 conectados");
		assertTrue(this.datos.removeConectado("antonio"), "Desconectando otro usuario");
		assertTrue(this.datos.removeConectado("luis"), "Desconectando otro usuario");
		assertEquals(0, this.datos.getConectados().size(), "No hay conectados");
	}

	@Test
	void testUsuarios() {
		DatosUsuario u = new DatosUsuario("Nombre de pepe", "pepe", "pass de pepe");
		DatosUsuarioInterface u2;

		assertTrue(this.datos.addUsuario(u) != null, "Añadiendo usuario");
		assertTrue(this.datos.hasUsuario(u.getNick()), "Verificando que el usuario existe");
		u2 = this.datos.getUsuario(u.getNick());
		assertEquals(u2.getNick(), u.getNick(), "Comprobando getUsuario");
		assertEquals(1, this.datos.getUsuarios().keySet().size(), "Verificando que solo hay un usuario");
		assertTrue(this.datos.removeUsuario(u.getNick()) != null, "Eliminando usuario");
		assertFalse(this.datos.removeUsuario(u.getNick()) != null, "Eliminando mismo usuario");
		assertEquals(0, this.datos.getUsuarios().keySet().size(), "No hay usuarios");
	}

	@Test
	void testAddSeguidor() {
		this.datos.addUsuario(new DatosUsuario("seguido", "seguido", ""));

		for (int i = 0; i < 10; i++) {
			this.datos.addUsuario(new DatosUsuario("seguidor_" + i, "seguidor_" + i, ""));
			this.datos.addSeguidor("seguido", "seguidor_" + i);
			assertTrue(this.datos.esSeguidor("seguido", "seguidor_" + i), "Comprobando seguidor (" + i + ")");
		}

		assertEquals(11, this.datos.getUsuarios().size(), "Se han creado 10 usuarios");
		assertEquals(10, this.datos.getSeguidores("seguido").size(), "Hay 10 seguidores");
		assertEquals(0, this.datos.getSeguidores("seguidor_0").size(), "Hay usuarios sin seguidores");
		assertEquals(1, this.datos.getSeguidos("seguidor_0").size(), "Hay 1 usuario seguido");
		assertTrue(this.datos.removeSeguidor("seguido", "seguidor_0"), "Eliminando seguidor");
		assertFalse(this.datos.removeSeguidor("seguido", "seguidor_0"), "Eliminando seguidor que no sigue");
		assertEquals(9, this.datos.getSeguidores("seguido").size(), "Hay 10 seguidores");
		assertEquals(0, this.datos.getSeguidos("seguidor_0").size(), "Hay 0 usuarios seguidos");

	}

	@Test
	void testIniciarServicios() {
		assertTrue(!this.datos.iniciarServicios(), "Iniciando servicios ya iniciados");
	}

	@Test
	void testTrinos() {
		int total = 0, total2 = 0;

		/**
		 * Creamos un puñado de usuarios
		 */
		for (int i = 0; i < 10; i++) {
			Trino t = null;

			assertTrue(this.datos.addUsuario(new DatosUsuario("nombre_" + i, "nick_" + i, "")) != null,
					"creando usuario " + i);

			for (int k = 0; k < 10; k++) {
				t = new Trino("Trino numero " + k + " del usuario " + i, "nick_" + i);
				assertTrue(this.datos.addTrino(t), "Creando trino");
				total++;
			}

			assertEquals(10, this.datos.getTrinosUsuario("nick_" + i).size(), "Comprobando trinos usuario " + i);
			assertTrue(this.datos.hasTrino(t), "Trino existe");
			assertTrue(this.datos.removeTrino(t), "Eliminando trino");
			assertFalse(this.datos.hasTrino(t), "Trino no existe");
			total--;
		}

		for (Entry<String, List<TrinoInterface>> s : this.datos.getTrinos().entrySet()) {
			total2 += s.getValue().size();
		}

		assertEquals(total, total2, "Comprobando total de trinos");
	}
}
