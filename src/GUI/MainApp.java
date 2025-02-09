package GUI;

import javax.swing.*;

import GUI.Docente.ProfesorPanel;

public class MainApp {
    private static JFrame frame;

    public static void iniciarGUI() {
        
        SwingUtilities.invokeLater(() -> {
            
            new SplashScreen().mostrar();

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
        frame.revalidate();  // Realizar la validación de la nueva interfaz
        frame.repaint();     // Actualizar la interfaz
    }

    public static void mostrarPantallaDocente(int profesorId) {
        frame.setSize(900, 600); // Tamaño grande para el panel del docente
        frame.setLocationRelativeTo(null); // Mantener centrado
        mostrarPantalla(new ProfesorPanel(profesorId).getPanel()); // Pasar el ID del profesor
    }

    public static void mostrarPantallaEstudiante() {
        frame.setSize(900, 600); // Tamaño grande para el panel del docente
        frame.setLocationRelativeTo(null); // Mantener centrado
        mostrarPantalla(new EstudiantePanel().getPanel());
    }
}
