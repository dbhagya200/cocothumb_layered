package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.PlaceOrderBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.CustomerDAO;
import lk.ijse.cocothumbLayered.dao.custom.CustomerOrderDAO;
import lk.ijse.cocothumbLayered.dao.custom.ItemDAO;
import lk.ijse.cocothumbLayered.dao.custom.OrderDetailsDAO;
import lk.ijse.cocothumbLayered.dto.CustomerDTO;
import lk.ijse.cocothumbLayered.dto.ItemDTO;
import lk.ijse.cocothumbLayered.dto.OrderDetailsDTO;
import lk.ijse.cocothumbLayered.dto.OrdersDTO;
import lk.ijse.cocothumbLayered.entity.Customer;
import lk.ijse.cocothumbLayered.entity.Item;
import lk.ijse.cocothumbLayered.entity.OrderDetails;
import lk.ijse.cocothumbLayered.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.cocothumbLayered.db.dbConnection.dbConnection;

public class PlaceOrderBOimpl implements PlaceOrderBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    CustomerOrderDAO customerOrderDAO = (CustomerOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.searchById(id);
        return new CustomerDTO(c.getCust_id(), c.getCust_NIC(), c.getCust_name(),
                c.getCust_address(), c.getCust_contact(), c.getUser_id());

    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.searchById(code);
        return new ItemDTO(i.getItem_code(), i.getItem_type(), i.getUnit_price(),
                i.getUnit_price_forCompany(), i.getStock_qty(), i.getUser_id());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return customerOrderDAO.generateNewID();
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<Customer> customerEntityData = customerDAO.getAll();
        ArrayList<CustomerDTO> convertToDto = new ArrayList<>();
        for (Customer c : customerEntityData) {
            convertToDto.add(new CustomerDTO(c.getCust_id(), c.getCust_NIC(),
                    c.getCust_name(), c.getCust_address(), c.getCust_contact(), c.getUser_id()));
        }
        return convertToDto;
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        List<Item> itemEntityData = itemDAO.getAll();
        ArrayList<ItemDTO> convertToDto = new ArrayList<>();
        for (Item i : itemEntityData) {
            convertToDto.add(new ItemDTO(i.getItem_code(), i.getItem_type(),
                    i.getUnit_price(), i.getUnit_price_forCompany(), i.getStock_qty(), i.getUser_id()));
        }
        return convertToDto;
    }

    @Override
    public boolean placeOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            if (customerOrderDAO.exist(dto.getOrder_id())) {
                return false;
            }

            connection.setAutoCommit(false);

            boolean b1 = customerOrderDAO.add(new Orders(dto.getOrder_id(), dto.getCust_NIC(),
                    dto.getCust_id(), dto.getUser_id(), dto.getOrder_date()));

            if (!b1) {
                connection.rollback();
                connection.setAutoCommit(true);
            }

            for (OrderDetailsDTO d : dto.getOrderDetails()) {
                OrderDetails orderDetails = new OrderDetails(d.getItem_code(), d.getOrder_id(),
                        d.getQty(), d.getDescription(), d.getUnit_price(), d.getAmount(),
                        d.getPay_method(), d.getEmail());

                boolean b2 = orderDetailsDAO.add(orderDetails);
                if (!b2) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                ItemDTO item = findItem(d.getItem_code());
                item.setStock_qty(String.valueOf(Integer.parseInt(item.getStock_qty()) - d.getQty()));

                boolean b3 = itemDAO.update(new Item(item.getItem_code(), item.getItem_type(),
                        item.getUnit_price(), item.getUnit_price_forCompany(),
                        item.getStock_qty(), item.getUser_id()));

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


