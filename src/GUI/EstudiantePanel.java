package GUI;

import javax.swing.*;
import java.awt.*;

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
            JComboBox<String> registrationOptions = new JComboBox<>(options);
            registrationOptions.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            gbc.gridx = 1;
            panel.add(registrationOptions, gbc);

            // Botón para continuar
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            JButton continueButton = new JButton("Continuar");
            continueButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
            continueButton.setBackground(new Color(0, 200, 0));
            continueButton.setForeground(Color.WHITE);
            continueButton.setFocusPainted(false);

            continueButton.addActionListener(e -> {
                String selectedOption = (String) registrationOptions.getSelectedItem();

                if ("Generar QR".equals(selectedOption)) {
                    System.out.println("Generar QR seleccionado.");
                    JOptionPane.showMessageDialog(panel, "Funcionalidad de Generar QR no implementada aún.");
                } else {
                    System.out.println("Ingresar Cédula seleccionado.");
                    JOptionPane.showMessageDialog(panel, "Funcionalidad de Ingresar Cédula no implementada aún.");
                }
            });

            panel.add(continueButton, gbc);
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
