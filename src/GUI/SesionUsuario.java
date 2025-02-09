package GUI;

public class SesionUsuario {
    private static String usuario;

    // Establecer el usuario
    public static void setUsuario(String user) {
        usuario = user;
    }

    // Obtener el usuario
    public static String getUsuario() {
        return usuario;
    }
}
