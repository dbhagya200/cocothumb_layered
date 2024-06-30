package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.CustomerBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.CustomerDAO;
import lk.ijse.cocothumbLayered.dto.CustomerDTO;
import lk.ijse.cocothumbLayered.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOimpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.add(new Customer(dto.getCust_id(), dto.getCust_NIC(),
                dto.getCust_name(), dto.getCust_address(),
                dto.getCust_contact(), dto.getUser_id()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCust_id(), dto.getCust_NIC(),
                dto.getCust_name(), dto.getCust_address(),
                dto.getCust_contact(), dto.getUser_id()));
    }

    @Override
    public Customer searchCustomerByNIC(String NIC) throws SQLException {
        return customerDAO.searchByNIC(NIC);
    }

    @Override
    public Customer searchCustomerId(String id) throws SQLException {
        return customerDAO.searchById(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        List<CustomerDTO> allCustomer = new ArrayList<>();
        List<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            allCustomer.add(new CustomerDTO(customer.getCust_id(), customer.getCust_NIC(),
                    customer.getCust_name(), customer.getCust_address(), customer.getCust_contact(), customer.getUser_id()));
        }
    return allCustomer;
    }

    @Override
    public String generateNewCustomerID() throws SQLException {
        return customerDAO.generateNewID();
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }


}
