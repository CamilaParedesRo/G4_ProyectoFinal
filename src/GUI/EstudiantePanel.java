package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BusinessLogic.Entities.AsistenciaCedula;

public class EstudiantePanel implements Pantalla {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    public EstudiantePanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        // Pestañas
        tabbedPane.addTab("Inicio", new InicioPanel().getPanel());
        tabbedPane.addTab("Asistencia", new AsistenciaPanel().getPanel());
        tabbedPane.addTab("Perfil", new PerfilPanel().getPanel());

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

    // Panel de Asistencia
    private class AsistenciaPanel {
        private JPanel panel;
        private JTextField cedulaField;
        private JComboBox<String> registrationOptions;
        private JButton continueButton;
        private JLabel statusLabel;

        public AsistenciaPanel() {
            panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Etiqueta
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            JLabel optionLabel = new JLabel("Elija cómo desea registrarse:");
            optionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            panel.add(optionLabel, gbc);

            // ComboBox para elegir el tipo de registro
            String[] options = {"Generar QR", "Ingresar Cédula"};
            registrationOptions = new JComboBox<>(options);
            registrationOptions.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            gbc.gridx = 1;
            panel.add(registrationOptions, gbc);

            // Campo de texto para la cédula (inicialmente oculto)
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            JLabel cedulaLabel = new JLabel("Ingrese su cédula:");
            cedulaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            panel.add(cedulaLabel, gbc);
            cedulaLabel.setVisible(false);

            gbc.gridy = 2;
            cedulaField = new JTextField(10);
            cedulaField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            panel.add(cedulaField, gbc);
            cedulaField.setVisible(false);

            // Botón para continuar
            gbc.gridy = 3;
            continueButton = new JButton("Registrar Asistencia");
            continueButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
            continueButton.setBackground(new Color(0, 200, 0));
            continueButton.setForeground(Color.WHITE);
            continueButton.setFocusPainted(false);
            panel.add(continueButton, gbc);

            // Etiqueta para mostrar mensajes de estado
            gbc.gridy = 4;
            statusLabel = new JLabel(" ");
            statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
            statusLabel.setForeground(Color.RED);
            panel.add(statusLabel, gbc);

            // Evento del JComboBox para mostrar u ocultar el campo de cédula
            registrationOptions.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean showCedula = "Ingresar Cédula".equals(registrationOptions.getSelectedItem());
                    cedulaLabel.setVisible(showCedula);
                    cedulaField.setVisible(showCedula);
                    panel.revalidate();
                    panel.repaint();
                }
            });

            // Evento del botón
            continueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ("Ingresar Cédula".equals(registrationOptions.getSelectedItem())) {
                        registrarAsistencia();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Funcionalidad de Generar QR no implementada aún.");
                    }
                }
            });
        }

        private void registrarAsistencia() {
            String cedula = cedulaField.getText().trim();

            if (!validarCedula(cedula)) {
                statusLabel.setText("Cédula inválida. Debe contener 10 dígitos numéricos.");
                return;
            }

            AsistenciaCedula asistenciaCedula = new AsistenciaCedula();
            try {
                boolean registrado = asistenciaCedula.registrarAsistenciaPorCedula(cedula, "Manual");
                if (registrado) {
                    statusLabel.setText("Asistencia registrada correctamente.");
                    statusLabel.setForeground(Color.GREEN);
                } else {
                    statusLabel.setText("No se pudo registrar. Puede haber duplicado.");
                    statusLabel.setForeground(Color.RED);
                }
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        }

        private boolean validarCedula(String cedula) {
            return cedula.matches("\\d{10}");
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    // Panel de Perfil
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