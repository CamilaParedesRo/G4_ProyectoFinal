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
                conn = DriverManager.getConnection(DBPathConnection);
            }
        } catch (SQLException e) {
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
