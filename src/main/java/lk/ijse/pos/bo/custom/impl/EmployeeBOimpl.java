package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.EmployeeDAO;
import lk.ijse.pos.dao.custom.impl.EmployeeDAOimpl;

public class EmployeeBOimpl {
EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
}
