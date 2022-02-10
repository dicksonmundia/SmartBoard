package Controllers;

import DataObjects.ProjectIns;
import Models.NewColumnMdl;
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

public class NewColumnCtr {
    @FXML
    private TextField newColumnTxt;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;

    public ProjectIns projectIns = ProjectIns.getInstance();


    public void onOkAction() throws IOException {
        //add new column
        NewColumnMdl mdl = new NewColumnMdl();

        Alert alertDialogue;
        if (newColumnTxt.getText().trim().equals("")) {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Create column");
            alertDialogue.setContentText("invalid column name!");
            alertDialogue.show();
        } else {
            if (mdl.createColumn(newColumnTxt.getText())) {
                projectIns.setProject(null);
                toWorkSpace(okBtn);
            } else {
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Create column");
                alertDialogue.setContentText("column already exist!");
                alertDialogue.show();
            }
        }
    }

    public void onCancelAction() throws IOException {
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

}
