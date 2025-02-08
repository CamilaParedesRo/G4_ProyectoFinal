package BusinessLogic;

import java.util.List;
import DataAccess.DAO.DAO_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class BL_estudiante {
    private DTO_estudiante estudiante; // Cache
    private DAO_estudiante sDAO = new DAO_estudiante();

    public BL_estudiante() {}

    public List<DTO_estudiante> getAll() throws Exception {
        return sDAO.readAll();
    }

    public DTO_estudiante getByIdestudiante(int idEstudiante) throws Exception {
        estudiante = sDAO.readBy(idEstudiante);
        return estudiante;
    }

    public boolean create(DTO_estudiante dto_estudiante) throws Exception {
        return sDAO.create(dto_estudiante);
    }

    public boolean update(DTO_estudiante dto_estudiante) throws Exception {
        return sDAO.update(dto_estudiante);
    }

    public boolean delete(int idEstudiante) throws Exception {
        return sDAO.delete(idEstudiante);
    }

    public DTO_estudiante findByCedula(String cedula) throws Exception {
        return sDAO.findByCedula(cedula); // Debe devolver un DTO_estudiante o null
    }

    public Integer getMaxRow() throws Exception {
        return sDAO.getMaxRow();
    }

    public DTO_estudiante getByUsuario(String usuario) throws Exception {
        return sDAO.readByUsuario(usuario);
    }

    // Métodos para actualizar atributos individuales
    public boolean actualizarNombre(String usuario, String nuevoNombre) throws Exception {
        return sDAO.updateNombre(usuario, nuevoNombre);
    }

    public boolean actualizarApellido(String usuario, String nuevoApellido) throws Exception {
        return sDAO.updateApellido(usuario, nuevoApellido);
    }

    public boolean actualizarCedula(String usuario, String nuevaCedula) throws Exception {
        return sDAO.updateCedula(usuario, nuevaCedula);
    }

    public boolean actualizarCorreo(String usuario, String nuevoCorreo) throws Exception {
        return sDAO.updateCorreo(usuario, nuevoCorreo);
    }

    public boolean actualizarUsuario(String usuarioAntiguo, String nuevoUsuario) throws Exception {
        return sDAO.updateUsuario(usuarioAntiguo, nuevoUsuario);
    }
}
