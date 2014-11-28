package com.proyecto.servlets;

import java.io.IOException;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.proyecto.beans.PersonaDTO;
import com.proyecto.service.UsuarioService;

/**
 * Servlet implementation class ServletLogueo
 */
@WebServlet("/ServletLogueo")
public class SvLogueo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		procesar(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		procesar(request, response);
	}


	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		
		

		
		//Capturando datos
		String usuario=request.getParameter("txtUsuario");
		String clave=request.getParameter("txtPassword");

		//Llamando al servicio
		UsuarioService servicioUsuario=new UsuarioService();
		RequestDispatcher rd=null;
		
		try{
			
			
			PersonaDTO usuarioX=servicioUsuario.validarLogueo(usuario, clave);
			
			if (usuarioX!=null) {
				//Capturamos la sesion actual
				HttpSession miSesion=request.getSession();
				
				//Imprimimos el id de la sesion, Para saber la session actual
				//System.out.println("Sesion iniciada: "+miSesion.getId());
				
				//A nivel de Session, Enviamos objetos a traves de la sesion, es casi igual al request
				miSesion.setAttribute("datos", usuarioX.getNombre() +" "+usuarioX.getApellido());
				miSesion.setAttribute("idVendedor", usuarioX.getUsuario());
				//A nivel de request
				request.setAttribute("datos", usuarioX.getNombre() +" "+usuarioX.getApellido());
				

				rd=request.getRequestDispatcher("bienvenido.jsp");
				rd.forward(request, response);
				
			}else{
				request.setAttribute("mensaje", "Error con los datos");
				rd=request.getRequestDispatcher("logueo.jsp");
				rd.forward(request, response);
			}
			
		}catch(Exception e){
			
			System.out.println("Error en el dispatcher: "+e);
		}

		
	}

}