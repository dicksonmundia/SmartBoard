package Models;

import DB.DBConnection;
import DataObjects.User;
import DataObjects.UserInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMdl {
    private final Connection connection;
    private final UserInstance userInstance;

    public LoginMdl () {
        connection = DBConnection.connect();
        userInstance = UserInstance.getInstance();
    }

    public Boolean isConnectedToDb(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    //check if user exist in the database with the given username and password
    public boolean isValidUser (String username, String password) {
        boolean isValidUser = false;
        // check in the database if there is a user who exist with such username and password
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = "SELECT username, firstname, lastname, image, password FROM users WHERE username=? and password=?";

        if (isConnectedToDb()){
            try {
                statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);

                resultSet = statement.executeQuery();
                if  (resultSet.next()) {

                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setImage(resultSet.getString("image"));
                    user.setPassword(resultSet.getString("password"));

                    //start user session
                    startSession(user);

                    isValidUser = true;
                }



            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                try {
                    assert statement != null;
                    statement.close();
                    assert resultSet != null;
                    resultSet.close();
                } catch (SQLException s) {
                    s.printStackTrace();
                }
            }
        }
        return isValidUser;
    }

    private void startSession(User user) {
        userInstance.setUser(user);
    }


}
