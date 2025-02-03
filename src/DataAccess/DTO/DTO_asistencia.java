package DataAccess.DTO;

import java.time.LocalDateTime;

public class DTO_asistencia {
    // Atributos
    private int idAsistencia;
    private int idEstudiante;
    private LocalDateTime fechaAsistencia;
    private String metodoAsistencia;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String cedulaEstudiante;
    private LocalDateTime fecha_registro;
    private LocalDateTime fecha_modifica;
    private char estado;

    // Constructor 
    public DTO_asistencia() {}

    // Constructor básico
    public DTO_asistencia(String metodoAsistencia) {
        this.metodoAsistencia = metodoAsistencia;
    }

    // Constructor con todos los atributos
    public DTO_asistencia(int idAsistencia, int idEstudiante, LocalDateTime fechaAsistencia,
                          String metodoAsistencia, LocalDateTime fecha_registro,
                          LocalDateTime fecha_modifica, char Estado ) {  
        this.idAsistencia = idAsistencia;
        this.idEstudiante = idEstudiante;
        this.fechaAsistencia = fechaAsistencia;
        this.metodoAsistencia = metodoAsistencia;
        this.fecha_registro = fecha_registro;
        this.fecha_modifica = fecha_modifica;
        this.estado = Estado;
    }

    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public LocalDateTime getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(LocalDateTime fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public String getMetodoAsistencia() {
        return metodoAsistencia;
    }

    public void setMetodoAsistencia(String metodoAsistencia) {
        this.metodoAsistencia = metodoAsistencia;
    }

    public LocalDateTime getFechaRegistro() {
        return fecha_registro;
    }

    public void setFechaRegistro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public LocalDateTime getFechaModifica() {
        return fecha_modifica;
    }

    public void setFechaModifica(LocalDateTime fecha_modifica) {
        this.fecha_modifica = fecha_modifica;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
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

    // Método toString para representación en texto
    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdAsistencia:       " + getIdAsistencia()
            + "\n Nombre Estudiante:  " + getNombreEstudiante()
            + "\n Apellido Estudiante:" + getApellidoEstudiante()
            + "\n Cédula Estudiante:  " + getCedulaEstudiante()
            + "\n FechaAsistencia:    " + getFechaAsistencia()
            + "\n MetodoAsistencia:   " + getMetodoAsistencia()
            + "\n FechaRegistro:      " + getFechaRegistro()
            + "\n FechaModifica:      " + (getFechaModifica() != null ? getFechaModifica() : "N/A")
            + "\n Estado:             " + getEstado();
    }
}
