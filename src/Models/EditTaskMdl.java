package Models;

import DB.DBConnection;
import DataObjects.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditTaskMdl {
    private final Connection connection;


    public EditTaskMdl () {
        connection = DBConnection.connect();
    }

    public boolean editTask (Task task) {
        boolean edit = false;


        String query = "UPDATE tasks SET task_name=?, description=?, due_date=? WHERE task_id=?;";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, task.getTask_name());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getDue_date());
            statement.setInt(4, task.getTask_ID());

            if (statement.executeUpdate() > 0) edit = true;
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
        return edit;
    }
}
