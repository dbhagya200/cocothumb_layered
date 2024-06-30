package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.SupplierDTO;
import lk.ijse.cocothumbLayered.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    public  boolean saveSupplier(SupplierDTO dto) throws SQLException;


    public  boolean updateSupplier(SupplierDTO dto) throws SQLException ;

    public Supplier searchSupplierId(String id) throws SQLException ;

    public  boolean deleteSupplier(String id) throws SQLException ;


    public List<SupplierDTO> getAllSupplier() throws SQLException ;

    //public  List<String> getCustomerIds() throws SQLException ;

    //List<String> getCustomerNIC() throws SQLException ;

    String generateSupplierNewID() throws SQLException ;
}
