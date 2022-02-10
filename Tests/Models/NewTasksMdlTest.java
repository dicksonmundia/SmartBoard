package Models;

import DataObjects.Column;
import DataObjects.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewTasksMdlTest {

    NewTasksMdl tasksMdl;
    Column column;
    Task task;

    @BeforeEach
    void setUp() throws Exception{
        column = new Column();
        column.setColumn_ID(1);
        column.setColumn_name("To-Do");
        column.setProject_ID(1);


        task = new Task();
        task.setColumn_ID(1);
        task.setTask_name("java syntax");
        task.setDue_date("23/10/2021");
        task.setDescription("learning");
        task.setColumn_ID(column.getColumn_ID());

        tasksMdl = new NewTasksMdl();

    }

    @Test
    void createTask() {
        Assertions.assertTrue(tasksMdl.createTask(task));
    }

    @Test
    void deleteTask() {
        Assertions.assertTrue(tasksMdl.deleteTask());
    }

    @Test
    void moveTask() {
        //lets move to column id 2
        Assertions.assertTrue(tasksMdl.moveTask());
    }
}