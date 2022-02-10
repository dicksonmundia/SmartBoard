package Controllers;

import DataObjects.Task;
import Models.NewTasksMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NewTaskCtr {
    @FXML
    private DatePicker dateTxt;
    @FXML
    private TextField taskNameTxt;
    @FXML
    private TextArea descriptionTxt;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;


    public void onOkAction() throws IOException {
        Alert alertDialogue;
        if (!invalidInput()) {
            Task task = new Task();
            task.setTask_name(taskNameTxt.getText());
            task.setDescription(descriptionTxt.getText());
            try {
                task.setDue_date(dateTxt.getValue().toString());
            } catch (NullPointerException ignore){

            }
            NewTasksMdl mdl = new NewTasksMdl();

            if (mdl.createTask(task)) {
                toWorkSpace(okBtn);
            } else {
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Create Task");
                alertDialogue.setContentText("Task Exists!");
                alertDialogue.show();
            }
        } else {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Create Task");
            alertDialogue.setContentText("invalid task name");
            alertDialogue.show();
        }

    }

    public void onCancelBtn() throws IOException {
        toWorkSpace(cancelBtn);
    }

    private void toWorkSpace(Button btn) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/workspace.fxml")));
        Stage workspace = new Stage();
        Scene scene = new Scene(root);
        workspace.setTitle("SmartBoard");
        workspace.setScene(scene);
        workspace.show();

        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    private boolean invalidInput () {
        return taskNameTxt.getText().trim().equals("") || descriptionTxt.getText().trim().equals("");
    }

    public void addDate() {
        dateTxt.setVisible(true);
    }
}
