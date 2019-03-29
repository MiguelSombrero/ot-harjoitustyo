
package budgetapp.dao;

import budgetapp.domain.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DbUserDao implements UserDao<User, String> {

    private String path;
    private String dbUser;
    private String password;
    private String driver;
    
    public DbUserDao(String path, String dbUser, String password, String driver) {
        this.path = path;
        this.dbUser = dbUser;
        this.password = password;
        this.driver = driver;
    }
    
    @Override
    public User create(User object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "INSERT INTO User (username, password, created) VALUES (?,?,?)");
        
        query.setString(1, object.getUsername());
        query.setString(2, object.getPassword());
        query.setString(3, object.getCreated().toString());
        
        query.executeUpdate();
        query.close();
        connection.close();
        
        return object;
    }

    @Override
    public User read(String key) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "SELECT username, password, created FROM User WHERE username = ?");
        
        query.setString(1, key);
        ResultSet result = query.executeQuery();
        
        if (!result.next()) {
            return null;
        }
        
        User user = new User(
                result.getString("username"),
                result.getString("password"),
                Date.valueOf(result.getString("created")).toLocalDate());
        
        result.close();
        query.close();
        connection.close();
        
        return user;
    }

    @Override
    public User update(User object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "UPDATE User SET password = ? WHERE username = ?");
        
        query.setString(1, object.getPassword());
        query.setString(2, object.getUsername());
        query.executeUpdate();
        
        query.close();
        connection.close();
        
        return object;
    }

    @Override
    public void remove(String key) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        connection.prepareStatement("PRAGMA foreign_keys = ON;").execute();
            
        PreparedStatement query = connection.prepareStatement("DELETE FROM User WHERE username = ?");
        query.setString(1, key);
        query.executeUpdate();
        
        query.close();
        connection.close();
    }

    @Override
    public List<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
