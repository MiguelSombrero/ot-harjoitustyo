
package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Cost;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DbCostDaoTest {
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();
    
    String path;
    String user;
    String password;
    String driver;
    
    DbCostDao dao;
    
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
        
        dao = new DbCostDao(path, user, password, driver);
        
        dao.create(new Cost(1, Category.ALCOHOL, 12.90, Date.valueOf("2019-04-05").toLocalDate(), "Miika"));
        dao.create(new Cost(2, Category.EDUCATION, 54.90, Date.valueOf("2019-03-29").toLocalDate(), "Miika"));
        dao.create(new Cost(3, Category.SUPLEMENTS, 10.99, Date.valueOf("2019-02-25").toLocalDate(), "Miika"));
        dao.create(new Cost(4, Category.ALCOHOL, 1.90, Date.valueOf("2018-04-07").toLocalDate(), "Jukka"));
        dao.create(new Cost(5, Category.COSMETICS, 34.0, Date.valueOf("2019-02-05").toLocalDate(), "Jukka"));
    }
    
    @Test
    public void readCostWorks() throws SQLException {
        assertTrue(dao.listByUser("Miika") != null);
    }
    
    @Test
    public void savesMoreThanOneCost() throws SQLException {
        assertEquals(3, dao.listByUser("Miika").size());
    }
    
    @Test
    public void removeCostDeletesCost() throws SQLException {
        dao.remove(1);
        List<Cost> cost = dao.listByUser("Miika").stream()
                .filter(current -> current.getId() == 1)
                .collect(Collectors.toCollection(ArrayList::new));
        
        assertTrue(cost.isEmpty());
    }
    
    @Test
    public void removeCostRemovesOnlyOneCost() throws SQLException {
        dao.remove(1);
        List<Cost> cost = dao.listByUser("Miika");
        assertEquals(2, cost.size());
    }
    
    @Test
    public void removeByUserWorks() throws SQLException {
        // miten testaat, kun hakee aina uudelleen tietokannasta?
    }
      
    @After
    public void tearDown() {
        this.temp.delete();
    }

}
