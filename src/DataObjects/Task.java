package DataObjects;

public class Task {
    private String task_name;
    private String description;
    private String due_date;
    private int task_ID;
    private int column_ID;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public int getTask_ID() {
        return task_ID;
    }

    public void setTask_ID(int task_ID) {
        this.task_ID = task_ID;
    }

    public int getColumn_ID() {
        return column_ID;
    }

    public void setColumn_ID(int column_ID) {
        this.column_ID = column_ID;
    }


}
