package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.CustomerDAOimpl;
import lk.ijse.pos.dao.custom.impl.EmployeeDAOimpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        EMPLOYEE, CUSTOMER
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOimpl();
            case EMPLOYEE:
                return new EmployeeDAOimpl();
            default:
                return null;
        }
    }
}
