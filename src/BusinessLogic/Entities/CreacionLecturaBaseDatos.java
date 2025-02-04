package BusinessLogic.Entities;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import DataAccess.DataHelper;

import com.google.zxing.client.j2se.MatrixToImageWriter;

public class CreacionLecturaBaseDatos {

    public static void main(String[] args) {
        Set<String> processedIds = new HashSet<>(); 

        try (Connection connection = DataHelper.openConnection()) {
            // Crear la tabla QRS si no existe
            createTableIfNotExists(connection);

            String query = "SELECT * FROM estudiante";  
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String idEstudiante = rs.getString("id_estudiante");
                    String cedulaEstudiante = rs.getString("cedula_estudiante");
                    if (!processedIds.contains(idEstudiante)) {
                        // Generar el QR y convertirlo a base64
                        String qrBase64 = generate_qr(idEstudiante, cedulaEstudiante);
                        insertQRCodeInDB(qrBase64, idEstudiante, connection);
                        processedIds.add(idEstudiante); 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    // Método para crear la tabla QRS si no existe
    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS QRS ("
                + "id_QR INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "QR VARCHAR(225) UNIQUE NOT NULL, "
                + "fecha_registro DATETIME NOT NULL DEFAULT (datetime('now','localtime')), "
                + "fecha_modifica DATETIME, "
                + "estado CHAR(1) NOT NULL DEFAULT 'A', "
                + "id_estudiante INTEGER, "  
                + "FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableQuery);
            System.out.println("Tabla 'QRS' creada si no existía.");
        }
    }

    @SuppressWarnings("deprecation")
    public static String generate_qr(String imageName, String qrCodeData) {
        try {
            String filePath = "QRs/" + imageName + ".png"; 
            String charset = "UTF-8"; // Codificación UTF-8
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            // Generar el código QR
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);

            // Escribir la imagen en un archivo
            File qrFile = new File(filePath);
            qrFile.getParentFile().mkdirs(); 
            MatrixToImageWriter.writeToFile(matrix, "PNG", qrFile);
            System.out.println("QR Code image created successfully at " + filePath);

            // Convertir la imagen QR a base64
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Convertir la imagen a base64
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (Exception e) {
            System.err.println("Error al generar el QR: " + e);
            return null;
        }
    }

    // Método para insertar el código QR en la base de datos y asociarlo con un estudiante
    public static void insertQRCodeInDB(String qrBase64, String idEstudiante, Connection connection) {
        String query = "INSERT INTO QRS (QR, id_estudiante) VALUES (?, ?)"; 

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, qrBase64); 
            stmt.setString(2, idEstudiante); 
            stmt.executeUpdate();
            System.out.println("QR Code inserted successfully for student ID: " + idEstudiante);
        } catch (SQLException e) {
            System.err.println("Error al insertar en la base de datos: " + e.getMessage());
        }
    }
}
