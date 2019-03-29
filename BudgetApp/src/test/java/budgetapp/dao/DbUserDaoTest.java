
package budgetapp.dao;

import budgetapp.domain.User;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DbUserDaoTest {
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();
    
    String path;
    String user;
    String password;
    String driver;
    
    DbUserDao dao;
    
    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config-test.properties"));

        path = properties.getProperty("databasePath") + temp.getRoot().getAbsolutePath() + "/budget.db";
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        driver = properties.getProperty("driver");
        
        DatabaseDao database = new DatabaseDao(path, user, password, driver);
        database.createDatabase();
        
        dao = new DbUserDao(path, user, password, driver);
        dao.create(new User("Miika", "Salasana", Date.valueOf("2019-02-20").toLocalDate()));
    }
    
//    @Test
//    public void createUserWorks() throws SQLException {
//        
//    }
    
    @Test
    public void readUserWorks() throws SQLException {
        assertTrue(dao.read("Miika") != null);
    }
    
    @Test
    public void usernameSavedRight() throws SQLException {
        assertEquals("Miika", dao.read("Miika").getUsername());
    }
    
    
    
    
    @After
    public void tearDown() {
    }

}
