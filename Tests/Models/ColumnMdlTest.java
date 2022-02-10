package Models;

import DataObjects.Column;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ColumnMdlTest {
    Column column;
    ColumnMdl columnMdl;
    int project_id;


    @BeforeEach
    void setUp() throws Exception{
        columnMdl = new ColumnMdl();
        column = new Column();

        project_id = 1;


        column.setColumn_ID(1);
        column.setColumn_name("To-Do");
        column.setProject_ID(project_id);
    }

    @Test
    void createNewColumn() {
        Assertions.assertTrue(columnMdl.createNewColumn(project_id, column));
    }

    @Test
    void getAllColumns() {
        List<Column> columns = columnMdl.getAllColumns(project_id);
        Assertions.assertTrue(columns.size() == 0);
    }

    @Test
    void deleteColumn() {
        Assertions.assertTrue(columnMdl.deleteColumn(column.getColumn_ID()));
    }
}