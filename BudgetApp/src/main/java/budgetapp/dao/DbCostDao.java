
package budgetapp.dao;

import budgetapp.domain.Cost;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DbCostDao implements CostDao<Cost, String> {

    private String path;
    private String user;
    private String password;
    private String driver;
    
    public DbCostDao (String path, String user, String password, String driver) {
        this.path = path;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }
    
    @Override
    public Cost create(Cost object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, user, password);
        PreparedStatement query = connection.prepareStatement(
                "INSERT INTO Cost (userId, category, price, purchased) VALUES (?,?,?,?)");
        
        query.setString(1, object.getUser());
        query.setString(2, object.getCategory());
        query.setDouble(3, object.getPrice());
        query.setString(4, object.getPurchased().toString());
        
        query.executeUpdate();
        query.close();
        connection.close();
        
        return object;
    }

    @Override
    public void remove(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cost> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
