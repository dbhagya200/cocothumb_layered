package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public class CustomerBOimpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return false;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        return null;
    }

    @Override
    public void loadNextCustomerId() {

    }

    @Override
    public String nextId(String currentId) {
        return null;
    }
}
