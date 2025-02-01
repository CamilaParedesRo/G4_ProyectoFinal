package DataAccess.DTO;
import java.time.LocalDateTime;

public class DTO_estudiante {

    // Atributos
    private int idEstudiante;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String cedulaEstudiante;
    private String correoEstudiante;
    private String usuarioEstudiante;
    private String claveEstudiante;
    private String qrEstudiante;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModifica;
    private char estado;

    
    public DTO_estudiante() {
        this.fechaRegistro = LocalDateTime.now(); 
        this.estado = 'A'; 
    }

    
    public DTO_estudiante(int idEstudiante, String nombreEstudiante, String apellidoEstudiante, String cedulaEstudiante,
                         String correoEstudiante, String usuarioEstudiante, String claveEstudiante, String qrEstudiante,
                         LocalDateTime fechaRegistro, LocalDateTime fechaModifica, char estado) {
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.usuarioEstudiante = usuarioEstudiante;
        this.claveEstudiante = claveEstudiante;
        this.qrEstudiante = qrEstudiante;
        this.fechaRegistro = fechaRegistro;
        this.fechaModifica = fechaModifica;
        this.estado = estado;
    }

    
    public DTO_estudiante(String nombreEstudiante, String apellidoEstudiante, String cedulaEstudiante,
                         String correoEstudiante, String usuarioEstudiante, String claveEstudiante, String qrEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.usuarioEstudiante = usuarioEstudiante;
        this.claveEstudiante = claveEstudiante;
        this.qrEstudiante = qrEstudiante;
        this.fechaRegistro = LocalDateTime.now(); 
        this.fechaModifica = null; 
        this.estado = 'A'; 
    }

    public DTO_estudiante(int int1, String string, String string2, String string3, String string4, String string5,
            String string6, String string7, String string8, String string9, String string10) {
        //TODO Auto-generated constructor stub
    }


    // Getters y Setters
    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante = apellidoEstudiante;
    }

    public String getCedulaEstudiante() {
        return cedulaEstudiante;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }

    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    public String getUsuarioEstudiante() {
        return usuarioEstudiante;
    }

    public void setUsuarioEstudiante(String usuarioEstudiante) {
        this.usuarioEstudiante = usuarioEstudiante;
    }

    public String getClaveEstudiante() {
        return claveEstudiante;
    }

    public void setClaveEstudiante(String claveEstudiante) {
        this.claveEstudiante = claveEstudiante;
    }

    public String getQrEstudiante() {
        return qrEstudiante;
    }

    public void setQrEstudiante(String qrEstudiante) {
        this.qrEstudiante = qrEstudiante;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(LocalDateTime fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    // Método toString para representación en texto
    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdEstudiante:         " + getIdEstudiante()
            + "\n NombreEstudiante:     " + getNombreEstudiante()
            + "\n ApellidoEstudiante:   " + getApellidoEstudiante()
            + "\n CedulaEstudiante:     " + getCedulaEstudiante()
            + "\n CorreoEstudiante:     " + getCorreoEstudiante()
            + "\n UsuarioEstudiante:    " + getUsuarioEstudiante()
            + "\n ClaveEstudiante:      " + getClaveEstudiante()
            + "\n QREstudiante:         " + getQrEstudiante()
            + "\n FechaRegistro:        " + getFechaRegistro()
            + "\n FechaModifica:        " + (getFechaModifica() != null ? getFechaModifica() : "N/A")
            + "\n Estado:               " + getEstado();
    }
}