package BusinessLogic.Entities;

import DataAccess.DataHelper;
import Framework.PatException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AsistenciaCedula extends DataHelper {
    
    public boolean registrarAsistenciaPorCedula(String cedula, String metodoAsistencia) throws Exception {
    String queryVerificarEstudiante = "SELECT id_estudiante FROM estudiante WHERE cedula_estudiante = ?";
    String queryVerificarAsistencia = "SELECT COUNT(*) FROM asistencia WHERE id_estudiante = ? AND DATE(fecha_asistencia) = ?";
    String queryInsertar = "INSERT INTO asistencia (id_estudiante, fecha_asistencia, metodo_asistencia, fecha_registro, estado) "
                         + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = openConnection();
         PreparedStatement pstmtVerificarEstudiante = conn.prepareStatement(queryVerificarEstudiante)) {

        pstmtVerificarEstudiante.setString(1, cedula);
        try (ResultSet rsEstudiante = pstmtVerificarEstudiante.executeQuery()) {
            if (rsEstudiante.next()) {
                int idEstudiante = rsEstudiante.getInt("id_estudiante");

                // Verificar si ya hay un registro hoy
                try (PreparedStatement pstmtVerificarAsistencia = conn.prepareStatement(queryVerificarAsistencia)) {
                    pstmtVerificarAsistencia.setInt(1, idEstudiante);
                    try (ResultSet rsAsistencia = pstmtVerificarAsistencia.executeQuery()) {
                        if (rsAsistencia.next() && rsAsistencia.getInt(1) > 0) {
                            throw new PatException("El estudiante ya registró asistencia hoy.", getClass().getName(), "registrarAsistenciaPorCedula()");
                        }
                    }
                }

                // Insertar nuevo registro de asistencia
                try (PreparedStatement pstmtInsertar = conn.prepareStatement(queryInsertar)) {
                    LocalDateTime fechaActual = LocalDateTime.now();

                    pstmtInsertar.setInt(1, idEstudiante);
                    pstmtInsertar.setTimestamp(2, Timestamp.valueOf(fechaActual));
                    pstmtInsertar.setString(3, metodoAsistencia);
                    pstmtInsertar.setTimestamp(4, Timestamp.valueOf(fechaActual));
                    pstmtInsertar.setString(5, "A"); // Estado activo

                    pstmtInsertar.executeUpdate();
                    return true;
                }
            } else {
                throw new PatException("La cédula ingresada no existe en la base de datos.", getClass().getName(), "registrarAsistenciaPorCedula()");
            }
        }
    } catch (SQLException e) {
        throw new PatException(e.getMessage(), getClass().getName(), "registrarAsistenciaPorCedula()");
    }
}

}