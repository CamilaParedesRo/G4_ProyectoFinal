package DataAccess.DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DataAccess.DataHelper;
import DataAccess.IDAO;
import DataAccess.DTO.DTO_sexo;
import Framework.PatException;

public class DAO_sexo extends DataHelper implements IDAO<DTO_sexo> {

    @Override
    public DTO_sexo readBy(Integer idSexo) throws Exception {
        DTO_sexo sexo = new DTO_sexo();
        String query = "SELECT IdSexo, NombreSexo, FechaRegistro, FechaModifica, Estado "
                     + "FROM SEXO WHERE Estado = 'A' AND IdSexo = ?";
        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idSexo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sexo = new DTO_sexo(
                        rs.getInt("IdSexo"),
                        rs.getString("NombreSexo"),
                        rs.getTimestamp("FechaRegistro").toLocalDateTime(),
                        rs.getTimestamp("FechaModifica") != null ? rs.getTimestamp("FechaModifica").toLocalDateTime() : null,
                        rs.getString("Estado")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readBy()");
        }
        return sexo;
    }

    @Override
    public List<DTO_sexo> readAll() throws Exception {
        List<DTO_sexo> lst = new ArrayList<>();
        String query = "SELECT IdSexo, NombreSexo, Estado, FechaRegistro, FechaModifica FROM SEXO WHERE Estado = 'A'";
        try (Connection conn = openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                DTO_sexo s = new DTO_sexo(
                    rs.getInt("IdSexo"),
                    rs.getString("NombreSexo"),
                    rs.getTimestamp("FechaRegistro").toLocalDateTime(),
                    rs.getTimestamp("FechaModifica") != null ? rs.getTimestamp("FechaModifica").toLocalDateTime() : null,
                    rs.getString("Estado")
                );
                lst.add(s);
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "readAll()");
        }
        return lst;
    }

    @Override
    public boolean create(DTO_sexo entity) throws Exception {
        String query = "INSERT INTO SEXO (NombreSexo) VALUES (?)";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, entity.getNombre_sexo());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "create()");
        }
    }

    @Override
    public boolean update(DTO_sexo entity) throws Exception {
        String query = "UPDATE SEXO SET NombreSexo = ?, FechaModifica = ? WHERE IdSexo = ?";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, entity.getNombre_sexo());
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(3, entity.getId_sexo());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "update()");
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = "UPDATE SEXO SET Estado = ? WHERE IdSexo = ?";
        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "X");
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "delete()");
        }
    }

    public Integer getMaxRow() throws Exception {
        String query = "SELECT COUNT(*) TotalReg FROM SEXO WHERE Estado = 'A'";
        try (Connection conn = openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new PatException(e.getMessage(), getClass().getName(), "getMaxRow()");
        }
        return 0;
    }
}
