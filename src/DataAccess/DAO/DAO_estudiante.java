package DataAccess.DAO;

import DataAccess.DataHelper;
import DataAccess.DTO.DTO_estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_estudiante extends DataHelper {

    // Método para registrar un nuevo estudiante
    public boolean create(DTO_estudiante estudiante) throws SQLException {
        String query = "INSERT INTO estudiante (nombre_estudiante, apellido_estudiante, cedula_estudiante, " +
                    "codigo_unico_estudiante, id_sexo, correo_estudiante, usuario_estudiante, clave_estudiante) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, estudiante.getNombreEstudiante());
            pstmt.setString(2, estudiante.getApellidoEstudiante());
            pstmt.setString(3, estudiante.getCedulaEstudiante());
            pstmt.setString(4, estudiante.getCodigoUnicoEstudiante());
            pstmt.setInt(5, estudiante.getId_sexo()); // Aseguramos que id_sexo se pase correctamente
            pstmt.setString(6, estudiante.getCorreoEstudiante());
            pstmt.setString(7, estudiante.getUsuarioEstudiante());
            pstmt.setString(8, estudiante.getClaveEstudiante());

            return pstmt.executeUpdate() > 0; // Devuelve `true` si la inserción fue exitosa
        }
    }

    // Buscar estudiante por cédula
    public DTO_estudiante findByCedula(String cedula) throws SQLException {
        DTO_estudiante estudiante = null;
        String query = "SELECT * FROM estudiante WHERE cedula_estudiante = ? AND estado = 'A'";

        try (Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cedula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = mapResultSetToEstudiante(rs);
            }
        }
        return estudiante;
    }

    // Método auxiliar para mapear un ResultSet a un DTO_estudiante
    private DTO_estudiante mapResultSetToEstudiante(ResultSet rs) throws SQLException {
        return new DTO_estudiante(
                rs.getInt("id_estudiante"),
                rs.getString("nombre_estudiante"),
                rs.getString("apellido_estudiante"),
                rs.getString("cedula_estudiante"),
                rs.getString("codigo_unico_estudiante"),
                rs.getInt("id_sexo"),
                rs.getString("correo_estudiante"),
                rs.getString("usuario_estudiante"),
                rs.getString("clave_estudiante"),
                rs.getTimestamp("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime() : null,
                rs.getTimestamp("fecha_modifica") != null ? rs.getTimestamp("fecha_modifica").toLocalDateTime() : null,
                rs.getString("estado").charAt(0)
        );
    }
}
