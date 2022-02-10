package Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImage {
    private String imageName;
    private Image profileImage;
    private final FileChooser fileChooser;

    public SaveImage () {
        fileChooser = new FileChooser();
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public boolean saveImage () {
        boolean saved = false;
        //get current file location
        File currentFileLoc = new File("src/Images/"+imageName);
        String path = currentFileLoc.getAbsolutePath();

        File saveFileTo = new File(path);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(profileImage,null);
        try {
            if (ImageIO.write(bufferedImage,"png", saveFileTo))  saved = true;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return saved;
    }

    public void selectImage() {
        Stage stage = new Stage();
        fileChooser.setTitle("Choose profile picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("images", "*.png"),
                new FileChooser.ExtensionFilter("images", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        try {
            this.profileImage =  new Image(selectedFile.toURI().toString());
            this.imageName = selectedFile.getName();
        } catch ( NullPointerException e ) {
            System.out.println("Error: Image not Selected");
            this.profileImage = new Image("./Images/user.png");
            this.imageName = "user.png";
        }

    }
}
