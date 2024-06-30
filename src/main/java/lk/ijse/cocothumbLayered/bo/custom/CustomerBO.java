package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.CustomerDTO;
import lk.ijse.cocothumbLayered.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public  boolean saveCustomer(CustomerDTO dto) throws SQLException ;


    public  boolean updateCustomer(CustomerDTO dto) throws SQLException ;

    public  Customer searchCustomerByNIC(String NIC) throws SQLException ;
    public  Customer searchCustomerId(String id) throws SQLException ;

    public  boolean deleteCustomer(String id) throws SQLException ;


    public  List<CustomerDTO> getAllCustomers() throws SQLException ;

    //public  List<String> getCustomerIds() throws SQLException ;

    //List<String> getCustomerNIC() throws SQLException ;

    String generateNewCustomerID() throws SQLException ;

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException;
}
