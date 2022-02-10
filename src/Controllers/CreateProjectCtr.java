package Controllers;

import Models.CreateProjectMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CreateProjectCtr {
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField newProjectTxt;


    public void onOkAction() throws IOException {
        Alert alertDialogue;
        if (validInput()) {
            CreateProjectMdl mdl = new CreateProjectMdl();
            if (mdl.createProject(newProjectTxt.getText())){

                Stage stage = (Stage) okBtn.getScene().getWindow();
                stage.close();

                toWorkSpace();

                alertDialogue = new Alert(Alert.AlertType.INFORMATION);
                alertDialogue.setTitle("Create Project");
                alertDialogue.setContentText("project created successfully");


            } else {
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Create Project");
                alertDialogue.setContentText("project already exists");
            }
        }
        else {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Create Project");
            alertDialogue.setContentText("Invalid Project Name");
        }
        alertDialogue.show();

    }

    public void onCancelAction() throws IOException {
        toWorkSpace();
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }


    private boolean validInput () {
        return !newProjectTxt.getText().trim().equals("");
    }

    private void toWorkSpace () throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/workspace.fxml")));
        Stage workspace = new Stage();
        Scene scene = new Scene(root);
        workspace.setTitle("SmartBoard");
        workspace.setScene(scene);
        workspace.show();
    }
}
