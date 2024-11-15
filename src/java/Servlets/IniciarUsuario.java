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

@WebServlet("/IniciarUsuario")
public class IniciarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Iniciar Sesión de Usuario</title>");
        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Iniciar Sesión de Usuario</h1>");
        out.println("<form method='POST' action='IniciarUsuario'>");
        out.println("ID: <input type='number' name='id' required><br>");
        out.println("Nombre: <input type='text' name='Nombre' required><br>");
        out.println("Clave: <input type='password' name='Clave' required><br>");
        out.println("<input type='submit' value='Iniciar Sesión'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Validar si los campos están vacíos antes de intentar procesarlos
        String idStr = request.getParameter("id");
        String Nombre = request.getParameter("Nombre");
        String Clave = request.getParameter("Clave");

        if (idStr == null || Nombre == null || Clave == null || idStr.isEmpty() || Nombre.isEmpty() || Clave.isEmpty()) {
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Error: Todos los campos son obligatorios.</h1>");
            out.println("<a href='IniciarUsuario'>Volver al inicio de sesión</a>");
            out.println("</body>");
            out.println("</html>");
            return; // Detener la ejecución si hay campos vacíos
        }

        int id;
        try {
            // Intentar convertir el ID a un número entero
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Error: El ID debe ser un número válido.</h1>");
            out.println("<a href='IniciarUsuario'>Volver al inicio de sesión</a>");
            out.println("</body>");
            out.println("</html>");
            return; // Detener la ejecución si el ID no es un número válido
        }

        // Intentar conectar a la base de datos y verificar el usuario
        try (Connection conn = BD.getConnection()) {
            String sql = "SELECT * FROM Usuario WHERE id = ? AND Nombre = ? AND Clave = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Establecer los parámetros de la consulta SQL
            stmt.setInt(1, id);
            stmt.setString(2, Nombre);
            stmt.setString(3, Clave);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            out.println("<html>");
            out.println("<head><title>Inicio de Sesión</title></head>");
            out.println("<body>");
            

            // Verificar si el usuario existe
            if (rs.next()) {
              
               // Si todo es valido
                out.println("<html>");
                out.println("<head><title>Inicio Exitoso</title></head>");
                out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                out.println("<body>");
                out.println("<h1>Inicio de sesión exitoso. Bienvenido, " + Nombre + ".</h1>");
                out.println("<a class=btn8 href='MostrarProductos'>Ver Productos</a>");
                out.println("</body>");
                out.println("</html>");
                
               // Si hay errores como datos incorrectos o invalidos 
                
            } else {
                out.println("<html>");
                out.println("<head><title>Error al Inciar Sesión</title></head>");
                out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
                out.println("<body>");
                out.println("<h1>Error al Iniciar sesión</h1>");
                out.println("<a class=btn9 href='IniciarUsuario'>Volver a intentar</a>");
                out.println("</body>");
                out.println("</html>");
            }
            
            // Si hay errores al conectar la base de datos
            
        } catch (SQLException e) {
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Error en la base de datos: " + e.getMessage() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
