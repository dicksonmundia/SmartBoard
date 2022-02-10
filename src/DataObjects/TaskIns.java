package DataObjects;

public final class TaskIns {
    private Task task;

    private final static TaskIns TASK_INSTANCE = new TaskIns();

    private TaskIns() {}

    public static TaskIns getInstance() {return TASK_INSTANCE;}

    public void setTask (Task task) {this.task = task;}

    public Task getTask() {return task;}
}
