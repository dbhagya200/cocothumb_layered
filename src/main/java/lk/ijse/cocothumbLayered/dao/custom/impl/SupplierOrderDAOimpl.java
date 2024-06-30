package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.SupplierOrderDAO;
import lk.ijse.cocothumbLayered.entity.SuppOrder;

import java.sql.*;

public class SupplierOrderDAOimpl implements SupplierOrderDAO {
    @Override
    public boolean save(SuppOrder suppOrder) throws SQLException {
        return SQLUtil.execute("INSERT INTO supp_order(order_id, supp_id, user_id, date) VALUES(?, ?, ?,?)",
                suppOrder.getOrder_id(), suppOrder.getSupp_id(),
                suppOrder.getUser_id(), suppOrder.getDate());
    }

   /* @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT order_id FROM supp_order ORDER BY CAST(SUBSTRING(order_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }*/

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM `supp_order` ORDER BY order_id DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("order_id").replace("OID-", "")) + 1)) : "OID-001";

    }
}
