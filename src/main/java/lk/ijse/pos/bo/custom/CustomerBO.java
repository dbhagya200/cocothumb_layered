package lk.ijse.pos.bo.custom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public  boolean saveCustomer(CustomerDTO dto) throws SQLException ;


    public  boolean updateCustomer(CustomerDTO dto) throws SQLException ;

    //public  T searchCustomerByNIC(String NIC) throws SQLException ;
    //public  T searchCustomerId(String id) throws SQLException ;

    public  boolean deleteCustomer(String id) throws SQLException ;


    public  List<CustomerDTO> getAllCustomers() throws SQLException ;

    //public  List<String> getCustomerIds() throws SQLException ;

    //List<String> getCustomerNIC() throws SQLException ;

    //String currentCustomerId() throws SQLException ;
     void loadNextCustomerId();
     String nextId(String currentId);
}
