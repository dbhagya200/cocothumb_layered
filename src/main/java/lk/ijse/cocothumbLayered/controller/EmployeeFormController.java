package lk.ijse.cocothumbLayered.controller;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cocothumbLayered.bo.BOFactory;
import lk.ijse.cocothumbLayered.bo.custom.EmployeeBO;
import lk.ijse.cocothumbLayered.bo.custom.UserBO;
import lk.ijse.cocothumbLayered.controller.Util.EmailSender;
import lk.ijse.cocothumbLayered.controller.Util.Regex;
import lk.ijse.cocothumbLayered.controller.Util.TextField;
import lk.ijse.cocothumbLayered.dto.EmployeeDTO;
import lk.ijse.cocothumbLayered.entity.Employee;
import lk.ijse.cocothumbLayered.view.tdm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.cocothumbLayered.controller.UserFormController.*;

public class EmployeeFormController {


    public JFXTextField txtEmail;
    @FXML
    private TableColumn<?, ?> colSalary;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;
    @FXML
    private AnchorPane rootNode2;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

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

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXComboBox<JobRole> cmbjobrole;
    private List<EmployeeDTO> employeeList = new ArrayList<>();
    private static String e_id;
    private static String e_contact;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    public static String getE_contact() {
        return e_contact;
    }
    public static String getE_id() {
        return e_id;
    }

    @FXML
    void cmbjobroleOnAction(ActionEvent event) throws SQLException {


    }

    @FXML
    void btnClear(ActionEvent event) throws SQLException {
        txtId1.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtSalary.setText("");
        txtEmail.setText("");
        employeeBO.generateEmployeeNewID();

    }

    @FXML
    void btnAddNewMachine(ActionEvent event) throws IOException {
        AnchorPane rootNodeMachine = FXMLLoader.load(getClass().getResource("/view/add_machine.fxml"));

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add Machine Form");
        popupStage.setScene(new Scene(rootNodeMachine));
        popupStage.showAndWait();
    }

