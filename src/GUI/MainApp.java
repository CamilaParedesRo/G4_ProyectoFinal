package GUI;

import javax.swing.*;
import GUI.Docente.ProfesorPanel;

public class MainApp {
    private static JFrame frame;

    public static void iniciarGUI() {
        
        SwingUtilities.invokeLater(() -> {

            frame = new JFrame("Sistema de Registro");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 600);
            frame.setLocationRelativeTo(null);

            // Mostrar el panel de login al iniciar la aplicación
            mostrarPantalla(new LoginPanelGeneralGUI().getPanel());

            frame.setVisible(true);
        });
    }

    public static void mostrarPantalla(JPanel panel) {
        frame.getContentPane().removeAll();  // Eliminar cualquier componente anterior
        frame.getContentPane().add(panel);   // Añadir el nuevo panel
        frame.revalidate();  // Validar la nueva interfaz
        frame.repaint();     // Actualizar la UI
    }

    public static void mostrarPantallaDocente(int profesorId) {
        frame.setSize(900, 600); // Tamaño grande para el panel del docente
        frame.setLocationRelativeTo(null); // Mantener centrado
        mostrarPantalla(new ProfesorPanel(profesorId).getPanel()); // Pasar el ID del profesor
    }

    // Método para mostrar la pantalla del estudiante
    public static void mostrarPantallaEstudiante() {
        frame.setSize(900, 600); // Ajustar tamaño grande para el panel del estudiante
        frame.setLocationRelativeTo(null); // Mantener centrado
        mostrarPantalla(new EstudiantePanel().getPanel());
    }
}
