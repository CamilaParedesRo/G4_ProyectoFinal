package GUI.Docente;

import GUI.MainApp;

import GUI.Pantalla;
import GUI.LoginPanelGeneralGUI;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListaAsistencia implements Pantalla {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public ListaAsistencia() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Enmarcado con título
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(50, 150, 250), 2),
                "Lista de Estudiantes",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 20),
                new Color(50, 150, 250)
        ));

        // Definir columnas de la tabla
        String[] columnNames = {"ID", "Nombre", "Código", "Asistencia"};

        // Datos de prueba (luego se reemplazarán con la base de datos)
        Object[][] data = {
                {1, "Juan Pérez", "202312345", false},
                {2, "Ana Gómez", "202398765", false},
                {3, "Carlos Sánchez", "202354321", false},
                {4, "María López", "202367890", false}
        };

        // Modelo de tabla para que la columna de asistencia sea editable (checkbox)
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class; // Checkbox en columna "Asistencia"
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

        // PANEL DE BOTONES
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        // BOTÓN "GUARDAR ASISTENCIA"
        JButton saveButton = new JButton("Guardar Asistencia");
        saveButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        saveButton.setBackground(new Color(0, 150, 0)); // Verde
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAsistencia();
            }
        });

        // BOTÓN "CERRAR SESIÓN"
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        logoutButton.setBackground(new Color(200, 50, 50)); // Rojo
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cerrando sesión...");
                MainApp.mostrarPantalla(new LoginPanelGeneralGUI().getPanel()); // Redirigir a login
            }
        });

        // Agregar botones al panel de botones
        buttonPanel.add(saveButton);
        buttonPanel.add(logoutButton);

        // Agregar panel de botones en la parte inferior
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Método para guardar asistencia (simulado, en el futuro irá a la base de datos)
    private void guardarAsistencia() {
        StringBuilder asistencia = new StringBuilder("Asistencia guardada:\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String nombre = (String) tableModel.getValueAt(i, 1);
            boolean presente = (Boolean) tableModel.getValueAt(i, 3);
            asistencia.append(nombre).append(": ").append(presente ? "Presente" : "Ausente").append("\n");
        }

        JOptionPane.showMessageDialog(panel, asistencia.toString(), "Confirmación", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}