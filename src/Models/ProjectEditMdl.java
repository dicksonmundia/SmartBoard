package Models;

import DataObjects.Project;

public class ProjectEditMdl {
    public ProjectEditMdl() {}

    // create new project
    public boolean createProject(String username, Project project) {
        //create a project under the username username in database
        return true;
    }

    // make project default
    public boolean makeDefault (int project_id) {
        //update the project in database set it to a state of default
        return  true;
    }
}
