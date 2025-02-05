package GUI;

import javax.swing.*;
import DataAccess.DataHelper;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class LoginPanelGeneralGUI implements Pantalla {
    private JPanel panel;
    private Image backgroundImage;

    @SuppressWarnings("unused")
    public LoginPanelGeneralGUI() {
        // BACKGROUND
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

        loginButton.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String contraseña = new String(passField.getPassword()).trim();
            String selectedRole = (String) roleSelect.getSelectedItem();
        
            // Verificar que los campos no estén vacíos
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Error: Usuario y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            boolean validLogin = false;
            try {
                // Validar credenciales con la base de datos o lógica de autenticación
                validLogin = validarCredenciales(selectedRole, usuario, contraseña);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        
            if (validLogin) {
                // Guardar el usuario en la clase SesionUsuario
                SesionUsuario.setUsuario(usuario);  // Guardamos el usuario autenticado
        
                if (selectedRole.equals("Docente")) {
                    System.out.println("Usuario Docente autenticado. Redirigiendo a la interfaz del docente...");
                    MainApp.mostrarPantallaDocente();  // Cambia la vista y el tamaño de la ventana
                } else if (selectedRole.equals("Estudiante")) {
                    System.out.println("Usuario Estudiante autenticado. Redirigiendo a la interfaz del Estudiante...");
                    JOptionPane.showMessageDialog(panel, "Bienvenido, " + usuario + ". Redirección en proceso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    MainApp.mostrarPantallaEstudiante();  // Cambia la vista y el tamaño de la ventana
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Error: Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                loginButton.setBackground(new Color(0, 180, 0));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                loginButton.setBackground(new Color(0, 200, 0));
            }
        });

        panel.add(loginButton, gbc);

        // REGISTRARSE
        gbc.gridy++;
        JLabel registerLabel = new JLabel("Registrarse", SwingConstants.CENTER);
        registerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        registerLabel.setForeground(Color.BLACK);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setText("<html><body><span style='text-decoration:underline;'>Registrarse</span></body></html>");
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Redirigiendo a la pantalla de registro...");
                MainApp.mostrarPantalla(new RegistrarUsuarioGUI().getPanel());
            }
        });

        gbc.gridwidth = 2;
        panel.add(registerLabel, gbc);
    }

    private boolean validarCredenciales(String rol, String usuario, String contraseña) throws Exception {
        boolean esValido = false;
        String query;

        if ("Estudiante".equals(rol)) {
            query = "SELECT * FROM estudiante WHERE usuario_estudiante = ? AND clave_estudiante = ?";
        } else if ("Docente".equals(rol)) {
            query = "SELECT * FROM profesor WHERE usuario_profesor = ? AND clave_profesor = ?";
        } else {
            JOptionPane.showMessageDialog(panel, "Error: Rol inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (Connection conn = DataHelper.openConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                esValido = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panel, "Error en la consulta SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return esValido;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}




//   public class LoginPanelGeneralGUI implements Pantalla{
//     private JPanel panel;
//       private Image backgroundImage;

//       public LoginPanelGeneralGUI() {
//              BACKGROUND
//           backgroundImage = new ImageIcon("C:/Users/elian/G4_ProyectoFinal/src/GUI/Assets/fondo.png").getImage();

//           panel = new JPanel() {
//               @Override
//               protected void paintComponent(Graphics g) {
//                   super.paintComponent(g);
//                   g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//               }
//           };

//           panel.setLayout(new GridBagLayout());
//           panel.setBackground(Color.WHITE);

//           GridBagConstraints gbc = new GridBagConstraints();
//           gbc.insets = new Insets(10, 10, 10, 10);
//           gbc.fill = GridBagConstraints.HORIZONTAL;
//           gbc.gridx = 0;
//           gbc.gridy = 0;
//           gbc.gridwidth = 2;

//              LOGO
//           ImageIcon originalIcon = new ImageIcon("C:/Users/elian/G4_ProyectoFinal/src/GUI/Assets/LogoEpn.png");
//           Image image = originalIcon.getImage().getScaledInstance(290, 150, Image.SCALE_SMOOTH);
//           ImageIcon resizedIcon = new ImageIcon(image);
//           JLabel logoLabel = new JLabel(resizedIcon);
//           logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
//           panel.add(logoLabel, gbc);

//              TÍTULO
//           gbc.gridy++;
//           JLabel titleLabel = new JLabel("REGISTRO DE ASISTENCIA");
//           titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
//           titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//           panel.add(titleLabel, gbc);

//              CAMPOS DE TEXTO
//           gbc.gridy++;
//           gbc.gridwidth = 1;
//           JLabel userLabel = new JLabel("Usuario:");
//           userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//           panel.add(userLabel, gbc);

//           gbc.gridx = 1;
//           JTextField userField = new JTextField(15);
//           userField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//           panel.add(userField, gbc);

//           gbc.gridx = 0;
//           gbc.gridy++;
//           JLabel passLabel = new JLabel("Contraseña:");
//           passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//           panel.add(passLabel, gbc);

//           gbc.gridx = 1;
//           JPasswordField passField = new JPasswordField(15);
//           passField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//           panel.add(passField, gbc);

//              SELECCIÓN DE ROL
//           gbc.gridx = 0;
//           gbc.gridy++;
//           JLabel roleLabel = new JLabel("Rol:");
//           roleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//           panel.add(roleLabel, gbc);

//           gbc.gridx = 1;
//           String[] roles = {"Estudiante", "Docente"};
//           JComboBox<String> roleSelect = new JComboBox<>(roles);
//           roleSelect.setFont(new Font("Times New Roman", Font.PLAIN, 16));
//           panel.add(roleSelect, gbc);

//              BOTÓN DE INICIO DE SESIÓN
//           gbc.gridx = 0;
//           gbc.gridy++;
//           gbc.gridwidth = 2;
//           JButton loginButton = new JButton("Iniciar Sesión");
//           loginButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
//           loginButton.setBackground(new Color(0, 200, 0));
//           loginButton.setForeground(Color.WHITE);
//           loginButton.setFocusPainted(false);

//              Evento de validación al hacer clic en "Iniciar Sesión"
//           loginButton.addActionListener(e -> {
//               String usuario = userField.getText().trim();
//               String contraseña = new String(passField.getPassword()).trim();
//               String selectedRole = (String) roleSelect.getSelectedItem();

//                  Validar que usuario y contraseña no estén vacíos
//               if (usuario.isEmpty() || contraseña.isEmpty()) {
//                   JOptionPane.showMessageDialog(panel, "Error: Usuario y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
//                   return;
//               }

//                  Simulación de autenticación con "base de datos"
//               if (validarCredenciales(usuario, contraseña)) {
//                   if (selectedRole.equals("Docente")) {
//                       System.out.println("Usuario Docente autenticado. Redirigiendo a Lista de Asistencia...");
//                       MainApp.mostrarPantalla(new ListaAsistencia().getPanel());    Redirigir a la lista de asistencia
//                   } else {
//                       System.out.println("Usuario Estudiante autenticado. (Aquí se podrá redirigir a otra pantalla)");
//                       JOptionPane.showMessageDialog(panel, "Bienvenido, " + usuario + ". Redirección en proceso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
//                   }
//               } else {
//                   JOptionPane.showMessageDialog(panel, "Error: Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
//               }
//           });

//             Agregar efecto de hover
//           loginButton.addMouseListener(new MouseAdapter() {
//               @Override
//               public void mouseEntered(MouseEvent evt) {
//                   loginButton.setBackground(new Color(0, 180, 0));
//               }

//               @Override
//               public void mouseExited(MouseEvent evt) {
//                   loginButton.setBackground(new Color(0, 200, 0));
//               }
//           });

//           panel.add(loginButton, gbc);

//              REGISTRARSE (como enlace de texto)
//           gbc.gridy++;
//           JLabel registerLabel = new JLabel("Registrarse", SwingConstants.CENTER);
//           registerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
//           registerLabel.setForeground(Color.BLACK);
//           registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

//              Subrayado solo en la palabra "Registrarse"
//           registerLabel.setText("<html><body><span style='text-decoration:underline;'>Registrarse</span></body></html>");

//              Evento para redirigir a la pantalla de registro
//           registerLabel.addMouseListener(new MouseAdapter() {
//               @Override
//               public void mouseClicked(MouseEvent e) {
//                   System.out.println("Redirigiendo a la pantalla de registro...");
//                   MainApp.mostrarPantalla(new RegistrarUsuarioGUI().getPanel());    Redirigir a la pantalla de registro
//               }
//           });

//           gbc.gridwidth = 2;
//           panel.add(registerLabel, gbc);
//       }

//          Método simulado para validar credenciales (en el futuro se conectará a la base de datos)
//       private boolean validarCredenciales(String usuario, String contraseña) {
//           return usuario.equals("admin") && contraseña.equals("1234");    Simulación de usuario válido
//       }

//       @Override
//       public JPanel getPanel() {
//           return panel;
//       }
//   }

