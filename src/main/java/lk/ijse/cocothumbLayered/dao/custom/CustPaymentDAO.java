package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.entity.Payment;

import java.sql.SQLException;

public interface CustPaymentDAO {
     String currentId() throws SQLException;
    public boolean save(Payment payment) throws SQLException;
}
