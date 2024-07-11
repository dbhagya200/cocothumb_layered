package lk.ijse.cocothumbLayered.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.cocothumbLayered.bo.BOFactory;
import lk.ijse.cocothumbLayered.bo.custom.PlaceOrderBO;
import lk.ijse.cocothumbLayered.dto.*;
import lk.ijse.cocothumbLayered.view.tdm.CartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static lk.ijse.cocothumbLayered.db.dbConnection.dbConnection;

public class CustomerOrderFormController {


    public AnchorPane rootNode4;
    public TableColumn colMethod;
    public JFXComboBox cmbMethod;
    public TextField txtEmail;
    public JFXButton bttnPlaseOrder;
    @FXML
    private  TextField txtCustId;
    public Label lblNetTotal;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtNetTotal;

    @FXML
    private  TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStockQty;

    @FXML
    private TextField txtUnitPrice;
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private  ComboBox<String> cmbCustomerNIC;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private  TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private DatePicker lblOrderDate;
    @FXML
    private AnchorPane rootNodePayment;

    @FXML
    private  TableView<CartTm> tblOrderCart;

    public AnchorPane customRoot;
    private ObservableList<CartTm> cartList = FXCollections.observableArrayList();
    private  double netTotal = 0;
    private  String cust_id;

    public  String getCust_id() {
        return cust_id;
    }

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    @FXML
    void btnShowTable(ActionEvent event) throws IOException {

    }



