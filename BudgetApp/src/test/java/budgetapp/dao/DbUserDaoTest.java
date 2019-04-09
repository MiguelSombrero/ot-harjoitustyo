
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
    
    @Test
    public void readUserWorks() throws SQLException {
        assertTrue(dao.read("Miika") != null);
    }
    
    @Test
    public void usernameSavedRight() throws SQLException {
        assertEquals("Miika", dao.read("Miika").getUsername());
    }
    
    @Test
    public void passwordSavedRight() throws SQLException {
        assertEquals("Salasana", dao.read("Miika").getPassword());
    }
    
    @Test
    public void createdSavedRight() throws SQLException {
        assertEquals(Date.valueOf("2019-02-20").toLocalDate(), dao.read("Miika").getCreated());
    }
    
    @Test
    public void readReturnsNullIfNotFound() throws SQLException {
        assertTrue(dao.read("EiKukaan") == null);
    }
    
    @Test
    public void updateUserWorks() throws SQLException {
        User newUser = new User("Miika", "UusiSalasana", Date.valueOf("2019-02-20").toLocalDate());
        dao.update(newUser);
        assertEquals("UusiSalasana", dao.read("Miika").getPassword());
    }
    // tähän voisi olla hyvä myös testi, ettei UPDATE tee uutta käyttäjää
    
    @Test
    public void removeUserDeletesUser() throws SQLException {
        dao.remove("Miika");
        assertEquals(null, dao.read("Miika"));
    }
    // tähän voisi olla hyvä testi, katsoa että käyttäjän poisto poistaa myös kaikki datan
    
    @After
    public void tearDown() {
        this.temp.delete();
    }

}
