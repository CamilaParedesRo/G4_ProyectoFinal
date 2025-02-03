package BusinessLogic;

import java.util.List;

import DataAccess.DAO.DAO_asistencia;
import DataAccess.DTO.DTO_asistencia;

public class BL_asistencia {
     private DTO_asistencia asistencia;       // cache
    private DAO_asistencia sDAO = new DAO_asistencia();

    public BL_asistencia(){}

    public List<DTO_asistencia> getAll() throws Exception{
        return sDAO.readAll();
    }
    public DTO_asistencia getByIdasistencia(int idEstudiante) throws Exception{
        asistencia = sDAO.readBy(idEstudiante);
        return asistencia;
    }
    public boolean create(DTO_asistencia dto_asistencia) throws Exception{   
        return sDAO.create(dto_asistencia);
    }
    public boolean update(DTO_asistencia dto_asistencia) throws Exception{
        return sDAO.update(dto_asistencia);
    }
    public boolean delete(int idEstudiante) throws Exception{
        return sDAO.delete(idEstudiante);
    }
    public Integer getMaxRow() throws Exception{
        return sDAO.getMaxRow();
    }
    
}
