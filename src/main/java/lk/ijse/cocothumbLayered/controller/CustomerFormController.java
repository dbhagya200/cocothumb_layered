package lk.ijse.cocothumbLayered.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.cocothumbLayered.bo.custom.CustomerBO;
import lk.ijse.cocothumbLayered.controller.Util.Regex;
import lk.ijse.cocothumbLayered.controller.Util.TextField;
import lk.ijse.cocothumbLayered.dto.CustomerDTO;
import lk.ijse.cocothumbLayered.entity.Customer;
import lk.ijse.cocothumbLayered.view.tdm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {


    public JFXButton btnUpdate;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootNode3;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;


    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtId1;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtName;
    private List<lk.ijse.cocothumbLayered.dto.CustomerDTO> customerList = new ArrayList<>();

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @FXML
    void btnClear(ActionEvent event) throws SQLException, ClassNotFoundException {
        txtId.setText("");
        txtNIC.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        generateNewId();

    }

    @FXML
    void btnSave(ActionEvent event) throws ClassNotFoundException, SQLException {
       String cust_id = txtId.getText();
        String cust_NIC = txtNIC.getText();
        String cust_name = txtName.getText();
        String cust_address = txtAddress.getText();
        String cust_contact = txtContact.getText();
        String user_id = NewLoginController.getUserId();


            /*Save Customer*/
        if (cust_name.equals("")){
            txtName.setStyle("-fx-border-color: red");
        }

        if (isValid()) {

            try {
                if (existCustomer(cust_id)) {
                    new Alert(Alert.AlertType.ERROR, "Customer ID " + cust_id + " already exists").show();
                }

                //Add Customer
                boolean isSaved = customerBO.saveCustomer(new CustomerDTO(cust_id, cust_NIC, cust_name,
                        cust_address, cust_contact, user_id));

                tblCustomer.getItems().add(new CustomerTm(cust_id, cust_NIC, cust_name,
                        cust_address, cust_contact));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                }
               // initialize();
                loadCustomerTable();
                btnClear(event);

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
       //initialize();
        loadCustomerTable();
       btnClear(event);
    }
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);
    }

    @FXML
    void btnUpdate(ActionEvent event) throws ClassNotFoundException, SQLException {
        String cust_id = txtId.getText();
        String cust_NIC = txtNIC.getText();
        String cust_name = txtName.getText();
        String cust_address = txtAddress.getText();
        String cust_contact = txtContact.getText();
        String user_id = NewLoginController.getUserId();

        if (isValid()){

            try {
                boolean isUpdated = customerBO.updateCustomer(new CustomerDTO(cust_id, cust_NIC, cust_name,
                        cust_address, cust_contact, user_id));

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                }
               // initialize();
                loadCustomerTable();
                btnClear(event);

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            CustomerTm selectedItem = new CustomerTm(cust_id, cust_NIC, cust_name,
                    cust_address, cust_contact);
            System.out.println("selectedItem = " + selectedItem);
        }

       // initialize();
        loadCustomerTable();
        btnClear(event);

    }

    @FXML
    void NICSearch(ActionEvent event) {
        String cust_NIC = txtNIC.getText();

        try {
            Customer customer = customerBO.searchCustomerByNIC(cust_NIC);

            if (customer != null) {
                txtId.setText(customer.getCust_id());
                txtNIC.setText(customer.getCust_NIC());
                txtName.setText(customer.getCust_name());
                txtAddress.setText(customer.getCust_address());
                txtContact.setText(customer.getCust_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    @FXML
    void btnDelete(ActionEvent event) throws ClassNotFoundException, SQLException {

        txtId.getText();

        try {
            String id = tblCustomer.getSelectionModel().getSelectedItem().getCust_id();
            boolean isDeleted = customerBO.deleteCustomer(id);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }

            loadCustomerTable();
            btnClear(event);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        loadCustomerTable();
        btnClear(event);
    }
    public void initialize() throws ClassNotFoundException, SQLException {
        this.customerList = getAllCustomers();
        setCustomerValue();
        loadCustomerTable();
        generateNewId();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue != null) {
                txtId.setText(newValue.getCust_id());
                txtNIC.setText(newValue.getCust_NIC());
                txtName.setText(newValue.getCust_name());
                txtAddress.setText(newValue.getCust_address());
                txtContact.setText(newValue.getCust_contact());

                txtId.setDisable(false);
                txtNIC.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
            }
        });

    }

    private void generateNewId() throws ClassNotFoundException {
        try {
            String currentId = customerBO.generateNewCustomerID();
            System.out.println("customerBO eke id ek "+currentId);

            txtId.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    private void setCustomerValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("cust_NIC"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cust_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cust_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("cust_contact"));
    }

    private void loadCustomerTable() {
        tblCustomer.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<CustomerDTO> allCustomers = (ArrayList<CustomerDTO>) customerBO.getAllCustomers();

            for (CustomerDTO c : allCustomers) {
                tblCustomer.getItems().add(new CustomerTm(c.getCust_id(), c.getCust_NIC(), c.getCust_name(), c.getCust_address(), c.getCust_contact()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        System.out.println("selectedItem = " + tblCustomer.getSelectionModel().getSelectedItem());
    }
    private List<lk.ijse.cocothumbLayered.dto.CustomerDTO> getAllCustomers() {
        List<lk.ijse.cocothumbLayered.dto.CustomerDTO> customerList = null;
        try {
            customerList = customerBO.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    public void actionsearch(ActionEvent actionEvent) {
        String cust_id = txtId1.getText();

        try {
            Customer customer = customerBO.searchCustomerId(cust_id);

            if (customer != null) {
                txtId.setText(customer.getCust_id());
                txtNIC.setText(customer.getCust_NIC());
                txtName.setText(customer.getCust_name());
                txtAddress.setText(customer.getCust_address());
                txtContact.setText(customer.getCust_contact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void idSearch(ActionEvent actionEvent) {
    }

    public void txtCustomerNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NIC,txtNIC);
    }
    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.contact,txtContact);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(TextField.NIC,txtNIC)) return false;
        else if (!Regex.setTextColor(TextField.contact,txtContact)) return false;
        return true;
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.name,txtName);
    }
    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.address,txtAddress);
    }
}



