package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:smartBoard.db");
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
