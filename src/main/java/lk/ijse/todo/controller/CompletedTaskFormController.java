package lk.ijse.todo.controller;

/*
    @author DanujaV
    @created 11/7/23 - 3:59 AM   
*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.todo.dto.TasksDto;
import lk.ijse.todo.dto.tm.CompleteTm;
import lk.ijse.todo.dto.tm.DueTm;
import lk.ijse.todo.model.TaskModel;
import lk.ijse.todo.model.UserModel;

import java.util.List;

public class CompletedTaskFormController {
    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CompleteTm> tblComplete;

    public void initialize(){
        setCellValueFactory();
        loadCompletedTasks();
    }

    private void loadCompletedTasks() {
        ObservableList<CompleteTm> obList = FXCollections.observableArrayList();

        List<TasksDto> list = TaskModel.getAllCompletedTask(UserModel.getEmailOfUName(LoginFormController.uName));

        for(TasksDto dto : list) {
            obList.add(
                    new CompleteTm(
                            dto.getDescription(),
                            dto.getDueDate()
                    )
            );
        }

        tblComplete.setItems(obList);

        for (int i = 0; i < obList.size(); i++) {

            CompleteTm completeTm = obList.get(i);

            obList.get(i).getBtnDelete().setOnAction(event -> {

                String description = completeTm.getDescription();

                boolean isDeleted = TaskModel.deleteTask(description);

                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Task Deleted!").show();
                    obList.remove(completeTm);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
                }
            });
        }


        tblComplete.setItems(obList);
    }

    private void setCellValueFactory() {
        colDescription.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("description"));
        colDueDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("dueDate"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }
}
