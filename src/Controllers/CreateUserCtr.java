package Controllers;

import DataObjects.User;
import Models.CreateUserMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateUserCtr implements Initializable {
    @FXML
    private ImageView userProfileImg;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnClose;


    private String imageName;
    private Image profileImage;

    private SaveImage saveImage;

    private CreateUserMdl createUserMdl;

    private Alert alertDialogue;


    public void actCreateUserBtn() {
        if (hasValidInput()) {
            //save profile image
            saveImage.setImageName(imageName);
            saveImage.setProfileImage(profileImage);
            if (saveImage.saveImage()) {
                //save user to db
                User user = new User();
                user.setPassword(txtPassword.getText());
                user.setUsername(txtUsername.getText());
                user.setFirstname(txtFirstName.getText());
                user.setLastname(txtLastName.getText());
                user.setImage(imageName);

                alertDialogue = new Alert(Alert.AlertType.INFORMATION);
                alertDialogue.setTitle("Saving Image");
                alertDialogue.setContentText(createUserMdl.createUser(user));


                txtPassword.setText("");
                txtUsername.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
            }else {
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Saving Image");
                alertDialogue.setContentText("Error occurred in saving profile image! Try Again");
            }
        } else {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Invalid Input");
            alertDialogue.setContentText("Please Fill in all fields!");
        }
        alertDialogue.show();
    }



    private boolean hasValidInput () {
        if (txtUsername.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            return false;
        }
        else return !txtUsername.getText().trim().equals("") && !txtFirstName.getText().trim().equals("") && !txtLastName.getText().trim().equals("") && !txtPassword.getText().trim().equals("");
    }

    public void actCloseBtn() {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/login.fxml")));
            Stage newUser = new Stage();
            Scene scene = new Scene(root);
            newUser.setTitle("Log in to SmartBoard");
            newUser.setScene(scene);
            newUser.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createUserMdl = new CreateUserMdl();
        saveImage = new SaveImage();
        imageName = "user.png";
        try {
            profileImage = new Image("./Images/"+imageName);
        } catch (IllegalArgumentException arg) {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Invalid Image");
            alertDialogue.setContentText("Invalid Image!");
            alertDialogue.show();
        }
    }

    public void actSelectProfile() {
        saveImage.selectImage();
        userProfileImg.setImage(saveImage.getProfileImage());
        imageName = saveImage.getImageName();
        profileImage = saveImage.getProfileImage();
    }
}
