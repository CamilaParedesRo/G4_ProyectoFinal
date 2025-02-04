package BusinessLogic.Entities;
import DataAccess.DAO.DAO_asistencia;
import DataAccess.DTO.DTO_estudiante;
import DataAccess.DTO.DTO_asistencia;
import Framework.PatException;
import DataAccess.DataHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AsistenciaCedula extends DataHelper {

    private DAO_asistencia daoAsistencia;

    public AsistenciaCedula() {
        this.daoAsistencia = new DAO_asistencia();
    }

    /**
     * Busca un estudiante por su cédula en la base de datos.
     *
     * @param cedula La cédula del estudiante.
     * @return Un objeto DTO_estudiante si se encuentra, o null si no existe.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public DTO_estudiante findByCedula(String cedula) throws Exception {
        DTO_estudiante estudiante = null;
        String query = "SELECT CedulaEstudiante, IdEstudiante FROM Estudiante WHERE Estado = 'A' AND CedulaEstudiante = ?";

        try (PreparedStatement pstmt = openConnection().prepareStatement(query)) {
            pstmt.setString(1, cedula);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    estudiante = new DTO_estudiante();
                    estudiante.setCedulaEstudiante(rs.getString("CedulaEstudiante"));
                    estudiante.setIdEstudiante(rs.getInt("IdEstudiante")); // Asumiendo que existe este campo
                }
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "findByCedula()");
        }
        return estudiante;
    }

    /**
     * Registra la asistencia de un estudiante por su cédula.
     *
     * @param cedula La cédula del estudiante.
     * @return true si la asistencia se registró correctamente, false en caso contrario.
     * @throws Exception Si la cédula no existe o ocurre un error en la base de datos.
     */
    public boolean registrarAsistencia(String cedula) throws Exception {
        // Verificar si la cédula existe
        DTO_estudiante estudiante = findByCedula(cedula);
        if (estudiante == null) {
            throw new PatException("Cédula no encontrada", getClass().getName(), "registrarAsistencia()");
        }

        // Crear el DTO de asistencia
        DTO_asistencia asistencia = new DTO_asistencia();
        asistencia.setIdEstudiante(estudiante.getIdEstudiante()); // Asignar el ID del estudiante
        asistencia.setFechaAsistencia(LocalDateTime.now()); // Fecha y hora actual
        asistencia.setMetodoAsistencia("Cédula"); // Método de asistencia
        asistencia.setFechaRegistro(LocalDateTime.now()); // Fecha de registro
        asistencia.setEstado('A'); // Estado activo

        // Registrar la asistencia
        return daoAsistencia.create(asistencia);
    }

   
}