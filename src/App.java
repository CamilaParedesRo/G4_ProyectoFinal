
import BusinessLogic.BL_asistencia;
import BusinessLogic.BL_estudiante;
import BusinessLogic.Entities.LectoQRr;
import GUI.MainApp;

public class App {

    public static void main(String[] args) throws Exception {
                System.out.println("Iniciando la aplicación...");
        
        // Aquí podrías agregar lógica general, carga de datos, etc.
        LectoQRr lectoQRr = new LectoQRr();
        String cedula = lectoQRr.validarQR();
        lectoQRr.validarDataBase(cedula);
        // Llamamos a la GUI
       //MainApp.iniciarGUI();
    }
}

    