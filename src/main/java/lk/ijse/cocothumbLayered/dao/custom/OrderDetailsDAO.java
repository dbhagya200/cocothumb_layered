package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.SuperDAO;
import lk.ijse.cocothumbLayered.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO extends SuperDAO {
    public boolean save(OrderDetails entity) throws SQLException;
    public boolean save(List<OrderDetails> odList) throws SQLException;
}
