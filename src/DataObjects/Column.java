package DataObjects;

public class Column {
    private String column_name;
    private int column_ID;
    private int project_ID;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public int getColumn_ID() {
        return column_ID;
    }

    public void setColumn_ID(int column_ID) {
        this.column_ID = column_ID;
    }

    public int getProject_ID() {
        return project_ID;
    }

    public void setProject_ID(int project_ID) {
        this.project_ID = project_ID;
    }
}
