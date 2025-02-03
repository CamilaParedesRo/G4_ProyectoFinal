package GUI;

import javax.swing.*;
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
            mostrarPantalla(new RegistrarUsuarioGUI().getPanel()); 

            frame.setVisible(true);
        });
    }

    public static void mostrarPantalla(JPanel panel) {
        frame.getContentPane().removeAll();  // Eliminar cualquier componente anterior
        frame.getContentPane().add(panel);   // Añadir el nuevo panel
        frame.revalidate();  // Realizar la validación de la nueva interfaz
        frame.repaint();     // Actualizar la interfaz
    }

    // Método para redirigir a la pantalla de registro

}
