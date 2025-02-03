package GUI;


import javax.swing.*;
import java.awt.*;

public class GenerarQRGUI {

    public JPanel getPanel() {
        // Crear el panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Cargar la imagen del QR desde el sistema de archivos o recursos
        String imagePath = "C:/Users/camila/G4_ProyectoFinal/QRs/Assets/1.png"; // Ruta de la imagen del QR
        ImageIcon qrImageIcon = new ImageIcon(imagePath);  // Cargar la imagen del QR

        // Verificar si la imagen fue cargada correctamente
        if (qrImageIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            JOptionPane.showMessageDialog(panel, "Error al cargar la imagen del QR.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear un JLabel para mostrar la imagen
        JLabel qrLabel = new JLabel(qrImageIcon);
        qrLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qrLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Agregar el JLabel al panel
        panel.add(qrLabel, BorderLayout.CENTER);

        // Retornar el panel que contiene la imagen del QR
        return panel;
    }
}
