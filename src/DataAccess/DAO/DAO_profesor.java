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
import DataAccess.DTO.DTO_profesor;
import Framework.PatException;

public class DAO_profesor extends DataHelper implements IDAO<DTO_profesor> {

    @Override
    public DTO_profesor readBy(Integer id) throws Exception {
        DTO_profesor profesor = new DTO_profesor();
        String query = " SELECT IdProfesor, NombreProfesor, ApellidoProfesor, CedulaProfesor, "
                     + " CorreoProfesor, UsuarioProfesor, ClaveProfesor, "
                     + " FechaRegistro, FechaModifica, Estado "
                     + " FROM Profesor "
                     + " WHERE Estado = 'A' AND IdProfesor = " + id.toString();
        try {
            Connection conn = openConnection();         // Conectar a la base de datos
            Statement stmt = conn.createStatement();    // Crear una declaración SQL
            ResultSet rs = stmt.executeQuery(query);    // Ejecutar la consulta
            while (rs.next()) {
                profesor = new DTO_profesor(
                    rs.getInt(1),           // IdProfesor
                    rs.getString(2),        // NombreProfesor
                    rs.getString(3),        // ApellidoProfesor
                    rs.getString(4),        // CedulaProfesor
                    rs.getString(5),        // CorreoProfesor
                    rs.getString(6),        // UsuarioProfesor
                    rs.getString(7),        // ClaveProfesor
                    rs.getTimestamp(8).toLocalDateTime(), // FechaRegistro
                    rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null, // FechaModifica
                    rs.getString(10).charAt(0) // Estado
                );
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readBy()");
        }
        return profesor;
    }

    @Override
    public List<DTO_profesor> readAll() throws Exception {
        List<DTO_profesor> profesores = new ArrayList<>();
        String query = " SELECT IdProfesor, NombreProfesor, ApellidoProfesor, CedulaProfesor, "
                     + " CorreoProfesor, UsuarioProfesor, ClaveProfesor, "
                     + " FechaRegistro, FechaModifica, Estado "
                     + " FROM Profesor "
                     + " WHERE Estado = 'A' ";
        try {
            Connection conn = openConnection();         // Conectar a la base de datos
            Statement stmt = conn.createStatement();    // Crear una declaración SQL
            ResultSet rs = stmt.executeQuery(query);    // Ejecutar la consulta
            while (rs.next()) {
                DTO_profesor profesor = new DTO_profesor(
                    rs.getInt(1),           // IdProfesor
                    rs.getString(2),        // NombreProfesor
                    rs.getString(3),        // ApellidoProfesor
                    rs.getString(4),        // CedulaProfesor
                    rs.getString(5),        // CorreoProfesor
                    rs.getString(6),        // UsuarioProfesor
                    rs.getString(7),        // ClaveProfesor
                    rs.getTimestamp(8).toLocalDateTime(), // FechaRegistro
                    rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null, // FechaModifica
                    rs.getString(10).charAt(0) // Estado
                );
                profesores.add(profesor);
            }
        } catch (SQLException e) {
            throw e; // Lanzar la excepción en caso de error
        }
        return profesores;
    }

    @Override
    public boolean create(DTO_profesor entity) throws Exception {
        String query = " INSERT INTO Profesor (NombreProfesor, ApellidoProfesor, CedulaProfesor, "
                     + " CorreoProfesor, UsuarioProfesor, ClaveProfesor) "
                     + " VALUES (?, ?, ?, ?, ?, ?) ";
        try {
            Connection conn = openConnection(); // Abrir la conexión con la base de datos
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            // Asignar los valores a los parámetros de la consulta
            pstmt.setString(1, entity.getNombreProfesor());   // NombreProfesor
            pstmt.setString(2, entity.getApellidoProfesor()); // ApellidoProfesor
            pstmt.setString(3, entity.getCedulaProfesor());   // CedulaProfesor
            pstmt.setString(4, entity.getCorreoProfesor());   // CorreoProfesor
            pstmt.setString(5, entity.getUsuarioProfesor());  // UsuarioProfesor
            pstmt.setString(6, entity.getClaveProfesor());    // ClaveProfesor
            
            pstmt.executeUpdate(); // Ejecutar la consulta de inserción
            return true; // Retornar true si la operación fue exitosa
        } catch (SQLException e) {
            throw e; // Lanzar la excepción en caso de error
        } finally {
            closeConnection(); // Cerrar la conexión después de usarla
        }
    }

    @Override
    public boolean update(DTO_profesor entity) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        // Actualizar NombreProfesor, ApellidoProfesor, CorreoProfesor, UsuarioProfesor, ClaveProfesor y FechaModifica
        String query = " UPDATE Profesor SET NombreProfesor = ?, ApellidoProfesor = ?, CorreoProfesor = ?, "
                     + " UsuarioProfesor = ?, ClaveProfesor = ?, FechaModifica = ? "
                     + " WHERE IdProfesor = ? ";
        try {
            Connection conn = openConnection(); // Abrir la conexión con la base de datos
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            // Asignar los valores a los parámetros de la consulta
            pstmt.setString(1, entity.getNombreProfesor());   // NombreProfesor
            pstmt.setString(2, entity.getApellidoProfesor()); // ApellidoProfesor
            pstmt.setString(3, entity.getCorreoProfesor());   // CorreoProfesor
            pstmt.setString(4, entity.getUsuarioProfesor());  // UsuarioProfesor
            pstmt.setString(5, entity.getClaveProfesor());    // ClaveProfesor
            pstmt.setString(6, dtf.format(now));              // FechaModifica
            pstmt.setInt(7, entity.getIdProfesor());          // IdProfesor (condición WHERE)
            
            pstmt.executeUpdate(); // Ejecutar la consulta de actualización
            return true; // Retornar true si la operación fue exitosa
        } catch (SQLException e) {
            throw e; // Lanzar la excepción en caso de error
        } finally {
            closeConnection(); // Cerrar la conexión después de usarla
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = " UPDATE Profesor SET Estado = ? WHERE IdProfesor = ? ";
        try {
            Connection conn = openConnection(); // Abrir la conexión con la base de datos
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "X"); // Cambiar el estado a inactivo
            pstmt.setInt(2, id);     // IdProfesor (condición WHERE)
            pstmt.executeUpdate();   // Ejecutar la consulta de actualización
            return true; // Retornar true si la operación fue exitosa
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "delete()");
        }
    }

    public Integer getMaxRow() throws Exception {
        String query = " SELECT COUNT(*) TotalReg FROM Profesor "
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