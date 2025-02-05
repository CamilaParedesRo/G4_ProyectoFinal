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
        private JTextField qrInputField;
        private JComboBox<String> registrationOptions;
        private JButton continueButton;
        private JLabel statusLabel;
        private JButton showQRButton;

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
            qrInputField = new JTextField(20);
            panel.add(qrInputField, gbc);
            qrInputField.setVisible(false);

            gbc.gridy = 5;
            statusLabel = new JLabel(" ");
            panel.add(statusLabel, gbc);
            statusLabel.setVisible(false);

            gbc.gridy = 6;
            showQRButton = new JButton("Mostrar QR");
            panel.add(showQRButton, gbc);
            showQRButton.setVisible(false);

            registrationOptions.addActionListener(e -> toggleInputs());
            continueButton.addActionListener(e -> registrarAsistencia());
            showQRButton.addActionListener(e -> mostrarQR());

            qrInputField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { validarQRIngresado(); }
                @Override
                public void removeUpdate(DocumentEvent e) { validarQRIngresado(); }
                @Override
                public void changedUpdate(DocumentEvent e) { validarQRIngresado(); }
            });
        }

        private void toggleInputs() {
            String selectedOption = (String) registrationOptions.getSelectedItem();
            boolean isQR = "Ingresar por QR".equals(selectedOption);
            qrInputField.setVisible(isQR);
            showQRButton.setVisible(isQR);
            cedulaField.setVisible(!isQR);
            statusLabel.setVisible(true);
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
            String qrData = qrInputField.getText().trim();
            if (!qrData.isEmpty()) {
                LectoQRr lector = new LectoQRr();
                try {
                    if (lector.validarDataBase(qrData)) {
                        AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
                        boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(qrData, "QR");
                        statusLabel.setText(registrado ? "Asistencia registrada." : "Registro duplicado.");
                        statusLabel.setForeground(registrado ? Color.GREEN : Color.RED);
                    } else {
                        statusLabel.setText("Código QR no válido.");
                        statusLabel.setForeground(Color.RED);
                    }
                    statusLabel.setVisible(true);
                } catch (Exception ex) {
                    statusLabel.setText("Error: " + ex.getMessage());
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setVisible(true);
                }
            }
        }

        private void mostrarQR() {
            CreacionLecturaBaseDatos baseDatos = new CreacionLecturaBaseDatos();
            BufferedImage qrImage = baseDatos.procesarEstudiante();
            if (qrImage != null) {
                JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(qrImage)), "Código QR", JOptionPane.PLAIN_MESSAGE);
            } else {
                statusLabel.setText("No se pudo generar el QR.");
                statusLabel.setForeground(Color.RED);
                statusLabel.setVisible(true);
            }
        }

        public JPanel getPanel() { return panel; }
    }
}
