package BusinessLogic.Entities;

import java.util.Scanner;

import BusinessLogic.BL_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class LectoQRr {

    public String validarQR() {
    @SuppressWarnings("resource")
    
    Scanner scanner = new Scanner(System.in);
    System.out.println("Lectura del QR");
    return scanner.nextLine();

    }
    
    public boolean validarDataBase(String cedula) throws Exception {
        BL_estudiante blEstudiante = new BL_estudiante();
        DTO_estudiante estudiante = blEstudiante.findByCedula(cedula); // Ahora sí devuelve un DTO_estudiante
    
        if (estudiante != null) {
            System.out.println("Cédula encontrada en BD: " + estudiante.getCedulaEstudiante());
            return true;
        } else {
            System.out.println("No se encontró la cédula en la BD.");
            return false;
        }
    }
    
    

    
    
    
}