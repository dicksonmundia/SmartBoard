package Controllers;

import Models.LoginMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginCtr implements Initializable {
    @FXML
    private Label createLbl;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnClose;


    private LoginMdl loginMdl;

    public void actLoginBtn() throws IOException {
        Alert alertDialogue;
        if (isValidInput()) {
            if(loginMdl.isValidUser(txtUsername.getText(), txtPassword.getText())){
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/workspace.fxml")));
                Stage workspace = new Stage();
                Scene scene = new Scene(root);
                workspace.setTitle("SmartBoard");
                workspace.setScene(scene);
                workspace.show();

                Stage stage = (Stage) btnSignIn.getScene().getWindow();
                stage.close();
            }else {
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Invalid User");
                alertDialogue.setContentText("No user with such credentials!");
                alertDialogue.show();
            }
        } else {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Invalid Input");
            alertDialogue.setContentText("Please Fill in all fields!");
            alertDialogue.show();
        }
    }

    public void actCloseBtn() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void actCreateLbl() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/createUser.fxml")));
        Stage newUser = new Stage();
        Scene scene = new Scene(root);
        newUser.setTitle("Create a new user");
        newUser.setScene(scene);
        newUser.show();

        Stage stage = (Stage) createLbl.getScene().getWindow();
        stage.close();
    }

    private boolean isValidInput() {
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            return false;
        }
        else return !txtUsername.getText().trim().equals("") && !txtPassword.getText().trim().equals("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginMdl = new LoginMdl();
    }

}
