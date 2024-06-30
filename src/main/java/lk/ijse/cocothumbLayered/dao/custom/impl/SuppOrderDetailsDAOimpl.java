package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.SuppOrderDetailsDAO;
import lk.ijse.cocothumbLayered.entity.SuppDetails;

import java.sql.SQLException;
import java.util.List;

public class SuppOrderDetailsDAOimpl implements SuppOrderDetailsDAO {

    @Override
    public boolean save(List<SuppDetails> sodList) throws SQLException {
        for (SuppDetails sd : sodList) {
            if(!save(sd)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(SuppDetails sd) throws SQLException {
        return SQLUtil.execute("INSERT INTO supp_details(item_code,supp_id,order_id,qty,description,unit_price_forCompany,amount,pay_method,email) VALUES(?, ?, ?, ?,?,?,?,?,?)",
               sd.getItem_code(),sd.getSupp_id(),
                sd.getOrder_id(), sd.getQty(),
                sd.getDescription(), sd.getUnit_price_forCompany(),
                sd.getAmount(),sd.getPay_method(),
                sd.getEmail() );
    }
}
