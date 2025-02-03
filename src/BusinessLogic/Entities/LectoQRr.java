package BusinessLogic.Entities;

import java.util.Scanner;

import BusinessLogic.BL_estudiante;

public class LectoQRr {

    public String validarQR() {
    @SuppressWarnings("resource")
    
    Scanner scanner = new Scanner(System.in);
    System.out.println("Lectura del QR");
    return scanner.nextLine();

    }

    public boolean validarBataBase (String cedula) throws Exception {
        BL_estudiante blEstudiante = new BL_estudiante();
        if(blEstudiante.findByCedula(cedula)) {
            System.out.println("Funciona");
            return true;
        } else {
            return false;
        }
    }
}