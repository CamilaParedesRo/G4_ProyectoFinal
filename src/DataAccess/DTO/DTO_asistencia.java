package DataAccess.DTO;

import java.time.LocalDateTime;

public class DTO_asistencia {
    // Atributos
    private int idAsistencia;
    private int idEstudiante;
    private LocalDateTime fechaAsistencia;
    private String metodoAsistencia;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModifica;
    private char estado;

    // Nuevos atributos para los datos del estudiante
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String cedulaEstudiante;

   
    // Constructor por defecto
    public DTO_asistencia() {
        this.fechaAsistencia = LocalDateTime.now();
        this.fechaRegistro = LocalDateTime.now();
        this.estado = 'A';
    }

    // Constructor básico
    public DTO_asistencia(int idAsistencia, int idEstudiante, LocalDateTime fechaAsistencia, String metodoAsistencia) {
        this(idAsistencia, idEstudiante, fechaAsistencia, metodoAsistencia, LocalDateTime.now(), null, 'A');
    }

    // Constructor con todos los atributos
    public DTO_asistencia(int idAsistencia, int idEstudiante, LocalDateTime fechaAsistencia,
                          String metodoAsistencia, LocalDateTime fechaRegistro,
                          LocalDateTime fechaModifica, char estado) {
        this.idAsistencia = idAsistencia;
        this.idEstudiante = idEstudiante;
        this.fechaAsistencia = fechaAsistencia;
        this.metodoAsistencia = metodoAsistencia;
        this.fechaRegistro = fechaRegistro;
        this.fechaModifica = fechaModifica;
        this.estado = estado;
    }

    // Constructor con datos del estudiante
    public DTO_asistencia(int idAsistencia, String nombreEstudiante, String apellidoEstudiante,
                          String cedulaEstudiante, LocalDateTime fechaAsistencia, String metodoAsistencia) {
        this.idAsistencia = idAsistencia;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.fechaAsistencia = fechaAsistencia;
        this.metodoAsistencia = metodoAsistencia;
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
