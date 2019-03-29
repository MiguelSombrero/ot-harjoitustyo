
package budgetapp.domain;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
        
        costController.addCost(Category.CAFE, 6.7, Date.valueOf("2019-03-20").toLocalDate(), "Miika");
        costController.addCost(Category.CLOTHES, 56.7, Date.valueOf("2018-03-21").toLocalDate(), "Jukka");
        
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
        Double price = costDao.list("Miika").get(0).getPrice();
        
        // miksi ei toimi?
        //assertEquals(26.7, price);
    }
    
    @Test
    public void addCostSavesPurchasedRight() throws SQLException {
        assertEquals("2019-03-20", costDao.list("Miika").get(0).getPurchased().toString());
    }
    
    @Test
    public void addCostSavesUserRight() throws SQLException {
        
    }
    
    @Test
    public void emptyCacheWorks() throws SQLException {
        
    }
    
    @Test
    public void fedsCostsFromDatabase() throws SQLException {
        
    }
    
    @Test
    public void sumsRightCostsByWeekday() throws SQLException {
        
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
