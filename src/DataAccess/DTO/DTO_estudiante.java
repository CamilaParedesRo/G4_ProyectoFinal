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
    private Integer id_sexo;
    private Integer id_clase;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModifica;
    private char estado;

    // Constructor vacío
    public DTO_estudiante() {}

    // Constructor para búsqueda por cédula
    public DTO_estudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    // Constructor para registro de estudiantes
    public DTO_estudiante(String nombre, String apellido, String cedula, String correo, String usuario, String clave) {
        this.nombreEstudiante = nombre;
        this.apellidoEstudiante = apellido;
        this.cedulaEstudiante = cedula;
        this.correoEstudiante = correo;
        this.usuarioEstudiante = usuario;
        this.claveEstudiante = clave;
        this.fechaRegistro = LocalDateTime.now();
        this.estado = 'A'; // Estado activo por defecto
    }

    // Constructor completo (para la base de datos)
    public DTO_estudiante(int idEstudiante, String nombreEstudiante, String apellidoEstudiante, String cedulaEstudiante,
                          String correoEstudiante, String usuarioEstudiante, String claveEstudiante,
                          Integer id_sexo, Integer id_clase, LocalDateTime fechaRegistro, LocalDateTime fechaModifica, char estado) {
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.usuarioEstudiante = usuarioEstudiante;
        this.claveEstudiante = claveEstudiante;
        this.id_sexo = id_sexo;
        this.id_clase = id_clase;
        this.fechaRegistro = fechaRegistro;
        this.fechaModifica = fechaModifica;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getApellidoEstudiante() { return apellidoEstudiante; }
    public void setApellidoEstudiante(String apellidoEstudiante) { this.apellidoEstudiante = apellidoEstudiante; }

    public String getCedulaEstudiante() { return cedulaEstudiante; }
    public void setCedulaEstudiante(String cedulaEstudiante) { this.cedulaEstudiante = cedulaEstudiante; }

    public String getCorreoEstudiante() { return correoEstudiante; }
    public void setCorreoEstudiante(String correoEstudiante) { this.correoEstudiante = correoEstudiante; }

    public String getUsuarioEstudiante() { return usuarioEstudiante; }
    public void setUsuarioEstudiante(String usuarioEstudiante) { this.usuarioEstudiante = usuarioEstudiante; }

    public String getClaveEstudiante() { return claveEstudiante; }
    public void setClaveEstudiante(String claveEstudiante) { this.claveEstudiante = claveEstudiante; }

    public Integer getId_sexo() { return id_sexo; }
    public void setId_sexo(Integer id_sexo) { this.id_sexo = id_sexo; }

    public Integer getId_clase() { return id_clase; }
    public void setId_clase(Integer id_clase) { this.id_clase = id_clase; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public LocalDateTime getFechaModifica() { return fechaModifica; }
    public void setFechaModifica(LocalDateTime fechaModifica) { this.fechaModifica = fechaModifica; }

    public char getEstado() { return estado; }
    public void setEstado(char estado) { this.estado = estado; }
}
