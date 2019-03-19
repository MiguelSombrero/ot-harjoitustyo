
package budgetapp.dao;

import java.sql.SQLException;
import java.util.List;

public interface UserDao<T, K> {
    
    T create (T object) throws SQLException;
    
    T read (K key) throws SQLException;
    
    T update (T object) throws SQLException;
    
    void remove (K key) throws SQLException;
    
    List<T> list ();
}
