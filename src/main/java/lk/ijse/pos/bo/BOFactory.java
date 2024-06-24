package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.CustomerBOimpl;
import lk.ijse.pos.bo.custom.impl.EmployeeBOimpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,EMPLOYEE
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOimpl();
            case EMPLOYEE:
                //return new EmployeeBOimpl();
            default:
                return null;
        }
    }
}
