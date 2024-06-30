package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.OrderDetailsDAO;
import lk.ijse.cocothumbLayered.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDAOimpl implements OrderDetailsDAO {

    @Override
    public boolean save(OrderDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO order_details(item_code, order_id, qty,description,unit_price,amount,pay_method,email) VALUES(?, ?, ?, ?,?,?,?,?)",
                entity.getItem_code(), entity.getOrder_id(),
                entity.getQty(), entity.getDescription(),
                entity.getUnit_price(), entity.getAmount(),
                entity.getPay_method(),entity.getEmail());

    }

    @Override
    public boolean save(List<OrderDetails> odList) throws SQLException {
        for (OrderDetails OD : odList) {
            if(!save(OD)) {
                return false;
            }
        }
        return true;
    }

}
