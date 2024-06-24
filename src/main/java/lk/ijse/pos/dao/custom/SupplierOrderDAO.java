package lk.ijse.pos.dao.custom;

import lk.ijse.pos.entity.SuppOrder;

import java.sql.SQLException;

public interface SupplierOrderDAO {
     boolean save(SuppOrder suppOrder) throws SQLException;
     String currentId() throws SQLException;

    }