    @FXML
    void btnAddToCartOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        String item_code = cmbItemCode.getValue();
        System.out.println("dfghjbngh"+item_code);
        int qty = Integer.parseInt(txtQty.getText());
        String description = txtItemType.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double amount = qty * unitPrice;
        String pay_method = String.valueOf(cmbMethod.getValue());
        String email = txtEmail.getText();
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);
                System.out.println("remove = " + cartList);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });


            if (txtQty.getText().equals(txtStockQty.getText())|| Integer.parseInt(txtQty.getText()) < Integer.parseInt(txtStockQty.getText())) {

                for (int j = 0; j < tblOrderCart.getItems().size(); j++) {
                    if (item_code.equals(colItemCode.getCellData(j))) {
                        qty += cartList.get(j).getQty();

                        if (qty > Integer.parseInt(txtStockQty.getText())) {
                            new Alert(Alert.AlertType.ERROR, "Item is out of stock").show();
                            return;
                        }

                        amount = unitPrice * qty;

                        cartList.get(j).setQty(qty);
                        cartList.get(j).setAmount(amount);


                        initialize();

                        tblOrderCart.refresh();
                        calculateNetTotal();
                        txtQty.setText("");
                        txtItemType.setText("");
                        cmbItemCode.setDisable(false);

                        return;
                    }
                }

                    CartTm cartTm = new CartTm(item_code,  qty,description, unitPrice, amount,pay_method,email, btnRemove);

                    cartList.add(cartTm);
                    tblOrderCart.setItems(cartList);



            }else {
                new Alert(Alert.AlertType.ERROR, "Item is out of stock").show();
                return;
            }

        txtQty.setText("");
        txtItemType.setText("");
        cmbItemCode.setDisable(false);
        //cmbItemCode.getSelectionModel().clearSelection();
        txtStockQty.setText("");
        txtUnitPrice.setText("");
        calculateNetTotal();
    }



    @FXML
    void btnPlaceOrder(ActionEvent event) throws IOException, ClassNotFoundException {
        String order_id = txtOrderId.getText();
        String cust_NIC = cmbCustomerNIC.getValue();
        cust_id = txtCustId.getText();
        String user_id = NewLoginController.getUserId();
        Date date = Date.valueOf(LocalDate.now());


        OrdersDTO orders = new OrdersDTO(order_id, cust_NIC,cust_id, user_id, date);


        List<OrderDetailsDTO> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = cartList.get(i);

            OrderDetailsDTO OD = new OrderDetailsDTO(

                    tm.getItem_code(),
                    order_id,
                    tm.getQty(),
                    tm.getDescription(),
                    tm.getUnit_price(),
                    tm.getAmount(),
                    tm.getPay_method(),
                    tm.getEmail()
            );
            odList.add(OD);
        }

        PlaceOrderDTO PO = new PlaceOrderDTO(orders, odList);
        try {
            boolean isPlaced = placeOrderBO.placeOrder(PO);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();


            } else {
                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }


    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        txtNetTotal.setText(String.valueOf(netTotal));
        System.out.println("net total = " + netTotal);
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("pay_method"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }
    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String cust_NIC = (String) cmbCustomerNIC.getValue();

        try {
         CustomerDTO customerDTO = placeOrderBO.searchCustomer(cust_NIC);

            txtCustName.setText(customerDTO.getCust_name());
            txtCustId.setText(customerDTO.getCust_id());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
    String code = cmbItemCode.getValue();
        System.out.println("code = " + code);

    try {
        ItemDTO itemDTO = placeOrderBO.searchItem(code);
        txtItemType.setText(itemDTO.getItem_type());
        txtUnitPrice.setText(String.valueOf(itemDTO.getUnit_price()));
        txtStockQty.setText(itemDTO.getStock_qty());
        
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
        txtQty.requestFocus();
    }

    public void comboMethod() {
        cmbMethod.getItems().addAll(PayMethod.values());

        cmbMethod.setOnAction(Event -> {
            PayMethod selected = (PayMethod) cmbMethod.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selected);
        });
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {

        btnAddToCartOnAction(event);
    }
    public void initialize() throws ClassNotFoundException, SQLException {
        generateNewId();
        setCellValueFactory();
        getCustomerNIC();
        getItemCodes();
        comboMethod();
    }

    private void generateNewId() throws ClassNotFoundException {
        try {
            String currentId = placeOrderBO.generateNewOrderID();
            System.out.println("placeOrderBO eke id ek "+currentId);

            txtOrderId.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    private void getCustomerNIC() throws SQLException, ClassNotFoundException {

        try {
            List<CustomerDTO> allCustomers = placeOrderBO.getAllCustomers();
          for (CustomerDTO c : allCustomers) {
              cmbCustomerNIC.getItems().add(c.getCust_NIC());
          }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getItemCodes() {

        try {
            List<ItemDTO> allItems = placeOrderBO.getAllItems();
         for (ItemDTO i : allItems) {
             cmbItemCode.getItems().add(i.getItem_code());
         }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnaddtocart(MouseEvent mouseEvent) {
    }


    @FXML
    void btnPrintBill(ActionEvent event) throws SQLException, JRException, ClassNotFoundException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/coco_bill.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);


        Map<String, Object> data = new HashMap<>();
        data.put("OrderID",txtOrderId.getText());
        data.put("NetTotal",txtNetTotal.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        dbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);



        txtCustName.setText("");
        txtCustId.setText("");
        txtEmail.setText("");
        txtNetTotal.setText("");
        txtItemType.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        cmbItemCode.setValue("");
        cmbCustomerNIC.setValue("");
        cmbMethod.setValue("");
        txtStockQty.setText("");
        tblOrderCart.getItems().clear();
        generateNewId();

        /*JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/coco_bill.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);


        Map<String, Object> data = new HashMap<>();
        data.put("Order ID",txtOrderId.getText());
        data.put("Net Total",txtNetTotal.getText());
        data.put("custId",txtCustId.getText());
        data.put("description",txtItemType.getText());
        data.put("unitPrice",txtUnitPrice.getText());
        data.put("qty",txtQty.getText());
        data.put("amount",txtNetTotal.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        dbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);*/

    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
        //Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.INT,txtQty);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        //Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.email.email, (JFXTextField) txtEmail);
    }
  /*  public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.INT, (JFXTextField) txtQty)) return false;
        else if (!Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.email, (JFXTextField) txtEmail)) return false;
        return true;
    }*/
    public void txtQtyOnKeyType(KeyEvent keyEvent) {
        /*if (txtQty.getText().equals(Integer.parseInt(txtStockQty.getText()))) {
            txtQty.setStyle("-fx-border-color: green");
            btnAddToCart.setDisable(false);
        }
        else {
            btnAddToCart.setDisable(true);
            txtQty.setStyle("-fx-border-color: red");
        }*/
    }
    public void txtEmailOnKeyType(KeyEvent keyEvent) {
       /* if (isValid()) {
            txtEmail.setStyle("-fx-border-color: green");
            btnAddToCart.setDisable(false);
            bttnPlaseOrder.setDisable(false);
        }
        else {
            btnAddToCart.setDisable(true);
            bttnPlaseOrder.setDisable(true);
            txtEmail.setStyle("-fx-border-color: red");
        }*/
    }
}
