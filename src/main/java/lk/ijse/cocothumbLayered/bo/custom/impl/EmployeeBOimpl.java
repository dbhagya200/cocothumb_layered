package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.EmployeeBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.EmployeeDAO;
import lk.ijse.cocothumbLayered.dto.EmployeeDTO;
import lk.ijse.cocothumbLayered.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOimpl implements EmployeeBO {
EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.add(new Employee(dto.getE_Id(), dto.getE_Name(),
                dto.getE_jobrole(), dto.getE_Address(), dto.getE_Contact(),
                dto.getE_Salary(), dto.getE_email(), dto.getMachine_id()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getE_Id(), dto.getE_Name(),
                dto.getE_jobrole(), dto.getE_Address(), dto.getE_Contact(),
                dto.getE_Salary(), dto.getE_email(), dto.getMachine_id()));
    }

    @Override
    public Employee searchEmployeeByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public Employee searchEmployeeId(String id) throws SQLException {
        return employeeDAO.searchById(id);
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        List<EmployeeDTO> allCustomer = new ArrayList<>();
        List<Employee> all = employeeDAO.getAll();
        for (Employee employee : all) {
            allCustomer.add(new EmployeeDTO(employee.getE_Id(), employee.getE_Name(),
                    employee.getE_jobrole(), employee.getE_Address(), employee.getE_Contact(),
                    employee.getE_Salary(), employee.getE_email(), employee.getMachine_id()));
        }
        return allCustomer;
    }

    @Override
    public String generateEmployeeNewID() throws SQLException {
        return employeeDAO.generateNewID();
    }

}
