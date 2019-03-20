
package budgetapp.dao;

import java.sql.SQLException;
import java.util.List;

public interface CostDao<T, K> {
    
    T create (T object) throws SQLException;
    
    void remove (K key) throws SQLException;
    
    List<T> list (K key) throws SQLException;
}
