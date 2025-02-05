package DataAccess.DAO;

import DataAccess.DTO.DTO_asistencia;
import DataAccess.DataHelper;
import DataAccess.IDAO;
import Framework.PatException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAO_asistencia extends DataHelper implements IDAO<DTO_asistencia> {
    @Override
    public DTO_asistencia readBy(Integer id) throws Exception {
        DTO_asistencia asistencia = null;
<<<<<<< HEAD
        String query = "SELECT ID_Asistencia, Nombre_Estudiante, Apellido_Estudiante, Cedula_Estudiante, Codigo_Unico_Estudiante, Fecha, Metodo "
                     + "FROM vista_historial_asistencia  WHERE ID_Asistencia = ?";
=======
        String query = "SELECT ID_Asistencia, Nombre_Estudiante, Apellido_Estudiante, Codigo_Unico_Estudiante, Fecha_Asistencia, Metodo_Asistencia "
                     + "FROM vista_historial_asistencia WHERE ID_Asistencia = ?";
>>>>>>> origin/Main_Prueba
    
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    asistencia = new DTO_asistencia();
                    asistencia.setIdAsistencia(rs.getInt("ID_Asistencia"));
                    asistencia.setNombreEstudiante(rs.getString("Nombre_Estudiante"));
                    asistencia.setApellidoEstudiante(rs.getString("Apellido_Estudiante"));
<<<<<<< HEAD
                    asistencia.setCodigoEstudiante(rs.getString("Codigo_Unico_Estudiante"));
                    asistencia.setCedulaEstudiante(rs.getString("Cedula_Estudiante"));
                    asistencia.setFechaAsistencia(rs.getTimestamp("Fecha").toLocalDateTime());
                    asistencia.setMetodoAsistencia(rs.getString("Metodo"));
=======
                    asistencia.setCedulaEstudiante(rs.getString("Codigo_Unico_Estudiante"));
                    asistencia.setFechaAsistencia(rs.getTimestamp("Fecha_Asistencia").toLocalDateTime());
                    asistencia.setMetodoAsistencia(rs.getString("Metodo_Asistencia"));
>>>>>>> origin/Main_Prueba
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
<<<<<<< HEAD
        String query = "SELECT ID_Asistencia, Nombre_Estudiante, Apellido_Estudiante, Cedula_Estudiante,Codigo_Unico_Estudiante,Fecha, Metodo FROM vista_asistencia";
=======
        String query = "SELECT ID_Asistencia, Nombre_Estudiante, Apellido_Estudiante, Codigo_Unico_Estudiante, Fecha_Asistencia, Metodo_Asistencia FROM vista_historial_asistencia";
>>>>>>> origin/Main_Prueba
    
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
    
            while (rs.next()) {
                DTO_asistencia asistencia = new DTO_asistencia();
                asistencia.setIdAsistencia(rs.getInt("ID_Asistencia"));
                asistencia.setNombreEstudiante(rs.getString("Nombre_Estudiante")); 
                asistencia.setApellidoEstudiante(rs.getString("Apellido_Estudiante"));
<<<<<<< HEAD
                asistencia.setCedulaEstudiante(rs.getString("Cedula_Estudiante"));
                asistencia.setCodigoEstudiante(rs.getString("Codigo_Unico_Estudiante"));
                asistencia.setFechaAsistencia(rs.getTimestamp("Fecha").toLocalDateTime());
                asistencia.setMetodoAsistencia(rs.getString("Metodo"));
=======
                asistencia.setCedulaEstudiante(rs.getString("Codigo_Unico_Estudiante"));
                asistencia.setFechaAsistencia(rs.getTimestamp("Fecha_Asistencia").toLocalDateTime());
                asistencia.setMetodoAsistencia(rs.getString("Metodo_Asistencia"));
>>>>>>> origin/Main_Prueba
                asistencias.add(asistencia);
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readAll()");
        }
        return asistencias;
    }
    

    @Override
    public boolean create(DTO_asistencia entity) throws Exception {
        String query = "INSERT INTO asistencia (id_estudiante, fecha_asistencia, metodo_asistencia, fecha_registro, estado) "
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
        String query = "UPDATE asistencia SET metodo_asistencia = ?, fecha_modifica = ? WHERE id_asistencia = ?";
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
        String query = "UPDATE asistencia SET estado = ? WHERE id_asistencia = ?";
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
        String query = "SELECT COUNT(*) FROM asistencia WHERE estado = 'A'";
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
