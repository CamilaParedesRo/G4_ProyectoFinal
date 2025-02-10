package BusinessLogic.Entities;

import java.util.Scanner;
import BusinessLogic.BL_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class LectoQRr {

    public String validarQR() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("🔹 No se detectó escáner. Ingrese manualmente la cédula del estudiante:");
        return scanner.nextLine();
    }

    public boolean validarDataBase(String cedula) throws Exception { // Buscar estudiante por cédula
        BL_estudiante blEstudiante = new BL_estudiante();
        DTO_estudiante estudiante = blEstudiante.findByCedula(cedula); 
    
        if (estudiante != null) {
            System.out.println("Cédula encontrada en BD: " + estudiante.getCedulaEstudiante());
            return true;
        } else {
            System.out.println(" No se encontró la cédula en la BD.");
            return false;
        }
    }
}
