package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import BusinessLogic.Entities.AsistenciaCedula;
import BusinessLogic.Entities.CreacionLecturaBaseDatos;
import BusinessLogic.Entities.LectoQRr;

public class EstudiantePanel implements Pantalla {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    public EstudiantePanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.addTab("Inicio", new InicioPanel().getPanel());
        tabbedPane.addTab("Asistencia", new AsistenciaPanel().getPanel());
        tabbedPane.addTab("Perfil", new PerfilPanel().getPanel());

        panel.add(tabbedPane, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private class InicioPanel {
        private JPanel panel;

        public InicioPanel() {
            panel = new JPanel();
            panel.add(new JLabel("Bienvenido al sistema de gestión para estudiantes."));
        }

        public JPanel getPanel() {
            return panel;
        }
    }
    
private class AsistenciaPanel {
    private JPanel panel;
    private JTextField cedulaField;
    private JTextField qrInputField;  // Campo para mostrar el contenido del QR escaneado
    private JComboBox<String> registrationOptions;
    private JButton continueButton;
    private JLabel statusLabel;
    private JButton showQRButton;

    @SuppressWarnings("unused")
    public AsistenciaPanel() {
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel optionLabel = new JLabel("Elija cómo desea registrarse:");
        optionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(optionLabel, gbc);

        String[] options = {"Ingresar por QR", "Ingresar por Cédula"};
        registrationOptions = new JComboBox<>(options);
        registrationOptions.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(registrationOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel cedulaLabel = new JLabel("Ingrese su cédula:");
        cedulaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(cedulaLabel, gbc);
        cedulaLabel.setVisible(false);

        gbc.gridy = 2;
        cedulaField = new JTextField(10);
        cedulaField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(cedulaField, gbc);
        cedulaField.setVisible(false);

        gbc.gridy = 3;
        continueButton = new JButton("Registrar Asistencia");
        continueButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        continueButton.setBackground(new Color(0, 200, 0));
        continueButton.setForeground(Color.WHITE);
        panel.add(continueButton, gbc);

        // Campo para mostrar el QR escaneado
        gbc.gridy = 4;
        JLabel qrLabel = new JLabel("QR Escaneado:");
        qrLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(qrLabel, gbc);
        qrLabel.setVisible(false);
        
        gbc.gridy = 5;
        qrInputField = new JTextField(20);  // Este campo muestra el contenido del QR escaneado
        qrInputField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(qrInputField, gbc);
        qrInputField.setVisible(false); // Inicialmente invisible

        gbc.gridy = 6;
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);
        statusLabel.setVisible(false); // Inicialmente invisible

        gbc.gridy = 7;
        showQRButton = new JButton("Mostrar QR");
        showQRButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        showQRButton.setBackground(new Color(0, 100, 200));
        showQRButton.setForeground(Color.WHITE);
        panel.add(showQRButton, gbc);
        showQRButton.setVisible(false); // Solo se muestra si se selecciona "Generar QR"


        registrationOptions.addActionListener(e -> {
            String selectedOption = (String) registrationOptions.getSelectedItem();
            
        if ("Generar QR".equals(selectedOption)) { // Botones para "Generar QR"
                qrInputField.setVisible(true); 
                cedulaLabel.setVisible(false); 
                cedulaField.setVisible(false); 
                statusLabel.setVisible(true);  
                qrLabel.setVisible(true); 
                showQRButton.setVisible(true);
            } else {                                    // Botones para "Ingresar Cédula"
                qrInputField.setVisible(false);  
                cedulaLabel.setVisible(true);   
                cedulaField.setVisible(true);  
                statusLabel.setVisible(false);  
                qrLabel.setVisible(false); 
                showQRButton.setVisible(false);
            }
        });

        showQRButton.addActionListener(e -> mostrarQR());

        continueButton.addActionListener(e -> {
            if ("Ingresar Cédula".equals(registrationOptions.getSelectedItem())) {
                registrarAsistencia();
            }
        });

        // Agregar DocumentListener al campo qrInputField para detectar cambios
        qrInputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validarQRIngresado();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validarQRIngresado();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validarQRIngresado();
            }
        });
    }

    private void registrarAsistencia() {
        String cedula = cedulaField.getText().trim();
        if (!cedula.matches("\\d{10}")) {
            statusLabel.setText("Cédula inválida. Debe contener 10 dígitos numéricos.");
            return;
        }
        AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
        try {
            boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(cedula, "Manual");
            statusLabel.setText(registrado ? "Asistencia registrada correctamente." : "No se pudo registrar. Puede haber duplicado.");
            statusLabel.setForeground(registrado ? Color.GREEN : Color.RED);
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    // Método para validar el QR ingresado manualmente en qrInputField
    private void validarQRIngresado() {
        String qrData = qrInputField.getText().trim();
        
        if (!qrData.isEmpty()) {
            LectoQRr lector = new LectoQRr();
            try {
                if (lector.validarDataBase(qrData)) {
                    AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
                    boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(qrData, "QR");
                    statusLabel.setText(registrado ? "Asistencia registrada correctamente." : "No se pudo registrar. Puede haber duplicado.");
                    statusLabel.setForeground(registrado ? Color.GREEN : Color.RED);
                } else {
                    statusLabel.setText("Código QR no válido en la base de datos.");
                    statusLabel.setForeground(Color.RED);
                }
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        } else {
            statusLabel.setText(" ");
        }
    }

    private void mostrarQR() {
        CreacionLecturaBaseDatos creacionLecturaBaseDatos = new CreacionLecturaBaseDatos();
        BufferedImage qrImage = creacionLecturaBaseDatos.procesarEstudiante();  // Obtener la imagen QR generada

        if (qrImage != null) {
            ImageIcon qrIcon = new ImageIcon(qrImage);
            JLabel qrLabel = new JLabel(qrIcon);
            JPanel qrPanel = new JPanel();
            qrPanel.setLayout(new BorderLayout());
            qrPanel.add(qrLabel, BorderLayout.CENTER);
            // Mostrar el JPanel con el QR en una nueva ventana o en el mismo panel
            JFrame qrFrame = new JFrame("Código QR");
            qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            qrFrame.add(qrPanel);
            qrFrame.pack();
            qrFrame.setLocationRelativeTo(null);  // Centrar la ventana
            qrFrame.setVisible(true);
        } else {
            statusLabel.setText("No se pudo generar el QR.");
            statusLabel.setForeground(Color.RED);
        }
    }

    public JPanel getPanel() {
        return panel;
    }


}

    private class PerfilPanel {
        private JPanel panel;

        public PerfilPanel() {
            panel = new JPanel();
            panel.add(new JLabel("Información del perfil del estudiante."));
        }

        public JPanel getPanel() {
            return panel;
        }
    }
}
