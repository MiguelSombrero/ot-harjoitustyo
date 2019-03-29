
package budgetapp.domain;

import budgetapp.dao.UserDao;
import java.sql.SQLException;

public class UserController {
    
    private UserDao userDao;
    private User user;
    
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void logoutUser() {
        this.user = null;
    }
    
    public Integer createUser(String username, String password) {
        try {
            if (userDao.read(username) == null) {
                userDao.create(new User(username, password));
                return 0;
            }
            return 1;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    public Integer loginUser(String username, String password) {
        try {
            User logginIn = (User) userDao.read(username);
            if (logginIn != null && logginIn.getPassword().equals(password)) {
                this.user = logginIn;
                return 0;
            }
            return 1;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    public Integer removeUser() {
        try {
            userDao.remove(user.getUsername());
            return 0;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    public Integer changePassword(String newPassword) {
        try {
            this.user.setPassword(newPassword);
            userDao.update(this.user);
            return 0;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    public boolean checkCredentials (String string) {
        return (string.length() > 4 && string.length() < 16);
    }
    
}
