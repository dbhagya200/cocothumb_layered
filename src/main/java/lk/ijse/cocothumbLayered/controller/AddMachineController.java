package lk.ijse.cocothumbLayered.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumbLayered.bo.BOFactory;
import lk.ijse.cocothumbLayered.bo.custom.MachineBO;
import lk.ijse.cocothumbLayered.controller.Util.Regex;
import lk.ijse.cocothumbLayered.dto.MachineDTO;
import lk.ijse.cocothumbLayered.entity.Machine;
import lk.ijse.cocothumbLayered.view.tdm.MachineTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddMachineController {


    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootNodeMachine;

    @FXML
    private TableView<MachineTm> tblMachine;

    @FXML
    private JFXTextField txtBrandName;

    @FXML
    private JFXTextField  txtId;
    @FXML
    private JFXTextField  txtId1;

    private static String machine_id;

    public static String getMachineId() {
        return machine_id;
    }
    private List<MachineDTO> machineList = new ArrayList<>();

    MachineBO machineBO = (MachineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MACHINE);

    public void clearData() throws ClassNotFoundException {
        txtId.setText("");
        txtBrandName.setText("");
        generateNewId();
    }


    @FXML
    void btnSave(ActionEvent event) throws ClassNotFoundException {
     machine_id = txtId.getText();
    String brand = txtBrandName.getText();


    if (isValid()) {
        try {
            boolean isSaved = machineBO.saveMachine(new MachineDTO(machine_id,brand));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Machine saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

        initialize();
        clearData();
    }

    @FXML
    void btnUpdate(ActionEvent event) throws ClassNotFoundException {
    String machine_id = txtId.getText();
    String brand = txtBrandName.getText();

    if (isValid()) {

        try {
            boolean isUpdated = machineBO.updateMachine(new MachineDTO(machine_id,brand));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "machine updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        MachineTm selectedItem = new MachineTm(machine_id,brand);
        System.out.println("selectedItem = " + selectedItem);
    }

        initialize();
        clearData();
    }

    @FXML
    void idSearch(ActionEvent event) {
        String machine_id = txtId.getText();

        try {
            Machine machine = machineBO.searchMachineId(machine_id);

            if (machine != null) {
                txtId.setText(machine.getMachine_id());
                txtBrandName.setText(machine.getBrand());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() throws ClassNotFoundException {
        this.machineList = getAllMachines();
        setMachineValue();
        loadMachineTable();
        generateNewId();

        tblMachine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue != null) {
                txtId.setText(newValue.getMachine_id());
                txtBrandName.setText(newValue.getBrand());

                txtId.setDisable(false);
                txtBrandName.setDisable(false);

            }
        });

    }

    private List<MachineDTO> getAllMachines() {

        List<lk.ijse.cocothumbLayered.dto.MachineDTO> machineList = null;
        try {
            machineList = machineBO.getAllMachine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return machineList;
    }

    private void setMachineValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("machine_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("brand"));
    }

    private void loadMachineTable() {
        tblMachine.getItems().clear();
        try {
            /*Get all*/
            ArrayList<MachineDTO> allMachines = (ArrayList<MachineDTO>) machineBO.getAllMachine();

            for (MachineDTO m : allMachines) {
                tblMachine.getItems().add(new MachineTm(m.getMachine_id(), m.getBrand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        System.out.println("selectedItem = " + tblMachine.getSelectionModel().getSelectedItem());
    }

    private void generateNewId() throws ClassNotFoundException {
        try {
            String currentId = machineBO.generateMachineNewID();
            System.out.println("machineBO eke id ek "+currentId);

            txtId.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void actionsearch(ActionEvent actionEvent) {
        String machine_id = txtId1.getText();

        try {
            Machine machine = machineBO.searchMachineId(machine_id);

            if (machine != null) {
                txtId.setText(machine.getMachine_id());
                txtBrandName.setText(machine.getBrand());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.name.name,txtBrandName);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.name,txtBrandName)) return false;
        return true;
    }
}
