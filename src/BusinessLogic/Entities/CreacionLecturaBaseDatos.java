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
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class CreacionLecturaBaseDatos {

    public BufferedImage procesarEstudiante() {
        Set<String> processedIds = new HashSet<>();
        BufferedImage qrImage = null; // Variable para almacenar la imagen QR final.
        
        try (Connection connection = DataHelper.openConnection()) {
            createTableIfNotExists(connection);
            String query = "SELECT * FROM estudiante";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String idEstudiante = rs.getString("id_estudiante");
                    String cedulaEstudiante = rs.getString("cedula_estudiante");
                    if (!processedIds.contains(idEstudiante)) {
                        qrImage = generate_qr(idEstudiante, cedulaEstudiante); // Genera la imagen QR
                        insertQRCodeInDB(qrImage, idEstudiante, connection);
                        processedIds.add(idEstudiante);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qrImage; // Retorna la última imagen QR generada
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
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
    public BufferedImage generate_qr(String imageName, String qrCodeData) {
        try {
            String filePath = "QRs/" + imageName + ".png"; // Ruta del archivo QR
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);

            File qrFile = new File(filePath);
            qrFile.getParentFile().mkdirs();
            MatrixToImageWriter.writeToFile(matrix, "PNG", qrFile);
            System.out.println("QR Code image created successfully at " + filePath);

            // Convertir la imagen QR en un BufferedImage
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            return qrImage;

        } catch (Exception e) {
            System.err.println("Error al generar el QR: " + e);
            return null;
        }
    }

    public void insertQRCodeInDB(BufferedImage qrImage, String idEstudiante, Connection connection) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String qrBase64 = Base64.getEncoder().encodeToString(imageBytes);

            String query = "INSERT INTO QRS (QR, id_estudiante) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, qrBase64);
                stmt.setString(2, idEstudiante);
                stmt.executeUpdate();
                System.out.println("QR Code inserted successfully for student ID: " + idEstudiante);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al insertar en la base de datos: " + e.getMessage());
        }
    }
}


