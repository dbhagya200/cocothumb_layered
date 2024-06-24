package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.SupplierOrderDAO;
import lk.ijse.pos.entity.SuppOrder;

import java.sql.*;

public class SupplierOrderDAOimpl implements SupplierOrderDAO {
    @Override
    public boolean save(SuppOrder suppOrder) throws SQLException {
        return SQLUtil.execute("INSERT INTO supp_order(order_id, supp_id, user_id, date) VALUES(?, ?, ?,?)",
                suppOrder.getOrder_id(), suppOrder.getSupp_id(),
                suppOrder.getUser_id(), suppOrder.getDate());
    }

    @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT order_id FROM supp_order ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
