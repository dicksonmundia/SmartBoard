package Models;

import DB.DBConnection;
import DataObjects.User;
import DataObjects.UserInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileEditMdl {
    private final UserInstance userInstance = UserInstance.getInstance();
    private final Connection connection;
    private final User currentUser;

    public ProfileEditMdl () {

        connection = DBConnection.connect();
        currentUser = userInstance.getUser();
    }

    public Boolean isConnectedToDb(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public String updateUser (String firstname, String lastname, String image) {
        String updated = "Error updating";
        if (!isConnectedToDb()) {
            updated = "connection closed";
        }else {

        PreparedStatement statement;

        String query = "UPDATE users SET firstname=?, lastname=?, image=? WHERE username=?";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,firstname);
            statement.setString(2, lastname);
            statement.setString(3, image);
            statement.setString(4, currentUser.getUsername());

            if (statement.executeUpdate() > 0) {
                currentUser.setFirstname(firstname);
                currentUser.setLastname(lastname);
                currentUser.setImage(image);
                userInstance.setUser(currentUser);
                updated = "profile successfully updated.";
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }}

        return updated;
    }

}
