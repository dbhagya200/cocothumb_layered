package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.CrudDAO;
import lk.ijse.cocothumbLayered.dto.OrdersDTO;

import java.sql.SQLException;

public interface CustomerOrderDAO extends CrudDAO<OrdersDTO> {
    String generateNewID() throws SQLException;
}
