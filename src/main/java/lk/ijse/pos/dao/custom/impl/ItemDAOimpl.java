package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetails;
import lk.ijse.pos.entity.SuppDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOimpl implements ItemDAO {

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO item(item_code,item_type,unit_price,unit_price_forCompany,stock_qty,user_id) VALUES (?, ?, ?, ?, ?,?)",
                entity.getItem_code(),entity.getItem_type(),
                entity.getUnit_price(),entity.getUnit_price_forCompany(),
                entity.getStock_qty(),entity.getUser_id());
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute("UPDATE item SET item_type = ?, unit_price = ?,unit_price_forCompany = ?, stock_qty = ? WHERE item_code = ?",
                entity.getItem_type(),entity.getUnit_price(),
                entity.getUnit_price_forCompany(),
                entity.getStock_qty(),entity.getItem_code());
    }

    @Override
    public Item searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public Item searchById(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item WHERE item_code = ?", id);
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM item WHERE item_code = ?",id);
    }

    @Override
    public List<Item> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item");

        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String item_type = resultSet.getString(2);
            double unit_price = Double.parseDouble(resultSet.getString(3));
            double unit_price_forCompany = Double.parseDouble(resultSet.getString(4));
            String stock_qty = resultSet.getString(5);
            String user_id = resultSet.getString(6);


            Item item = new Item(item_code, item_type,unit_price,unit_price_forCompany, stock_qty,user_id);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }


    @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT item_code FROM item ORDER BY CAST(SUBSTRING(item_code, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public List<String> getCodes() throws SQLException {

        ResultSet resultSet =SQLUtil.execute ("SELECT item_code FROM item");
        List<String> codeList = new ArrayList<>();

        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    @Override
    public boolean updateQty(List<OrderDetails> odList) throws SQLException {
        return SQLUtil.execute("UPDATE item SET stock_qty = stock_qty - ? WHERE item_code = ?",
                odList.get(0).getQty(),odList.get(0).getItem_code());
    }

    @Override
    public boolean updateSQty(SuppDetails sd) throws SQLException {
        return SQLUtil.execute("UPDATE item SET stock_qty = stock_qty + ? WHERE item_code = ?",
                sd.getQty(),sd.getItem_code());
    }
 /*   public static boolean updateQty(List<OrderDetails> odList) throws SQLException {
        for (OrderDetails od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }*/
/* public static boolean updateSQty(List<SuppDetails> odList) throws SQLException {
     for (SuppDetails sd : odList) {
         if(!updateSQty(sd)) {
             return false;
         }
     }
     return true;
 }*/

}
