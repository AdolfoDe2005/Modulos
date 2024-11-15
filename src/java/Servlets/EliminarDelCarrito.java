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

@WebServlet("/EliminarDelCarrito")
public class EliminarDelCarrito extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int idUsuario = 1; // Cambia esto para obtener el ID del usuario autenticado

        try (Connection conn = BD.getConnection()) {
            String sql = "DELETE FROM carrito WHERE id_producto = ? AND id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idUsuario);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Redirigir nuevamente a la página del carrito después de eliminar el producto
                response.sendRedirect("MostrarCarrito");
            } else {
                throw new ServletException("No se pudo eliminar el producto del carrito.");
            }

        } catch (SQLException e) {
            throw new ServletException("Error al eliminar producto del carrito", e);
        }
    }
}
