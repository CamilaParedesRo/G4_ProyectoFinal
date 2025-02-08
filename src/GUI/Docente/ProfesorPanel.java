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

    private static final String DB_URL = "jdbc:sqlite:Database/PoliAsistencia.sqlite"; // Ruta a la base de datos

    public ProfesorPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#F5F5F5")); // Fondo suave

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(Color.decode("#E3F2FD")); // Color de pestañas
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
            panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.decode("#BBDEFB")); // Fondo azul claro

            JLabel label = new JLabel("Bienvenido al sistema de gestión de asistencia");
            label.setFont(new Font("Sans-Serif", Font.BOLD, 18));
            label.setForeground(Color.decode("#1E88E5")); // Azul oscuro

            panel.add(label);
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
            panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.decode("#F5F5F5"));

            // Columnas de la tabla
            String[] columnNames = {"ID", "Nombre", "Apellido", "Código Único", "Cédula", "Correo", "Sexo", "Estado"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Tabla no editable
                }
            };

            table = new JTable(tableModel);
            table.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
            table.setRowHeight(25);
            table.getTableHeader().setFont(new Font("Sans-Serif", Font.BOLD, 14));
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
            panel = new JPanel(new GridLayout(0, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(Color.decode("#E3F2FD")); // Azul claro

            cargarInformacionProfesor();
        }

        private void cargarInformacionProfesor() {
            String query = "SELECT * FROM vista_profesores WHERE ID_Profesor = 1"; // Cambiar el ID según el profesor autenticado

            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement statement = connection.prepareStatement(query)) {

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
                        label.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
                        label.setForeground(Color.decode("#1E88E5")); // Azul oscuro
                        panel.add(label);
                    }
                } else {
                    JLabel noInfo = new JLabel("No se encontró información del profesor.");
                    noInfo.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
                    noInfo.setForeground(Color.RED);
                    panel.add(noInfo);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel, "Error al cargar la información del profesor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public JPanel getPanel() {
            return panel;
        }
    }
}

