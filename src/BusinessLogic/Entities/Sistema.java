package BusinessLogic.Entities;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


public class Sistema {

    

public class QRGenerator {

    public static String generarQR(String cedulaEstudiante) throws WriterException, IOException {
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        
        String contenidoQR = "Cédula: " + cedulaEstudiante;

        // Generar la matriz de bits del QR
        BitMatrix bitMatrix = qrCodeWriter.encode(contenidoQR, BarcodeFormat.QR_CODE, 200, 200);

        // Convertir la matriz de bits a una imagen en formato PNG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        // Convertir la imagen a base64 para almacenarla en la base de datos
        String qrBase64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        return qrBase64;
    }
}
}

