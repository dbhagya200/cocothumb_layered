package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
     User searchByName(String userName) throws SQLException;
}
