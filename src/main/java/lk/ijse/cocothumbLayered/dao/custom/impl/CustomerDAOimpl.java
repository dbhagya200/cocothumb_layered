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
        return SQLUtil.execute("INSERT INTO customer (cust_id,cust_NIC,cust_name, cust_address, cust_contact,user_id) VALUES (?,?,?,?,?,?)",
                entity.getCust_id(),entity.getCust_NIC(),entity.getCust_name(),
                entity.getCust_address(),entity.getCust_contact(),entity.getUser_id());
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET cust_NIC=?,cust_name=?, cust_address=?,cust_contact=? WHERE cust_id=?",
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

    @Override   
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT cust_id FROM customer ORDER BY CAST(SUBSTRING(cust_id, 3) AS UNSIGNED) DESC LIMIT 1");
        if (rst.next()) {
            String[] split = rst.getString(1).split("C#");
            int id = Integer.parseInt(split[1],10);
            return "C#" + String.format("%04d", ++id);
        }
        return "C#0001";


    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT cust_id FROM customer WHERE cust_id=?", id);
        return rst.next();
    }
}
