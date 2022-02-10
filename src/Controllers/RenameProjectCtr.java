package Controllers;

import DataObjects.Project;
import DataObjects.ProjectIns;
import Models.CreateProjectMdl;
import Models.RenameProjectMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RenameProjectCtr implements Initializable {
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField newProjectTxt;


    private int project_id;
    private final ProjectIns projectIns = ProjectIns.getInstance();

    public void onOkAction() {
        Alert alertDialogue;
        if (newProjectTxt.getText().trim().equals("")) {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Rename Project");
            alertDialogue.setContentText("Empty project name!");
            alertDialogue.show();
        } else {
            Project project = new Project();
            project.setProject_name(newProjectTxt.getText());
            project.setProject_id(project_id);

            CreateProjectMdl mdl1 = new CreateProjectMdl();
            if (mdl1.checkProject(newProjectTxt.getText())){
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Rename Project");
                alertDialogue.setContentText("project name already exist!");
                alertDialogue.show();
            } else {
                RenameProjectMdl mdl = new RenameProjectMdl();
                if (mdl.updateName(project)) {
                    workspace();
                    Stage stage = (Stage) okBtn.getScene().getWindow();
                    stage.close();
                } else {
                    alertDialogue = new Alert(Alert.AlertType.ERROR);
                    alertDialogue.setTitle("Rename Project");
                    alertDialogue.setContentText("error in updating!");
                    alertDialogue.show();
                }
            }


        }
    }



    public void onCancelAction() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        workspace();
    }

    private void workspace() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Project project = projectIns.getProject();
        project_id = project.getProject_id();

        newProjectTxt.setText(project.getProject_name());
    }
}
