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

@WebServlet("/ListarUsuarios")
public class ListarUsuarios extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Lista de Usuarios</title></head>");
        out.println("<link rel='stylesheet' type='text/css' href='Style1.css'>");
        out.println("<body>");
        out.println("<h1>Usuarios Registrados</h1>");

        try (Connection conn = BD.getConnection()) {
            String sql = "SELECT * FROM Usuario";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Nombre</th><th>Edad</th><th>Email</th><th>Clave</th><th>Acciones</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("Nombre");
                int edad = rs.getInt("Edad");
                String email = rs.getString("email");
                String Clave = rs.getString("Clave");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + nombre + "</td>");
                out.println("<td>" + edad + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + Clave + "</td>");
             

                
               out.println("<td><a class='btn' href='ActualizarUsuarios?id=" + id + "'>Editar</a>" +
            "<a class='btn' href='EliminarUsuario?id=" + id + "'>Eliminar</a></td>");
               out.println("</tr>");

            }
            out.println("</table>");

        } catch (SQLException e) {
            throw new ServletException("Error al obtener los usuarios", e);
        }
        
        out.println("<a class='btn2' href='RegistrarUsuarios'>Registrar nuevo usuario</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
