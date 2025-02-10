package BusinessLogic.Entities;
import java.awt.image.BufferedImage;
import DataAccess.DataHelper;
import java.util.Map;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;


public class ObtenerQR {

    private String usuario_estudiante; 

    public ObtenerQR(String Usuario_estudiante) {
        this.usuario_estudiante = Usuario_estudiante;
    }

    public BufferedImage generarQRUsuario() {
        BufferedImage qrImage = null;

        if (usuario_estudiante == null || usuario_estudiante.isEmpty()) {
            System.out.println("Error: El nombre del estudiante es nulo o vacío.");
            return null;
        }

        try (Connection connection = DataHelper.openConnection()) {
            if (connection == null || connection.isClosed()) {
                System.out.println("Error: No se pudo establecer la conexión con la base de datos.");
                return null;
            }

            String query = "SELECT id_estudiante, cedula_estudiante FROM estudiante WHERE usuario_estudiante = ?";
            System.out.println("Ejecutando consulta con usuario: " + usuario_estudiante);

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, usuario_estudiante.trim());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String idEstudiante = rs.getString("id_estudiante");
                        String cedulaEstudiante = rs.getString("cedula_estudiante");

                        System.out.println("Usuario encontrado: ID = " + idEstudiante + ", Cédula = " + cedulaEstudiante);

                        // Generar el QR con la cédula del estudiante
                        qrImage = generateQR(cedulaEstudiante);
                    } else {
                        System.out.println("No se encontró el usuario en la base de datos.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qrImage;
    }

    private BufferedImage generateQR(String qrCodeData) {
        try {
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = Map.of(
                    EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);

            return MatrixToImageWriter.toBufferedImage(matrix);
        } catch (Exception e) {
            System.err.println("Error al generar el QR: " + e);
            return null;
        }
    }
}
