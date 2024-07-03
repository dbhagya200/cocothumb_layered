package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.SupplierOrderDAO;
import lk.ijse.cocothumbLayered.entity.SuppOrder;

import java.sql.*;
import java.util.List;

public class SupplierOrderDAOimpl implements SupplierOrderDAO {

    @Override
    public boolean add(SuppOrder entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO supp_order(order_id, supp_id, user_id, date) VALUES(?, ?, ?,?)",
                entity.getOrder_id(), entity.getSupp_id(),
                entity.getUser_id(), entity.getDate());
    }

    @Override
    public boolean update(SuppOrder entity) throws SQLException {
        return false;
    }

    @Override
    public SuppOrder searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public SuppOrder searchById(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<SuppOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM `supp_order` ORDER BY order_id DESC LIMIT 1;");
        if (rst.next()) {
            String[] split = rst.getString(1).split("SO#");
            int id = Integer.parseInt(split[1],10);
            return "SO#" + String.format("%04d", ++id);
        }
        return "SO#0001";
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM `supp_order` WHERE order_id=?",id);
        return rst.next();
    }
}
