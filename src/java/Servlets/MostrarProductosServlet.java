package Servlets;

import Conexion.BD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MostrarProductos")
public class MostrarProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = BD.getConnection()) {
            String sql = "SELECT * FROM productos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.println("<html>");
            out.println("<head><title>Lista de Productos</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style3.css'>");
            out.println("<body>");
            out.println("<h1>Lista de Productos</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Nombre</th><th>Descripción</th><th>Precio</th><th>Stock</th><th>Acción</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + nombre + "</td>");
                out.println("<td>" + descripcion + "</td>");
                out.println("<td>" + precio + "</td>");
                out.println("<td>" + stock + "</td>");
                out.println("<td><a class=btn href='AgregarAlCarrito?id=" + id + "'>Agregar al carrito</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            throw new ServletException("Error al obtener los productos", e);
        }
    }
}
