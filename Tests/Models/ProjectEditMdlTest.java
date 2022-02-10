package Models;

import DataObjects.Project;
import DataObjects.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectEditMdlTest {
    ProjectEditMdl projectMdl;
    User user;
    Project project;

    @BeforeEach
    void setUp() throws Exception {
        projectMdl = new ProjectEditMdl();
        user = new User();
        project = new Project();

        user.setUsername("user");
        project.setUsername(user.getUsername());
        project.setProject_id(1);
    }

    @Test
    void createProject() {
        Assertions.assertTrue(projectMdl.createProject(user.getUsername(), project));
    }

    @Test
    void makeDefault() {
        Assertions.assertTrue(projectMdl.makeDefault(project.getProject_id()));
    }
}