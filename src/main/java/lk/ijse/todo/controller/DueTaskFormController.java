package lk.ijse.todo.controller;

/*
    @author DanujaV
    @created 11/7/23 - 3:46 AM   
*/

import com.sun.javafx.scene.control.GlobalMenuAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.todo.dto.TasksDto;
import lk.ijse.todo.dto.tm.DueTm;
import lk.ijse.todo.model.TaskModel;
import lk.ijse.todo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DueTaskFormController {

    @FXML
    private TableColumn<?, ?> colComplete;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<DueTm> tblDue;

    public void initialize(){
        setCellValueFactory();
        loadDueTasks();
    }

    private void setCellValueFactory() {
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colComplete.setCellValueFactory(new PropertyValueFactory<>("btnComplete"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
    }

    private void loadDueTasks() {
        ObservableList<DueTm> obList = FXCollections.observableArrayList();

        List<TasksDto> list = TaskModel.getAllDueTask(UserModel.getEmailOfUName(LoginFormController.uName));

        for(TasksDto dto : list) {
            obList.add(
                    new DueTm(
                            dto.getDescription(),
                            dto.getDueDate()
                    )
            );
        }

        tblDue.setItems(obList);

        for (int i = 0; i < obList.size(); i++) {

            DueTm dueTm = obList.get(i);

            obList.get(i).getBtnComplete().setOnAction(event -> {

                String description = dueTm.getDescription();

                boolean isSaved = TaskModel.MarkAsCompleted(description);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Task Completed !").show();
                    tblDue.getItems().clear();
                    loadDueTasks();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Went Wrong !").show();
                }
            });

            obList.get(i).getBtnDelete().setOnAction(event -> {

                String description = dueTm.getDescription();

                boolean isDeleted = TaskModel.deleteTask(description);

                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Task Deleted!").show();
                    obList.remove(dueTm);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
                }

            });
        }
        tblDue.setItems(obList);
    }
}
