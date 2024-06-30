package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.CustomerDAO;
import lk.ijse.cocothumbLayered.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOimpl implements CustomerDAO {

    @Override
    public boolean add(Customer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer (cust_id,cust_NIC,cust_name, cust_address, cust_contact,user_id) VALUES (?,?,?,?,?,?)",entity.getCust_id(),entity.getCust_NIC(),entity.getCust_name(),entity.getCust_address(),entity.getCust_contact(),entity.getUser_id());
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute("UPDATE Customer SET cust_NIC,cust_name=?, cust_address=?,cust_contact=? WHERE id=?",
                entity.getCust_NIC(),entity.getCust_name(),entity.getCust_address(),
                entity.getCust_contact(),entity.getCust_id());
    }

    @Override
    public Customer searchByNIC(String NIC) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer  WHERE cust_NIC = ?",NIC);

        Customer customer = null;

        if (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_NIC = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String cust_address = resultSet.getString(4);
            String cust_contact = resultSet.getString(5);
            String user_id = resultSet.getString(6);

            customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);
        }
        return customer;
    }

    @Override
    public Customer searchById(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer  WHERE cust_id = ?",id);

        Customer customer = null;

        if (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_NIC = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String cust_address = resultSet.getString(4);
            String cust_contact = resultSet.getString(5);
            String user_id = resultSet.getString(6);

            customer = new Customer(cust_id,cust_NIC, cust_name, cust_address, cust_contact,user_id);
        }
        return customer;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE cust_id = ?",id);
    }

    @Override
    public List<Customer> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        List<Customer> customersList = new ArrayList<>();
        while (resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String cust_NIC = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String cust_address = resultSet.getString(4);
            String cust_contact = resultSet.getString(5);
            String user_id = resultSet.getString(6);

            Customer customer = new Customer(cust_id, cust_NIC,cust_name, cust_address, cust_contact,user_id);
            customersList.add(customer);
        }
        return customersList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public List<String> getNIC() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT cust_NIC FROM customer");

        List<String> nicList = new ArrayList<>();

        while (resultSet.next()) {
            nicList.add(resultSet.getString(1));
        }
        return nicList;
    }

 /*   @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT cust_id FROM customer ORDER BY CAST(SUBSTRING(cust_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }*/

    @Override   
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT cust_id FROM customer ORDER BY CAST(SUBSTRING(cust_id, 2) AS UNSIGNED) DESC LIMIT 1");

        // If there is a result, increment the number part of the ID and format it, otherwise start with C00001
        if (rst.next()) {
            // Extract the numerical part of the ID, increment it, and format it with leading zeros
            int lastIdNum = Integer.parseInt(rst.getString("cust_id").replace("C00", ""));
            return String.format("C00%03d", lastIdNum + 1);
        } else {
            // If there are no existing IDs, start with C00001
            return "C00001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT cust_id FROM customer WHERE cust_id=?", id);
        return rst.next();
    }
}
