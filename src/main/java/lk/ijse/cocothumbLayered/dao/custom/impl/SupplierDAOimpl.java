package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.SupplierDAO;
import lk.ijse.cocothumbLayered.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOimpl implements SupplierDAO {
    @Override
    public boolean add(Supplier entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO supplier(supp_id,supp_name,supp_address,supp_contact) VALUES (?, ?, ?, ?)",
                entity.getSupp_id(), entity.getSupp_name(),
                entity.getSupp_address(), entity.getSupp_contact());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute("UPDATE supplier SET supp_name=?, supp_address=?, supp_contact=? WHERE supp_id=?",
                entity.getSupp_name(), entity.getSupp_address(),
                entity.getSupp_contact(), entity.getSupp_id());
    }

    @Override
    public Supplier searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public Supplier searchById(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier  WHERE supp_id = ?");

        Supplier supplier = null;

        if (resultSet.next()) {
            String supp_id = resultSet.getString(1);
            String supp_name = resultSet.getString(2);
            String supp_address = resultSet.getString(3);
            String supp_contact = resultSet.getString(4);

            supplier = new Supplier(supp_id, supp_name, supp_address, supp_contact);
        }
        return supplier;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supp_id = ?",id);
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");

        List<Supplier> suppliersList = new ArrayList<>();
        while (resultSet.next()) {
            String supp_id = resultSet.getString(1);
            String supp_name = resultSet.getString(2);
            String supp_address = resultSet.getString(3);
            String supp_contact = resultSet.getString(4);

            Supplier supplier = new Supplier(supp_id, supp_name, supp_address, supp_contact);
            suppliersList.add(supplier);
        }
        return suppliersList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT supp_id FROM supplier");

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }


 /*   @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT supp_id FROM supplier ORDER BY CAST(SUBSTRING(supp_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }*/

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT supp_id FROM supplier ORDER BY supp_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("supp_id");
            int newId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supp_id FROM Customer WHERE supp_id=?", id);
        return rst.next();
    }
}
