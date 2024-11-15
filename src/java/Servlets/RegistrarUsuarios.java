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

@WebServlet("/RegistrarUsuarios")
public class RegistrarUsuarios extends HttpServlet {
    
    @Override
    // Para recoger los datos solicitados 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head><title>Registrar Usuario</title>");
        out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Registrar Usuario</h1>");
        out.println("<form method='POST' action='RegistrarUsuarios'>");
        out.println("ID: <input type='number' name='id'><br>");
        out.println("Nombre: <input type='text' name='Nombre'><br>");
        out.println("Edad: <input type='number' name='Edad'><br>");
        out.println("Email: <input type='email' name='email'><br>");
        out.println("Clave: <input type='password' name='Clave'><br>"); 
        
        out.println("<input type='submit' value='Registrar'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
    
    @Override
    //Para alamacenar los datos en la base
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
       
        int id = Integer.parseInt(request.getParameter("id"));  
        String Nombre = request.getParameter("Nombre");
        int Edad = Integer.parseInt(request.getParameter("Edad"));  
        String email = request.getParameter("email");
        String Clave = request.getParameter("Clave");
        
        try (Connection conn = BD.getConnection())
            //Consulta sql
        {
            String sql = "INSERT INTO Usuario (id, Nombre, Edad, email, Clave) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
           
            stmt.setInt(1, id);        
            stmt.setString(2, Nombre);  
            stmt.setInt(3, Edad);       
            stmt.setString(4, email);   
            stmt.setString(5, Clave);   
            
            stmt.executeUpdate(); 
            
            //Si el codigo es correcto se muestra html de confirmación
            
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Registro Exitoso</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
            out.println("<body>");
            out.println("<h1>Usuario registrado exitosamente</h1>");
            out.println("<a class='btn' href='RegistrarUsuarios'>Registrar otro usuario</a>");
            out.println("<a class='btn10' href='MostrarProductos'>Ver Productos</a>");
            out.println("</body>");
            out.println("</html>");}
            
        //Si el codigo es incorrecto html para intentarlo de nuevo
        
        catch (SQLException e) {
           response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Error en el Registro</title></head>");
            out.println("<link rel='stylesheet' type='text/css' href='Style.css'>");
            out.println("<body>");
            out.println("<h1>Usuario No Registrado</h1>");
            out.println("<a class='btn' href='RegistrarUsuarios'>Intentar de nuevo</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
