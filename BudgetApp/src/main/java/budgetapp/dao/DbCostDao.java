
package budgetapp.dao;

import budgetapp.domain.Cost;
import budgetapp.domain.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbCostDao implements CostDao<Cost, String> {

    private String path;
    private String dbUser;
    private String password;
    private String driver;
    private HashMap<String, List<Cost>> cache;
    
    public DbCostDao (String path, String dbUser, String password, String driver) {
        this.path = path;
        this.dbUser = dbUser;
        this.password = password;
        this.driver = driver;
        this.cache = new HashMap<>();
    }
    
    @Override
    public Cost create(Cost object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
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
    public List<Cost> list(String key) throws SQLException {
        if (!cache.containsKey(key)) {
            
            Connection connection = DriverManager.getConnection(path, dbUser, password);
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM Cost WHERE userId = ?");
        
            query.setString(1, key);
        
            ResultSet result = query.executeQuery();
            List<Cost> costs = new ArrayList<>();
        
            while (result.next()) {
                costs.add( new Cost(
                                result.getString("category"),
                                result.getDouble("price"),
                                Date.valueOf(result.getString("purchased")).toLocalDate(),
                                result.getString("userId")));
            }
        
            result.close();
            query.close();
            connection.close();
            
            cache.put(key, costs);
        }
        
        return cache.get(key);
    }
    
}
