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

@WebServlet("/MostrarCarrito")
public class MostrarCarrito extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int idUsuario = 1; // Cambia esto para obtener el ID del usuario autenticado

        try (Connection conn = BD.getConnection()) {
            String sql = "SELECT productos.id, productos.nombre, productos.precio, carrito.cantidad " +
                         "FROM carrito " +
                         "JOIN productos ON carrito.id_producto = productos.id " +
                         "WHERE carrito.id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            out.println("<html>");
            out.println("<head><title>Carrito de Compras</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style3.css'>");
            out.println("<body>");
            out.println("<h1>Carrito de Compras</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Producto</th><th>Precio</th><th>Cantidad</th><th>Acciones</th></tr>");

            while (rs.next()) {
                int idProducto = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");

                out.println("<tr>");
                out.println("<td>" + nombre + "</td>");
                out.println("<td>" + precio + "</td>");
                out.println("<td>" + cantidad + "</td>");
                // Botón para eliminar el producto
                out.println("<td><form action='EliminarDelCarrito' method='POST'>");
                out.println("<input type='hidden' name='idProducto' value='" + idProducto + "'>");
                out.println("<input type='submit' class=btn2 value='Eliminar'></form></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            
            out.println("<a class=btn3 href='FinalizarCompra'>Finalizar Compra</a>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            throw new ServletException("Error al mostrar el carrito", e);
        }
    }
}
