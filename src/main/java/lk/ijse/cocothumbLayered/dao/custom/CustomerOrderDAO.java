package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.CrudDAO;
import lk.ijse.cocothumbLayered.entity.Orders;

import java.sql.SQLException;

public interface CustomerOrderDAO extends CrudDAO<Orders> {
    String generateNewID() throws SQLException;
}
