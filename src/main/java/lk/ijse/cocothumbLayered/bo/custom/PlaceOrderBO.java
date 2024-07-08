package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.CustomerDTO;
import lk.ijse.cocothumbLayered.dto.ItemDTO;
import lk.ijse.cocothumbLayered.dto.PlaceOrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException ;
    public boolean existItem(String code) throws SQLException, ClassNotFoundException;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;
    public String generateOrderID() throws SQLException, ClassNotFoundException ;
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    public boolean placeOrder(PlaceOrderDTO dto)throws SQLException, ClassNotFoundException;
    String generateNewOrderID() throws SQLException ;
    public ItemDTO findItem(String code)throws SQLException, ClassNotFoundException;


}
