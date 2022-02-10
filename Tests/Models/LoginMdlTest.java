package Models;

import DataObjects.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginMdlTest {
    LoginMdl loginMdl;
    User user;

    @BeforeEach
    void setUp() throws Exception {
        loginMdl = new LoginMdl();
        user = new User();

        user.setUsername("user1");
        user.setPassword("user1");
    }

    @Test
    void isValidUser() {
        Assertions.assertTrue(loginMdl.isValidUser(user.getUsername(), user.getPassword()));
    }
}