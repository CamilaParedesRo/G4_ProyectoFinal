package BusinessLogic;

import java.util.List;
import DataAccess.DAO.DAO_estudiante;
import DataAccess.DTO.DTO_estudiante;

public class BL_estudiante {
    private DAO_estudiante sDAO = new DAO_estudiante();

    public BL_estudiante() {}

    // Obtener todos los estudiantes
    public List<DTO_estudiante> getAll() throws Exception {
        return sDAO.readAll();
    }

    // Obtener un estudiante por su ID
    public DTO_estudiante getByIdEstudiante(int idEstudiante) throws Exception {
        return sDAO.readBy(idEstudiante);
    }

    // Crear un estudiante (ahora con código único)
    public boolean create(DTO_estudiante dto_estudiante) throws Exception {
        // Validar que el código único esté presente
        if (dto_estudiante.getCodigoUnicoEstudiante() == null || dto_estudiante.getCodigoUnicoEstudiante().trim().isEmpty()) {
            throw new Exception("El código único del estudiante es obligatorio.");
        }
        return sDAO.create(dto_estudiante);
    }

    // Actualizar datos del estudiante
    public boolean update(DTO_estudiante dto_estudiante) throws Exception {
        return sDAO.update(dto_estudiante);
    }

    // Eliminar un estudiante
    public boolean delete(int idEstudiante) throws Exception {
        return sDAO.delete(idEstudiante);
    }

    // Buscar estudiante por cédula
    public boolean findByCedula(String cedula) throws Exception {
        return sDAO.findByCedula(cedula) != null;
    }

    // Obtener el ID máximo de estudiante
    public Integer getMaxRow() throws Exception {
        return sDAO.getMaxRow();
    }

    
}
