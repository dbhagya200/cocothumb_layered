package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.UserDAO;
import lk.ijse.cocothumbLayered.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {
    @Override
    public boolean add(User entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO user(user_id, u_name, u_password, u_role, u_email,e_id) VALUES (?, ?, ?, ?, ?,?)",
                entity.getUser_id(), entity.getU_name(),
                entity.getU_password(), entity.getU_role(),
                entity.getU_email(), entity.getE_id());
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return SQLUtil.execute("UPDATE user SET u_password = ? WHERE user_id= ?",
                entity.getU_password(), entity.getUser_id());
    }

    @Override
    public User searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public User searchById(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_id = ?",id);

        User user = null;

        if (resultSet.next()) {
            String user_id = resultSet.getString(1);
            String u_name = resultSet.getString(2);
            String u_password = resultSet.getString(3);
            String u_email = resultSet.getString(4);
            String u_role = resultSet.getString(5);
            String e_id = resultSet.getString(6);

            user = new User(user_id, u_name, u_password, u_email, u_role, e_id);
        }
        return user;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user");

        List<User> usersList = new ArrayList<>();
        while (resultSet.next()) {
            String user_id = resultSet.getString(1);
            String u_name = resultSet.getString(2);
            String u_passsword = resultSet.getString(3);
            String u_email = resultSet.getString(4);
            String u_role = resultSet.getString(5);
            String e_id = resultSet.getString(6);

            User user = new User(user_id, u_name, u_passsword,u_email, u_role, e_id);
            usersList.add(user);
        }
        return usersList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

/*    @Override
    public String currentId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT user_id FROM user ORDER BY CAST(SUBSTRING(user_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }*/

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("user_id");
            int newId = Integer.parseInt(id.replace("U00", "")) + 1;
            return String.format("U00%03d", newId);
        } else {
            return "U0001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM Customer WHERE user_id=?", id);
        return rst.next();
    }

    @Override
    public User searchByName(String userName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE u_name = ?", userName);

        User user = null;

        if (resultSet.next()) {
            String user_id = resultSet.getString(1);
            String u_name = resultSet.getString(2);
            String u_password = resultSet.getString(3);
            String u_email = resultSet.getString(4);
            String u_role = resultSet.getString(5);
            String e_id = resultSet.getString(6);

            user = new User(user_id, u_name, u_password, u_email, u_role, e_id);
        }
        return user;
    }
}
