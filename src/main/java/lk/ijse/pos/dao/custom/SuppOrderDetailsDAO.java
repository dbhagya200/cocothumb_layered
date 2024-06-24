package lk.ijse.pos.dao.custom;

import lk.ijse.pos.entity.SuppDetails;

import java.sql.SQLException;
import java.util.List;

public interface SuppOrderDetailsDAO {
    public boolean save(List<SuppDetails> sodList) throws SQLException;
     boolean save(SuppDetails sd) throws SQLException;
}
