package lk.ijse.cocothumbLayered.dao.custom;

import lk.ijse.cocothumbLayered.dao.CrudDAO;
import lk.ijse.cocothumbLayered.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
     User searchByName(String userName) throws SQLException;
}
