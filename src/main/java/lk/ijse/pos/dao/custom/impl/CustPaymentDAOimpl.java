package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.CustPaymentDAO;
import lk.ijse.pos.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustPaymentDAOimpl implements CustPaymentDAO {

    @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT pay_id FROM cust_payment ORDER BY CAST(SUBSTRING(pay_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public boolean save(Payment payment) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer(pay_id, cust_id, pay_method, t_price, date) VALUES (?, ?, ?, ?, ?)",
                payment.getPay_id(),payment.getCust_id(),
                payment.getPay_method(),payment.getT_price(),
                payment.getDate());
    }
}
