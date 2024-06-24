package lk.ijse.pos.dao.custom;

import lk.ijse.pos.entity.Orders;

import java.sql.SQLException;

public interface CustomerOrderDAO {
    public String currentId() throws SQLException;
    public boolean save(Orders orders) throws SQLException;
}
