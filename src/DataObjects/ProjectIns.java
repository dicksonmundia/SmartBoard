package DataObjects;

public final class ProjectIns {
    private Project project;

    private final static ProjectIns PROJECT_INSTANCE = new ProjectIns();

    private ProjectIns () {}

    public static ProjectIns getInstance(){return PROJECT_INSTANCE;}

    public void setProject(Project pr) {this.project = pr;}

    public Project getProject() {return project;}
}
