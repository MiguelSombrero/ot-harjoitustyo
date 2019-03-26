
package budgetapp.domain;

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
    }
    
    @Test
    public void addCostWorks() {
        
    }
    
    @Test
    public void addCostSavesCategoryRight() {
        
    }
    
    @Test
    public void addCostSavesPriceRight() {
        
    }
    
    @Test
    public void addCostSavesPurchasedRight() {
        
    }
    
    @Test
    public void addCostSavesUserRight() {
        
    }
    
    @Test
    public void emptyCacheWorks() {
        
    }
    
    @Test
    public void fedsCostsFromDatabase() {
        
    }
    
    @Test
    public void sumsRightCostsByWeekday() {
        
    }
    
    @Test
    public void sumsRightCostsByMonth() {
        
    }
    
    @Test
    public void sumsRightCostsByCategory() {
        
    }
    
    @Test
    public void sumsRightCostsByMonthYearly() {
        
    }
    
    @Test
    public void sumsRightCostsByCategoryYearly() {
        
    }
    
}
