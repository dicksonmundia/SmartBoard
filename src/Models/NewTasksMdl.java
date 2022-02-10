package Models;

import DB.DBConnection;
import DataObjects.Column;
import DataObjects.ColumnIns;
import DataObjects.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewTasksMdl {
    private final Connection connection;
    private final ColumnIns columnIns = ColumnIns.getInstance();
    private final Column column;
    private PreparedStatement statement;

    public NewTasksMdl() {
        connection = DBConnection.connect();
        column = columnIns.getColumn();
    }

    //create a new task in a column
    public boolean createTask(Task task) {
        boolean created = false;
        //create a new task under specified column id. return true if created
        String query = "INSERT INTO tasks (column_id, task_name, description, due_date) VALUES (?,?,?,?)";

        if (!columnExist(task.getTask_name())) {
            try {
                statement = connection.prepareStatement(query);
                statement.setInt(1, column.getColumn_ID());
                statement.setString(2, task.getTask_name());
                statement.setString(3, task.getDescription());
                statement.setString(4, task.getDue_date());

                if(statement.executeUpdate() > 0) {
                    created = true;
                    columnIns.setColumn(null);
                }
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

        return  created;
    }

    //delete task in column
    public boolean deleteTask() {
        // delete specific task in database by the provided task id. return true if deleted

        return true;
    }

    //move task to another column
    public boolean moveTask() {
        //user should provide the two columns. where the task belong and where to move it to
        //delete the task in preColumn and creat it in post column in database

        return true;
    }

    private boolean columnExist (String task_name) {
        boolean exist = false;

        String query = "SELECT * FROM tasks WHERE task_name=? AND column_id=?";

        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, task_name);
            statement.setInt(2, column.getColumn_ID());

            resultSet = statement.executeQuery();

            if (resultSet.next()) exist = true;
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

        return exist;
    }
}
