
package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Cost;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbCostDao implements CostDao<Cost, Integer> {

    private String path;
    private String dbUser;
    private String password;
    private String driver;
    private HashMap<String, List<Cost>> cache;
    
    public DbCostDao(String path, String dbUser, String password, String driver) {
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
        query.setString(2, object.getCategory().toString());
        query.setDouble(3, object.getPrice());
        query.setString(4, object.getPurchased().toString());
        
        query.executeUpdate();
        query.close();
        connection.close();
        
        return object;
    }

    @Override
    public void remove (Integer id) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement("DELETE FROM Cost WHERE id = ?");
        query.setInt(1, id);
        
        query.executeUpdate();
        query.close();
        connection.close();
    }
    
    @Override
    public void removeByUser(String key) {
        // ON DELETE CASCADE will remove Costs from database when user is deleted
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
    }

    @Override
    public List<Cost> listByUser(String key) throws SQLException {
        if (!cache.containsKey(key)) {
            
            Connection connection = DriverManager.getConnection(path, dbUser, password);
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM Cost WHERE userId = ? ORDER BY purchased DESC");
        
            query.setString(1, key);
        
            ResultSet result = query.executeQuery();
            List<Cost> costs = new ArrayList<>();
        
            while (result.next()) {
                costs.add(new Cost(
                        result.getInt("id"),
                        Category.valueOf(result.getString("category").toUpperCase()),
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
