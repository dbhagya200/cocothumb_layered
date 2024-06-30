package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.SuperDAO;
import lk.ijse.cocothumbLayered.entity.SuppOrder;

import java.sql.SQLException;

public interface SupplierOrderDAO extends SuperDAO {
     boolean save(SuppOrder suppOrder) throws SQLException;
     //String currentId() throws SQLException;
    String generateNewID() throws SQLException;

    }
