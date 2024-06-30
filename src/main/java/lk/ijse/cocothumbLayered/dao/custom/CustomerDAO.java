package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.CrudDAO;
import lk.ijse.cocothumbLayered.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    List<String> getNIC() throws SQLException;
}
