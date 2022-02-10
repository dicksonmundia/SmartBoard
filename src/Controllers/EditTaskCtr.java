package Controllers;

import DataObjects.Task;
import DataObjects.TaskIns;
import Models.EditTaskMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditTaskCtr implements Initializable {
    @FXML
    private TextField taskNameTxt;
    @FXML
    private TextArea descriptionTxt;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private DatePicker dateTxt;
    private final TaskIns taskIns = TaskIns.getInstance();
    private int task_id;

    public void deleteDate() {
        dateTxt.setValue(null);
    }

    public void onOkAction() {

        EditTaskMdl editTaskMdl = new EditTaskMdl();
        Task task = new Task();
        task.setTask_ID(task_id);
        try {
            task.setDue_date(dateTxt.getValue().toString());
        } catch (NullPointerException ignore) {

        }

        task.setTask_name(taskNameTxt.getText().trim());
        task.setDescription(descriptionTxt.getText().trim());

        if (editTaskMdl.editTask(task)) {
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();


            workSpace();
        }else {
            Alert alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Edit Task");
            alertDialogue.setContentText("Task not edited! Try again");
            alertDialogue.show();
        }
    }

    public void onCancelBtn() {
        workSpace();

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task task = taskIns.getTask();
        task_id = task.getTask_ID();
        taskNameTxt.setText(task.getTask_name());
        descriptionTxt.setText(task.getDescription());
        try {
            LocalDate localDate = LocalDate.parse(task.getDue_date());
            dateTxt.setValue(localDate);
        } catch (NullPointerException ignored) {
        }

    }

    private void workSpace () {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/workspace.fxml")));
            Stage workspace = new Stage();
            Scene scene = new Scene(root);
            workspace.setTitle("SmartBoard");
            workspace.setScene(scene);
            workspace.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
