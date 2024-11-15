package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Inicio")
public class Inicio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar la respuesta como HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Generar la página HTML
        out.println("<html>");
        out.println("<head><title>Inicio</title>");
         out.println("<link rel='stylesheet' type='text/css' href='Style4.css'>");
        out.println("</head>");
        out.println("<body>");
        
        // Contenedor principal
        out.println("<div class='container'>");
        out.println("<img src='img/aa.png' alt='Imagen de inicio'>");  // Imagen de inicio (debe estar en la carpeta 'img' del proyecto)
        out.println("<h1>Bienvenido a Homeclothings</h1>");
        out.println("<a href='IniciarUsuario' class='btn'>Iniciar Sesión</a>");  // Botón para iniciar sesión
        out.println("<a href='RegistrarUsuarios' class='btn'>Registrar Usuario</a>");  // Botón para registrar usuario
        out.println("</div>");
        
        out.println("</body>");
        out.println("</html>");
    }
}

