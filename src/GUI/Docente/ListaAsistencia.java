package GUI.Docente;

<<<<<<< HEAD
import GUI.MainApp;
=======
>>>>>>> 0c8b0494b5d1edcf0792b9b9dec0c3c2367e57c0
import GUI.Pantalla;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ListaAsistencia implements Pantalla {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    // Parámetros de conexión a la base de datos
    private static final String DB_URL = "jdbc:sqlite:Database/PoliAsistencia.sqlite";


    public ListaAsistencia() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Enmarcado con título
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(50, 150, 250), 2),
                "Historial de Asistencia",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 20),
                new Color(50, 150, 250)
        ));

        // Definir columnas de la tabla
        String[] columnNames = {
            "ID Asistencia", "Nombre Estudiante", "Apellido Estudiante",
            "Código Único", "Fecha Asistencia", "Método Asistencia"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar la edición de celdas
            }
        };

        // Crear la tabla
        table = new JTable(tableModel);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(50, 150, 250));
        table.getTableHeader().setForeground(Color.WHITE);

        // Agregar la tabla a un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Cargar datos desde la vista de la base de datos
        cargarDatos();
    }

    // Método para cargar datos desde la vista de la base de datos
    private void cargarDatos() {
        String query = "SELECT * FROM vista_historial_asistencia";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idAsistencia = resultSet.getInt("ID_Asistencia");
                String nombre = resultSet.getString("Nombre_Estudiante");
                String apellido = resultSet.getString("Apellido_Estudiante");
                String codigoUnico = resultSet.getString("Codigo_Unico_Estudiante");
                Date fechaAsistencia = resultSet.getDate("Fecha_Asistencia");
                String metodoAsistencia = resultSet.getString("Metodo_Asistencia");

                // Agregar los datos a la tabla
                tableModel.addRow(new Object[]{idAsistencia, nombre, apellido, codigoUnico, fechaAsistencia, metodoAsistencia});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panel, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
