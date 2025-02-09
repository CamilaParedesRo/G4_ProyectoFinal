package GUI;
import javax.swing.*;

import BusinessLogic.BL_estudiante;
import DataAccess.DTO.DTO_estudiante;

import java.awt.*;


public class PerfilPanel {
    private JPanel panel;
    private JLabel tituloLabel;
    private JLabel nombreLabel;
    private JLabel apellidoLabel;
    private JLabel cedulaLabel;
    private JLabel correoLabel;
    private JLabel usuarioLabel;
    private JLabel statusLabel;

    public PerfilPanel() {
        panel = new JPanel(new GridBagLayout()); // Diseño flexible y centrado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título "Perfil"
        tituloLabel = new JLabel("Perfil", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setForeground(Color.BLUE);

        // Mensaje de estado
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        nombreLabel = new JLabel("Nombre: ", SwingConstants.CENTER);
        apellidoLabel = new JLabel("Apellido: ", SwingConstants.CENTER);
        cedulaLabel = new JLabel("Cédula: ", SwingConstants.CENTER);
        correoLabel = new JLabel("Correo: ", SwingConstants.CENTER);
        usuarioLabel = new JLabel("Usuario: ", SwingConstants.CENTER);

        // Fuente y estilo
        Font font = new Font("Arial", Font.BOLD, 14);
        nombreLabel.setFont(font);
        apellidoLabel.setFont(font);
        cedulaLabel.setFont(font);
        correoLabel.setFont(font);
        usuarioLabel.setFont(font);

        // Agregar los elementos al panel
        panel.add(tituloLabel, gbc);
        gbc.gridy++;
        panel.add(statusLabel, gbc); // Ahora está más visible
        gbc.gridy++;
        panel.add(nombreLabel, gbc);
        gbc.gridy++;
        panel.add(apellidoLabel, gbc);
        gbc.gridy++;
        panel.add(cedulaLabel, gbc);
        gbc.gridy++;
        panel.add(correoLabel, gbc);
        gbc.gridy++;
        panel.add(usuarioLabel, gbc);

        // Obtener usuario logueado
        String usuarioLogueado = SesionUsuario.getUsuario();

        if (usuarioLogueado == null || usuarioLogueado.isEmpty()) {
            statusLabel.setText("Error: Usuario no válido.");
            statusLabel.setForeground(Color.RED);
            return;
        }

        try {
            BL_estudiante blEstudiante = new BL_estudiante();
            DTO_estudiante estudiante = blEstudiante.getByUsuario(usuarioLogueado);

            if (estudiante != null) {
                nombreLabel.setText("Nombre: " + estudiante.getNombreEstudiante());
                apellidoLabel.setText("Apellido: " + estudiante.getApellidoEstudiante());
                cedulaLabel.setText("Cédula: " + estudiante.getCedulaEstudiante());
                correoLabel.setText("Correo: " + estudiante.getCorreoEstudiante());
                usuarioLabel.setText("Usuario: " + estudiante.getUsuarioEstudiante());

                statusLabel.setForeground(Color.GREEN);
            } else {
                statusLabel.setText("Error: No se encontró el estudiante.");
                statusLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error al cargar el perfil.");
            statusLabel.setForeground(Color.RED);
        }
    }

    public JPanel getPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        return container;
    }
}

