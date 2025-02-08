package BusinessLogic.Entities;

import BusinessLogic.BL_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class MostrarPerfilEstudiantes {

    public void mostrarPerfilEstudiante(String usuarioLogueado) throws Exception {
        System.out.println("Mostrando perfil del estudiante...");
        BL_estudiante blEstudiante = new BL_estudiante();
        DTO_estudiante estudiante = blEstudiante.getByUsuario(usuarioLogueado);
    
        if (estudiante != null) {
            System.out.println("Nombre: " + estudiante.getNombreEstudiante());
            System.out.println("Apellido: " + estudiante.getApellidoEstudiante());
            System.out.println("Cédula: " + estudiante.getCedulaEstudiante());
            System.out.println("Correo: " + estudiante.getCorreoEstudiante());
            System.out.println("Usuario: " + estudiante.getUsuarioEstudiante());
        } else {
            System.out.println("No se encontró el estudiante con usuario");
        }
    }
    







}
