
package Conexion;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
    
    public static final String db= "escuelaCA";
            //"escuelaCAzul";  //nombre de base de datos
    private static final String user= "root"; //nombre de usuario
    private static final String password= ""; //contraseña de tu MySQL
    private static final String url= "jdbc:mysql://localhost/"+db;
    private static  com.mysql.jdbc.Connection Conexion;

    public static com.mysql.jdbc.Connection getConnection() {
        try {
            Conexion =(com.mysql.jdbc.Connection) DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de Conexion"+e.getMessage());
        }
        return Conexion;
    }
    
    public void cerrarConexion(com.mysql.jdbc.Connection con){
        try{
            Conexion.close();
        }catch(SQLException e){
            
        }
    }


}

