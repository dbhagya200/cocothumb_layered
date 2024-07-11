package lk.ijse.cocothumbLayered.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumbLayered.bo.BOFactory;
import lk.ijse.cocothumbLayered.bo.custom.SupplierBO;
import lk.ijse.cocothumbLayered.controller.Util.Regex;
import lk.ijse.cocothumbLayered.controller.Util.TextField;
import lk.ijse.cocothumbLayered.dto.SupplierDTO;
import lk.ijse.cocothumbLayered.entity.Supplier;
import lk.ijse.cocothumbLayered.view.tdm.SupplierTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddSuppFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootNode8;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtId1;

    @FXML
    private JFXTextField txtName;

    private List<SupplierDTO> supplierList = new ArrayList<>();

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @FXML
    void btnClear(ActionEvent event) throws ClassNotFoundException {
        txtId1.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        generateNewId();

    }

    @FXML
    void btnExit(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form_old.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode8.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }

    @FXML
    void btnHome(ActionEvent event) throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootNode8.getScene().getWindow();

        stage.setScene(new Scene(rootNode1));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSave(ActionEvent event) throws ClassNotFoundException, SQLException {
        String supp_id = txtId.getText();
        String supp_name = txtName.getText();
        String supp_address = txtAddress.getText();
        String supp_contact = txtContact.getText();

    if (isValid()){

    try {
        boolean isSaved = supplierBO.saveSupplier(new SupplierDTO(supp_id, supp_name,
                supp_address, supp_contact));
        tblSupplier.getItems().add(new SupplierTm(supp_id, supp_name,
                supp_address, supp_contact));
        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
            initialize();
            btnClear(event);
        }
    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
}

        initialize();
        btnClear(event);

    }

    @FXML
    void btnUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {

        String supp_id = txtId.getText();
        String supp_name = txtName.getText();
        String supp_address = txtAddress.getText();
        String supp_contact = txtContact.getText();

if (isValid()){
    try {
        boolean isUpdated = supplierBO.updateSupplier(new SupplierDTO(supp_id, supp_name, supp_address, supp_contact));
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
        }
        initialize();
        btnClear(event);
    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
    SupplierTm selectedItem = new SupplierTm(supp_id, supp_name, supp_address, supp_contact);
    System.out.println("selectedItem = " + selectedItem);
}

        initialize();
        btnClear(event);

    }

    @FXML
    void btnDelete(ActionEvent event) throws ClassNotFoundException, SQLException {
         txtId.getText();

        try {
            String id = tblSupplier.getSelectionModel().getSelectedItem().getSupp_id();
            boolean isDeleted = supplierBO.deleteSupplier(id);
            tblSupplier.getItems().remove(tblSupplier.getSelectionModel().getSelectedItem());
            tblSupplier.getSelectionModel().clearSelection();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
            }
            initialize();
            btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        initialize();
        btnClear(event);
    }

    @FXML
    void idSearch(ActionEvent event) {

        String supp_id = txtId.getText();

        try {
            Supplier supplier = supplierBO.searchSupplierId(supp_id);

            if (supplier != null) {
                txtId.setText(supplier.getSupp_id());
                txtName.setText(supplier.getSupp_name());
                txtAddress.setText(supplier.getSupp_address());
                txtContact.setText(supplier.getSupp_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() throws ClassNotFoundException, SQLException {
        this.supplierList = supplierBO.getAllSupplier();
        generateNewId();
        setSupplierValue();
        loadSupplierTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue != null) {
                txtId.setText(newValue.getSupp_id());
                txtName.setText(newValue.getSupp_name());
                txtAddress.setText(newValue.getSupp_address());
                txtContact.setText(newValue.getSupp_contact());

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
            }
        });

    }



    private void generateNewId() throws ClassNotFoundException {
        try {
            String currentId = supplierBO.generateSupplierNewID();
            System.out.println("supplierBO eke id ek "+currentId);

            txtId.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setSupplierValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("supp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supp_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supp_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("supp_contact"));
    }
    private void loadSupplierTable() throws SQLException {
        tblSupplier.getItems().clear();
        try {
            ArrayList<SupplierDTO> allSuppliers = (ArrayList<SupplierDTO>) supplierBO.getAllSupplier();

            for (SupplierDTO supplier : allSuppliers) {
                tblSupplier.getItems().add(new SupplierTm(supplier.getSupp_id(),
                        supplier.getSupp_name(), supplier.getSupp_address(),
                        supplier.getSupp_contact()));

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        SupplierTm selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }




    public void actionsearch(ActionEvent actionEvent) {
        String supp_id = txtId1.getText();

        try {
            Supplier supplier = supplierBO.searchSupplierId(supp_id);

            if (supplier != null) {
                txtId.setText(supplier.getSupp_id());
                txtName.setText(supplier.getSupp_name());
                txtAddress.setText(supplier.getSupp_address());
                txtContact.setText(supplier.getSupp_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.name,txtName);
    }
    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.address,txtAddress);
    }
    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.contact,txtContact);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.name,txtName)) return false;
        else if (!Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.contact,txtContact)) return false;
        else if (!Regex.setTextColor(TextField.address,txtAddress)) return false;

        return true;
    }
}
