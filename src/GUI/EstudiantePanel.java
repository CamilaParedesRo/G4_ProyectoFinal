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
        //tabbedPane.addTab("Perfil", new PerfilPanel().getPanel());

        panel.add(tabbedPane, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

     // Panel de Inicio
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
    private JTextField codigoQRField;  // Campo para mostrar el contenido del QR escaneado
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

        // Campo para mostrar el QR escaneado
        gbc.gridy = 6;
        JLabel qrLabel = new JLabel("QR Escaneado:");
        qrLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(qrLabel, gbc);
        qrLabel.setVisible(false);
        
        gbc.gridy = 7;
        codigoQRField = new JTextField(20);  // Este campo muestra el contenido del QR escaneado
        codigoQRField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(codigoQRField, gbc);
        codigoQRField.setVisible(false); // Inicialmente invisible

        gbc.gridy = 8;
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);
        statusLabel.setVisible(false); // Inicialmente invisible

        gbc.gridy = 9 ;
        showQRButton = new JButton("Mostrar QR");
        showQRButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        showQRButton.setBackground(new Color(0, 100, 200));
        showQRButton.setForeground(Color.WHITE);
        panel.add(showQRButton, gbc);
        showQRButton.setVisible(false); // Solo se muestra si se selecciona "Generar QR"


        registrationOptions.addActionListener(e -> {
            String selectedOption = (String) registrationOptions.getSelectedItem();
            
        if ("Ingresar por QR".equals(selectedOption)) { // Botones para "Generar QR"
                codigoQRField.setVisible(true); 
                cedulaLabel.setVisible(false); 
                cedulaField.setVisible(false); 
                statusLabel.setVisible(true);  
                qrLabel.setVisible(true); 
                showQRButton.setVisible(true);
                continueButton.setVisible(false);
            } else {                                    // Botones para "Ingresar Cédula"
                codigoQRField.setVisible(false);  
                cedulaLabel.setVisible(true);   
                cedulaField.setVisible(true);  
                statusLabel.setVisible(false);  
                qrLabel.setVisible(false); 
                showQRButton.setVisible(false);
                continueButton.setVisible(true);
            }
        });

        continueButton.addActionListener(e -> registrarAsistencia());
      
        showQRButton.addActionListener(e -> mostrarQR());

        // Agregar DocumentListener al campo codigoQRField para detectar cambios
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
                    ex.printStackTrace(); // Muestra errores detallados en la consola
                }
            } else {
                statusLabel.setText(" ");
            }
        }
        



        private void mostrarQR() {

            String usuarioLogueado = SesionUsuario.getUsuario(); // Recuperar usuario

            if (usuarioLogueado == null || usuarioLogueado.isEmpty()) {
                statusLabel.setText("Error: Usuario no válido.");
                statusLabel.setForeground(Color.RED);
                return;
            }
        
            CreacionLecturaBaseDatos CreacionQR = new CreacionLecturaBaseDatos(usuarioLogueado.trim()); // Evita espacios en blanco
            BufferedImage qrImage = CreacionQR.generarQRUsuario(); // Generar el QR para el usuario
        
            if (qrImage != null) {
                ImageIcon qrIcon = new ImageIcon(qrImage);
                JLabel qrLabel = new JLabel(qrIcon);
                JPanel qrPanel = new JPanel();
                qrPanel.setLayout(new BorderLayout());
                qrPanel.add(qrLabel, BorderLayout.CENTER);
        
                // Mostrar QR en una nueva ventana
                JFrame qrFrame = new JFrame("Código QR");
                qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                qrFrame.add(qrPanel);
                qrFrame.pack();
                qrFrame.setLocationRelativeTo(null);
                qrFrame.setVisible(true);
            } else {
                statusLabel.setText("No se pudo generar el QR.");
                statusLabel.setForeground(Color.RED);
            }
        }
        
        
        public JPanel getPanel() { return panel; }
}

}
    
