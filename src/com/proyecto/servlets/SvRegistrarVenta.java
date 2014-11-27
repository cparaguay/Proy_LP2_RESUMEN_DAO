package com.proyecto.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.proyecto.service.VentaService;


@WebServlet("/SvRegistrarVenta")
public class SvRegistrarVenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		procesar(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		procesar(request, response);
	}


	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			
			
			//Capturando datos
			
			String idVendedor=request.getParameter("txtIdVendedor");
			double monto=Double.parseDouble(request.getParameter("txtMonto"));
			
			VentaService servicioVenta=new VentaService();
			
			int r=servicioVenta.registrarVenta(idVendedor, monto);
			
			RequestDispatcher rd;
			HttpSession miSesion=request.getSession();
			
			if(r>0){
				
				rd=request.getRequestDispatcher("listadoVentas");
				
			}else {
				
				miSesion.setAttribute("Mensaje", "Error al registrar ventas" );
				rd=request.getRequestDispatcher("registrarVenta.jsp");
				
			}
			
			rd.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
