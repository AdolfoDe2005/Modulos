package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD {
    
    private static final String URL = "jdbc:mysql://localhost:5151/login";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Batcountry2005"; 
    
      public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver JDBC", e);
        }
    }
}

