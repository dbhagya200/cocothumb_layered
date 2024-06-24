package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO  {
    public boolean save(OrderDetails entity) throws SQLException;
    public boolean save(List<OrderDetails> odList) throws SQLException;
}
