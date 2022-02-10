package Models;

import DB.DBConnection;
import DataObjects.User;
import DataObjects.UserInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateProjectMdl {
    private final Connection connection;
    private final User currentUser;
    private PreparedStatement statement;

    public CreateProjectMdl () {
        connection = DBConnection.connect();
        UserInstance userInstance = UserInstance.getInstance();
        currentUser = userInstance.getUser();
    }

    public boolean createProject (String project_name) {
        boolean created = false;
        if (!checkProject(project_name)){
            String query = "INSERT INTO projects (project_name, username, state) VALUES (?,?,?)";
            try {
                statement = connection.prepareStatement(query);
                statement.setString(1, project_name);
                statement.setString(2, currentUser.getUsername());
                statement.setString(3, "null");

                if (statement.executeUpdate() > 0) created = true;
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                try {
                    statement.close();
                } catch (SQLException t) {
                    t.printStackTrace();
                }
            }
        }

        return created;
    }

    public boolean checkProject (String project_name) {
        boolean found = false;
        ResultSet resultSet = null;

        String query = "SELECT * FROM projects WHERE project_name=? AND username=?";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, project_name);
            statement.setString(2, currentUser.getUsername());

            resultSet = statement.executeQuery();

            if (resultSet.next()) found = true;

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

        return found;
    }

}
