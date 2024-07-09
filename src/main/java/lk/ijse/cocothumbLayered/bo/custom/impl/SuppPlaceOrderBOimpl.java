package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.SuppPlaceOrderBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.ItemDAO;
import lk.ijse.cocothumbLayered.dao.custom.SuppOrderDetailsDAO;
import lk.ijse.cocothumbLayered.dao.custom.SupplierDAO;
import lk.ijse.cocothumbLayered.dao.custom.SupplierOrderDAO;
import lk.ijse.cocothumbLayered.dto.*;
import lk.ijse.cocothumbLayered.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.cocothumbLayered.db.dbConnection.dbConnection;

public class SuppPlaceOrderBOimpl implements SuppPlaceOrderBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER);
    SuppOrderDetailsDAO suppOrderDetailsDAO = (SuppOrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_ORDER_DETAILS);

    @Override
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier s = supplierDAO.searchById(id);
        return new SupplierDTO(s.getSupp_id(),s.getSupp_name(),s.getSupp_address(),
                s.getSupp_contact());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.searchById(code);
        return new ItemDTO(i.getItem_code(),i.getItem_type(),i.getUnit_price(),
                i.getUnit_price_forCompany(),i.getStock_qty(),i.getUser_id());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return supplierOrderDAO.generateNewID();
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> allSupplier = new ArrayList<>();
        List<Supplier> all = supplierDAO.getAll();

        for (Supplier s : all) {
            allSupplier.add(new SupplierDTO(s.getSupp_id(),s.getSupp_name(),
                    s.getSupp_address(),s.getSupp_contact()));
        }
        return allSupplier;
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> allItem = new ArrayList<>();
        List<Item> all = itemDAO.getAll();

        for (Item item : all) {
            allItem.add(new ItemDTO(item.getItem_code(), item.getItem_type(),
                    item.getUnit_price(), item.getUnit_price_forCompany(),
                    item.getStock_qty(), item.getUser_id()));
        }
        return allItem;
    }

    @Override
    public boolean placeOrder(SPlaceOrderDTO dto) throws SQLException, ClassNotFoundException {
        SuppOrderDTO order = dto.getSuppOrder();
        List<SuppDetailsDTO> odList = dto.getSodList();

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            if (supplierOrderDAO.exist(order.getOrder_id())) {
                return false;
            }

            connection.setAutoCommit(false);

            boolean b1 = supplierOrderDAO.add(new SuppOrder(order.getOrder_id(),order.getSupp_id(),
                    order.getUser_id(),order.getDate()));
            System.out.println("b1 = " + b1);

            if (!b1) {
                connection.rollback();
                connection.setAutoCommit(true);
            }

            for (SuppDetailsDTO sd : odList) {
                SuppDetails orderDetails = new SuppDetails(sd.getItem_code(),sd.getSupp_id(),
                        sd.getOrder_id(),sd.getQty(),sd.getDescription(),sd.getUnit_price_forCompany(),
                        sd.getAmount(),sd.getPay_method(),sd.getEmail());
                System.out.println("SupplierOrderDetails = " + orderDetails);

                boolean b2 = suppOrderDetailsDAO.save(orderDetails);
                System.out.println("b2 = " + b2);
                if (!b2) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                ItemDTO item = findItem(sd.getItem_code());
                item.setStock_qty(String.valueOf(Integer.parseInt(item.getStock_qty()) + sd.getQty()));

                boolean b3 = itemDAO.update(new Item(item.getItem_code(), item.getItem_type(),
                        item.getUnit_price(), item.getUnit_price_forCompany(),
                        item.getStock_qty(), item.getUser_id()));
                System.out.println("b3 = " + b3);

                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String generateNewOrderID() throws SQLException {
        return supplierOrderDAO.generateNewID();
    }

    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        try {
            Item item = itemDAO.searchById(code);
            return new ItemDTO(item.getItem_code(), item.getItem_type(), item.getUnit_price(),
                    item.getUnit_price_forCompany(), item.getStock_qty(), item.getUser_id());
        } catch (Exception e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        }
    }
}
