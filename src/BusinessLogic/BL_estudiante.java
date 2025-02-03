package BusinessLogic;

import java.util.List;

import DataAccess.DAO.DAO_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class BL_estudiante {
     private DTO_estudiante estudiante;       // cache
    private DAO_estudiante sDAO = new DAO_estudiante();

    public BL_estudiante(){}

    public List<DTO_estudiante> getAll() throws Exception{
        return sDAO.readAll();
    }
    public DTO_estudiante getByIdestudiante(int idEstudiante) throws Exception{
        estudiante = sDAO.readBy(idEstudiante);
        return estudiante;
    }
    public boolean create(DTO_estudiante dto_estudiante) throws Exception{   
        return sDAO.create(dto_estudiante);
    }
    public boolean update(DTO_estudiante dto_estudiante) throws Exception{
        return sDAO.update(dto_estudiante);
    }
    public boolean delete(int idEstudiante) throws Exception{
        return sDAO.delete(idEstudiante);
    }
    public boolean findByCedula(String cedula) throws Exception{
        if (sDAO.findByCedula(cedula) == null) {
            return true;
        } else {
            return false;
        }
      
    }
    public Integer getMaxRow() throws Exception{
        return sDAO.getMaxRow();
    }
}

