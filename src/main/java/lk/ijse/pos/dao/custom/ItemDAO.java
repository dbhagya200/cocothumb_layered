package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetails;
import lk.ijse.pos.entity.SuppDetails;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    public List<String> getCodes() throws SQLException;
    public boolean updateQty(List<OrderDetails> odList) throws SQLException;
     boolean updateSQty(SuppDetails sd) throws SQLException;
}
