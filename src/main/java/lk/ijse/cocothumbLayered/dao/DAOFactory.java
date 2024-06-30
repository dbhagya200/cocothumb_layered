package lk.ijse.cocothumbLayered.dao;

import lk.ijse.cocothumbLayered.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        EMPLOYEE, CUSTOMER,ITEM,MACHINE,CUSTOMER_ORDER,ORDER_DETAILS,
        SUPPLIER, SUPPLIER_ORDER, SUPPLIER_ORDER_DETAILS,USER
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOimpl();
            case EMPLOYEE:
                return new EmployeeDAOimpl();
            case ITEM:
                return new ItemDAOimpl();
            case MACHINE:
                return new MachineDAOimpl();
            case CUSTOMER_ORDER:
                return new CustomerOrderDAOimpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOimpl();
            case SUPPLIER:
                return new SupplierDAOimpl();
            case SUPPLIER_ORDER:
                return new SupplierOrderDAOimpl();
            case SUPPLIER_ORDER_DETAILS:
                return new SuppOrderDetailsDAOimpl();
            case USER:
                return new UserDAOimpl();
            default:
                return null;
        }
    }
}
