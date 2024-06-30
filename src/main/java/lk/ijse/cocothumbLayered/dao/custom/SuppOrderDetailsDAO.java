package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.SuperDAO;
import lk.ijse.cocothumbLayered.entity.SuppDetails;

import java.sql.SQLException;
import java.util.List;

public interface SuppOrderDetailsDAO extends SuperDAO {
    public boolean save(List<SuppDetails> sodList) throws SQLException;
     boolean save(SuppDetails sd) throws SQLException;
}
