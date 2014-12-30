package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.proyecto.beans.VentaDTO;
import com.proyecto.service.VentaService;

/**
 * Servlet implementation class SvGestionaVenta
 */
@WebServlet("/SvGestionaVenta")
public class SvGestionaVenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operacion=request.getParameter("operacion");
		
		
		if(operacion.equals("registrarVenta")){
			
			this.registrarVenta(request, response);
			
		}
		else if(operacion.equals("modificarVenta")){
			
			this.modificarVenta(request, response);
		}
		else if(operacion.equals("eliminarVenta")){
			
		}
		else if(operacion.endsWith("listarVentas")){
			
			this.listarVentas(request, response);
		}
		
	}


	private void registrarVenta(HttpServletRequest request, HttpServletResponse response) {
		
		
        try {
        	
			//Capturando datos para registrar venta
			
			String idVendedor=request.getParameter("txtIdVendedor");
			String idProducto=request.getParameter("txtIdProducto");
			int    cantidad=Integer.parseInt(request.getParameter("txtCantidad"));
			double precio=Double.parseDouble(request.getParameter("txtPrecio"));
			
			
			VentaDTO ventaX=new VentaDTO(idVendedor, idProducto, cantidad, precio);
			
			VentaService servicioVenta=new VentaService();
			
	
			int r=servicioVenta.registrarVenta(ventaX);
			
			HttpSession miSesion=request.getSession();
			
			if(r>0){
				
				this.listarVentas(request, response);
		
			}else {
				
				miSesion.setAttribute("Mensaje", "Error al registrar ventas" );
				request.getRequestDispatcher("registrarVenta.jsp").forward(request, response);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("Error al registrarVenta: "+e);
		}

		
	}


	private void modificarVenta(HttpServletRequest request, HttpServletResponse response) {
		
		
	}


	private void listarVentas(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
        try {
			
        	String tipoListado=request.getParameter("tipoListado");
        	
			VentaService servicioVenta=new VentaService();
			ArrayList<VentaDTO> listadoVentas=servicioVenta.listarVentas();
			
			HttpSession sesionX=request.getSession();
			sesionX.setAttribute("listadoVentas", listadoVentas);
			
			
			if(tipoListado==null){
				
				request.getRequestDispatcher("listarVentas.jsp").forward(request, response);
			}
			else if(tipoListado.equals("listarVentasModificar")){
				
			}
			else if(tipoListado.equals("listarVentasEliminar")){
				
			}
			
		
				
			
		} catch (Exception e) {
			System.out.println("Error al listarVentas: "+e);
		}
		
		
	}

}