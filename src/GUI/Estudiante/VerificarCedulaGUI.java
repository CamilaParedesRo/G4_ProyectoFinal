package GUI.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DataAccess.DAO.DAO_estudiante;  // Asegúrate de tener el DAO para interactuar con la base de datos
import DataAccess.DTO.DTO_estudiante;

public class VerificarCedulaGUI {

    private JPanel panel;
    private JTextField cedulaField;

    public VerificarCedulaGUI() {
        // Crear panel principal
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Verificar Cédula de Estudiante", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Campo para ingresar cédula
        JLabel cedulaLabel = new JLabel("Número de Cédula:");
        cedulaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(cedulaLabel, gbc);

        cedulaField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(cedulaField, gbc);

        // Botón para verificar cédula
        JButton verificarButton = new JButton("Verificar");
        verificarButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        verificarButton.setBackground(new Color(0, 150, 255));  // Azul
        verificarButton.setForeground(Color.WHITE);
        verificarButton.setFocusPainted(false);

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarCedula();
            }
        });

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(verificarButton, gbc);

        // Botón para regresar al login
        JButton backButton = new JButton("Regresar");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        backButton.setBackground(new Color(220, 50, 50));  // Rojo
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            System.out.println("Volviendo al Login...");
            // MainApp.mostrarPantalla(new LoginPanelGeneralGUI().getPanel());  // Descomentar para regresar a la pantalla de login
        });

        gbc.gridy++;
        panel.add(backButton, gbc);
    }

    // Método para verificar la cédula en la base de datos
    private void verificarCedula() {
        String cedula = cedulaField.getText().trim();

        // Validar que la cédula no esté vacía
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Por favor, ingrese un número de cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de formato de cédula (puedes ajustarlo según tus necesidades)
        if (!cedula.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(panel, "La cédula debe tener exactamente 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear objeto DAO para acceder a la base de datos
        DAO_estudiante daoEstudiante = new DAO_estudiante();
        DTO_estudiante estudiante = daoEstudiante.findByCedula(cedula);

        if (estudiante != null) {
            // Si la cédula existe en la base de datos
            JOptionPane.showMessageDialog(panel, "Cédula verificada con éxito. Bienvenido, " + estudiante.getNombre() + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Aquí puedes redirigir a la siguiente pantalla o realizar alguna acción
        } else {
            // Si la cédula no existe en la base de datos
            JOptionPane.showMessageDialog(panel, "Error: La cédula no está registrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
