package DataAccess.DAO;

import DataAccess.DataHelper;
import DataAccess.DTO.DTO_estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_estudiante {

    public DAO_estudiante() {
        // Ya no abrimos la conexión aquí, cada método la obtiene de DataHelper
    }

    // Método para obtener todos los estudiantes
    public List<DTO_estudiante> readAll() throws SQLException {
        List<DTO_estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT * FROM estudiante"; // Cambiado de "estudiantes" a "estudiante"

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                estudiantes.add(mapResultSetToEstudiante(rs));
            }
        }
        return estudiantes;
    }

    // Método para buscar estudiante por ID
    public DTO_estudiante readBy(int idEstudiante) throws SQLException {
        DTO_estudiante estudiante = null;
        String query = "SELECT * FROM estudiante WHERE id_estudiante = ?";

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idEstudiante);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = mapResultSetToEstudiante(rs);
            }
        }
        return estudiante;
    }

    // Método para registrar un estudiante en la base de datos
    public boolean create(DTO_estudiante estudiante) throws SQLException {
        String query = "INSERT INTO estudiante (nombre_estudiante, apellido_estudiante, cedula_estudiante, "
                + "codigo_unico_estudiante, id_sexo, correo_estudiante, usuario_estudiante, clave_estudiante) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, estudiante.getNombreEstudiante());
            pstmt.setString(2, estudiante.getApellidoEstudiante());
            pstmt.setString(3, estudiante.getCedulaEstudiante());
            pstmt.setString(4, estudiante.getCodigoUnicoEstudiante()); // Nuevo campo agregado
            pstmt.setInt(5, estudiante.getId_sexo());
            pstmt.setString(6, estudiante.getCorreoEstudiante());
            pstmt.setString(7, estudiante.getUsuarioEstudiante());
            pstmt.setString(8, estudiante.getClaveEstudiante());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Método auxiliar para mapear ResultSet a DTO_estudiante
    private DTO_estudiante mapResultSetToEstudiante(ResultSet rs) throws SQLException {
        return new DTO_estudiante(
                rs.getInt("id_estudiante"),
                rs.getString("nombre_estudiante"),
                rs.getString("apellido_estudiante"),
                rs.getString("cedula_estudiante"),
                rs.getString("codigo_unico_estudiante"), // Nuevo campo agregado
                rs.getInt("id_sexo"),
                rs.getString("correo_estudiante"),
                rs.getString("usuario_estudiante"),
                rs.getString("clave_estudiante"),
                rs.getTimestamp("fecha_registro").toLocalDateTime(),
                rs.getTimestamp("fecha_modifica") != null ? rs.getTimestamp("fecha_modifica").toLocalDateTime() : null,
                rs.getString("estado").charAt(0));
    }

    public DTO_estudiante findByCedula(String cedula) throws SQLException {
        DTO_estudiante estudiante = null;
        String query = "SELECT * FROM estudiante WHERE cedula_estudiante = ?"; // Asegura que el nombre de la tabla es
                                                                               // correcto

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cedula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = new DTO_estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("nombre_estudiante"),
                        rs.getString("apellido_estudiante"),
                        rs.getString("cedula_estudiante"),
                        rs.getString("codigo_unico_estudiante"),
                        rs.getInt("id_sexo"),
                        rs.getString("correo_estudiante"),
                        rs.getString("usuario_estudiante"),
                        rs.getString("clave_estudiante"),
                        rs.getTimestamp("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime()
                                : null,
                        rs.getTimestamp("fecha_modifica") != null ? rs.getTimestamp("fecha_modifica").toLocalDateTime()
                                : null,
                        rs.getString("estado").charAt(0));
            }
        } catch (SQLException e) {
            System.out.println("Error en findByCedula: " + e.getMessage());
            throw e;
        }
        return estudiante; // Retorna null si no encuentra un estudiante
    }

    // Método para eliminar un estudiante
    public boolean delete(int idEstudiante) throws SQLException {
        String query = "DELETE FROM estudiante WHERE id_estudiante = ?";

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idEstudiante);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    // Obtener el ID máximo de estudiante
    public Integer getMaxRow() throws SQLException {
        String query = "SELECT MAX(id_estudiante) FROM estudiante";

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return null; // Retorna null si no hay registros
    }

    // Método para actualizar un estudiante en la base de datos
    public boolean update(DTO_estudiante estudiante) throws SQLException {
        String query = "UPDATE estudiante SET nombre_estudiante = ?, apellido_estudiante = ?, cedula_estudiante = ?, " +
                "codigo_unico_estudiante = ?, id_sexo = ?, correo_estudiante = ?, usuario_estudiante = ?, " +
                "clave_estudiante = ?, fecha_modifica = datetime('now','localtime') " +
                "WHERE id_estudiante = ?";

        try (Connection conn = DataHelper.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, estudiante.getNombreEstudiante());
            pstmt.setString(2, estudiante.getApellidoEstudiante());
            pstmt.setString(3, estudiante.getCedulaEstudiante());
            pstmt.setString(4, estudiante.getCodigoUnicoEstudiante());
            pstmt.setInt(5, estudiante.getId_sexo());
            pstmt.setString(6, estudiante.getCorreoEstudiante());
            pstmt.setString(7, estudiante.getUsuarioEstudiante());
            pstmt.setString(8, estudiante.getClaveEstudiante());
            pstmt.setInt(9, estudiante.getIdEstudiante());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

}
