package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.SupplierBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.SupplierDAO;
import lk.ijse.cocothumbLayered.dto.SupplierDTO;
import lk.ijse.cocothumbLayered.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOimpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public boolean saveSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.add(new Supplier(dto.getSupp_id(),dto.getSupp_name(),
                dto.getSupp_address(),dto.getSupp_contact()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.update(new Supplier(dto.getSupp_id(),dto.getSupp_name(),
                dto.getSupp_address(),dto.getSupp_contact()));
    }

    @Override
    public Supplier searchSupplierId(String id) throws SQLException {
        return supplierDAO.searchById(id);
    }


    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        return supplierDAO.delete(id);
    }

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException {
        List<SupplierDTO> allSupplier = new ArrayList<>();
        List<Supplier> all = supplierDAO.getAll();
        for (Supplier s : all) {
            allSupplier.add(new SupplierDTO(s.getSupp_id(),s.getSupp_name(),
                    s.getSupp_address(),s.getSupp_contact()));
        }
        return allSupplier;
    }

    @Override
    public String generateSupplierNewID() throws SQLException {
        return supplierDAO.generateNewID();
    }
}
