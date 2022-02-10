package Controllers;

import DataObjects.User;
import DataObjects.UserInstance;
import Models.ProfileEditMdl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ProfileEditCtr implements Initializable {

    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastName;
    @FXML
    private ImageView userProfileImg;
    @FXML
    private Label lblUsername;
    @FXML
    private Button okBtn;
    @FXML
    private Button onCancelBtn;


    private String imageName;
    private Image profileImage;
    private SaveImage saveImage;


    public void onOkAction() throws IOException {
        //check for valid inputs
        Alert alertDialogue;
        if (invalidInputs()) {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Update profile");
            alertDialogue.setContentText("invalid input fields");

        }else {
           //save user image
            saveImage.setImageName(imageName);
            saveImage.setProfileImage(profileImage);
            if (saveImage.saveImage()) {
                //update user
                ProfileEditMdl mdl = new ProfileEditMdl();

                alertDialogue = new Alert(Alert.AlertType.INFORMATION);
                alertDialogue.setTitle("Update profile");
                alertDialogue.setContentText(mdl.updateUser(txtFirstname.getText(), txtLastName.getText(), imageName));
                toWorkSpace(okBtn);
            } else {
                alertDialogue = new Alert(Alert.AlertType.ERROR);
                alertDialogue.setTitle("Update profile");
                alertDialogue.setContentText("Error in saving image! Try again.");
            }
        }
        alertDialogue.show();
    }


    public void onCancelAction() throws IOException {
        toWorkSpace(onCancelBtn);
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserInstance userInstance = UserInstance.getInstance();
        User currentUser = userInstance.getUser();

        imageName = currentUser.getImage();

        try {
            profileImage = new Image("./Images/"+imageName);
        }catch (Exception ignored) {
            profileImage = new Image("./Images/user.png");
        }


        lblUsername.setText(currentUser.getUsername());
        saveImage = new SaveImage();
        userProfileImg.setImage(profileImage);

    }

    public void selectProfile() {
        saveImage.selectImage();
        userProfileImg.setImage(saveImage.getProfileImage());
        imageName = saveImage.getImageName();
        profileImage = saveImage.getProfileImage();
    }


    private boolean invalidInputs () {
        return txtFirstname.getText().trim().equals("") || txtLastName.getText().trim().equals("");
    }


}
