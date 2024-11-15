package Servlets;

import Conexion.BD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AgregarAlCarrito")
public class AgregarAlCarritoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("id"));
        int idUsuario = 1; // Puedes cambiarlo para obtenerlo del usuario autenticado
        int cantidad = 1; // Puedes permitir al usuario elegir la cantidad

        try (Connection conn = BD.getConnection()) {
            String sql = "INSERT INTO carrito (id_usuario, id_producto, cantidad) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);

            stmt.executeUpdate();

            response.sendRedirect("MostrarCarrito");

        } catch (SQLException e) {
            throw new ServletException("Error al agregar producto al carrito", e);
        }
    }
}
