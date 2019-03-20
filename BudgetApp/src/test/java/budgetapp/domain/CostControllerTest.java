
package budgetapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
}
