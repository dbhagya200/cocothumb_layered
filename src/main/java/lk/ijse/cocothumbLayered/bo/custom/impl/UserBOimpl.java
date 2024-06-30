package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.UserBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.UserDAO;
import lk.ijse.cocothumbLayered.dto.UserDTO;
import lk.ijse.cocothumbLayered.entity.User;

import java.sql.SQLException;

public class UserBOimpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO dto) throws SQLException {
        return userDAO.add(new User(dto.getUser_id(), dto.getU_name(),
                dto.getU_password(), dto.getU_email(), dto.getU_role(), dto.getE_id()));
    }

    @Override
    public String generateUserNewID() throws SQLException {
        return userDAO.generateNewID();
    }
}
