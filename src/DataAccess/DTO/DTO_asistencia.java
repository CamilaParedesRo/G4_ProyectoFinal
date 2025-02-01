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
    
    public DTO_asistencia() {
        this.fechaAsistencia = LocalDateTime.now();
        this.fechaRegistro = LocalDateTime.now();
        this.estado = 'A';
    }

    public DTO_asistencia(int idAsistencia, int idEstudiante, LocalDateTime fechaAsistencia,
                          String metodoAsistencia) {
        this.idAsistencia = idAsistencia;
        this.idEstudiante = idEstudiante;
        this.fechaAsistencia = fechaAsistencia;
        this.metodoAsistencia = metodoAsistencia;
        this.fechaAsistencia = LocalDateTime.now();
        this.fechaRegistro = LocalDateTime.now();
        this.estado = 'A';
        
        
    }


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

    // Método toString para representación en texto
    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdAsistencia:       " + getIdAsistencia()
            + "\n IdEstudiante:       " + getIdEstudiante()
            + "\n FechaAsistencia:    " + getFechaAsistencia()
            + "\n MetodoAsistencia:   " + getMetodoAsistencia()
            + "\n FechaRegistro:      " + getFechaRegistro()
            + "\n FechaModifica:      " + (getFechaModifica() != null ? getFechaModifica() : "N/A")
            + "\n Estado:             " + getEstado();
    }
}