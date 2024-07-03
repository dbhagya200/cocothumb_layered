package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.EmployeeDAO;
import lk.ijse.cocothumbLayered.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOimpl implements EmployeeDAO {
    @Override
    public boolean add(Employee entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO employee (e_id, e_name,e_jobrole, e_address, e_contact,e_salary, e_email,machine_id)VALUES (?, ?, ?, ?, ?,?,?,?)",
                entity.getE_Id(),
                entity.getE_Name(),
                entity.getE_jobrole(),
                entity.getE_Address(),
                entity.getE_Contact(),
                entity.getE_Salary(),
                entity.getE_email(),
                entity.getMachine_id());
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute( "UPDATE employee SET e_name = ?,e_jobrole = ?, e_address = ?," +
                " e_contact = ?,e_salary = ?,e_email = ?,machine_id = ? WHERE e_id = ?",
                entity.getE_Name(),entity.getE_jobrole(),entity.getE_Address(),
                entity.getE_Contact(),entity.getE_Salary(),entity.getE_email(),
                entity.getMachine_id(),entity.getE_Id());
    }

    @Override
    public Employee searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public Employee searchById(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE e_id = ?",id);

        Employee employee = null;

        if (resultSet.next()) {
            String e_id = resultSet.getString(1);
            String e_name = resultSet.getString(2);
            String e_jobrole = resultSet.getString(3);
            String e_address = resultSet.getString(4);
            String e_contact = resultSet.getString(5);
            double e_salary = Double.parseDouble(resultSet.getString(6));
            String e_email = resultSet.getString(7);
            String machine_id = resultSet.getString(8);


            employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact, e_salary,e_email,machine_id);
        }
        return employee;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE e_id = ?",id);
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String e_id = resultSet.getString(1);
            String e_name = resultSet.getString(2);
            String e_jobrole = resultSet.getString(3);
            String e_address = resultSet.getString(4);
            String e_contact = resultSet.getString(5);
            double e_salary = Double.parseDouble(resultSet.getString(6));
            String e_email = resultSet.getString(7);
            String machine_id = resultSet.getString(8);

            Employee employee = new Employee(e_id, e_name,e_jobrole, e_address, e_contact,e_salary,e_email,machine_id);
            employeeList.add(employee);
        }
        return employeeList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }



    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT e_id FROM employee ORDER BY CAST(SUBSTRING(e_id, 3) AS UNSIGNED) DESC LIMIT 1");
        if (rst.next()) {
            String[] split = rst.getString(1).split("E#");
            int id = Integer.parseInt(split[1],10);
            return "E#" + String.format("%04d", ++id);
        }
        return "E#0001";
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT e_id FROM employee WHERE e_id=?", id);
        return rst.next();
    }
}
