package DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import DataAccess.IDAO;
import DataAccess.DataHelper;
import DataAccess.DTO.DTO_profesor;
import Framework.PatException;

public class DAO_profesor extends DataHelper implements IDAO<DTO_profesor> {

    @Override
    public DTO_profesor readBy(Integer id) throws Exception {
        DTO_profesor profesor = new DTO_profesor();
        String query = " SELECT IdProfesor, NombreProfesor, ApellidoProfesor, CedulaProfesor, "
                    + " CorreoProfesor, UsuarioProfesor, ClaveProfesor, "
                    + " FechaRegistro, FechaModifica, Estado "
                    + " FROM Profesor "
                    + " WHERE Estado = 'A' AND IdProfesor = " + id.toString();
        try {
            Connection conn = openConnection();         
            Statement stmt = conn.createStatement();    
            ResultSet rs = stmt.executeQuery(query);    
            while (rs.next()) {
                profesor = new DTO_profesor(
                    rs.getInt(1),           
                    rs.getString(2),        
                    rs.getString(3),    
                    rs.getString(4),        
                    rs.getString(5),       
                    rs.getString(6),        
                    rs.getString(7),        
                    rs.getTimestamp(8).toLocalDateTime(), 
                    rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null, 
                    rs.getString(10).charAt(0) 
                );
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readBy()");
        }
        return profesor;
    }

    @Override
    public List<DTO_profesor> readAll() throws Exception {
        List<DTO_profesor> profesores = new ArrayList<>();
        String query = " SELECT IdProfesor, NombreProfesor, ApellidoProfesor, CedulaProfesor, "
                     + " CorreoProfesor, UsuarioProfesor, ClaveProfesor, "
                     + " FechaRegistro, FechaModifica, Estado "
                     + " FROM Profesor "
                     + " WHERE Estado = 'A' ";
        try {
            Connection conn = openConnection();         
            Statement stmt = conn.createStatement();    
            ResultSet rs = stmt.executeQuery(query);    
            while (rs.next()) {
                DTO_profesor profesor = new DTO_profesor(
                    rs.getInt(1),         
                    rs.getString(2),       
                    rs.getString(3),        
                    rs.getString(4),       
                    rs.getString(5),        
                    rs.getString(6),        
                    rs.getString(7),        
                    rs.getTimestamp(8).toLocalDateTime(),
                    rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null, 
                    rs.getString(10).charAt(0) 
                );
                profesores.add(profesor);
            }
        } catch (SQLException e) {
            throw e; 
        }
        return profesores;
    }

    @Override
public boolean create(DTO_profesor entity) throws Exception {
    String query = "INSERT INTO profesor (nombre_profesor, apellido_profesor, cedula_profesor, "
                + "id_sexo, correo_profesor, usuario_profesor, clave_profesor, fecha_registro, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, datetime('now','localtime'), 'A')";
    try (Connection conn = openConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

        if (entity.getId_sexo() == null) {
            throw new Exception("El campo id_sexo no puede ser nulo.");
        }

        pstmt.setString(1, entity.getNombreProfesor());
        pstmt.setString(2, entity.getApellidoProfesor());
        pstmt.setString(3, entity.getCedulaProfesor());
        pstmt.setInt(4, entity.getId_sexo()); 
        pstmt.setString(5, entity.getCorreoProfesor());
        pstmt.setString(6, entity.getUsuarioProfesor());
        pstmt.setString(7, entity.getClaveProfesor());

        int rowsInserted = pstmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        throw e;
    } finally {
            closeConnection(); 
        }
    }

    @Override
    public boolean update(DTO_profesor entity) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String query = " UPDATE Profesor SET nombre_profesor = ?, apellido_profesor = ?, correo_profesor = ?, "
                    + " usuario_profesor = ?, clave_profesor = ?, fecha_modifica = ? "
                    + " WHERE id_profesor = ? ";
        try {
            Connection conn = openConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, entity.getNombreProfesor());   
            pstmt.setString(2, entity.getApellidoProfesor()); 
            pstmt.setString(3, entity.getCorreoProfesor());   
            pstmt.setString(4, entity.getUsuarioProfesor());  
            pstmt.setString(5, entity.getClaveProfesor());    
            pstmt.setString(6, dtf.format(now));              
            pstmt.setInt(7, entity.getIdProfesor());          
            
            pstmt.executeUpdate(); 
            return true; 
        } catch (SQLException e) {
            throw e; 
        } finally {
            closeConnection(); 
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = " UPDATE Profesor SET Estado = ? WHERE IdProfesor = ? ";
        try {
            Connection conn = openConnection(); // Abrir la conexión con la base de datos
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "X"); 
            pstmt.setInt(2, id);     
            pstmt.executeUpdate();   
            return true; 
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "delete()");
        }
    }

    public Integer getMaxRow() throws Exception {
        String query = " SELECT COUNT(*) TotalReg FROM Profesor "
                     + " WHERE Estado = 'A' ";
        try {
            Connection conn = openConnection();         
            Statement stmt = conn.createStatement();   
            ResultSet rs = stmt.executeQuery(query);    
            while (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) {
            throw e; 
        }
        return 0; 
    }
}