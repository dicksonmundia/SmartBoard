package Models;

import DB.DBConnection;
import DataObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectWorkspaceMdl {

    private final Connection connection;
    private final User currentUser;
    private PreparedStatement statement;

    public ProjectWorkspaceMdl () {
        connection = DBConnection.connect();
        UserInstance userInstance = UserInstance.getInstance();
        currentUser = userInstance.getUser();
    }

    //get all user projects from db
    public List<Project> getProjects () {
        List<Project> projects = new ArrayList<>();
        // get all project form database by users username
        String query = "SELECT * FROM projects WHERE username=?";
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, currentUser.getUsername());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setProject_id(resultSet.getInt("project_id"));
                project.setProject_name(resultSet.getString("project_name"));
                project.setUsername(resultSet.getString("username"));
                project.setState(resultSet.getString("state"));

                projects.add(project);

            }

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                statement.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
        return projects;
    }

    //get all project columns
    public List<Column> getColumns (int project_id) {
        List<Column> columns = new ArrayList<>();
        // get all columns form database by project id
        String query = "SELECT * FROM columns WHERE project_id=?";
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, project_id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Column column = new Column();
                column.setColumn_ID(resultSet.getInt("column_id"));
                column.setProject_ID(resultSet.getInt("project_id"));
                column.setColumn_name(resultSet.getString("column_name"));

                columns.add(column);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                statement.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException t) {
                t.printStackTrace();
            }

        }
        return columns;
    }

    //get all column tasks
    public List<Task> getTasks (int column_id) {
        List<Task> tasks = new ArrayList<>();
        // get all tasks form database by column id
        String query = "SELECT * FROM tasks WHERE column_id=?";
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, column_id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTask_ID(resultSet.getInt("task_id"));
                task.setColumn_ID(resultSet.getInt("column_id"));
                task.setDescription(resultSet.getString("description"));
                task.setDue_date(resultSet.getString("due_date"));
                task.setTask_name(resultSet.getString("task_name"));

                tasks.add(task);
            }

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return tasks;
    }

    public boolean deleteTask (int task_id) {
        boolean deleted = false;
        String query = "DELETE FROM tasks WHERE task_id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, task_id);

            if (statement.executeUpdate() > 0) deleted = true;
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }

        return deleted;
    }

    public boolean setDefaultProject (String projectName, String state) {
        boolean set = false;
        String query = "UPDATE projects SET state=? WHERE project_name=? AND username=?;";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, state);
            statement.setString(2, projectName);
            statement.setString(3, currentUser.getUsername());

            if (statement.executeUpdate() > 0) set = true;
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException es) {
                es.printStackTrace();
            }
        }
        return set;
    }

}
