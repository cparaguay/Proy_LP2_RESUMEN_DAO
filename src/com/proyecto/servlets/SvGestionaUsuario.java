package com.proyecto.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.proyecto.beans.UsuarioDTO;
import com.proyecto.service.UsuarioService;

/**
 * Servlet implementation class SvGestionaUsuarios
 */
@WebServlet("/SvGestionaUsuarios")
public class SvGestionaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Este servicio permitira recibir a nivel de request, el primer parametro llamado tipo de operacion
	// y segun sea el tipo enviara a cada metodo adecuado
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String operacion = request.getParameter("operacion");

		if (operacion.equals("registrarUsuario")) {

			this.registrarUsuario(request, response);

		} else if (operacion.equals("modificarUsuario")) {

			this.modificarUsuario(request, response);

		} else if (operacion.equals("eliminarUsuario")) {

			this.eliminarUsuario(request, response);

		} else if (operacion.equals("listarUsuarios")) {

			this.listarUsuarios(request, response);
		}
		else if(operacion.equals("capturarUsuario")){
			
			this.capturarUsuario(request, response);
			
		}
	}


	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) {

		try {

			// Capturando datos del usuario para registralo
			
			String usuario = request.getParameter("txtUsuario");
			String clave = request.getParameter("txtPassword");
			String nombre = request.getParameter("txtNombre");
			String apellido = request.getParameter("txtApellido");
			String fecha = request.getParameter("txtFecha");
			
            HttpSession sesionX=request.getSession();
			UsuarioService servicioUsuario = new UsuarioService();

			int r = servicioUsuario.insertarUsuario(usuario, clave, nombre, apellido, fecha);

			if (r > 0) {
				 
				//Los mensajes en este caso se debe enviar a nivel de sesion para que persista a su paginacion
				sesionX.setAttribute("mensaje","Usuario registrado correctamente: "+usuario);
				request.getRequestDispatcher("GestionaUsuario?operacion=listarUsuarios").forward(request, response);
				
			} else {
				
                //Este tipo de mensajes no necesitan que persistan x eso se envia a nivel de request
				request.setAttribute("mensaje", "Error con los datos ingresados");
				request.getRequestDispatcher("registrarUsuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			System.out.println("Error al despachar en registrarUsuario: " + e);
		}

	}

	private void modificarUsuario(HttpServletRequest request, HttpServletResponse response) {

		// Capturando datos para modificar
		String usuario = request.getParameter("txtUsuario");
		String clave = request.getParameter("txtPassword");
		String nombre = request.getParameter("txtNombre");
		String apellido = request.getParameter("txtApellido");

		UsuarioService servicioUsaurio = new UsuarioService();
		int r = servicioUsaurio.actualizarUsuario(usuario, clave, nombre, apellido);
		HttpSession sesionX=request.getSession();
		RequestDispatcher rd = null;
		try {
			if (r > 0) {
				//Este mensaje se envia a nivel de sesion para que persista a su paginacion
				sesionX.setAttribute("mensaje", "Usuario modificado correctamente: "+usuario);
				rd = request.getRequestDispatcher("GestionaUsuario?operacion=listarUsuarios");
				rd.forward(request, response);
			} else {
				request.setAttribute("mensaje", "Error al actualizar");
				rd = request.getRequestDispatcher("actualizarUsuario.jsp");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println("Error al despachar modificarUsuario: " + e);
		}

	}

	private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			String idUsuario=request.getParameter("txtUsuario");

			UsuarioService servicioUsuario = new UsuarioService();
			int r = servicioUsuario.eliminarUsuario(idUsuario);
			HttpSession sesionX=request.getSession();
			RequestDispatcher rd;

			if (r > 0) {
				sesionX.setAttribute("mensaje", "Usuario eliminado correctamente: "+idUsuario);
				rd = request.getRequestDispatcher("GestionaUsuario?operacion=listarUsuarios");
				rd.forward(request, response);

			} else {
				request.setAttribute("mensaje", "Error al eliminar usuario");
				rd = request.getRequestDispatcher("eliminarUsuario.jsp");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println("Error al despachar en eliminarUsuario: " + e);
		}

	}

	// Este metodo recibira a nivel de request el tipo de listado y segun el
	// tipo se enviara al metodo adecuado
	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) {

		try {

			String tipoListado = request.getParameter("tipoListado");
			
			UsuarioService servicioUsuario = new UsuarioService();
			List<UsuarioDTO> listadoUsuario = servicioUsuario.listarUsuario();
			
			HttpSession miSesion = request.getSession();
			miSesion.setAttribute("listadoUsuarios", listadoUsuario);
			
			if (tipoListado==null) {

				request.getRequestDispatcher("listarUsuarios.jsp").forward(request, response);
			}
			else if(tipoListado.equals("listarUsuariosEliminar")){
				
				request.getRequestDispatcher("buscarUsuariosEliminar.jsp").forward(request, response);
			}
			else if(tipoListado.equals("listarUsuariosModificar")){
				
				request.getRequestDispatcher("buscarUsuarioModificar.jsp").forward(request, response);
			}

		} catch (Exception e) {
			System.out.println("Error al listadoProductos: " + e);
		}

	}
	
	
	private void capturarUsuario(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			String idUsuarioX=request.getParameter("idUsuario");
			String tipoCaptura=request.getParameter("tipoCaptura");
			
			UsuarioService sUsuario=new UsuarioService();
			
			UsuarioDTO usuarioX=sUsuario.buscarUsuario(idUsuarioX);
			request.setAttribute("usuarioX", usuarioX);
			if(tipoCaptura.equals("M")){
				
			request.getRequestDispatcher("actualizarUsuario.jsp").forward(request, response);
			}
			else if(tipoCaptura.equals("E")){
				
			request.getRequestDispatcher("eliminarUsuario.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			System.out.println("Error al capturar Usuario: "+e);
		}
		
	}



}
