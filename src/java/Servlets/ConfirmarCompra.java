package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ConfirmarCompra")
public class ConfirmarCompra extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String exito = request.getParameter("exito");
        
        out.println("<html>");
        out.println("<head><title>Resultado de la Compra</title></head>");
        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
        out.println("<body>");
        out.println("<h1>Resultado de la Compra</h1>");

        if ("true".equals(exito)) {
            out.println("<form><p>¡Compra finalizada con éxito!</p></form>");
        } else {
            out.println("<form><p>Error al finalizar la compra. Inténtalo de nuevo.</p></form>");
        }

        out.println("<a href='MostrarCarrito' class='btn5'>Regresar al Carrito</a>");
        out.println("<a href='Historial' class='btn5'>Historial de Compras</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
