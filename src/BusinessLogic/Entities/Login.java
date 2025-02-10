package BusinessLogic.Entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DataAccess.DataHelper;

    public class Login {
        
        public boolean iniciarSesion(String rol, String usuario, String contraseña) {
            if (rol == null || usuario == null || contraseña == null) {
                System.out.println("Error: Rol, usuario o contraseña no pueden ser nulos.");
                return false;
            }
            
            boolean esValido = validarUsuario(rol, usuario, contraseña);
            return esValido;
        }
        
        private boolean validarUsuario(String rol, String usuario, String contraseña) {
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                System.out.println("Error: Usuario o contraseña no pueden estar vacíos.");
                return false;
            }

            boolean encontrado = false;
            String query;
            
            if ("estudiante".equals(rol)) {
                query = "SELECT * FROM estudiante WHERE usuario_estudiante = ? AND clave_estudiante = ?";
            } else if ("profesor".equals(rol)) {
                query = "SELECT * FROM profesor WHERE usuario_profesor = ? AND clave_profesor = ?";
            } else {
                System.out.println("Error: Rol inválido.");
                return false;
            }

            try (Connection conn = DataHelper.openConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, usuario);
                stmt.setString(2, contraseña);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    System.out.println("Inicio de sesión exitoso como " + rol);
                    encontrado = true;
                } else {
                    System.out.println("Usuario o contraseña incorrectos.");
                }
                
            } catch (SQLException e) {
                System.out.println("Error en la consulta SQL: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            } finally {
                try {
                    DataHelper.closeConnection();
                } catch (Exception e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
            
            return encontrado;
        }
    
    }
