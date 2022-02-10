package Models;

import DB.DBConnection;
import DataObjects.Project;
import DataObjects.ProjectIns;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewColumnMdl {
    private final Connection connection;
    private final Project project;
    private PreparedStatement statement;

    public NewColumnMdl () {
        ProjectIns projectIns = ProjectIns.getInstance();
        project = projectIns.getProject();
        connection = DBConnection.connect();
    }

    public boolean createColumn (String column_name) {
        boolean created = false;

        String query = "INSERT INTO columns (column_name, project_id) VALUES (?,?)";

        if (!columnExists(column_name)) {
            try {
                statement = connection.prepareStatement(query);
                statement.setString(1, column_name);
                statement.setInt(2, project.getProject_id());

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

    private boolean columnExists (String column_name) {
        boolean columnExists = false;

        String query = "SELECT * FROM columns WHERE column_name=? AND project_id=?";
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, column_name);
            statement.setInt(2, project.getProject_id());

            resultSet = statement.executeQuery();
            if ( resultSet.next() ) columnExists = true;
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

        return columnExists;
    }
}
