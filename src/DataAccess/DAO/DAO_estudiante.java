package DataAccess.DAO;

import DataAccess.DTO.DTO_estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_estudiante {
    private Connection conn;

    // Constructor: establece la conexión con la base de datos
    public DAO_estudiante() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "usuario", "contraseña");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los estudiantes
    public List<DTO_estudiante> readAll() throws SQLException {
        List<DTO_estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT * FROM estudiantes";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
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
        String query = "SELECT * FROM estudiantes WHERE id_estudiante = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idEstudiante);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = mapResultSetToEstudiante(rs);
            }
        }
        return estudiante;
    }

    // Método para buscar estudiante por cédula
    public DTO_estudiante findByCedula(String cedula) throws SQLException {
        DTO_estudiante estudiante = null;
        String query = "SELECT * FROM estudiantes WHERE cedula = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cedula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                estudiante = mapResultSetToEstudiante(rs);
            }
        }
        return estudiante;
    }

    // Método para registrar un estudiante en la base de datos
    public boolean create(DTO_estudiante estudiante) throws SQLException {
        String query = "INSERT INTO estudiantes (nombre, apellido, cedula, correo, usuario, clave, id_sexo, id_clase, fecha_registro, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            mapEstudianteToPreparedStatement(estudiante, pstmt);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para actualizar estudiante
    public boolean update(DTO_estudiante estudiante) throws SQLException {
        String query = "UPDATE estudiantes SET nombre = ?, apellido = ?, cedula = ?, correo = ?, usuario = ?, clave = ?, id_sexo = ?, id_clase = ?, fecha_modifica = ?, estado = ? WHERE id_estudiante = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            mapEstudianteToPreparedStatement(estudiante, pstmt);
            pstmt.setInt(11, estudiante.getIdEstudiante());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para eliminar estudiante
    public boolean delete(int idEstudiante) throws SQLException {
        String query = "DELETE FROM estudiantes WHERE id_estudiante = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idEstudiante);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para obtener el número máximo de filas en la tabla
    public int getMaxRow() throws SQLException {
        String query = "SELECT MAX(id_estudiante) FROM estudiantes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Método auxiliar para mapear ResultSet a DTO_estudiante
    private DTO_estudiante mapResultSetToEstudiante(ResultSet rs) throws SQLException {
        return new DTO_estudiante(
            rs.getInt("id_estudiante"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("cedula"),
            rs.getString("correo"),
            rs.getString("usuario"),
            rs.getString("clave"),
            rs.getObject("id_sexo") != null ? rs.getInt("id_sexo") : null,
            rs.getObject("id_clase") != null ? rs.getInt("id_clase") : null,
            rs.getTimestamp("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime() : null,
            rs.getTimestamp("fecha_modifica") != null ? rs.getTimestamp("fecha_modifica").toLocalDateTime() : null,
            rs.getString("estado").charAt(0)
        );
    }

    // Método auxiliar para mapear DTO_estudiante a PreparedStatement
    private void mapEstudianteToPreparedStatement(DTO_estudiante estudiante, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, estudiante.getNombreEstudiante());
        pstmt.setString(2, estudiante.getApellidoEstudiante());
        pstmt.setString(3, estudiante.getCedulaEstudiante());
        pstmt.setString(4, estudiante.getCorreoEstudiante());
        pstmt.setString(5, estudiante.getUsuarioEstudiante());
        pstmt.setString(6, estudiante.getClaveEstudiante());
        pstmt.setObject(7, estudiante.getId_sexo());
        pstmt.setObject(8, estudiante.getId_clase());
        pstmt.setTimestamp(9, Timestamp.valueOf(estudiante.getFechaRegistro()));
        pstmt.setString(10, String.valueOf(estudiante.getEstado()));
    }
}
