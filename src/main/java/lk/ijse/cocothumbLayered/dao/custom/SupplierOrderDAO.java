package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.CrudDAO;
import lk.ijse.cocothumbLayered.entity.SuppOrder;

import java.sql.SQLException;

public interface SupplierOrderDAO extends CrudDAO<SuppOrder> {

     //String currentId() throws SQLException;
    String generateNewID() throws SQLException;

    }
