package GUI.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;  // Para comparar los QR

public class VerificarQRGUI {
    private JPanel panel;
    private JTextField qrField;
    private String qrGenerado;

    public VerificarQRGUI(String qrGenerado) {
        this.qrGenerado = qrGenerado; // QR generado previamente

        // Crear panel
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Verificar QR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Campo para ingresar QR escaneado
        JLabel qrLabel = new JLabel("Escanee el QR:");
        qrLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(qrLabel, gbc);

        qrField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(qrField, gbc);

        // Botón de verificación
        JButton verificarButton = new JButton("Verificar");
        verificarButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        verificarButton.setBackground(new Color(0, 150, 255));
        verificarButton.setForeground(Color.WHITE);
        verificarButton.setFocusPainted(false);

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarQR();
            }
        });

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(verificarButton, gbc);
    }

    // Método para verificar si el QR ingresado es el mismo que el generado
    private void verificarQR() {
        String qrIngresado = qrField.getText().trim();

        // Validación de QR ingresado
        if (qrIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Por favor, ingrese el QR escaneado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Comparar QR ingresado con el QR generado
        if (Objects.equals(qrIngresado, qrGenerado)) {
            JOptionPane.showMessageDialog(panel, "QR verificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
      
        } else {
            JOptionPane.showMessageDialog(panel, "Error: El QR ingresado no es correcto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
