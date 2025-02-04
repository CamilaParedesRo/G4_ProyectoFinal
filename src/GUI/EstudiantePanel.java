package GUI;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        private JComboBox<String> registrationOptions;
        private JButton continueButton, scanQRButton, showQRButton;
        private JLabel statusLabel;

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

            String[] options = {"Generar QR", "Ingresar Cédula"};
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

            gbc.gridy = 4;
            scanQRButton = new JButton("Escanear QR");
            scanQRButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
            scanQRButton.setBackground(new Color(0, 100, 200));
            scanQRButton.setForeground(Color.WHITE);
            panel.add(scanQRButton, gbc);
            scanQRButton.setVisible(false);

            // Nuevo botón para "Mostrar QR"
            gbc.gridy = 5;
            showQRButton = new JButton("Mostrar QR");
            showQRButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
            showQRButton.setBackground(new Color(0, 100, 200));
            showQRButton.setForeground(Color.WHITE);
            panel.add(showQRButton, gbc);
            showQRButton.setVisible(false);

            gbc.gridy = 6;
            statusLabel = new JLabel(" ");
            statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
            statusLabel.setForeground(Color.RED);
            panel.add(statusLabel, gbc);

            registrationOptions.addActionListener(e -> {
                boolean showCedula = "Ingresar Cédula".equals(registrationOptions.getSelectedItem());
                boolean showQR = "Generar QR".equals(registrationOptions.getSelectedItem());
                cedulaLabel.setVisible(showCedula);
                cedulaField.setVisible(showCedula);
                scanQRButton.setVisible(showQR);
                showQRButton.setVisible(showQR);
                panel.revalidate();
                panel.repaint();
            });

            continueButton.addActionListener(e -> {
                if ("Ingresar Cédula".equals(registrationOptions.getSelectedItem())) {
                    registrarAsistencia();
                }
            });

            scanQRButton.addActionListener(e -> escanearQR());

            // Acción para el nuevo botón "Mostrar QR"
            showQRButton.addActionListener(e -> mostrarQR());
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

        private void escanearQR() {
            LectoQRr lector = new LectoQRr();
            String qrData = lector.validarQR();
            try {
                if (lector.validarDataBase(qrData)) {
                    AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
                    boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(qrData, "QR");
                    statusLabel.setText(registrado ? "Asistencia registrada correctamente." : "No se pudo registrar. Puede haber duplicado.");
                    statusLabel.setForeground(registrado ? Color.GREEN : Color.RED);
                } else {
                    statusLabel.setText("Código QR no válido en la base de datos.");
                }
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        }

        // Método para mostrar el QR cuando se presiona el nuevo botón
        private void mostrarQR() {

            CreacionLecturaBaseDatos creacionLecturaBaseDatos = new CreacionLecturaBaseDatos();
            BufferedImage qrImage = creacionLecturaBaseDatos.procesarEstudiante();             // Obtener la imagen QR generada
            
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
                
                // Cambiar el texto del statusLabel
                statusLabel.setText("Mostrando QR...");
                statusLabel.setForeground(Color.BLUE);
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
