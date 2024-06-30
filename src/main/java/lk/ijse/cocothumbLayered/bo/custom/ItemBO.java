package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.ItemDTO;
import lk.ijse.cocothumbLayered.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    public  boolean saveItem(ItemDTO dto) throws SQLException;


    public  boolean updateItem(ItemDTO dto) throws SQLException ;

    public Item searchItemCode(String code) throws SQLException ;

    public  boolean deleteItem(String code) throws SQLException ;


    public List<ItemDTO> getAllItem() throws SQLException ;

    //public  List<String> getCustomerIds() throws SQLException ;

    //List<String> getCustomerNIC() throws SQLException ;

    String generateItemNewID() throws SQLException ;
}
