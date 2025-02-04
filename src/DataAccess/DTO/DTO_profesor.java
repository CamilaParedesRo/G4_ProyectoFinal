package DataAccess.DTO;



import java.time.LocalDateTime;

public class DTO_profesor {

    // Atributos
    private int idProfesor;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String cedulaProfesor;
    private Integer id_sexo;
    private String correoProfesor;
    private String usuarioProfesor;
    private String claveProfesor;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModifica;
    private char estado;

    // Constructor vacío
    public DTO_profesor() { }

    // Constructor con todos los campos
    public DTO_profesor(int idProfesor, String nombreProfesor, String apellidoProfesor, String cedulaProfesor,
                        String correoProfesor, String usuarioProfesor, String claveProfesor,
                        LocalDateTime fechaRegistro, LocalDateTime fechaModifica, char estado) {
        this.idProfesor = idProfesor;
        this.nombreProfesor = nombreProfesor;
        this.apellidoProfesor = apellidoProfesor;
        this.cedulaProfesor = cedulaProfesor;
        this.correoProfesor = correoProfesor;
        this.usuarioProfesor = usuarioProfesor;
        this.claveProfesor = claveProfesor;
        this.fechaRegistro = fechaRegistro;
        this.fechaModifica = fechaModifica;
        this.estado = estado;
    }

    // Constructor que omite campos con valores por defecto
    public DTO_profesor(String nombreProfesor, String apellidoProfesor, String cedulaProfesor,
                        String correoProfesor, String usuarioProfesor, String claveProfesor) {
        this.nombreProfesor = nombreProfesor;
        this.apellidoProfesor = apellidoProfesor;
        this.cedulaProfesor = cedulaProfesor;
        this.correoProfesor = correoProfesor;
        this.usuarioProfesor = usuarioProfesor;
        this.claveProfesor = claveProfesor;
        this.fechaRegistro = LocalDateTime.now(); // Valor por defecto
        this.fechaModifica = null; // Valor por defecto (opcional)
        this.estado = 'A'; // Valor por defecto
    }

    // Getters y Setters
    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApellidoProfesor() {
        return apellidoProfesor;
    }

    public void setApellidoProfesor(String apellidoProfesor) {
        this.apellidoProfesor = apellidoProfesor;
    }

    public String getCedulaProfesor() {
        return cedulaProfesor;
    }

    public void setCedulaProfesor(String cedulaProfesor) {
        this.cedulaProfesor = cedulaProfesor;
    }

    public Integer getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(Integer id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getCorreoProfesor() {
        return correoProfesor;
    }

    public void setCorreoProfesor(String correoProfesor) {
        this.correoProfesor = correoProfesor;
    }

    public String getUsuarioProfesor() {
        return usuarioProfesor;
    }

    public void setUsuarioProfesor(String usuarioProfesor) {
        this.usuarioProfesor = usuarioProfesor;
    }

    public String getClaveProfesor() {
        return claveProfesor;
    }

    public void setClaveProfesor(String claveProfesor) {
        this.claveProfesor = claveProfesor;
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
            + "\n IdProfesor:         " + getIdProfesor()
            + "\n NombreProfesor:     " + getNombreProfesor()
            + "\n ApellidoProfesor:   " + getApellidoProfesor()
            + "\n CedulaProfesor:     " + getCedulaProfesor()
            + "\n CorreoProfesor:     " + getCorreoProfesor()
            + "\n UsuarioProfesor:    " + getUsuarioProfesor()
            + "\n ClaveProfesor:      " + getClaveProfesor()
            + "\n FechaRegistro:      " + getFechaRegistro()
            + "\n FechaModifica:      " + (getFechaModifica() != null ? getFechaModifica() : "N/A")
            + "\n Estado:             " + getEstado();
    }
}
