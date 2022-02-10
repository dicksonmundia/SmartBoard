package Models;

import DataObjects.Column;

import java.util.ArrayList;
import java.util.List;

public class ColumnMdl {

    public ColumnMdl () {

    }

    // add a new project column
    public boolean createNewColumn (int project_id, Column column) {
        //add a column to the database with a foreign key if project id. return true if created
        return true;
    }

    //get all columns by user's project id in the database
    public List<Column> getAllColumns (int project_id) {
        List<Column> columnList = new ArrayList<>();
        //fetch all columns from the database by project id. return the columns in list form
        return  columnList;
    }

    // delete a column from the database
    public boolean deleteColumn(int column_id) {
        //delete a column by id from the database. if deleted return true
        return true;
    }

}
