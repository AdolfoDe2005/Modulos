package Servlets;

import Conexion.BD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FinalizarCompra")
public class FinalizarCompra extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Mostrar el formulario de confirmación
        out.println("<html>");
        out.println("<head><title>Confirmar Compra</title></head>");
        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
        out.println("<body>");
        out.println("<h1>Confirmar Compra</h1>");
        out.println("<form action='FinalizarCompra' method='POST'>");
        out.println("<p>¿Está seguro de que desea finalizar la compra?</p>");
        out.println("<input type='submit' value='Sí, finalizar' class='btn3'>");
        out.println("</form>");
        out.println("<a href='MostrarCarrito' class='btn4'>Cancelar</a>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idUsuario = 1; // Cambia esto para obtener el ID del usuario autenticado

        try (Connection conn = BD.getConnection()) {
            // Insertar los productos del carrito en el historial
            String insertHistorial = "INSERT INTO historial (id_usuario, id_producto, cantidad, precio_total) " +
                                     "SELECT carrito.id_usuario, carrito.id_producto, carrito.cantidad, " +
                                     "(carrito.cantidad * productos.precio) AS precio_total " +
                                     "FROM carrito " +
                                     "JOIN productos ON carrito.id_producto = productos.id " +
                                     "WHERE carrito.id_usuario = ?";
            PreparedStatement stmtHistorial = conn.prepareStatement(insertHistorial);
            stmtHistorial.setInt(1, idUsuario);
            int rowsHistorial = stmtHistorial.executeUpdate(); // Filas insertadas en el historial

            if (rowsHistorial > 0) {
                // Lógica para vaciar el carrito
                String deleteCarrito = "DELETE FROM carrito WHERE id_usuario = ?";
                PreparedStatement stmtDelete = conn.prepareStatement(deleteCarrito);
                stmtDelete.setInt(1, idUsuario);
                int rowsCarrito = stmtDelete.executeUpdate(); // Filas eliminadas del carrito

                // Redirigir al servlet de confirmación con el estado de éxito
                if (rowsCarrito > 0) {
                    response.sendRedirect("ConfirmarCompra?exito=true");
                } else {
                    response.sendRedirect("ConfirmarCompra?exito=false");
                }
            } else {
                response.sendRedirect("ConfirmarCompra?exito=false");
            }

        } catch (SQLException e) {
            throw new ServletException("Error al finalizar la compra", e);
        }
    }
}

