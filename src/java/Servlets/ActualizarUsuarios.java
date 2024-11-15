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

@WebServlet("/ActualizarUsuarios")
public class ActualizarUsuarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                try (Connection conn = BD.getConnection()) {
                    String sql = "SELECT * FROM Usuario WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String Nombre = rs.getString("Nombre");
                        int Edad = rs.getInt("Edad");
                        String email = rs.getString("email");
                        int Clave = rs.getInt("Clave");

                        out.println("<html>");
                        out.println("<head><title>Actualizar Usuario</title></head>");
                        out.println("<link rel='stylesheet' type='text/css' href='Style2.css'>");
                        out.println("<body>");
                        out.println("<h1>Actualizar Usuario</h1>");
                        out.println("<form method='POST' action='ActualizarUsuarios?id=" + id + "'>");
                        out.println("ID: <input type='number' name='id' value='" + id + "' readonly><br>");
                        out.println("Nombre: <input type='text' name='Nombre' value='" + Nombre + "'><br>");
                        out.println("Edad: <input type='number' name='Edad' value='" + Edad + "'><br>");
                        out.println("Email: <input type='email' name='email' value='" + email + "'><br>");
                        out.println("Clave: <input type='password' name='Clave' value='" + Clave + "'><br>");
                        out.println("<input type='submit' value='Actualizar'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                    } else {
                        out.println("<html>");
                        out.println("<head><title>Usuario Incorrecto</title></head>");
                        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                        out.println("<body>");
                        out.println("<h1>Usuario no encontrado</h1>");
                         out.println("<a class=btn7 href='ActualizarUsuarios'>Volver a intentar</a>");
                        out.println("</body>");
                        out.println("</html>");
                       
                    }
                }
            } catch (SQLException e) {
                throw new ServletException("Error al obtener el usuario", e);
            } catch (NumberFormatException e) {
                out.println("ID no válido.");
            }
        } else {
            // Formulario para seleccionar el ID del usuario
            out.println("<html>");
            out.println("<head><title>Seleccionar Usuario</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style2.css'>");
            out.println("<body>");
            out.println("<h1>Seleccionar Usuario para Actualizar</h1>");
            out.println("<form method='GET' action='ActualizarUsuarios'>");
            out.println("<label for='id'>ID del Usuario:</label>");
            out.println("<input type='number' id='id' name='id' required>");
            out.println("<input type='submit' value='Buscar'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int id = Integer.parseInt(request.getParameter("id"));
        String Nombre = request.getParameter("Nombre");
        int Edad = Integer.parseInt(request.getParameter("Edad"));
        String email = request.getParameter("email");
        int Clave = Integer.parseInt(request.getParameter("Clave"));

        try (Connection conn = BD.getConnection()) {
            String sql = "UPDATE Usuario SET Nombre = ?, Edad = ?, email = ?, Clave = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Nombre);
            stmt.setInt(2, Edad);
            stmt.setString(3, email);
            stmt.setInt(4, Clave);
            stmt.setInt(5, id);

            int rowsAffected = stmt.executeUpdate();

            // Mostrar mensaje de confirmación o error según el resultado
            if (rowsAffected > 0) {
                out.println("<html>");
                out.println("<head><title>Actualización Exitosa</title></head>");
                out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                out.println("<body>");
                out.println("<h1>Usuario Actualizado Correctamente</h1>");
                out.println("<a class=btn7 href='ListarUsuarios'>Volver a la lista de usuarios</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<html>");
                out.println("<head><title>Error en la Actualización</title></head>");
                out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                out.println("<body>");
                out.println("<h1>Error al Actualizar el Usuario</h1>");
                out.println("<a class=btn7 href='ListarUsuarios'>Volver a la lista de usuarios</a>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al actualizar el usuario", e);
        }
    }
}
