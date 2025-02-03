package GUI.Estudiante;

import GUI.MainApp;
import GUI.Pantalla;
import GUI.RegistrarUsuarioGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AsistenciaEstudiante implements Pantalla {
    private JPanel panel;
    private Image backgroundImage;

    public AsistenciaEstudiante() {
        // Cargar imagen de fondo
        backgroundImage = new ImageIcon("C:/Users/elian/G4_ProyectoFinal/src/GUI/Assets/fondo.png").getImage();

        // Crear panel con fondo
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // TÍTULO
        JLabel titleLabel = new JLabel("Registro Estudiante", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        // Opciones para el tipo de registro
        gbc.gridwidth = 1; // Volver a una sola columna
        JLabel optionLabel = new JLabel("Elija cómo desea registrarse:");
        optionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(optionLabel, gbc);

        // ComboBox para elegir el tipo de registro
        String[] options = {"Generar QR", "Ingresar Cédula"};
        JComboBox<String> registrationOptions = new JComboBox<>(options);
        registrationOptions.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(registrationOptions, gbc);

        // Botón para continuar con la selección
        gbc.gridy++;
        JButton continueButton = new JButton("Continuar");
        continueButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        continueButton.setBackground(new Color(0, 200, 0));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) registrationOptions.getSelectedItem();

                if ("Generar QR".equals(selectedOption)) {
                    // Si elige generar QR, mostrar la pantalla para generar QR
                    System.out.println("Generar QR seleccionado.");
                    MainApp.mostrarPantalla(new GenerarQRGUI().getPanel()); // Aquí deberías crear el panel GenerarQRGUI
                } else {
                    // Si elige ingresar cédula, mostrar el formulario tradicional de registro
                    System.out.println("Ingresar Cédula seleccionado.");
                    MainApp.mostrarPantalla(new RegistrarUsuarioGUI().getPanel()); // Redirigir al registro normal
                }
            }
        });

        panel.add(continueButton, gbc);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
