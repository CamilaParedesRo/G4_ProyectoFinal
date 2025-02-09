package UIC.From;
import javax.swing.*;
import BusinessLogic.BL_estudiante;
import BusinessLogic.Entities.ModificarEstudiante;
import DataAccess.DTO.DTO_estudiante;
import GUI.SesionUsuario;

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
    
    @SuppressWarnings("unused")
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

        nombreLabel = new JLabel("Nombre: ");
        apellidoLabel = new JLabel("Apellido: ");
        cedulaLabel = new JLabel("Cédula: ");
        correoLabel = new JLabel("Correo: ");
        usuarioLabel = new JLabel("Usuario: ");

        // Fuente y estilo
        Font font = new Font("Arial", Font.BOLD, 14);
        nombreLabel.setFont(font);
        apellidoLabel.setFont(font);
        cedulaLabel.setFont(font);
        correoLabel.setFont(font);
        usuarioLabel.setFont(font);

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

        BL_estudiante blEstudiante = new BL_estudiante();
        ModificarEstudiante modificarEstudiante = new ModificarEstudiante(blEstudiante);
        // Botones Modificar
        JButton modificarNombre = new JButton("Modificar");
        JButton modificarApellido = new JButton("Modificar");
        JButton modificarCedula = new JButton("Modificar");
        JButton modificarCorreo = new JButton("Modificar");
        JButton modificarUsuario = new JButton("Modificar");

        // Agregar los elementos al panel
        panel.add(tituloLabel, gbc);
        gbc.gridy++;
        panel.add(statusLabel, gbc);
        gbc.gridy++;

        agregarFila(panel, nombreLabel, modificarNombre, gbc);
        agregarFila(panel, apellidoLabel, modificarApellido, gbc);
        agregarFila(panel, cedulaLabel, modificarCedula, gbc);
        agregarFila(panel, correoLabel, modificarCorreo, gbc);
        agregarFila(panel, usuarioLabel, modificarUsuario, gbc);

        modificarNombre.addActionListener(e -> {
            try {
                modificarEstudiante.modificarNombre(nombreLabel, usuarioLogueado);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        modificarApellido.addActionListener(e -> {
            try {
                modificarEstudiante.modificarApellido(apellidoLabel, usuarioLogueado);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        modificarCedula.addActionListener(e -> {
            try {
                modificarEstudiante.modificarCedula(cedulaLabel, usuarioLogueado);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        modificarCorreo.addActionListener(e -> {
            try {
                modificarEstudiante.modificarCorreo(correoLabel, usuarioLogueado);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        modificarUsuario.addActionListener(e -> {
            try {
                modificarEstudiante.modificarUsuario(usuarioLabel, usuarioLogueado);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void agregarFila(JPanel panel, JLabel label, JButton button, GridBagConstraints gbc) {
        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(button, gbc);
        gbc.gridy++;
    }

    public JPanel getPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        return container;
    }
}
