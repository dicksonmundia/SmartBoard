package Controllers;

import DataObjects.*;
import Models.ProjectWorkspaceMdl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectWorkspaceCtr implements Initializable {
    @FXML
    private MenuItem setDefaultItem;
    @FXML
    private MenuItem unsetDefaultItem;
    @FXML
    private Label lblUsername;
    @FXML
    private Button profileBtn;
    @FXML
    private Label quoteLbl;
    @FXML
    private MenuBar myMenuBar;
    @FXML
    private Button logoutBtn;
    @FXML
    private TabPane tabPane;
    private Alert alertDialogue;

    private final UserInstance userInstance = UserInstance.getInstance();
    private List<Project> projects;

    private final ColumnIns columnIns = ColumnIns.getInstance();
    private final DefaultProjectIns defaultProjectIns = DefaultProjectIns.getInstance();
    private int project_index = -1;

    public ProjectIns projectIns = ProjectIns.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = userInstance.getUser();
        if (currentUser == null) {
            alertDialogue = new Alert(Alert.AlertType.CONFIRMATION);
            alertDialogue.setTitle("Invalid User");
            alertDialogue.setContentText("Error: user not initialized.");
            System.exit(1);
        }else {
            //display quote
            Quote quote = new Quote();
            quoteLbl.setWrapText(true);
            quoteLbl.setText(quote.getQuote());
            //display user projects
            displayProjects();
            lblUsername.setText(currentUser.getUsername());
            selectDefaultTab();
        }


    }

    private void selectDefaultTab() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        Default defaultProject = defaultProjectIns.getProjectIns();
        if (defaultProject != null) {
            selectionModel.select(defaultProject.getProject_index());
        } else {
            if (project_index != -1) selectionModel.select(project_index);
        }
    }


    private void displayProjects () {
        //get projects from database
        ProjectWorkspaceMdl mdl = new ProjectWorkspaceMdl();

        projects = mdl.getProjects();
        try {
            if (projects.size() != 0) {
                //display projects
                int index = -1;
                for (Project p : projects) {
                    index += 1;
                    projectTab(p.getProject_name(),p.getProject_id(),p.getState(),index);
                }
            }
        }catch (NullPointerException ignored) {

        }
    }

    private void projectTab(String tabName, int projectId, String state, int index) {

        //create new tab
        Tab tb1;
        if(state.equals("default")) {
            tb1 = new Tab(tabName+"(*)");
            project_index = index;
            setDefaultItem.setDisable(true);
            unsetDefaultItem.setDisable(false);
        }else {
            tb1 = new Tab(tabName);
        }
        tabPane.getTabs().add(tb1);


        //create grid view
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 10, 20, 10));

        //create scroll view
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(719,341);

        //set grid under scroll view
        scrollPane.setContent(grid);

        //set scroll view under tab
        tb1.setContent(scrollPane);




        columnView(grid,projectId);
    }

    private void columnView(GridPane gridPane, int projectID) {
        //get columns from database
        ProjectWorkspaceMdl mdl = new ProjectWorkspaceMdl();
        List<Column> columns = mdl.getColumns(projectID);

        int i = 0;

        try {
            if (columns.size() != 0) {
                //add columns
                for (Column cl : columns) {
                    gridPane.add(titleLayout(cl.getColumn_name(),cl.getColumn_ID()), i, 0);
                    taskView(gridPane,i,cl.getColumn_ID());
                    i+=1;
                }
            }
        }catch (NullPointerException ignored) {

        }

    }

    private void taskView(GridPane gridPane, int columnIndex, int columnID){

        ProjectWorkspaceMdl mdl = new ProjectWorkspaceMdl();
        List<Task> tasks = mdl.getTasks(columnID);

        int i = 1;

        try {
            if (tasks.size() != 0) {
                for (Task ts : tasks) {
                    gridPane.add(columnTaskView(ts), columnIndex, i);
                    i+=1;
                }
            }

        } catch (NullPointerException ignored) {

        }


    }

    public void createProject() throws IOException {
        setCurrentTabProject(tabPane.getTabs().size());
        newScene(null, null, "create_new_project.fxml", myMenuBar, "Create New Project");
    }

    private Project getProject(String projectName) {
      Project project = null;
      try {
          for (Project pr : projects) {
              if (pr.getProject_name().equals(projectName)){
                  project = pr;
                  break;
              }
          }
      } catch (NullPointerException ignored) {}
      return project;
    }

    public AnchorPane titleLayout(String title, int columnId) {
        AnchorPane titleAnchor = new AnchorPane();
        titleAnchor.setPrefSize(306.0,32.0);
        titleAnchor.setStyle("-fx-background-color: white");

        //create label
        Label titleLabel = new Label(title);
        titleLabel.setLayoutX(7.0);
        titleLabel.setLayoutY(7.0);


        //create menu button
        MenuButton menuButton = new MenuButton("Add");
        menuButton.setLayoutX(250.0);
        menuButton.setLayoutY(3.0);
        EventHandler<? super MouseEvent> click = (EventHandler<MouseEvent>) event -> {
            try {
                addTask(columnId,menuButton);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        };

        menuButton.setOnMouseClicked(click);

        titleAnchor.getChildren().addAll(titleLabel,menuButton);

        return titleAnchor;
    }

    public void addTask(int columnID, MenuButton menuButton) throws IOException {

        Column cl = new Column();
        cl.setColumn_name("nn");
        cl.setProject_ID(columnID);
        cl.setColumn_ID(columnID);


        columnIns.setColumn(cl);
        setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
        newScene(menuButton, null, "create_new_task.fxml", null, "Add New Task");

    }

    private AnchorPane columnTaskView(Task ts) {
        AnchorPane taskAnchor = new AnchorPane();
        taskAnchor.setPrefSize(306.0,124.0);
        taskAnchor.setStyle("-fx-background-color: white");

        //create title label
        Label titleLabel = new Label(ts.getTask_name());
        titleLabel.setLayoutX(21.0);
        titleLabel.setLayoutY(6.0);
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-text-fill: #823bda;");

        //create edit label
        Label editLabel = new Label("Edit");
        editLabel.setLayoutX(221.0);
        editLabel.setLayoutY(6.0);
        editLabel.setUnderline(true);
        editLabel.setStyle("-fx-text-fill: #823bda;");
        EventHandler<? super MouseEvent> edit = (EventHandler<MouseEvent>) event -> editTask(ts,editLabel);
        editLabel.setOnMouseClicked(edit);

        //create delete label
        Label deleteLabel = new Label("Delete");
        deleteLabel.setLayoutX(262.0);
        deleteLabel.setLayoutY(6.0);
        deleteLabel.setUnderline(true);
        deleteLabel.setStyle("-fx-text-fill: #823bda;");
        EventHandler<? super MouseEvent> delete = (EventHandler<MouseEvent>) event -> deleteTask(ts.getTask_ID(),ts.getTask_name(),deleteLabel);
        deleteLabel.setOnMouseClicked(delete);

        //create description label
        Label descLabel = new Label(ts.getDescription());
        descLabel.setLayoutX(21.0);
        descLabel.setLayoutY(39.0);
        descLabel.setWrapText(true);

        Image image = new Image("/Images/date.png",27,27,false,true);

        //Creating the image view
        ImageView imageView = new ImageView();
        imageView.setX(20);
        imageView.setY(86);
        imageView.setImage(image);
        imageView.setStyle("-fx-background-color: white");

        //create date label
        Label dateLabel = new Label(ts.getDue_date());
        dateLabel.setLayoutX(68);
        dateLabel.setLayoutY(89);

        taskAnchor.getChildren().addAll(titleLabel,editLabel,deleteLabel,descLabel,imageView,dateLabel);

        return taskAnchor;

    }

    private void deleteTask(int task_id, String task_name, Label deleteLabel) {
        setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
        alertDialogue = new Alert(Alert.AlertType.CONFIRMATION);
        alertDialogue.setTitle("Delete Task");
        alertDialogue.setContentText("Do you really want delete "+task_name+"?");

        Optional<ButtonType> result = alertDialogue.showAndWait();

        if (result.get() == ButtonType.OK) {
            ProjectWorkspaceMdl mdl = new ProjectWorkspaceMdl();
            if (mdl.deleteTask(task_id)) {
                tabPane.getTabs().clear();
                displayProjects();
                selectDefaultTab();
            }else {
                alertDialogue.setContentText("not deleted");
            }
        }


    }

    private void editTask(Task task, Label editLabel) {
        TaskIns taskIns = TaskIns.getInstance();
        taskIns.setTask(task);

        setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/edit_task.fxml")));
            Stage workspace = new Stage();
            Scene scene = new Scene(root);
            workspace.setTitle("Edit Task");
            workspace.setScene(scene);
            workspace.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Stage stage = (Stage) editLabel.getScene().getWindow();
        stage.close();

    }

    public void onLogoutAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/login.fxml")));
        Stage workspace = new Stage();
        Scene scene = new Scene(root);
        workspace.setTitle("Log in to SmartBoard");
        workspace.setScene(scene);
        workspace.show();

        userInstance.setUser(null);
        defaultProjectIns.setProjectIns(null);

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();


    }

    private void newScene (MenuButton btn, Button bt, String fxml, MenuBar mb, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/" + fxml)));
        Stage workspace = new Stage();
        Scene scene = new Scene(root);
        workspace.setTitle(title);
        workspace.setScene(scene);
        workspace.show();

        Stage stage;

        if (btn != null) {
            stage = (Stage) btn.getScene().getWindow();
        }else if (mb != null) {
            stage = (Stage) myMenuBar.getScene().getWindow();
        } else {
            stage = (Stage) bt.getScene().getWindow();
        }

        stage.close();
    }

    public void addColumnAction() throws IOException {
       String projectName = "null";
        try {
            projectName = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getText();
        } catch (ArrayIndexOutOfBoundsException ignored){
        }

        if (!projectName.equals("null")) {
            if (getProject(projectName) != null){
                projectIns.setProject(getProject(projectName));
                setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
                newScene(null, null, "create_new_column.fxml", myMenuBar, "Create new Column");
            }

        }
    }

    public void onProfileAction() throws IOException {
        setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
        newScene(null, profileBtn, "update_profile.fxml", null, "Edit Profile");
    }

    public void homePage() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/workspace.fxml")));
            Stage workspace = new Stage();
            Scene scene = new Scene(root);
            workspace.setTitle("SmartBoard");
            workspace.setScene(scene);
            workspace.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void renameProjectAction() {
        String projectName = "null";
        try {
            projectName = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getText();
        } catch (ArrayIndexOutOfBoundsException ignored){
        }

        if (!projectName.equals("null")) {
            if (getProject(projectName) != null){
                projectIns.setProject(getProject(projectName));
                try {
                    setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
                    newScene(null, null, "rename_project.fxml", myMenuBar, "Rename Project Name");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        }
    }

    public void deleteProject() {
        boolean isDefault = false;
        String projectName = "null";
        try {
            projectName = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getText();
            if (projectName.endsWith("(*)")) isDefault = true;
        } catch (ArrayIndexOutOfBoundsException ignored){
        }

        if (!projectName.equals("null")) {
            if (getProject(projectName) != null){
                if(isDefault) {
                    projectName = projectName.substring(0, projectName.length()-3);
                }
                projectIns.setProject(getProject(projectName));
                try {
                    setCurrentTabProject(0);
                    newScene(null, null, "delete_project.fxml", myMenuBar, "Confirm you want to delete "+projectName+"?");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        }
    }

    public void setDefault() {
        String projectName = "null";
        try {
            projectName = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getText();
        } catch (ArrayIndexOutOfBoundsException ignored){
        }

        ProjectWorkspaceMdl mdl = new ProjectWorkspaceMdl();
        if (mdl.setDefaultProject(projectName, "default")) {
            setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
            tabPane.getTabs().clear();
            displayProjects();
            selectDefaultTab();
        }

    }

    public void unsetDefault() {
        boolean unset = false;
        String projectName = "null";
        try {
            projectName = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getText();
            if (projectName.endsWith("(*)")) unset = true;
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ignored){
        }

        if (unset) {
            ProjectWorkspaceMdl mdl = new ProjectWorkspaceMdl();
            if (mdl.setDefaultProject(projectName.substring(0, projectName.length()-3), "null")) {
                setCurrentTabProject(tabPane.getSelectionModel().getSelectedIndex());
                tabPane.getTabs().clear();
                displayProjects();
                selectDefaultTab();
            }

        } else {
            alertDialogue = new Alert(Alert.AlertType.ERROR);
            alertDialogue.setTitle("Unset Default Project");
            alertDialogue.setContentText("the current project is not default. select the default project to unset it");
            alertDialogue.show();
        }
    }

    private void setCurrentTabProject(int idx) {
        String projectName = "null";
        try {
            projectName = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getText();
        } catch (ArrayIndexOutOfBoundsException ignored){
        }
        Default df = new Default();
        df.setProject_name(projectName);
        df.setProject_index(idx);

        defaultProjectIns.setProjectIns(df);
    }
}
