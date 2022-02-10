package Models;

import DB.DBConnection;
import DataObjects.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RenameProjectMdl {
    private final Connection connection;

    public RenameProjectMdl() {
        connection = DBConnection.connect();
    }

    public boolean updateName(Project project) {
        boolean updated = false;
        PreparedStatement statement = null;

        String query = "UPDATE projects SET project_name=? WHERE project_id=?;";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, project.getProject_name());
            statement.setInt(2, project.getProject_id());

            if (statement.executeUpdate() > 0) updated = true;
        } catch (SQLException t) {
            t.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return updated;
    }
}
