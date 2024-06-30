package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.UserDTO;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public  boolean saveUser(UserDTO dto) throws SQLException;
    String generateUserNewID() throws SQLException ;

}
