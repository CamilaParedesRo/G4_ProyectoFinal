package GUI.Docente;

import javax.swing.*;
import GUI.Pantalla;
import java.awt.*;


public class ProfesorPanel implements Pantalla {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    public ProfesorPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        // Pestañas
        tabbedPane.addTab("Inicio", new InicioPanel().getPanel());
        tabbedPane.addTab("Listado de Estudiantes", new ListaAsistencia().getPanel());
        tabbedPane.addTab("Estudiantes", new InformacionEstudiantesPanel().getPanel());
        tabbedPane.addTab("Perfil", new InformacionProfesorPanel().getPanel());

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
            panel = new JPanel();
            panel.add(new JLabel("Bienvenido al sistema de gestión de asistencia"));
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    // Panel de Información de Estudiantes
    private class InformacionEstudiantesPanel {
        private JPanel panel;

        public InformacionEstudiantesPanel() {
            panel = new JPanel();
            panel.add(new JLabel("Aquí se mostrará la información de los estudiantes"));
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    // Panel de Información del Profesor
    private class InformacionProfesorPanel {
        private JPanel panel;

        public InformacionProfesorPanel() {
            panel = new JPanel();
            panel.add(new JLabel("Información del docente"));
        }

        public JPanel getPanel() {
            return panel;
        }
    }
}
