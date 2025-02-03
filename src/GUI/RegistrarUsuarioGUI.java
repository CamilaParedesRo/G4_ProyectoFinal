package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarUsuarioGUI implements Pantalla {
    private JPanel panel;
    private Image backgroundImage;

    public RegistrarUsuarioGUI() {
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
        gbc.gridwidth = 1;

        // TÍTULO
        gbc.gridwidth = 2; // Ocupar dos columnas
        JLabel titleLabel = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Restaurar a una columna

        // CAMPOS
        addField("Nombre:", gbc, panel);
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        addField("Código Único:", gbc, panel);
        JTextField codeField = new JTextField(15);
        panel.add(codeField, gbc);

        addField("Número de Cédula:", gbc, panel);
        JTextField cedulaField = new JTextField(15);
        panel.add(cedulaField, gbc);

        addField("Usuario:", gbc, panel);
        JTextField userField = new JTextField(15);
        panel.add(userField, gbc);

        addField("Contraseña:", gbc, panel);
        JPasswordField passField = new JPasswordField(15);
        panel.add(passField, gbc);

        // BOTÓN DE REGISTRO
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Registrar");
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        registerButton.setBackground(new Color(0, 200, 0));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);

        // Evento del botón de registro con validaciones
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nameField.getText().trim();
                String codigo = codeField.getText().trim();
                String cedula = cedulaField.getText().trim();
                String usuario = userField.getText().trim();
                String contraseña = new String(passField.getPassword());

                // Validación: Todos los campos son obligatorios
                if (nombre.isEmpty() || codigo.isEmpty() || cedula.isEmpty() || usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Error: Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validaciones específicas
                if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    JOptionPane.showMessageDialog(panel, "Error: El nombre solo debe contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!codigo.matches("^\\d{9}$")) {
                    JOptionPane.showMessageDialog(panel, "Error: El código único debe tener exactamente 9 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!cedula.matches("^\\d{10}$")) {
                    JOptionPane.showMessageDialog(panel, "Error: La cédula debe tener exactamente 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Aquí en el futuro se agregará la lógica para guardar los datos en la base de datos
                System.out.println("Usuario registrado exitosamente:");
                System.out.println("Nombre: " + nombre);
                System.out.println("Código Único: " + codigo);
                System.out.println("Cédula: " + cedula);
                System.out.println("Usuario: " + usuario);
                JOptionPane.showMessageDialog(panel, "Registro exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(registerButton, gbc);

        // BOTÓN PARA VOLVER AL LOGIN
        gbc.gridy++;
        JButton backButton = new JButton("Regresar");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        backButton.setBackground(new Color(220, 50, 50)); // Rojo para destacar
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        // Evento para volver al login
        backButton.addActionListener(e -> {
            System.out.println("Volviendo al Login...");
            MainApp.mostrarPantalla(new LoginPanelGeneralGUI().getPanel()); // Redirigir al Login
        });

        panel.add(backButton, gbc);
    }

    // Método auxiliar para agregar etiquetas con alineación correcta
    private void addField(String labelText, GridBagConstraints gbc, JPanel panel) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(label, gbc);
        gbc.gridx = 1;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