    @FXML
    void btnSave(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        e_id = txtId.getText();
        String e_name = txtName.getText();
        String e_jobrole = cmbjobrole.getValue().toString();
        String e_address = txtAddress.getText();
        String e_contact = txtContact.getText();
        double e_salary = Double.parseDouble(txtSalary.getText());
        String e_email = txtEmail.getText();
        String machine_id = AddMachineController.getMachineId();


    if (e_jobrole.equals("Manager")||e_jobrole.equals("Assistant")){
    userBO.generateUserNewID();
    txtUserId.setText(String.valueOf(UserFormController.txtUserId));
    txtUserName.setText(EmployeeFormController.getE_id());
    txtPassword.setText(EmployeeFormController.getE_contact());
    UserFormController.txtEmail.setText(e_email);
    UserFormController.cmbJobrole.setValue(JobRole.valueOf(e_jobrole));
    UserFormController.txtEmpId.setText(e_id);


    }
         if (isValid()) {


        Employee employee = new Employee(e_id, e_name, e_jobrole, e_address,
                e_contact, e_salary,e_email, machine_id);
       // User user = new User(e_id, e_email);
        System.out.println(employee);
        try {
            boolean isSaved = employeeBO.saveEmployee(new EmployeeDTO(e_id, e_name, e_jobrole,
                    e_address, e_contact, e_salary, e_email, machine_id));

            tblEmployee.getItems().add(new EmployeeTm(e_id, e_name, e_jobrole,
                    e_address, e_contact, e_salary, e_email));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                initialize();
                btnClear(event);

                String body = "<html>"
                        + "<body>"
                        + "<p>Dear " + e_name + ",</p>"
                        + "<p>We are pleased to inform you that your account for accessing our system has been successfully created. Below are your login credentials:</p>"
                        + "<p>Username: <span style='color:blue; font-size:14px;'>" + e_email + "</span></p>"
                        + "<p>Password: <span style='color:green; font-size:14px;'>" + e_contact + "</span></p>"
                        + "<p>For security reasons, we recommend that you change your password upon your first login.</p>"
                        + "<p>If you have any questions or need assistance, please don't hesitate to contact our support team at support@example.com.</p>"
                        + "<p>Best regards,<br>CocoThumb</p>"
                        + "</body>"
                        + "</html>";
                EmailSender.sendEmail(e_email, "CocoThumb System User Login Access", body);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
    }
            initialize();
            btnClear(event);

    }
    private void generateNewId() throws ClassNotFoundException {
        try {
            String currentId = employeeBO.generateEmployeeNewID();
            System.out.println("employeeBO eke id ek "+currentId);

            txtId.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdate(ActionEvent event) throws SQLException, ClassNotFoundException {
        String e_id = txtId.getText();
        String e_name = txtName.getText();
        String e_jobrole= cmbjobrole.getValue().toString();
        String e_address = txtAddress.getText();
        String e_contact = txtContact.getText();
        double e_salary = Double.parseDouble(txtSalary.getText());
        String e_email = txtEmail.getText();
        String machine_id = AddMachineController.getMachineId();

    if (isValid()) {
        try {

            boolean isUpdated = employeeBO.updateEmployee(new EmployeeDTO(e_id, e_name, e_jobrole,
                    e_address, e_contact, e_salary, e_email, machine_id));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
                initialize();
                btnClear(event);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer ");
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        EmployeeTm selectedItem = new EmployeeTm(e_id, e_name, e_jobrole,
                e_address, e_contact, e_salary, e_email);
        System.out.println("selectedItem = " + selectedItem);
    }
        initialize();
        btnClear(event);
    }

    @FXML
    void idSearch(ActionEvent event) throws SQLException {
        String id = txtId.getText();

        Employee employee = employeeBO.searchEmployeeId(id);

        if (employee != null) {
            txtId.setText(employee.getE_Id());
            txtName.setText(employee.getE_Name());
            txtAddress.setText(employee.getE_Address());
            txtContact.setText(employee.getE_Contact());
            txtSalary.setText(String.valueOf(employee.getE_Salary()));
        }
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        this.employeeList = getAllEmployees();
        setEmployeeValue();
        loadEmployeeTable();
        generateNewId();
        comboJob();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue != null) {
                txtId.setText(newValue.getE_id());
                txtName.setText(newValue.getE_name());
                txtAddress.setText(newValue.getE_address());
                txtContact.setText(newValue.getE_contact());
                txtEmail.setText(newValue.getE_email());
                txtSalary.setText(String.valueOf(newValue.getE_salary()));
                cmbjobrole.setValue(JobRole.valueOf(newValue.getE_jobrole()));

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
                txtEmail.setDisable(false);
                txtSalary.setDisable(false);
                cmbjobrole.setDisable(false);
            }
        });

    }



    private void setEmployeeValue() {
        colid.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("e_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("e_contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("e_salary"));

        System.out.println("value set");
    }


    private void loadEmployeeTable() {
        tblEmployee.getItems().clear();
        try {
            /*Get all*/
            ArrayList<EmployeeDTO> allEmployees = (ArrayList<EmployeeDTO>) employeeBO.getAllEmployees();

            for (EmployeeDTO e : allEmployees) {
                tblEmployee.getItems().add(new EmployeeTm(e.getE_Id(), e.getE_Name(), e.getE_jobrole(),
                         e.getE_Contact(),e.getE_Address(), e.getE_Salary(),
                        e.getE_email()));}
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        System.out.println("selectedItem = " + tblEmployee.getSelectionModel().getSelectedItem());

    }

    public void comboJob(){
        cmbjobrole.getItems().addAll(JobRole.values());

        cmbjobrole.setOnAction(event -> {
            JobRole selected = cmbjobrole.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });

    }


    private List<EmployeeDTO> getAllEmployees() {

        List<lk.ijse.cocothumbLayered.dto.EmployeeDTO> employeeList = null;
        try {
            employeeList = employeeBO.getAllEmployees();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }



    public void btnDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try {
            String id = tblEmployee.getSelectionModel().getSelectedItem().getE_id();
            boolean isDeleted = employeeBO.deleteEmployee(id);
            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.getSelectionModel().clearSelection();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        btnClear(actionEvent);
    }


    public void actionsearch(ActionEvent actionEvent) throws SQLException {
        String id = txtId1.getText();

        Employee employee = employeeBO.searchEmployeeId(id);

        if (employee != null) {
            txtId.setText(employee.getE_Id());
            txtName.setText(employee.getE_Name());
            txtAddress.setText(employee.getE_Address());
            txtContact.setText(employee.getE_Contact());
            txtSalary.setText(String.valueOf(employee.getE_Salary()));
            txtEmail.setText(employee.getE_email());
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.name.name,txtName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.address.address,txtAddress);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.contact.contact,txtContact);
    }
    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.Double.Double,txtSalary);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(TextField.name,txtName)) return false;
        else if (!Regex.setTextColor(TextField.contact,txtContact)) return false;
        else if (!Regex.setTextColor(TextField.address,txtAddress)) return false;
        else if (!Regex.setTextColor(TextField.Double,txtSalary)) return false;
        else if (!Regex.setTextColor(TextField.email,txtEmail)) return false;
        return true;
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.email.email,txtEmail);

    }
}
