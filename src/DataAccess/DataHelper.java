package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataHelper {
    private static String DBPathConnection = "jdbc:sqlite:DataBase//PoliAsistencia.sqlite"; 
    private static Connection conn = null;
    
    protected DataHelper(){}
    
    public static synchronized Connection openConnection() throws Exception {
        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("Intentando conectar a la base de datos en: " + DBPathConnection);
                conn = DriverManager.getConnection(DBPathConnection);
                System.out.println("Conexión establecida correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            throw e;
        }
        return conn;
    }
    

    public static void closeConnection() throws Exception {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
