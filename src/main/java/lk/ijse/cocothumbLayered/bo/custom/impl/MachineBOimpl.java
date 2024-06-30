package lk.ijse.cocothumbLayered.bo.custom.impl;

import lk.ijse.cocothumbLayered.bo.custom.MachineBO;
import lk.ijse.cocothumbLayered.dao.DAOFactory;
import lk.ijse.cocothumbLayered.dao.custom.MachineDAO;
import lk.ijse.cocothumbLayered.dto.MachineDTO;
import lk.ijse.cocothumbLayered.entity.Machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineBOimpl implements MachineBO {

    MachineDAO machineDAO = (MachineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MACHINE);

    @Override
    public boolean saveMachine(MachineDTO dto) throws SQLException {
        return machineDAO.add(new Machine(dto.getMachine_id(), dto.getBrand()));
    }

    @Override
    public boolean updateMachine(MachineDTO dto) throws SQLException {
        return machineDAO.update(new Machine(dto.getMachine_id(), dto.getBrand()));
    }

    @Override
    public Machine searchMachineId(String id) throws SQLException {
        return machineDAO.searchById(id);
    }

    @Override
    public boolean deleteMachine(String id) throws SQLException {
        return machineDAO.delete(id);
    }

    @Override
    public List<MachineDTO> getAllMachine() throws SQLException {
        List<MachineDTO> allMachine = new ArrayList<>();
        List<Machine> all = machineDAO.getAll();
        for (Machine machine : all) {
            allMachine.add(new MachineDTO(machine.getMachine_id(), machine.getBrand()));
        }
        return allMachine;
    }

    @Override
    public String generateMachineNewID() throws SQLException {
        return machineDAO.generateNewID();
    }
}
