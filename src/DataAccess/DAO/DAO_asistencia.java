package DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DataAccess.DTO.DTO_asistencia;
import DataAccess.DataHelper;
import DataAccess.IDAO;
import Framework.PatException;

public class DAO_asistencia extends DataHelper implements IDAO<DTO_asistencia> {
    @Override
    public DTO_asistencia readBy(Integer id) throws Exception {
        DTO_asistencia asistencia = null;
        String query = "SELECT ID_Asistencia, Nombre_Estudiante, Apellido_Estudiante, Cedula_Estudiante, Fecha, Metodo "
                     + "FROM vista_asistencia WHERE ID_Asistencia = ?";
    
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    asistencia = new DTO_asistencia(
                        rs.getInt("ID_Asistencia"),             // ID de la asistencia
                        rs.getString("Nombre_Estudiante"),      // Nombre del estudiante
                        rs.getString("Apellido_Estudiante"),    // Apellido del estudiante
                        rs.getString("Cedula_Estudiante"),      // Cédula del estudiante
                        rs.getTimestamp("Fecha").toLocalDateTime(),  // Fecha de asistencia
                        rs.getString("Metodo")                 // Método de asistencia
                    );
                }
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readBy()");
        }
        return asistencia;
    }
    
    @Override
    public List<DTO_asistencia> readAll() throws Exception {
        List<DTO_asistencia> asistencias = new ArrayList<>();
        String query = "SELECT ID_Asistencia, Nombre_Estudiante, Apellido_Estudiante, Cedula_Estudiante, Fecha, Metodo FROM vista_asistencia";
    
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
    
            while (rs.next()) {
                asistencias.add(new DTO_asistencia(
                    rs.getInt("ID_Asistencia"),             // ID de la asistencia
                    rs.getString("Nombre_Estudiante"),      // Nombre del estudiante
                    rs.getString("Apellido_Estudiante"),    // Apellido del estudiante
                    rs.getString("Cedula_Estudiante"),      // Cédula del estudiante
                    rs.getTimestamp("Fecha").toLocalDateTime(),  // Fecha de asistencia
                    rs.getString("Metodo")                 // Método de asistencia
                ));
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readAll()");
        }
        return asistencias;
    }
    

    @Override
    public boolean create(DTO_asistencia entity) throws Exception {
        String query = "INSERT INTO Asistencia (idEstudiante, fechaAsistencia, metodoAsistencia, fechaRegistro, estado) "
                     + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, entity.getIdEstudiante());
            pstmt.setTimestamp(2, Timestamp.valueOf(entity.getFechaAsistencia()));
            pstmt.setString(3, entity.getMetodoAsistencia());
            pstmt.setTimestamp(4, Timestamp.valueOf(entity.getFechaRegistro()));
            pstmt.setString(5, String.valueOf(entity.getEstado()));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "create()");
        }
    }

    @Override
    public boolean update(DTO_asistencia entity) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        String query = "UPDATE Asistencia SET metodoAsistencia = ?, fechaModifica = ? WHERE idAsistencia = ?";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, entity.getMetodoAsistencia());
            pstmt.setTimestamp(2, Timestamp.valueOf(now));
            pstmt.setInt(3, entity.getIdAsistencia());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "update()");
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = "UPDATE Asistencia SET estado = ? WHERE idAsistencia = ?";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "X");
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "delete()");
        }
    }

    public Integer getMaxRow() throws Exception {
        String query = "SELECT COUNT(*) FROM Asistencia WHERE estado = 'A'";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "getMaxRow()");
        }
    }
}

