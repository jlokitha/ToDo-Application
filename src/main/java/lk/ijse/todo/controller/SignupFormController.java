package lk.ijse.todo.controller;

/*
    @author DanujaV
    @created 11/7/23 - 12:18 AM   
*/


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.todo.dto.UserDto;
import lk.ijse.todo.model.UserModel;

import java.io.IOException;

public class SignupFormController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        UserDto userDto = new UserDto(txtEmail.getText(), txtUserName.getText(), txtPw.getText());

        boolean isSaved = UserModel.saveUser(userDto);

        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "User Saved !").show();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Not Saved !").show();
        }
    }

    @FXML
    void hyperLoginHereOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
    }

    public void clear() {
        txtUserName.clear();
        txtEmail.clear();
        txtPw.clear();
    }
}
