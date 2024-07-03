package lk.ijse.cocothumbLayered.dao.custom.impl;

import lk.ijse.cocothumbLayered.dao.SQLUtil;
import lk.ijse.cocothumbLayered.dao.custom.MachineDAO;
import lk.ijse.cocothumbLayered.entity.Machine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAOimpl implements MachineDAO {

    @Override
    public boolean add(Machine entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO machine(machine_id,brand) VALUES (?, ?)",entity.getMachine_id(),entity.getBrand());
    }

    @Override
    public boolean update(Machine entity) throws SQLException {
            return SQLUtil.execute("UPDATE machine SET brand = ? WHERE machine_id = ?",entity.getBrand(),entity.getMachine_id());
    }

    @Override
    public Machine searchByNIC(String NIC) throws SQLException {
        return null;
    }

    @Override
    public Machine searchById(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM machine WHERE machine_id = ?",id);
        Machine machine = null;
        if (resultSet.next()) {
            String machine_id = resultSet.getString(1);
            String brand = resultSet.getString(2);

            machine = new Machine(machine_id,brand);
        }
        return machine;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<Machine> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM machine");

        List<Machine> machineList = new ArrayList<>();
        while (resultSet.next()) {
            String machine_id = resultSet.getString(1);
            String brand = resultSet.getString(2);

            Machine machine = new Machine(machine_id,brand);
            machineList.add(machine);
        }
        return machineList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }


    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT machine_id FROM machine ORDER BY machine_id DESC LIMIT 1;");
        if (rst.next()) {
            String[] split = rst.getString(1).split("M#");
            int id = Integer.parseInt(split[1],10);
            return "M#" + String.format("%04d", ++id);
        }
        return "M#0001";
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT machine_id FROM machine WHERE machine_id=?", id);
        return rst.next();
    }
}
