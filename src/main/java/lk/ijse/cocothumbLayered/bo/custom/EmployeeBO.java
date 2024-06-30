package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.EmployeeDTO;
import lk.ijse.cocothumbLayered.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public  boolean saveEmployee(EmployeeDTO dto) throws SQLException;


    public  boolean updateEmployee(EmployeeDTO dto) throws SQLException ;

    public Employee searchEmployeeByNIC(String NIC) throws SQLException ;
    public  Employee searchEmployeeId(String id) throws SQLException ;

    public  boolean deleteEmployee(String id) throws SQLException ;


    public List<EmployeeDTO> getAllEmployees() throws SQLException ;

    //public  List<String> getCustomerIds() throws SQLException ;

    //List<String> getCustomerNIC() throws SQLException ;

    String generateEmployeeNewID() throws SQLException ;
}
