package BusinessLogic.Entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import DataAccess.DataHelper;


public class Login {
    
    public boolean iniciarSesion(String rol, String usuario, String contraseña) {
        boolean esValido = false;
     
     // Verificar el usuario
        esValido = validarUsuario(rol, usuario, contraseña);
     
        return esValido;
    }
    
    private boolean validarUsuario(String rol, String usuario, String contraseña) {
        boolean encontrado = false;
        String query;
 
         // Ajustar los nombres de columnas según el rol
        if (rol.equals("estudiante")) {
            query = "SELECT * FROM estudiante WHERE usuario_estudiante = ? AND clave_estudiante = ?";
        } else {
            query = "SELECT * FROM profesor WHERE usuario_profesor = ? AND clave_profesor = ?";
        }
 
        try (Connection conn = DataHelper.openConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
 
        stmt.setString(1, usuario);
        stmt.setString(2, contraseña);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
        System.out.println("Inicio de sesión exitoso como " + rol);
        encontrado = true;
        }
        } catch (SQLException e) {
        System.out.println("Error en la consulta: " + e.getMessage());
            } catch (Exception e) {
        e.printStackTrace();
            } finally {
        try {
            DataHelper.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
            }
        
            return encontrado;
    }
 
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Login sistema = new Login();
            String rol = "";
            
            // Pedir al usuario que elija si es estudiante o profesor
            while (!rol.equals("estudiante") && !rol.equals("profesor")) {
        System.out.println("Seleccione su rol:");
        System.out.println("1. Estudiante");
        System.out.println("2. Profesor");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();
        sc.nextLine(); // Consumir la línea extra
            
        if (opcion == 1) {
            rol = "estudiante";
        } else if (opcion == 2) {
            rol = "profesor";
        } else {
            System.out.println("Opción inválida. Intente de nuevo.");
        }
            }
    
        // Solicitar usuario y contraseña
        System.out.print("Ingrese su usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraseña = sc.nextLine();
        
        // Validar credenciales
        if (sistema.iniciarSesion(rol, usuario, contraseña)) {
    System.out.println("Inicio de sesión exitoso.");
        } else {
    System.out.println("Usuario o contraseña incorrectos.");
        }
        
        sc.close();
    }
}
    

    
