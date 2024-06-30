package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.CrudDAO;
import lk.ijse.cocothumbLayered.entity.Item;
import lk.ijse.cocothumbLayered.entity.OrderDetails;
import lk.ijse.cocothumbLayered.entity.SuppDetails;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
    public List<String> getCodes() throws SQLException;
    public boolean updateQty(List<OrderDetails> odList) throws SQLException;
     boolean updateSQty(SuppDetails sd) throws SQLException;
}
