package Models;

import DB.DBConnection;
import DataObjects.User;
import DataObjects.UserInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteProjectMdl {
    private final Connection connection;
    private final User currentUser;

    public DeleteProjectMdl () {
        connection = DBConnection.connect();
        UserInstance userInstance = UserInstance.getInstance();
        currentUser = userInstance.getUser();
    }

    public boolean deleteProject (String project_name) {
        boolean deleted = false;
        PreparedStatement statement;

        String query = "DELETE FROM projects WHERE project_name=? AND username=?;";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, project_name);
            statement.setString(2, currentUser.getUsername());

            if (statement.executeUpdate() > 0) deleted = true;

        } catch (SQLException t) {
            t.printStackTrace();
        }
        return deleted;
    }
}
