package DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DataAccess.IDAO;
import DataAccess.DataHelper;
import DataAccess.DTO.DTO_estudiante;
import Framework.PatException;

public class DAO_estudiante extends DataHelper implements IDAO<DTO_estudiante> {

    @Override
public DTO_estudiante readBy(Integer idEstudiante) throws Exception {
    DTO_estudiante estudiante = new DTO_estudiante();
    String query = " SELECT IdEstudiante, NombreEstudiante, ApellidoEstudiante, CedulaEstudiante, "
                 + " CorreoEstudiante, UsuarioEstudiante, ClaveEstudiante, QREstudiante, "
                 + " FechaRegistro, FechaModifica, Estado "
                 + " FROM Estudiante "
                 + " WHERE Estado = 'A' AND IdEstudiante = " + idEstudiante.toString();
    try {
        Connection conn = openConnection();         // Conectar a la base de datos
        Statement stmt = conn.createStatement();    // Crear una declaración SQL
        ResultSet rs = stmt.executeQuery(query);    // Ejecutar la consulta
        while (rs.next()) {
            estudiante = new DTO_estudiante(
                rs.getInt("IdEstudiante"),           // IdEstudiante
                rs.getString("NombreEstudiante"),    // NombreEstudiante
                rs.getString("ApellidoEstudiante"),  // ApellidoEstudiante
                rs.getString("CedulaEstudiante"),    // CedulaEstudiante
                rs.getString("CorreoEstudiante"),    // CorreoEstudiante
                rs.getString("UsuarioEstudiante"),   // UsuarioEstudiante
                rs.getString("ClaveEstudiante"),     // ClaveEstudiante
                rs.getString("QREstudiante"),        // QREstudiante
                rs.getTimestamp("FechaRegistro").toLocalDateTime(),  // FechaRegistro
                rs.getTimestamp("FechaModifica") != null ? rs.getTimestamp("FechaModifica").toLocalDateTime() : null,  // FechaModifica
                rs.getString("Estado").charAt(0)     // Estado
            );
        }
    } catch (SQLException e) {
        throw new PatException(e.getMessage(), getClass().getName(), "readBy()");
    }
    return estudiante;
}
@Override
public List<DTO_estudiante> readAll() throws Exception {
    List<DTO_estudiante> estudiantes = new ArrayList<>();
    String query = " SELECT IdEstudiante, NombreEstudiante, ApellidoEstudiante, CedulaEstudiante, "
                 + " CorreoEstudiante, UsuarioEstudiante, ClaveEstudiante, QREstudiante, "
                 + " FechaRegistro, FechaModifica, Estado "
                 + " FROM Estudiante "
                 + " WHERE Estado = 'A' ";
    try {
        Connection conn = openConnection();         // Conectar a la base de datos
        Statement stmt = conn.createStatement();    // Crear una declaración SQL
        ResultSet rs = stmt.executeQuery(query);    // Ejecutar la consulta
        while (rs.next()) {
            DTO_estudiante estudiante = new DTO_estudiante(
                rs.getInt("IdEstudiante"),           // IdEstudiante
                rs.getString("NombreEstudiante"),    // NombreEstudiante
                rs.getString("ApellidoEstudiante"),  // ApellidoEstudiante
                rs.getString("CedulaEstudiante"),    // CedulaEstudiante
                rs.getString("CorreoEstudiante"),    // CorreoEstudiante
                rs.getString("UsuarioEstudiante"),   // UsuarioEstudiante
                rs.getString("ClaveEstudiante"),     // ClaveEstudiante
                rs.getString("QREstudiante"),        // QREstudiante
                rs.getTimestamp("FechaRegistro").toLocalDateTime(),  // FechaRegistro
                rs.getTimestamp("FechaModifica") != null ? rs.getTimestamp("FechaModifica").toLocalDateTime() : null,  // FechaModifica
                rs.getString("Estado").charAt(0)     // Estado
            );
            estudiantes.add(estudiante);
        }
    } catch (SQLException e) {
        throw e; // Lanzar la excepción en caso de error
    }
    return estudiantes;
}

