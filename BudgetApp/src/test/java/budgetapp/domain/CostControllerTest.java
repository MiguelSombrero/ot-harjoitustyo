
package budgetapp.domain;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CostControllerTest {
    
    private FakeCostDao costDao;
    private CostController costController;
    
    @Before
    public void setUp() {
        this.costDao = new FakeCostDao();
        this.costController = new CostController(costDao);
        
        costController.addCost(Category.CAFE, 26.7, Date.valueOf("2019-03-20").toLocalDate(), "Miika");
        
    }
    
    @Test
    public void addOneCostSamePerson() throws SQLException {
        assertEquals(1, costDao.list("Miika").size());
    }
    
    @Test
    public void addTwoCostsSamePerson() throws SQLException {
        costController.addCost(Category.CLOTHES, 56.7, Date.valueOf("2018-03-21").toLocalDate(), "Miika");
        assertEquals(2, costDao.list("Miika").size());
    }
    
    @Test
    public void addTwoCostsDifferentPerson() throws SQLException {
        assertEquals(1, costDao.list("Miika").size());
    }
    
    @Test
    public void addCostSavesCategoryRight() throws SQLException {
        assertEquals("Cafe", costDao.list("Miika").get(0).getCategory().toString());
    }
    
    @Test
    public void addCostSavesPriceRight() throws SQLException {
        assertEquals(26.7, costDao.list("Miika").get(0).getPrice(), 0.01);
    }
    
    @Test
    public void addCostSavesPurchasedRight() throws SQLException {
        assertEquals("2019-03-20", costDao.list("Miika").get(0).getPurchased().toString());
    }
    
    @Test
    public void addCostSavesUserRight() throws SQLException {
        assertEquals("Miika", costDao.list("Miika").get(0).getUser());
    }
    
    @Test
    public void emptyCacheWorks() throws SQLException {
        costController.emptyCostsCache("Miika");
        assertTrue(costDao.list("Miika") == null);
    }
    
    @Test
    public void getCostsFromDatabaseNotNull() throws SQLException {
        
    }
    
    @Test
    public void getCostsFromDatabaseNull() throws SQLException {
        
    }
    
    @Test
    public void sumsRightCostsByWeekday() throws SQLException {
        costController.addCost(Category.CAFE, 6.99, Date.valueOf("2019-04-01").toLocalDate(), "Jukka");
        costController.addCost(Category.CLOTHES, 56.89, Date.valueOf("2019-03-25").toLocalDate(), "Jukka");
        costController.addCost(Category.ALCOHOL, 12.99, Date.valueOf("2019-03-26").toLocalDate(), "Jukka");
        
        List<Cost> costs = costDao.list("Jukka");
        assertEquals(63.88, costController.sumWeekday(costs, new double[8])[1], 0.01);
    }
    
    @Test
    public void sumsRightCostsByMonth() throws SQLException {
        
    }
    
    @Test
    public void sumsRightCostsByCategory() throws SQLException {
        
    }
    
    @Test
    public void sumsRightCostsByMonthYearly() throws SQLException {
        
    }
    
    @Test
    public void sumsRightCostsByCategoryYearly() throws SQLException {
        
    }
    
}
