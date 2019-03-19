
package budgetapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseDao {
    
    private String path;
    private String user;
    private String password;
    private String driver;
    
    public DatabaseDao (String path, String user, String password, String driver) {
        this.path = path;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }
    
    public void createDatabase () {
        try {
            Connection connection = DriverManager.getConnection(path, user, password);
            connection.createStatement();
            
            String createUser = "CREATE TABLE IF NOT EXISTS User (" +
            "    username VARCHAR(20) PRIMARY KEY," +
            "    password VARCHAR(20)," +
            "    created TEXT);";
            
            String createEvent = "CREATE TABLE IF NOT EXISTS Cost (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    userId VARCHAR(20)," +
            "    category VARCHAR(20)," +
            "    price NUMERIC(9,2)," +
            "    purchased TEXT," +
            "    FOREIGN KEY (userId) REFERENCES User(username) ON DELETE CASCADE);";
            
            connection.prepareStatement(createUser).execute();
            connection.prepareStatement(createEvent).execute();
            
        }
        catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
