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

@WebServlet("/EliminarUsuario")
public class EliminarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id")); // Validar el ID
        } catch (NumberFormatException e) {
            mostrarError(out, "El ID proporcionado no es válido.");
            return;
        }

        try (Connection conn = BD.getConnection()) {
            // Eliminar el usuario de la base de datos
            String sql = "DELETE FROM Usuario WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            // Comprobar si se eliminó el usuario
            if (rowsAffected > 0) {
                // Mostrar mensaje de éxito
                out.println("<html>");
                out.println("<head><title>Usuario Eliminado</title></head>");
                out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                out.println("<body>");
                out.println("<h1>El usuario ha sido eliminado correctamente.</h1>");
                out.println("<a class=btn6 href='ListarUsuarios'>Volver a la lista de usuarios</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                mostrarError(out, "No se encontró un usuario con ese ID.");
            }

        } catch (SQLException e) {
            mostrarError(out, "Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Método para mostrar mensajes de error
    private void mostrarError(PrintWriter out, String mensajeError) {
        out.println("<html>");
        out.println("<head><title>Error</title></head>");
        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
        out.println("<body>");
        out.println("<h1>Ha ocurrido un error</h1>");
        out.println("<p>" + mensajeError + "</p>");
        out.println("<a class=btn6 href='ListarUsuarios'>Volver a la lista de usuarios</a>");
        out.println("</body>");
        out.println("</html>");
    }
}

