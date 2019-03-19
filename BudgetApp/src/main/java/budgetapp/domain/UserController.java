
package budgetapp.domain;

import budgetapp.dao.DbUserDao;
import budgetapp.dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserController {
    
    private UserDao userDao;
    private String user;
    
    public UserController (UserDao userDao) {
        this.userDao = userDao;
    }
    
    public Integer createUser (String username, String password) {
        try {
            if (userDao.read(username) == null) {
                userDao.create(new User(username, password, LocalDate.now()));
                return 0;
            }
            return 1;
        }
        catch (SQLException e) { return 2; }
    }
    
    public Integer loginUser (String username, String password) {
        try {
            User logginIn = (User) userDao.read(username);
            if (logginIn != null && logginIn.getPassword().equals(password)) {
                this.user = username;
                return 0;
            }
            return 1;
        }
        catch (SQLException e) { return 2; }
    }
    
    public Integer logoutUser () {
        this.user = null;
        return 0;
    }
    
    public Integer removeUser () {
        try {
            userDao.remove(user);
            return 0;
        }
        catch (SQLException e) { return 2; }
    }
    
    public Integer changePassword (String newPassword) {
        try {
            User updateableUser = (User) userDao.read(user);
            updateableUser.setPassword(newPassword);
            userDao.update(updateableUser);
            return 0;
        }
        catch (SQLException e) { return 2; }
    }
    
    public String getUser () {
        return this.user;
    }
    
    public void setUser (String user) {
        this.user = user;
    }
}
