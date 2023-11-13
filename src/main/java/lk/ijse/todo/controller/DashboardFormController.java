package lk.ijse.todo.controller;

/*
    @author DanujaV
    @created 11/7/23 - 2:51 AM   
*/

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.todo.model.TaskModel;
import lk.ijse.todo.model.UserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    @FXML
    private Label lblCompleted;

    @FXML
    private Label lblDue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCompleted.setText(String.valueOf(TaskModel.getAllCompletedTask(UserModel.getEmailOfUName(LoginFormController.uName)).size()));
        lblDue.setText(String.valueOf(TaskModel.getAllDueTask(UserModel.getEmailOfUName(LoginFormController.uName)).size()));
    }
}
