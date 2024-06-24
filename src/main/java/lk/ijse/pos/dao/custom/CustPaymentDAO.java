package lk.ijse.pos.dao.custom;

import lk.ijse.pos.entity.Payment;

import java.sql.SQLException;

public interface CustPaymentDAO {
     String currentId() throws SQLException;
    public boolean save(Payment payment) throws SQLException;
}
