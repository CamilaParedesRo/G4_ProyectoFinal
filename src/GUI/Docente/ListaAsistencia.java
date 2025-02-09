package GUI.Docente;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import GUI.Pantalla;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ListaAsistencia implements Pantalla {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;

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

        // Ajustar el ancho de las columnas
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) { // ID Asistencia
                column.setPreferredWidth(80); // Ancho más pequeño
            } else if (i == 4) { // Fecha Asistencia
                column.setPreferredWidth(200); // Ancho más grande
            } else {
                column.setPreferredWidth(150); // Ancho por defecto para otras columnas
            }
        }

        // Agregar la tabla a un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel para el filtro por fechas
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.WHITE);

        // Campo de texto para la fecha de inicio
        fechaInicioField = new JTextField(10);
        fechaInicioField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        filtroPanel.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"));
        filtroPanel.add(fechaInicioField);

        // Campo de texto para la fecha de fin
        fechaFinField = new JTextField(10);
        fechaFinField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        filtroPanel.add(new JLabel("Fecha Fin (yyyy-MM-dd):"));
        filtroPanel.add(fechaFinField);

        // Botón para aplicar el filtro
        JButton filtrarButton = new JButton("Filtrar");
        filtrarButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        filtrarButton.setBackground(new Color(50, 150, 250));
        filtrarButton.setForeground(Color.WHITE);
        filtrarButton.addActionListener(e -> aplicarFiltro());
        filtroPanel.add(filtrarButton);

        // Agregar el panel de filtro al panel principal
        panel.add(filtroPanel, BorderLayout.NORTH);

        // Cargar datos iniciales (sin filtro)
        cargarDatos(null, null);
    }

    // Método para cargar datos desde la vista de la base de datos
    private void cargarDatos(String fechaInicio, String fechaFin) {
        // Construir la consulta SQL con el filtro de fechas
        String query = "SELECT * FROM vista_historial_asistencia WHERE 1=1";
        if (fechaInicio != null && !fechaInicio.isEmpty()) {
            query += " AND DATE(Fecha_Asistencia) >= '" + fechaInicio + "'";
        }
        if (fechaFin != null && !fechaFin.isEmpty()) {
            query += " AND DATE(Fecha_Asistencia) <= '" + fechaFin + "'";
        }
        query += " ORDER BY ID_Asistencia";

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idAsistencia = resultSet.getInt("ID_Asistencia");
                String nombre = resultSet.getString("Nombre_Estudiante");
                String apellido = resultSet.getString("Apellido_Estudiante");
                String codigoUnico = resultSet.getString("Codigo_Unico_Estudiante");

                // Obtener fecha con hora correctamente
                Timestamp timestamp = resultSet.getTimestamp("Fecha_Asistencia");
                LocalDateTime fechaAsistencia = timestamp.toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaFormateada = fechaAsistencia.format(formatter);

                String metodoAsistencia = resultSet.getString("Metodo_Asistencia");

                // Agregar los datos a la tabla con la fecha y hora formateada
                tableModel.addRow(new Object[]{idAsistencia, nombre, apellido, codigoUnico, fechaFormateada, metodoAsistencia});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panel, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para aplicar el filtro por fechas
    private void aplicarFiltro() {
        String fechaInicio = fechaInicioField.getText().trim();
        String fechaFin = fechaFinField.getText().trim();

        // Validar el formato de las fechas
        try {
            if (!fechaInicio.isEmpty()) {
                LocalDateTime.parse(fechaInicio + "T00:00:00"); // Validar formato
            }
            if (!fechaFin.isEmpty()) {
                LocalDateTime.parse(fechaFin + "T00:00:00"); // Validar formato
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(panel, "Formato de fecha inválido. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cargar datos con el filtro aplicado
        cargarDatos(fechaInicio, fechaFin);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}