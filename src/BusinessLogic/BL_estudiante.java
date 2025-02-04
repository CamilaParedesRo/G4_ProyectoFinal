package BusinessLogic;

import java.util.List;
import DataAccess.DAO.DAO_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class BL_estudiante {
    private DTO_estudiante estudiante;
    private DAO_estudiante sDAO = new DAO_estudiante();

    public BL_estudiante() {}

    public List<DTO_estudiante> getAll() throws Exception {
        return sDAO.readAll();
    }

    public DTO_estudiante getByIdestudiante(int idEstudiante) throws Exception {
        return sDAO.readBy(idEstudiante);
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

    public boolean findByCedula(String cedula) throws Exception {
        return sDAO.findByCedula(cedula) != null;
    }

    public Integer getMaxRow() throws Exception {
        return sDAO.getMaxRow();
    }
}
