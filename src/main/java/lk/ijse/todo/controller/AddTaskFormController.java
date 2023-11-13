package lk.ijse.todo.controller;

/*
    @author DanujaV
    @created 11/7/23 - 3:23 AM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.todo.dto.TasksDto;
import lk.ijse.todo.model.TaskModel;
import lk.ijse.todo.model.UserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTaskFormController implements Initializable {
    @FXML
    private DatePicker txtDate;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    void btnAddTaskOnAction(ActionEvent event) {

        TasksDto tD = new TasksDto();

        tD.setTask_id(Integer.parseInt(txtId.getText()));
        tD.setEmail(UserModel.getEmailOfUName(LoginFormController.uName));
        tD.setDescription(txtDescription.getText());
        tD.setDueDate(String.valueOf(txtDate.getValue()));

        boolean isSaved = TaskModel.saveTask(tD);

        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "Task Added !").show();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Task Not Added !").show();
        }
    }

    public void clear() {
        txtId.clear();
        txtId.setText(TaskModel.generateNewId());
        txtDescription.clear();
        txtDate.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(TaskModel.generateNewId());
    }
}
