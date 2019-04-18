
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
    
    private List<Cost> costs;
    private List<Cost> costs2;
    
    @Before
    public void setUp() throws SQLException {
        this.costDao = new FakeCostDao();
        this.costController = new CostController(costDao);
        
        costController.addCost(Category.CAFE, 26.7, Date.valueOf("2019-03-20").toLocalDate(), "Miika");
        
        costController.addCost(Category.CAFE, 6.99, Date.valueOf("2019-04-01").toLocalDate(), "Jukka");
        costController.addCost(Category.CAFE, 12.80, Date.valueOf("2019-04-14").toLocalDate(), "Jukka");
        costController.addCost(Category.CAFE, 5.90, Date.valueOf("2019-03-14").toLocalDate(), "Jukka");
        costController.addCost(Category.CLOTHES, 56.89, Date.valueOf("2019-03-25").toLocalDate(), "Jukka");
        costController.addCost(Category.ALCOHOL, 12.99, Date.valueOf("2018-04-10").toLocalDate(), "Jukka");
        costController.addCost(Category.ALCOHOL, 34.0, Date.valueOf("2018-04-13").toLocalDate(), "Jukka");
        
        costController.addCost(Category.CLOTHES, 12.90, Date.valueOf("2019-05-14").toLocalDate(), "Jarno");
        costController.addCost(Category.CLOTHES, 116.89, Date.valueOf("2019-05-07").toLocalDate(), "Jarno");
        costController.addCost(Category.ALCOHOL, 2.90, Date.valueOf("2018-12-25").toLocalDate(), "Jarno");
        costController.addCost(Category.ALCOHOL, 26.00, Date.valueOf("2018-12-04").toLocalDate(), "Jarno");
        
        costs = costDao.listByUser("Jukka");
        costs2 = costDao.listByUser("Jarno");
    }
    
    @Test
    public void addOneCostSamePerson() throws SQLException {
        assertEquals(1, costDao.listByUser("Miika").size());
    }
    
    @Test
    public void addMoreThanOneCostsSamePerson() throws SQLException {
        assertEquals(6, costDao.listByUser("Jukka").size());
    }
    
    @Test
    public void addCostSavesCategoryRight() throws SQLException {
        assertEquals("Cafe", costDao.listByUser("Miika").get(0).getCategory().toString());
    }
    
    @Test
    public void addCostSavesPriceRight() throws SQLException {
        assertEquals(26.7, costDao.listByUser("Miika").get(0).getPrice(), 0.01);
    }
    
    @Test
    public void addCostSavesPurchasedRight() throws SQLException {
        assertEquals("2019-03-20", costDao.listByUser("Miika").get(0).getPurchased().toString());
    }
    
    @Test
    public void addCostSavesUserRight() throws SQLException {
        assertEquals("Miika", costDao.listByUser("Miika").get(0).getUser());
    }
    
    @Test
    public void emptyCacheWorks() throws SQLException {
        costController.emptyCostsCache("Miika");
        assertTrue(costDao.listByUser("Miika") == null);
    }
    
    @Test
    public void getCostsFromDatabaseNotNull() throws SQLException {
        assertEquals(1, costController.getCosts("Miika").size());
    }
    
    public void getCostsFromDatabaseNull() throws SQLException {
        assertTrue(costController.getCosts("Tyhja").isEmpty());
    }
    
    @Test
    public void sumsRightCostsByWeekday() throws SQLException {
        assertEquals(63.88, costController.sumWeekday(costs, new double[8])[1], 0.01);
    }
    
    @Test
    public void sumsRightCostsByMonth() throws SQLException {
        assertEquals(66.78, costController.sumMonth(costs, new double[13])[4], 0.01);
    }
    
    @Test
    public void sumsRightCostsByCategory() throws SQLException {
        assertEquals(46.99, costController.sumCategory(costs, new double[Category.values().length+1])[0], 0.01);
    }
    
    @Test
    public void sumsRightCostsByWeekdayYearly() throws SQLException {
        double[][] money = costController.sumWeekdayYearly(costs2, new double[30][8]);
        
        assertEquals(129.79, money[19][2], 0.01);
        assertEquals(28.90, money[18][2], 0.01);
    }
    
    @Test
    public void sumsRightCostsByMonthYearly() throws SQLException {
        double[][] money = costController.sumMonthYearly(costs2, new double[30][Category.values().length+1]);
        
        assertEquals(129.79, money[19][5], 0.01);
        assertEquals(28.90, money[18][12], 0.01);
    }
    
    @Test
    public void sumsRightCostsByCategoryYearly() throws SQLException {
        double[][] money = costController.sumCategoryYearly(costs2, new double[30][Category.values().length+1]);
        
        assertEquals(129.79, money[19][2], 0.01);
        assertEquals(28.90, money[18][0], 0.01);
    }
    
}
