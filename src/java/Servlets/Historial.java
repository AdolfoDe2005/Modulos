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

@WebServlet("/Historial")
public class Historial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Este es un ejemplo. Deberías obtener el ID del usuario autenticado
        int idUsuario = 1; 

        try (Connection conn = BD.getConnection()) {
            // Consulta para obtener la información del historial, unida con los productos
            String sql = "SELECT productos.nombre, historial.precio_total, historial.cantidad, historial.fecha_compra " +
                         "FROM historial " +
                         "JOIN productos ON historial.id_producto = productos.id " +
                         "WHERE historial.id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario); // Asegúrate de usar el id del usuario autenticado
            ResultSet rs = stmt.executeQuery();

            // Construimos la respuesta HTML
            out.println("<html>");
            out.println("<head><title>Historial de Compras</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style3.css'>");
            out.println("<body>");
            out.println("<h1>Historial de Compras</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Producto</th><th>Precio Total</th><th>Cantidad</th><th>Fecha de Compra</th></tr>");

            // Iteramos sobre los resultados de la consulta
            while (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                double precioTotal = rs.getDouble("precio_total");
                int cantidad = rs.getInt("cantidad");
                String fechaCompra = rs.getString("fecha_compra");

                // Mostramos los datos en la tabla
                out.println("<tr>");
                out.println("<td>" + nombreProducto + "</td>");
                out.println("<td>" + precioTotal + "</td>");
                out.println("<td>" + cantidad + "</td>");
                out.println("<td>" + fechaCompra + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            // Manejo de errores
            throw new ServletException("Error al mostrar el historial", e);
        }
    }
}
