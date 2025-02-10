package GUI.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.LoginPanelGeneralGUI;
import GUI.Pantalla;

public class EstudiantePanel implements Pantalla {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    public EstudiantePanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Crear un panel superior para el botón de cerrar sesión
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(createLogoutButton(), BorderLayout.EAST);
        
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.addTab("Inicio", new InicioPanel().getPanel());
        tabbedPane.addTab("Asistencia", new AsistenciaPanel().getPanel());
        tabbedPane.addTab("Perfil", new PerfilPanel().getPanel());

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
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("src\\GUI\\Resource\\EPN.jpg");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridBagLayout()); // Centrar el texto en el panel
        

        // Crear un panel para el texto con borde
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(255, 255, 255, 180)); // Blanco con transparencia
        textPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Borde negro de 3 px
        textPanel.setLayout(new BorderLayout()); 

        JLabel texto = new JLabel("Bienvenido al sistema de gestión para estudiantes.", SwingConstants.CENTER);
        texto.setFont(new Font("Serif", Font.BOLD, 20));
        texto.setForeground(Color.BLACK);
        textPanel.add(texto, BorderLayout.CENTER);

        // Ajustar tamaño del panel de texto
        textPanel.setPreferredSize(new Dimension(500, 100)); // Ajusta el tamaño según necesites

        // Agregar textPanel al panel principal
        panel.add(textPanel);
    }

    public JPanel getPanel() {
        return panel;
    }
}

    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("Serif", Font.BOLD, 14));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cerrarSesion();
                }
            }
        });
        return logoutButton;
    }

    private void cerrarSesion() {
        JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanelGeneralGUI().getPanel());
            frame.revalidate();
            frame.repaint();
        }
    }

    
  

}
    
