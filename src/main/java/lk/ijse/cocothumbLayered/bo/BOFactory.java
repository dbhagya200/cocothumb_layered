package lk.ijse.cocothumbLayered.bo;

import lk.ijse.cocothumbLayered.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,EMPLOYEE,ITEM,MACHINE,SUPPLIER,USER
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOimpl();
            case EMPLOYEE:
                return new EmployeeBOimpl();
            case ITEM:
                return new ItemBOimpl();
            case MACHINE:
                return new MachineBOimpl();
            case SUPPLIER:
                return new SupplierBOimpl();
            case USER:
                return new UserBOimpl();
            default:
                return null;
        }
    }
}
