package Controllers;

import DataObjects.Project;
import DataObjects.ProjectIns;
import Models.DeleteProjectMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeleteProject implements Initializable {
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label titleLbl;

    private final ProjectIns projectIns = ProjectIns.getInstance();
    private Project project;

    public void onOkAction() {
        DeleteProjectMdl mdl = new DeleteProjectMdl();
        if (mdl.deleteProject(project.getProject_name())) {
            workSpace();
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void onCancelAction() {
        workSpace();
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        project = projectIns.getProject();
        titleLbl.setText("Confirm you want to delete "+project.getProject_name()+" ?");
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
