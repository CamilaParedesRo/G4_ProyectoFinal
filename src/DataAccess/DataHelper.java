package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataHelper {

    private static final String DB_URL = "jdbc:sqlite:DataBase/PoliAsistencia.sqlite";
    private static Connection conn = null;

    protected DataHelper() {}

    public static synchronized Connection openConnection() throws SQLException {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("org.sqlite.JDBC");

                System.out.println("Intentando conectar a la base de datos SQLite en: " + DB_URL);
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Conexión a SQLite establecida correctamente.");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: No se encontró el driver JDBC de SQLite.", e);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos SQLite: " + e.getMessage(), e);
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null; 
                System.out.println("Conexión a SQLite cerrada correctamente.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cerrar la conexión con SQLite.", e);
        }
    }
}