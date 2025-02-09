package BusinessLogic.Entities;

import javax.swing.JOptionPane;
import javax.swing.*;
import BusinessLogic.BL_estudiante;

public class ModificarEstudiante {

    private BL_estudiante blEstudiante;

    public ModificarEstudiante(BL_estudiante blEstudiante) {
        this.blEstudiante = blEstudiante;
    }

    public void modificarNombre(JLabel nombreLabel, String usuarioLogueado) throws Exception {
        String nuevoNombre = JOptionPane.showInputDialog(null, "Ingrese nuevo nombre:", "Modificar Nombre", JOptionPane.PLAIN_MESSAGE);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            nombreLabel.setText("Nombre: " + nuevoNombre);
            blEstudiante.actualizarNombre(usuarioLogueado, nuevoNombre);
        }
    }

    public void modificarApellido(JLabel apellidoLabel, String usuarioLogueado) throws Exception {
        String nuevoApellido = JOptionPane.showInputDialog(null, "Ingrese nuevo apellido:", "Modificar Apellido", JOptionPane.PLAIN_MESSAGE);
        if (nuevoApellido != null && !nuevoApellido.trim().isEmpty()) {
            apellidoLabel.setText("Apellido: " + nuevoApellido);
            blEstudiante.actualizarApellido(usuarioLogueado, nuevoApellido);
        }
    }

    public void modificarCedula(JLabel cedulaLabel, String usuarioLogueado) throws Exception {
        String nuevaCedula = JOptionPane.showInputDialog(null, "Ingrese nueva cédula:", "Modificar Cédula", JOptionPane.PLAIN_MESSAGE);
        if (nuevaCedula != null && !nuevaCedula.trim().isEmpty()) {
            cedulaLabel.setText("Cédula: " + nuevaCedula);
            blEstudiante.actualizarCedula(usuarioLogueado, nuevaCedula);
        }
    }

    public void modificarCorreo(JLabel correoLabel, String usuarioLogueado) throws Exception {
        String nuevoCorreo = JOptionPane.showInputDialog(null, "Ingrese nuevo correo:", "Modificar Correo", JOptionPane.PLAIN_MESSAGE);
        if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
            correoLabel.setText("Correo: " + nuevoCorreo);
            blEstudiante.actualizarCorreo(usuarioLogueado, nuevoCorreo);
        }
    }

    public void modificarUsuario(JLabel usuarioLabel, String usuarioLogueado) throws Exception {
        String nuevoUsuario = JOptionPane.showInputDialog(null, "Ingrese nuevo usuario:", "Modificar Usuario", JOptionPane.PLAIN_MESSAGE);
        if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
            usuarioLabel.setText("Usuario: " + nuevoUsuario);
            blEstudiante.actualizarUsuario(usuarioLogueado, nuevoUsuario);
        }
    }
}

