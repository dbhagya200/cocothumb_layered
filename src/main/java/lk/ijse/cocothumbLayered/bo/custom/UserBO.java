package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.UserDTO;
import lk.ijse.cocothumbLayered.entity.User;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public  boolean saveUser(UserDTO dto) throws SQLException;
    String generateUserNewID() throws SQLException ;
    public boolean updateUser(UserDTO dto) throws SQLException;
    User searchUser(String id) throws SQLException;
    User searchUserByUserName(String userName) throws SQLException;

}