    @Override
public boolean create(DTO_estudiante entity) throws Exception {
    // Generar el código QR a partir de la cédula del estudiante
    String qrCode = generarQR(entity.getCedulaEstudiante());

    String query = " INSERT INTO Estudiante (NombreEstudiante, ApellidoEstudiante, CedulaEstudiante, "
                 + " CorreoEstudiante, UsuarioEstudiante, ClaveEstudiante, QREstudiante) "
                 + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
    try {
        Connection conn = openConnection(); // Abrir la conexión con la base de datos
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Asignar los valores a los parámetros de la consulta
        pstmt.setString(1, entity.getNombreEstudiante());   // NombreEstudiante
        pstmt.setString(2, entity.getApellidoEstudiante()); // ApellidoEstudiante
        pstmt.setString(3, entity.getCedulaEstudiante());   // CedulaEstudiante
        pstmt.setString(4, entity.getCorreoEstudiante());   // CorreoEstudiante
        pstmt.setString(5, entity.getUsuarioEstudiante());  // UsuarioEstudiante
        pstmt.setString(6, entity.getClaveEstudiante());    // ClaveEstudiante
        pstmt.setString(7, qrCode);                         // QREstudiante (generado a partir de la cédula)
        
        pstmt.executeUpdate(); // Ejecutar la consulta de inserción
        return true; // Retornar true si la operación fue exitosa
    } catch (SQLException e) {
        throw e; // Lanzar la excepción en caso de error
    } finally {
        closeConnection(); // Cerrar la conexión después de usarla
    }
}

    @Override
    public boolean update(DTO_estudiante entity) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        // Actualizar NombreEstudiante, ApellidoEstudiante, CorreoEstudiante, UsuarioEstudiante, ClaveEstudiante, QREstudiante y FechaModifica
        String query = " UPDATE Estudiante SET NombreEstudiante = ?, ApellidoEstudiante = ?, CorreoEstudiante = ?, "
                     + " UsuarioEstudiante = ?, ClaveEstudiante = ?, QREstudiante = ?, FechaModifica = ? "
                     + " WHERE IdEstudiante = ? ";
        try {
            Connection conn = openConnection(); // Abrir la conexión con la base de datos
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            // Asignar los valores a los parámetros de la consulta
            pstmt.setString(1, entity.getNombreEstudiante());   // NombreEstudiante
            pstmt.setString(2, entity.getApellidoEstudiante()); // ApellidoEstudiante
            pstmt.setString(3, entity.getCorreoEstudiante());   // CorreoEstudiante
            pstmt.setString(4, entity.getUsuarioEstudiante());  // UsuarioEstudiante
            pstmt.setString(5, entity.getClaveEstudiante());    // ClaveEstudiante
            pstmt.setString(6, entity.getQrEstudiante());       // QREstudiante
            pstmt.setString(7, dtf.format(now));                // FechaModifica
            pstmt.setInt(8, entity.getIdEstudiante());          // IdEstudiante (condición WHERE)
            
            pstmt.executeUpdate(); // Ejecutar la consulta de actualización
            return true; // Retornar true si la operación fue exitosa
        } catch (SQLException e) {
            throw e; // Lanzar la excepción en caso de error
        } finally {
            closeConnection(); // Cerrar la conexión después de usarla
        }
    }

    @Override
    public boolean delete(Integer idEstudiante) throws Exception {
        String query = " UPDATE Estudiante SET Estado = ? WHERE IdEstudiante = ? ";
        try {
            Connection conn = openConnection(); // Abrir la conexión con la base de datos
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "X"); // Cambiar el estado a inactivo
            pstmt.setInt(2, idEstudiante);     // IdEstudiante (condición WHERE)
            pstmt.executeUpdate();   // Ejecutar la consulta de actualización
            return true; // Retornar true si la operación fue exitosa
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "delete()");
        }
    }

    public Integer getMaxRow() throws Exception {
        String query = " SELECT COUNT(*) TotalReg FROM Estudiante "
                     + " WHERE Estado = 'A' ";
        try {
            Connection conn = openConnection();         // Conectar a la base de datos
            Statement stmt = conn.createStatement();    // Crear una declaración SQL
            ResultSet rs = stmt.executeQuery(query);    // Ejecutar la consulta
            while (rs.next()) {
                return rs.getInt(1); // Retornar el total de registros
            }
        } catch (SQLException e) {
            throw e; // Lanzar la excepción en caso de error
        }
        return 0; // Retornar 0 si no hay registros
    }
}

