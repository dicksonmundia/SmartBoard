package Models;

import DataObjects.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserMdlTest {
    CreateUserMdl createUserMdl;
    User user;
    @BeforeEach
    void setUp() {
        user = new User();
        createUserMdl = new CreateUserMdl();
        user.setUsername("user1");
        user.setPassword("user1");


    }

    @Test
    void createUser() {
        Assertions.assertTrue(createUserMdl.createUser(user).equals("user created.!"));
    }
}