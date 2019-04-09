
package budgetapp.domain;

import budgetapp.dao.UserDao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class FakeUserDao implements UserDao<User, String> {

    HashMap<String, User> users = new HashMap<>();
    
    @Override
    public User create(User object) throws SQLException {
        users.put(object.getUsername(), object);
        return object;
    }

    @Override
    public User read(String key) throws SQLException {
        if (users.containsKey(key)) return users.get(key);
        return null;
    }

    @Override
    public User update(User object) throws SQLException {
        users.put(object.getUsername(), object);
        return object;
    }

    @Override
    public void remove(String key) throws SQLException {
        users.remove(key);
    }

}
