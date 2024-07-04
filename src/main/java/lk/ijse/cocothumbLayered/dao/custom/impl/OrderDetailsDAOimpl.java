package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.OrderDetailsDAO;
import lk.ijse.cocothumbLayered.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDAOimpl implements OrderDetailsDAO {


    @Override
    public boolean add(OrderDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO order_details(item_code, order_id, qty,description,unit_price,amount,pay_method,email) VALUES(?, ?, ?, ?,?,?,?,?)",
                entity.getItem_code(), entity.getOrder_id(),
                entity.getQty(), entity.getDescription(),
                entity.getUnit_price(), entity.getAmount(),
                entity.getPay_method(),entity.getEmail());
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public OrderDetails searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public OrderDetails searchById(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
