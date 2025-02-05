package GUI;

import javax.swing.*;
import GUI.Docente.ListaAsistencia;
import DataAccess.DataHelper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanelGeneralGUI implements Pantalla {
    private JPanel panel;
    private Image backgroundImage;

    public LoginPanelGeneralGUI() {
        // Cargar imagen de fondo
        backgroundImage = new ImageIcon("C:/Users/elian/G4_ProyectoFinal/src/GUI/Assets/fondo.png").getImage();

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

        // LOGO
        ImageIcon originalIcon = new ImageIcon("C:/Users/elian/G4_ProyectoFinal/src/GUI/Assets/LogoEpn.png");
        Image image = originalIcon.getImage().getScaledInstance(290, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(logoLabel, gbc);

        // TÍTULO
        gbc.gridy++;
        JLabel titleLabel = new JLabel("REGISTRO DE ASISTENCIA");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, gbc);

        // CAMPOS DE TEXTO
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        JTextField userField = new JTextField(15);
        userField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField(15);
        passField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(passField, gbc);

        // SELECCIÓN DE ROL
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel roleLabel = new JLabel("Rol:");
        roleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        String[] roles = {"Estudiante", "Docente"};
        JComboBox<String> roleSelect = new JComboBox<>(roles);
        roleSelect.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(roleSelect, gbc);

        // BOTÓN DE INICIO DE SESIÓN
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        loginButton.setBackground(new Color(0, 200, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        // Evento de validación al hacer clic en "Iniciar Sesión"
        loginButton.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String contraseña = new String(passField.getPassword()).trim();
            String selectedRole = (String) roleSelect.getSelectedItem();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: Usuario y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean validLogin = false;
            try {
                validLogin = validarCredenciales(selectedRole, usuario, contraseña);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error en la autenticación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

            if (validLogin) {
                if ("Docente".equals(selectedRole)) {
                    System.out.println("Usuario Docente autenticado. Redirigiendo a la lista de asistencia...");
                    MainApp.mostrarPantalla(new ListaAsistencia().getPanel());
                } else {
                    System.out.println("Usuario Estudiante autenticado.");
                    JOptionPane.showMessageDialog(panel, "Bienvenido, " + usuario + ". Redirección en proceso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Error: Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(loginButton, gbc);

        // BOTÓN DE REGISTRO
        gbc.gridy++;
        JButton registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        registerButton.setBackground(new Color(50, 150, 250)); // Azul para diferenciarlo
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);

        // Evento para cambiar a la pantalla de registro
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Redirigiendo a la pantalla de registro...");
                MainApp.mostrarPantalla(new RegistrarUsuarioGUI().getPanel()); // Redirigir al Registro
            }
        });

        panel.add(registerButton, gbc);
    }

    private boolean validarCredenciales(String rol, String usuario, String contraseña) throws Exception {
        boolean esValido = false;
        String query;

        if ("Estudiante".equals(rol)) {
            query = "SELECT * FROM estudiante WHERE usuario_estudiante = ? AND clave_estudiante = ?";
        } else if ("Docente".equals(rol)) {
            query = "SELECT * FROM profesor WHERE usuario_profesor = ? AND clave_profesor = ?";
        } else {
            throw new Exception("Rol inválido");
        }

        try (Connection conn = DataHelper.openConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                esValido = true;
            }
        } catch (SQLException e) {
            throw new Exception("Error en la consulta SQL: " + e.getMessage());
        }
        return esValido;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
