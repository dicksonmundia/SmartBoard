package Models;

import DB.DBConnection;
import DataObjects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateUserMdl {
    private final Connection connection;
    private PreparedStatement statement;


    public CreateUserMdl () {
        connection = DBConnection.connect();
    }

    public Boolean isConnectedToDb(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    //create a new user whose username should be unique
    public String createUser(User user) {
        String result;
        if (isConnectedToDb()){
            //check in database if there is a user with same username if none, create the new user
            if (findUsername(user.getUsername()) == 1) {
                result = "username found.!";
            } else {
                // save the user
                if(addUser(user)) {
                    result = "user created.!";
                } else {
                    result = "user not created.! Try again";
                }
            }
        }else {
            result = "database connection closed";
        }
        return result;
    }

    private int findUsername(String username) {
        statement = null;
        int numUsername = 0;
        String query = "SELECT * FROM users WHERE username = ?";

        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numUsername += 1;
            }

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
        return  numUsername;
    }

    private Boolean addUser(User user) {
        boolean isAdded = false;
        statement = null;
        String query = "INSERT INTO users (username, firstname, lastname, image, password) VALUES (?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getImage());
            statement.setString(5, user.getPassword());

            if (statement.executeUpdate() > 0) isAdded = true;

        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
        return isAdded;
    }
}
