package DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DataAccess.DataHelper;
import DataAccess.DTO.DTO_estudiante;
import Framework.PatException;

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
            pstmt.setInt(5, estudiante.getId_sexo());
            pstmt.setString(6, estudiante.getCorreoEstudiante());
            pstmt.setString(7, estudiante.getUsuarioEstudiante());
            pstmt.setString(8, estudiante.getClaveEstudiante());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para obtener todos los estudiantes
    public List<DTO_estudiante> readAll() throws SQLException {
        List<DTO_estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT * FROM estudiante WHERE estado = 'A'";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                estudiantes.add(mapResultSetToEstudiante(rs));
            }
        }
        return estudiantes;
    }

    // Método para obtener un estudiante por ID
    public DTO_estudiante readBy(int idEstudiante) throws SQLException {
        String query = "SELECT * FROM estudiante WHERE id_estudiante = ? AND estado = 'A'";
        DTO_estudiante estudiante = null;

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idEstudiante);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = mapResultSetToEstudiante(rs);
            }
        }
        return estudiante;
    }

    // Método para obtener un estudiante por usuario
    public DTO_estudiante readByUsuario(String usuario) throws SQLException {
        String query = "SELECT * FROM estudiante WHERE usuario_estudiante = ? AND estado = 'A'";
        DTO_estudiante estudiante = null;

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = mapResultSetToEstudiante(rs);
            }
        }
        return estudiante;
    }

    // Método para actualizar un estudiante
    public boolean update(DTO_estudiante dto_estudiante) throws SQLException {
        String query = "UPDATE estudiante SET nombre_estudiante = ?, apellido_estudiante = ?, cedula_estudiante = ?, " +
                       "codigo_unico_estudiante = ?, id_sexo = ?, correo_estudiante = ?, usuario_estudiante = ?, clave_estudiante = ?, fecha_modifica = NOW() " +
                       "WHERE id_estudiante = ? AND estado = 'A'";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dto_estudiante.getNombreEstudiante());
            pstmt.setString(2, dto_estudiante.getApellidoEstudiante());
            pstmt.setString(3, dto_estudiante.getCedulaEstudiante());
            pstmt.setString(4, dto_estudiante.getCodigoUnicoEstudiante());
            pstmt.setInt(5, dto_estudiante.getId_sexo());
            pstmt.setString(6, dto_estudiante.getCorreoEstudiante());
            pstmt.setString(7, dto_estudiante.getUsuarioEstudiante());
            pstmt.setString(8, dto_estudiante.getClaveEstudiante());
            pstmt.setInt(9, dto_estudiante.getIdEstudiante());

            return pstmt.executeUpdate() > 0;
        }
    }

    public DTO_estudiante findByCedula(String cedula) throws SQLException {
        String query = "SELECT * FROM estudiante WHERE cedula_estudiante = ? AND estado = 'A'";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cedula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEstudiante(rs);
            }
        }
        return null;
    }


    // Método para eliminar un estudiante (cambia el estado a 'I')
    public boolean delete(int idEstudiante) throws SQLException {
        String query = "UPDATE estudiante SET estado = 'I', fecha_modifica = NOW() WHERE id_estudiante = ?";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idEstudiante);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para obtener el número total de estudiantes activos
    public Integer getMaxRow() throws SQLException {
        String query = "SELECT COUNT(*) FROM estudiante WHERE estado = 'A'";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

   // Método genérico para actualizar un campo específico
private boolean updateCampo(String usuario, String columna, String nuevoValor) throws Exception {
    String query = "UPDATE estudiante SET " + columna + " = ?, fecha_modifica = ? WHERE usuario_estudiante = ? AND estado = 'A'";

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    try (Connection conn = openConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, nuevoValor);
        pstmt.setString(2, dtf.format(now));
        pstmt.setString(3, usuario);

        int filasActualizadas = pstmt.executeUpdate();
        return filasActualizadas > 0;
    } catch (SQLException e) {
        throw new PatException(e.getMessage(), getClass().getName(), "updateCampo(" + columna + ")");
    }
}

// Métodos específicos que usan `updateCampo`
public boolean updateNombre(String usuario, String nuevoNombre) throws Exception {
    return updateCampo(usuario, "nombre_estudiante", nuevoNombre);
}

public boolean updateApellido(String usuario, String nuevoApellido) throws Exception {
    return updateCampo(usuario, "apellido_estudiante", nuevoApellido);
}

public boolean updateCedula(String usuario, String nuevaCedula) throws Exception {
    return updateCampo(usuario, "cedula_estudiante", nuevaCedula);
}

public boolean updateCorreo(String usuario, String nuevoCorreo) throws Exception {
    return updateCampo(usuario, "correo_estudiante", nuevoCorreo);
}

public boolean updateUsuario(String usuarioAntiguo, String nuevoUsuario) throws Exception {
    return updateCampo(usuarioAntiguo, "usuario_estudiante", nuevoUsuario);
}
    // Método para mapear un ResultSet a un DTO_estudiante
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
            rs.getTimestamp("fecha_registro").toLocalDateTime(),
            rs.getTimestamp("fecha_modifica") != null ? rs.getTimestamp("fecha_modifica").toLocalDateTime() : null,
            rs.getString("estado").charAt(0)
        );
    }
}