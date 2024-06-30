package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.SuperDAO;
import lk.ijse.cocothumbLayered.entity.Orders;

import java.sql.SQLException;

public interface CustomerOrderDAO extends SuperDAO {
    //public String currentId() throws SQLException;
    public boolean save(Orders orders) throws SQLException;
    String generateNewID() throws SQLException;
}
