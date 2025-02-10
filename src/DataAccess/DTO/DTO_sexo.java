package DataAccess.DTO;

import java.time.LocalDateTime;

public class DTO_sexo {

    private Integer id_sexo;
    private String nombre_sexo;
    private LocalDateTime fecha_registro;
    private LocalDateTime fecha_modifica;
    private String estado;

    public DTO_sexo() {}

    public DTO_sexo(String nombreSexo) {
        this.nombre_sexo = nombreSexo;
    }

    public DTO_sexo(Integer idSexo, String nombreSexo, LocalDateTime fechaRegistro, 
                    LocalDateTime fechaModifica, String estado) {
        this.id_sexo = idSexo;
        this.nombre_sexo = nombreSexo;
        this.fecha_registro = fechaRegistro;
        this.fecha_modifica = fechaModifica;
        this.estado = estado;
    }

    public Integer getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(Integer id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getNombre_sexo() {
        return nombre_sexo;
    }

    public void setNombre_sexo(String nombre_sexo) {
        this.nombre_sexo = nombre_sexo;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public LocalDateTime getFecha_modifica() {
        return fecha_modifica;
    }

    public void setFecha_modifica(LocalDateTime fecha_modifica) {
        this.fecha_modifica = fecha_modifica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getClass().getName()
            + "\n IdSexo:         " + getId_sexo()
            + "\n NombreSexo:     " + getNombre_sexo()
            + "\n FechaRegistro:        " + getFecha_registro()
            + "\n FechaModifica:        " + (getFecha_modifica() != null ? getFecha_modifica() : "N/A")
            + "\n Estado:               " + getEstado();
    }
}
