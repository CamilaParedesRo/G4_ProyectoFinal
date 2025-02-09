package BusinessLogic.Entities;

import java.util.Scanner;
import BusinessLogic.BL_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class LectoQRr {

    /**
     * Método para la lectura del QR (comentado por ahora).
     * Simula la lectura del QR pidiendo manualmente la cédula si no se tiene escáner.
     */
    public String validarQR() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("🔹 No se detectó escáner. Ingrese manualmente la cédula del estudiante:");
        return scanner.nextLine();
    }

    /**
     * Valida si la cédula del estudiante existe en la base de datos.
     */
    public boolean validarDataBase(String cedula) throws Exception {
        BL_estudiante blEstudiante = new BL_estudiante();
        DTO_estudiante estudiante = blEstudiante.findByCedula(cedula); // Buscar estudiante por cédula
    
        if (estudiante != null) {
            System.out.println("Cédula encontrada en BD: " + estudiante.getCedulaEstudiante());
            return true;
        } else {
            System.out.println(" No se encontró la cédula en la BD.");
            return false;
        }
    }
}
