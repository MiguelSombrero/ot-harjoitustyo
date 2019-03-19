package budgetapp.domain;

import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserControllerTest {
    
    private FakeUserDao userDao;
    private UserController userController;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        userController = new UserController(userDao);
        userController.createUser("Miika", "Salasana");
        
    }
    
    @Test
    public void creatingUserSavesUsernameRight () throws SQLException {
        assertEquals("Miika", userDao.read("Miika").getUsername());
    }
    
    @Test
    public void creatingUserSavesPasswordRight () throws SQLException {
        assertEquals("Salasana", userDao.read("Miika").getPassword());
    }
    
    @Test
    public void creatingUserSavesDayCreatedRight () throws SQLException {
        assertEquals(LocalDate.now(), userDao.read("Miika").getCreated());
    }
    
    @Test
    public void creatingSameUserTwiceNotPossible () throws SQLException {
        userController.createUser("Miika", "ToinenSalasana"); 
        assertEquals("Salasana", userDao.read("Miika").getPassword());
    }
    
    @Test
    public void cannotLoginWithUserNotExists () {
        userController.loginUser("Mikko", "Mikko");
        assertEquals(null, userController.getUser());
    }
    
    @Test
    public void cannotLoginWithWrongPassword () {
        userController.loginUser("Miika", "Mikko");
        assertEquals(null, userController.getUser());
    }
    
    @Test
    public void canLoginWithRightPassword () {
        userController.loginUser("Miika", "Salasana");
        assertEquals("Miika", userController.getUser());
    }
    
    @Test
    public void canLogout () {
        userController.loginUser("Miika", "Salasana");
        userController.logoutUser();
        assertEquals(null, userController.getUser());
    }
    
    @Test
    public void canRemoveLoggedUser () throws SQLException {
        userController.loginUser("Miika", "Salasana");
        userController.removeUser();
        User user = userDao.read("Miika");
        assertEquals(null, user);
    }
    
    @Test
    public void cannotRemoveUserNotLoggedIn () throws SQLException {
        userController.removeUser();
        User user = userDao.read("Miika");
        assertEquals("Miika", user.getUsername());
    }
    
    @Test
    public void canChangePasswordLoggedUser () throws SQLException {
        userController.loginUser("Miika", "Salasana");
        userController.changePassword("ToinenSalasana");
        User user = userDao.read("Miika");
        assertEquals("ToinenSalasana", user.getPassword());
    }
    
    
}
