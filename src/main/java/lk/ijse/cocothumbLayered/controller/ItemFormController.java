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
import lk.ijse.cocothumbLayered.bo.custom.ItemBO;
import lk.ijse.cocothumbLayered.controller.Util.Regex;
import lk.ijse.cocothumbLayered.controller.Util.TextField;
import lk.ijse.cocothumbLayered.dto.ItemDTO;
import lk.ijse.cocothumbLayered.entity.Item;
import lk.ijse.cocothumbLayered.view.tdm.ItemTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtCode1;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colitemType;

    @FXML
    private AnchorPane rootNode7;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private JFXTextField txtItemType;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtQtyOnHandCompany;

    @FXML
    private JFXTextField txtUnitPrice;
    private List<ItemDTO> itemList = new ArrayList<>();

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);



    @FXML
    void btnClear(ActionEvent event) throws ClassNotFoundException {
        txtCode1.setText("");
        txtCode.setText("");
        txtItemType.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHandCompany.setText("");
        txtQtyOnHand.setText("");
        generateNewId();

    }


    @FXML
    void btnSave(ActionEvent event) throws SQLException, ClassNotFoundException {
        String item_code = txtCode.getText();
        String item_type = txtItemType.getText();
        double unit_price = Double.parseDouble(txtUnitPrice.getText());
        double unit_price_forCompany = Double.parseDouble(txtQtyOnHandCompany.getText());
        String stock_qty = txtQtyOnHand.getText();
        String user_id = NewLoginController.getUserId();

        if (isValid()) {
            try {
                boolean isSaved = itemBO.saveItem(new ItemDTO(item_code, item_type,
                        unit_price,unit_price_forCompany, stock_qty, user_id));
                tblItem.getItems().add(new ItemTm(item_code, item_type,
                        unit_price, unit_price_forCompany, stock_qty));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "items saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        initialize();
        btnClear(event);

    }

    @FXML
    void btnUpdate(ActionEvent event) throws SQLException, ClassNotFoundException {
        String item_code = txtCode.getText();
        String item_type = txtItemType.getText();
        double unit_price = Double.parseDouble(txtUnitPrice.getText());
        double unit_price_forCompany = Double.parseDouble(txtQtyOnHandCompany.getText());
        String stock_qty = txtQtyOnHand.getText();
        String user_id = NewLoginController.getUserId();
    if (isValid()){
        Item item = new Item(item_code,item_type, unit_price,unit_price_forCompany, stock_qty, user_id);

        try {
            boolean isUpdated = itemBO.updateItem(new ItemDTO(item_code, item_type,
                    unit_price, unit_price_forCompany, stock_qty, user_id));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        ItemTm selectedItem = new ItemTm(item_code, item_type,
                unit_price, unit_price_forCompany, stock_qty);
        System.out.println("selectedItem = " + selectedItem);
    }

        initialize();
        btnClear(event);
    }



    public void SearchCode(ActionEvent actionEvent) {
        String item_code = txtCode.getText();

        try {
            Item item = itemBO.searchItemCode(item_code);

            if (item != null) {
                txtCode.setText(item.getItem_code());
                txtItemType.setText(item.getItem_type());
                txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
                txtQtyOnHand.setText(item.getStock_qty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void initialize() throws ClassNotFoundException, SQLException {
        this.itemList=itemBO.getAllItem();
        setItemValue();
        loadItemTable();
        generateNewId();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue != null) {
                txtCode.setText(newValue.getItem_code());
                txtItemType.setText(newValue.getItem_type());
                txtUnitPrice.setText(String.valueOf(newValue.getUnit_price()));
                txtQtyOnHand.setText(newValue.getStock_qty());
                txtQtyOnHandCompany.setText(String.valueOf(newValue.getUnit_price_forCompany()));

                txtCode.setDisable(false);
                txtItemType.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
                txtQtyOnHandCompany.setDisable(false);
            }
        });

    }

    private void loadItemTable() {
     tblItem.getItems().clear();
     try{
         List<ItemDTO> AllItems = itemBO.getAllItem();

         for (ItemDTO item : AllItems) {
             tblItem.getItems().add(new ItemTm(item.getItem_code(), item.getItem_type(),
                     item.getUnit_price(), item.getUnit_price_forCompany(), item.getStock_qty()));
         }
     }catch (SQLException e){
             new Alert(Alert.AlertType.ERROR, e.getMessage());
     }
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setItemValue() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("item_code"));
        colitemType.setCellValueFactory(new PropertyValueFactory<>("item_type"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("stock_qty"));

    }

    private void generateNewId() throws ClassNotFoundException, RuntimeException {
        try {
            String currentId = itemBO.generateItemNewID();
            System.out.println("itemBO eke id ek "+currentId);

            txtCode.setText(currentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<ItemDTO> getAllItems() {
        List<ItemDTO> itemList = null;
        try {
            itemList = itemBO.getAllItem();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }

    public void btnDelete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
         txtCode.getText();

        try {
            String id = tblItem.getSelectionModel().getSelectedItem().getItem_code();
            boolean isDeleted = itemBO.deleteItem(id);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
        btnClear(actionEvent);
    }

    public void actionsearch(ActionEvent actionEvent) {
        String item_code = txtCode1.getText();

        try {
            Item item = itemBO.searchItemCode(item_code);

            if (item != null) {
                txtCode.setText(item.getItem_code());
                txtItemType.setText(item.getItem_type());
                txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
                txtQtyOnHandCompany.setText(String.valueOf(item.getUnit_price_forCompany()));
                txtQtyOnHand.setText(item.getStock_qty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtItemTypeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.name.name,txtItemType);
    }

    public void txtUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.Double.Double,txtUnitPrice);
    }
    public void txtUcompanyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.Double.Double,txtQtyOnHandCompany);
    }
    public void txtQtyOnHandOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.INT.INT,txtQtyOnHand);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.cocothumbLayered.controller.Util.TextField.name,txtItemType)) return false;
        else if (!Regex.setTextColor(TextField.Double,txtUnitPrice)) return false;
        else if (!Regex.setTextColor(TextField.Double,txtQtyOnHandCompany)) return false;
        else if (!Regex.setTextColor(TextField.INT,txtQtyOnHand)) return false;
        return true;
    }

}

