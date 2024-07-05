package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.CustomerOrderDAO;
import lk.ijse.cocothumbLayered.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerOrderDAOimpl implements CustomerOrderDAO {


    @Override
    public boolean add(Orders entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO orders(order_id,cust_NIC ,cust_id,user_id,date,) VALUES(?, ?, ?,?,?)",
                entity.getOrder_id(),entity.getCust_NIC(),
                entity.getCust_id(),entity.getUser_id(),
                entity.getOrder_date());
    }

    @Override
    public boolean update(Orders entity) throws SQLException {
        return false;
    }

    @Override
    public Orders searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public Orders searchById(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM `orders` ORDER BY order_id DESC LIMIT 1;");
        if (rst.next()) {
            String[] split = rst.getString(1).split("O#");
            int id = Integer.parseInt(split[1],10);
            return "O#" + String.format("%04d", ++id);
        }
        return "O#0001";
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM `Orders` WHERE order_id=?",id);
        return rst.next();
    }
}
