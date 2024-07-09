package lk.ijse.cocothumbLayered.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.cocothumbLayered.bo.BOFactory;
import lk.ijse.cocothumbLayered.bo.custom.SuppPlaceOrderBO;
import lk.ijse.cocothumbLayered.dto.*;
import lk.ijse.cocothumbLayered.view.tdm.CartTms;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuppPlaceOrderFormController {


    public TableView tblOrderCart;
    public TableColumn colMethod;
    public JFXComboBox cmbMethod;
    public TextField txtEmail;
    @FXML
    private JFXButton AddToCart;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private ComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblNetTotal;

    @FXML
    private AnchorPane rootNode6;

    @FXML
    private TableView<CartTms> tblSuppOrderCart;

    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtNetTotal;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStockQty;

    @FXML
    private TextField txtSuppName;

    @FXML
    private TextField txtUnitPrice;

    private ObservableList<CartTms> cartList = FXCollections.observableArrayList();

    SuppPlaceOrderBO suppPlaceOrderBO = (SuppPlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPP_PLACE_ORDER);
     int itemq;

    @FXML
    void btnAddNewSupplier(ActionEvent event) throws IOException {
        AnchorPane rootNode8 = FXMLLoader.load(getClass().getResource("/view/add_supp_form.fxml"));
        Stage stage = (Stage) rootNode6.getScene().getWindow();
        rootNode6.getChildren().clear();
        rootNode6.getChildren().add(rootNode8);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }
    private void clear() throws ClassNotFoundException {
        txtSuppName.setText("");
        txtEmail.setText("");
        txtNetTotal.setText("");
        cmbSupplierId.setValue("");
        cmbItemCode.setValue("");
        txtStockQty.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtItemType.setText("");
        //cmbMethod.setValue("");
        cartList.clear();
        tblSuppOrderCart.refresh();
        generateNewId();
    }

    @FXML
    void btnAddToCart(ActionEvent event) throws SQLException {
        String item_code = (String) cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String description = txtItemType.getText();
        double unitPriceForCompany = Double.parseDouble(txtUnitPrice.getText());
        double amount = qty * unitPriceForCompany;
        String pay_method = String.valueOf(cmbMethod.getValue());
        String email = txtEmail.getText();
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblSuppOrderCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblSuppOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblSuppOrderCart.getItems().size(); i++) {
            if (item_code.equals(colItemCode.getCellData(i))) {
                qty += cartList.get(i).getQty();
                amount = unitPriceForCompany * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setAmount(amount);

                tblSuppOrderCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                txtItemType.setText("");
                cmbItemCode.setDisable(false);
                txtUnitPrice.setText("");
                txtStockQty.setText("");
                return;
            }
        }


        CartTms cartTms = new CartTms(item_code,  qty,description, unitPriceForCompany, amount,pay_method,email, btnRemove);

        cartList.add(cartTms);

        tblSuppOrderCart.setItems(cartList);
        txtQty.setText("");
        txtItemType.setText("");
        cmbItemCode.setDisable(false);
        txtUnitPrice.setText("");
        txtStockQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        double netTotal = 0;
        for (int i = 0; i < tblSuppOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        txtNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootNode6.getScene().getWindow();

        stage.setScene(new Scene(rootNode1));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnPlaceOrder(ActionEvent event) throws ClassNotFoundException {
        String order_id = txtOrderId.getText();
        String supp_id = cmbSupplierId.getValue();
        String user_id = NewLoginController.getUserId();
        Date date = Date.valueOf(LocalDate.now());

        var suppOrder = new SuppOrderDTO(order_id, supp_id, user_id, date);

        List<SuppDetailsDTO> sodList = new ArrayList<>();
        for (int i=0;i<tblSuppOrderCart.getItems().size();i++) {
            CartTms tms = cartList.get(i);

            SuppDetailsDTO SD = new SuppDetailsDTO(
                    tms.getItem_code(),
                    supp_id,
                    order_id,
                    tms.getQty(),
                    tms.getDescription(),
                    tms.getUnit_price_forCompany(),
                    tms.getAmount(),
                    tms.getPay_method(),
                    tms.getEmail()
            );
            sodList.add(SD);
        }
        SPlaceOrderDTO SPO = new SPlaceOrderDTO(suppOrder, sodList);

        try {
            boolean isPlaced = suppPlaceOrderBO.placeOrder(SPO);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier order placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "supplier order not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clear();
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws IOException {
        String code = (String) cmbItemCode.getValue();

        try {
            ItemDTO item = suppPlaceOrderBO.searchItem(code);
            txtItemType.setText(item.getItem_type());
            txtUnitPrice.setText(String.valueOf(item.getUnit_price_forCompany()));
            txtStockQty.setText(item.getStock_qty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtQty.requestFocus();
    }

    @FXML
    void cmbSupplier(ActionEvent event) {
        String supp_id = (String) cmbSupplierId.getValue();

        try {
            SupplierDTO supplier = suppPlaceOrderBO.searchSupplier(supp_id);

            txtSuppName.setText(supplier.getSupp_name());
            System.out.println("supp name "+ supplier.getSupp_name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void comboMethod() {
        cmbMethod.getItems().addAll(PayMethod.values());

        cmbMethod.setOnAction(Event -> {
            PayMethod selected = (PayMethod) cmbMethod.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        generateNewId();
        getSupplierId();
        getItemCodes();
        comboMethod();
    }

    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<ItemDTO> allItems = suppPlaceOrderBO.getAllItems();
            for (ItemDTO i : allItems) {
                cmbItemCode.getItems().add(i.getItem_code());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSupplierId() {
        try {
            List<SupplierDTO> allSuppliers = suppPlaceOrderBO.getAllSuppliers();
            for (SupplierDTO c : allSuppliers) {
                cmbSupplierId.getItems().add(c.getSupp_id());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNewId() throws ClassNotFoundException {
        try {
            String currentId = suppPlaceOrderBO.generateNewOrderID();
            System.out.println("SuppPlaceOrderBO eke id ek "+currentId);

            txtOrderId.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price_forCompany"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("pay_method"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }


    public void btnPrintBill(ActionEvent actionEvent) {
    }

}
