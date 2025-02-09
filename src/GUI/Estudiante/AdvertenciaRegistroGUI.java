package GUI.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DataAccess.DAO.DAO_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class AdvertenciaRegistroGUI {
    private JPanel panel;
    private JTextField cedulaField;

    public AdvertenciaRegistroGUI() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Verificar Registro", SwingConstants.CENTER);
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

        // Botón de verificación
        JButton verificarButton = new JButton("Verificar Registro");
        verificarButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        verificarButton.setBackground(new Color(0, 150, 255));
        verificarButton.setForeground(Color.WHITE);
        verificarButton.setFocusPainted(false);

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarRegistro();
            }
        });

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(verificarButton, gbc);
    }

    // Método para verificar si el estudiante ya está registrado
    private void verificarRegistro() {
        String cedula = cedulaField.getText().trim();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Por favor, ingrese un número de cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DAO_estudiante daoEstudiante = new DAO_estudiante();
            DTO_estudiante estudiante = daoEstudiante.findByCedula(cedula);

            if (estudiante != null) {
                JOptionPane.showMessageDialog(panel, "Advertencia: Ya estás registrado en el sistema.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Cédula no registrada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Error al verificar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
