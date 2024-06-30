package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.CustomerOrderDAO;
import lk.ijse.cocothumbLayered.entity.Orders;

import java.sql.*;

public class CustomerOrderDAOimpl implements CustomerOrderDAO {
 /*   @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT order_id FROM orders ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }*/


    @Override
    public boolean save(Orders orders) throws SQLException {
        return SQLUtil.execute("INSERT INTO orders(order_id,cust_NIC ,cust_id,user_id,date,) VALUES(?, ?, ?,?,?)",
                orders.getOrder_id(),orders.getCust_NIC(),
                orders.getCust_id(),orders.getUser_id(),
                orders.getOrder_date());
    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM `Orders` ORDER BY order_id DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("order_id").replace("OID-", "")) + 1)) : "OID-001";

    }
}
