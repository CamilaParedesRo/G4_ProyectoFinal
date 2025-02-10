package GUI.Estudiante;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import BusinessLogic.Entities.AsistenciaCedula;
import BusinessLogic.Entities.ObtenerQR;
import BusinessLogic.Entities.LectoQRr;
import BusinessLogic.Entities.SesionUsuario;

public class AsistenciaPanel {
    private JPanel panel;
    private JTextField cedulaField;
    private JTextField codigoQRField;
    private JComboBox<String> registrationOptions;
    private JButton continueButton;
    private JLabel statusLabel; 
    private JButton showQRButton;
    private JLabel qrImageLabel; 

    @SuppressWarnings("unused")
    public AsistenciaPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("src\\GUI\\Resource\\Fondo.jpg");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel optionLabel = new JLabel("Elija cómo desea registrarse:");
        panel.add(optionLabel, gbc);

        String[] options = {"Ingresar por QR", "Ingresar por Cédula"};
        registrationOptions = new JComboBox<>(options);
        gbc.gridx = 1;
        panel.add(registrationOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel cedulaLabel = new JLabel("Ingrese su cédula:");
        panel.add(cedulaLabel, gbc);
        cedulaLabel.setVisible(false);

        gbc.gridy = 2;
        cedulaField = new JTextField(10);
        panel.add(cedulaField, gbc);
        cedulaField.setVisible(false);

        gbc.gridy = 3;
        continueButton = new JButton("Registrar Asistencia");
        panel.add(continueButton, gbc);

        gbc.gridy = 4;
        codigoQRField = new JTextField(20);
        panel.add(codigoQRField, gbc);
        codigoQRField.setVisible(false);

        gbc.gridy = 5;
        statusLabel = new JLabel(" ");
        panel.add(statusLabel, gbc);
        statusLabel.setVisible(false);

        gbc.gridy = 6;
        showQRButton = new JButton("Mostrar QR");
        panel.add(showQRButton, gbc);
        showQRButton.setVisible(false);

        gbc.gridy = 7;
        qrImageLabel = new JLabel(); 
        panel.add(qrImageLabel, gbc);
        qrImageLabel.setVisible(false);

        registrationOptions.addActionListener(e -> {
            String selectedOption = (String) registrationOptions.getSelectedItem();
            if ("Ingresar por QR".equals(selectedOption)) {
                codigoQRField.setVisible(true);
                cedulaLabel.setVisible(false);
                cedulaField.setVisible(false);
                statusLabel.setVisible(true);
                showQRButton.setVisible(true);
                continueButton.setVisible(false);
            } else {
                codigoQRField.setVisible(false);
                cedulaLabel.setVisible(true);
                cedulaField.setVisible(true);
                statusLabel.setVisible(false);
                showQRButton.setVisible(false);
                continueButton.setVisible(true);
                qrImageLabel.setVisible(false);
            }
        });

    continueButton.addActionListener(e -> registrarAsistencia());
    showQRButton.addActionListener(e -> mostrarQR());

        codigoQRField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarQRIngresado(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarQRIngresado(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarQRIngresado(); }
        });
    }

    private void registrarAsistencia() {
        String cedula = cedulaField.getText().trim();
        if (!cedula.matches("\\d{10}")) {
            statusLabel.setText("Cédula inválida. Debe contener 10 dígitos.");
            statusLabel.setForeground(Color.RED);
            statusLabel.setVisible(true);
            return;
        }


        AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
        try {
            boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(cedula, "Manual");
            statusLabel.setText(registrado ? "Asistencia registrada." : "Registro duplicado.");
            statusLabel.setForeground(registrado ? Color.GREEN : Color.RED);
            statusLabel.setVisible(true);
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
            statusLabel.setForeground(Color.RED);
            statusLabel.setVisible(true);
        }
    }

        private void validarQRIngresado() {
            String codigoQR = codigoQRField.getText().trim().replaceAll("\"", ""); // Elimina comillas dobles
            System.out.println("QR ingresado: " + codigoQR);
        
            if (!codigoQR.isEmpty()) {
                LectoQRr lector = new LectoQRr();
                try {
                    boolean existe = lector.validarDataBase(codigoQR);
                    System.out.println("Existe en BD: " + existe);
        
                    if (existe) {
                        AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
                        boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(codigoQR, "QR");
                        System.out.println("Registro de asistencia: " + registrado);
        
                        statusLabel.setText(registrado ? "Asistencia registrada correctamente." : "No se pudo registrar. Puede haber duplicado.");
                        statusLabel.setForeground(registrado ? Color.GREEN : Color.RED);
                    } else {
                        statusLabel.setText("Código QR no válido en la base de datos.");
                        statusLabel.setForeground(Color.RED);
                    }
                } catch (Exception ex) {
                    statusLabel.setText("Error: " + ex.getMessage());
                    statusLabel.setForeground(Color.RED);
                    ex.printStackTrace(); 
                }
            } else {
                statusLabel.setText(" ");
            }
        }
    
    private void mostrarQR() {
        String usuarioLogueado = SesionUsuario.getUsuario();
        if (usuarioLogueado == null || usuarioLogueado.isEmpty()) {
            statusLabel.setText("Error: Usuario no válido.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        ObtenerQR CreacionQR = new ObtenerQR(usuarioLogueado);
        BufferedImage qrImage = CreacionQR.generarQRUsuario();
        if (qrImage != null) {
            qrImageLabel.setIcon(new ImageIcon(qrImage));
            qrImageLabel.setVisible(true);
        } else {
            statusLabel.setText("No se pudo generar el QR.");
            statusLabel.setForeground(Color.RED);
        }
    }


    public JPanel getPanel() { return panel; }
}