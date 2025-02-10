package GUI;

import javax.swing.*;
import GUI.Docente.ProfesorPanel;
import GUI.Estudiante.EstudiantePanel;

public class MainApp {
    private static JFrame frame;

    public static void iniciarGUI() {
        
        SwingUtilities.invokeLater(() -> {

            frame = new JFrame("Sistema de Registro");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 600);
            frame.setLocationRelativeTo(null);

            mostrarPantalla(new LoginPanelGeneralGUI().getPanel());

            frame.setVisible(true);
        });
    }

    public static void mostrarPantalla(JPanel panel) {
        frame.getContentPane().removeAll();  
        frame.getContentPane().add(panel);   
        frame.revalidate();  
        frame.repaint();    
    }

    public static void mostrarPantallaDocente(int profesorId) {
        frame.setSize(900, 600); 
        frame.setLocationRelativeTo(null);
        mostrarPantalla(new ProfesorPanel(profesorId).getPanel());
    }

    public static void mostrarPantallaEstudiante() {
        frame.setSize(900, 600); 
        frame.setLocationRelativeTo(null); 
        mostrarPantalla(new EstudiantePanel().getPanel());
    }
}
