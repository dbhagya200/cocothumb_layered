package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.ItemDTO;
import lk.ijse.cocothumbLayered.dto.SPlaceOrderDTO;
import lk.ijse.cocothumbLayered.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface SuppPlaceOrderBO extends SuperBO {
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException ;
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException ;
    public boolean existItem(String code) throws SQLException, ClassNotFoundException;
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException ;
    public String generateOrderID() throws SQLException, ClassNotFoundException ;
    public List<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    public boolean placeOrder(SPlaceOrderDTO dto)throws SQLException, ClassNotFoundException;
    String generateNewOrderID() throws SQLException ;
    public ItemDTO findItem(String code)throws SQLException, ClassNotFoundException;

}
