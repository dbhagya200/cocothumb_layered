package lk.ijse.cocothumbLayered.bo.custom;

import lk.ijse.cocothumbLayered.bo.SuperBO;
import lk.ijse.cocothumbLayered.dto.MachineDTO;
import lk.ijse.cocothumbLayered.entity.Machine;

import java.sql.SQLException;
import java.util.List;

public interface MachineBO extends SuperBO {
    public  boolean saveMachine(MachineDTO dto) throws SQLException;


    public  boolean updateMachine(MachineDTO dto) throws SQLException ;

    public Machine searchMachineId(String id) throws SQLException ;

    public  boolean deleteMachine(String id) throws SQLException ;


    public List<MachineDTO> getAllMachine() throws SQLException ;

    //public  List<String> getCustomerIds() throws SQLException ;

    //List<String> getCustomerNIC() throws SQLException ;

    String generateMachineNewID() throws SQLException ;
}
