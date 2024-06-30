package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.ItemBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.ItemDAO;
import lk.ijse.cocothumbLayered.dto.ItemDTO;
import lk.ijse.cocothumbLayered.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOimpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException {
        return itemDAO.add(new Item(dto.getItem_code(), dto.getItem_type(),
                dto.getUnit_price(), dto.getUnit_price_forCompany(),
                dto.getStock_qty(), dto.getUser_id()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException {
        return itemDAO.update(new Item(dto.getItem_code(), dto.getItem_type(),
                dto.getUnit_price(), dto.getUnit_price_forCompany(),
                dto.getStock_qty(), dto.getUser_id()));
    }

    @Override
    public Item searchItemCode(String code) throws SQLException {
        return itemDAO.searchById(code);
    }

    @Override
    public boolean deleteItem(String code) throws SQLException {
        return itemDAO.delete(code);
    }

    @Override
    public List<ItemDTO> getAllItem() throws SQLException {
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
    public String generateItemNewID() throws SQLException {
        return itemDAO.generateNewID();
    }
}
