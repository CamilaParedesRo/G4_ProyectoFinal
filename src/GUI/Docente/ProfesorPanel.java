package GUI.Docente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import GUI.Pantalla;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProfesorPanel implements Pantalla {
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private int profesorId; // Variable para almacenar el ID del profesor

    private static final String DB_URL = "jdbc:sqlite:Database/PoliAsistencia.sqlite"; // Ruta a la base de datos

    public ProfesorPanel(int profesorId) {
        this.profesorId = profesorId; // Asignar el ID del profesor
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#F5F5F5")); // Fondo suave

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(Color.decode("#E3F2FD"));
        tabbedPane.setFont(new Font("Sans-Serif", Font.BOLD, 14));

        // Pestañas
        tabbedPane.addTab("Inicio", new InicioPanel().getPanel());
        tabbedPane.addTab("Listado de Estudiantes", new ListaEstudiantesPanel().getPanel());
        tabbedPane.addTab("Perfil", new InformacionProfesorPanel().getPanel());

        // Botón de cerrar sesión
        JButton cerrarSesionButton = new JButton("Cerrar sesión");
        cerrarSesionButton.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        cerrarSesionButton.setBackground(Color.decode("#FFCDD2")); // Color rojo suave
        cerrarSesionButton.setForeground(Color.WHITE);
        cerrarSesionButton.setFocusPainted(false);
        cerrarSesionButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(panel, "¿Está seguro de que desea cerrar sesión?", "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Lógica para volver a la pantalla de inicio
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(new InicioPanel().getPanel()); // Redirigir al panel de inicio de sesión
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#F5F5F5"));
        topPanel.add(cerrarSesionButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
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
            panel = new BackgroundPanel("src\\GUI\\Resource\\EPN.jpg"); // Cambiar ruta de la imagen
            panel.setLayout(new GridBagLayout());

            // Crear un panel interno para resaltar el texto
            JPanel textPanel = new JPanel();
            textPanel.setBackground(Color.WHITE);
            textPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));

            JLabel label = new JLabel("Bienvenido al sistema de gestión de asistencia");
            label.setFont(new Font("Serif", Font.BOLD, 24)); // Fuente más grande
            label.setForeground(Color.BLACK); // Letras negras
            textPanel.add(label);

            panel.add(textPanel); // Agregar el panel interno al panel principal
        }

        public JPanel getPanel() {
            return panel;
        }
    }
    
    // Panel de Listado de Estudiantes
    private class ListaEstudiantesPanel {
        private JPanel panel;
        private JTable table;
        private DefaultTableModel tableModel;

        public ListaEstudiantesPanel() {
            panel = new BackgroundPanel("src\\GUI\\Resource\\InicioPanel.png"); // Cambiar ruta de la imagen
            panel.setLayout(new BorderLayout());

            // Columnas de la tabla
            String[] columnNames = {"ID", "Nombre", "Apellido", "Código Único", "Cédula", "Correo", "Sexo", "Estado"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Tabla no editable
                }
            };

            table = new JTable(tableModel);
            table.setFont(new Font("Serif", Font.PLAIN, 14));
            table.setRowHeight(25);
            table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));
            table.getTableHeader().setBackground(Color.decode("#64B5F6")); // Azul para el encabezado
            table.getTableHeader().setForeground(Color.WHITE);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(scrollPane, BorderLayout.CENTER);

            cargarEstudiantes();
        }

        private void cargarEstudiantes() {
            String query = "SELECT * FROM vista_estudiantes"; // Usamos la vista
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID_Estudiante");
                    String nombre = resultSet.getString("Nombre_Estudiante");
                    String apellido = resultSet.getString("Apellido_Estudiante");
                    String codigo = resultSet.getString("Codigo_Unico_Estudiante");
                    String cedula = resultSet.getString("Cedula_Estudiante");
                    String correo = resultSet.getString("Correo_Estudiante");
                    String sexo = resultSet.getString("Sexo_Estudiante");
                    String estado = resultSet.getString("Estado_Estudiante");

                    // Agregar fila a la tabla
                    tableModel.addRow(new Object[]{id, nombre, apellido, codigo, cedula, correo, sexo, estado});
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar los estudiantes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    // Panel de Información del Profesor
    private class InformacionProfesorPanel {
        private JPanel panel;

        public InformacionProfesorPanel() {
            panel = new BackgroundPanel("src\\GUI\\Resource\\InformacionProfesor.png"); // Cambiar ruta de la imagen
            panel.setLayout(new GridBagLayout()); // Usamos GridBagLayout para centrar el contenido

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Diseño vertical
            infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            infoPanel.setBackground(new Color(255, 255, 255, 230)); // Fondo blanco sólido con menos transparencia

            cargarInformacionProfesor(infoPanel);

            panel.add(infoPanel); // Añadimos el panel de información al panel principal
        }

        private void cargarInformacionProfesor(JPanel infoPanel) {
            // Usamos el ID del profesor autenticado
            String query = "SELECT * FROM vista_profesores WHERE ID_Profesor = ?";

            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement statement = connection.prepareStatement(query)) {

                // Asignamos el ID del profesor autenticado al parámetro de la consulta
                statement.setInt(1, profesorId); // profesorId es la variable que almacena el ID del profesor autenticado

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String nombre = resultSet.getString("Nombre_Profesor");
                    String apellido = resultSet.getString("Apellido_Profesor");
                    String cedula = resultSet.getString("Cedula_Profesor");
                    String correo = resultSet.getString("Correo_Profesor");
                    String sexo = resultSet.getString("Sexo_Profesor");
                    String estado = resultSet.getString("Estado_Profesor");

                    JLabel lblNombre = new JLabel("Nombre: " + nombre);
                    JLabel lblApellido = new JLabel("Apellido: " + apellido);
                    JLabel lblCedula = new JLabel("Cédula: " + cedula);
                    JLabel lblCorreo = new JLabel("Correo: " + correo);
                    JLabel lblSexo = new JLabel("Sexo: " + sexo);
                    JLabel lblEstado = new JLabel("Estado: " + estado);

                    JLabel[] labels = {lblNombre, lblApellido, lblCedula, lblCorreo, lblSexo, lblEstado};

                    for (JLabel label : labels) {
                        label.setFont(new Font("Serif", Font.PLAIN, 16));
                        label.setForeground(Color.BLACK); // Cambiamos el color de las letras a negro
                        label.setAlignmentX(Component.LEFT_ALIGNMENT);
                        infoPanel.add(label);
                        infoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre etiquetas
                    }
                } else {
                    JLabel noInfo = new JLabel("No se encontró información del profesor.");
                    noInfo.setFont(new Font("Serif", Font.PLAIN, 16));
                    noInfo.setForeground(Color.RED);
                    infoPanel.add(noInfo);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar la información del profesor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    // Clase para manejar el fondo del panel
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}

