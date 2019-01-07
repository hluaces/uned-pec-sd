package es.uned.servidor.controladores;

import es.uned.servicios.ServicioAutenticacionInterface;
import es.uned.servicios.ServicioGestorInterface;

/**
 * La entidad Servidor se encarga de controlar el proceso de autenticación de
 * los usuarios del sistema y gestión de sus mensajes (los cuales vamos a llamar
 * trinos), para ello hace uso de dos servicios:
 * 
 * - ServicioAutenticacion
 * 
 * - ServicioGestor
 * 
 * @author Hector Luaces Novo <hector@luaces-novo.es>
 * @see ServicioAutenticacionImpl
 * @see ServicioGestorImpl
 */
public class Servidor {
	private ServicioGestorInterface gestor;
	private ServicioAutenticacionInterface auth;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
