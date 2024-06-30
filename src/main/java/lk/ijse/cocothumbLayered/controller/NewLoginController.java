package lk.ijse.cocothumbLayered.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.cocothumbLayered.entity.User;
import lk.ijse.cocothumbLayered.dao.custom.UserDAO;
import lk.ijse.cocothumbLayered.dao.custom.impl.UserDAOimpl;

import java.io.IOException;
import java.sql.SQLException;

public class NewLoginController {

    @FXML
    private AnchorPane rootNodeLogin;

    @FXML
    private ImageView rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public TextField txtUserId;
    private static String userId;

    UserDAO userDAO = new UserDAOimpl();

    @FXML
    void btnlogin(ActionEvent event) throws IOException {

         userId = txtUserId.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }

    }
    @FXML
    void actionFpassword(ActionEvent event) throws IOException {
        AnchorPane rootNodeForgot = FXMLLoader.load(getClass().getResource("/view/forgot_password.fxml"));

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("New Window");
        popupStage.setScene(new Scene(rootNodeForgot));
        popupStage.showAndWait();
    }



   public static String getUserId() {
        return userId;
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        User user = userDAO.searchById(userId);
        System.out.println("user = " + user);
        if (user != null) {
            if (user.getU_password().equals(pw)) {
                System.out.println("pw = " + pw);
                navigateToTheDashboard();
                System.out.println("done");


            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "user id not found!").show();
        }

    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(this.getClass().getResource("/view/new_dashboard.fxml"));

        Scene scene = new Scene(rootNode1);

        Stage stage = (Stage) this.rootNodeLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }



}


