
package budgetapp.domain;

import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CostTest {
    
    Cost cost;
    
    @Before
    public void setUp() {
        cost = new Cost(20, Category.ALCOHOL, 10.99, LocalDate.parse("2019-05-21"), "Miika");
    }
    
    @Test
    public void createsCostRightWithId() {
        assertEquals(20, cost.getId(), 0.00);
        assertEquals(Category.ALCOHOL, cost.getCategory());
        assertEquals(10.99, cost.getPrice(), 0.00);
        assertEquals(LocalDate.parse("2019-05-21"), cost.getPurchased());
        assertEquals("Miika", cost.getUser());
    }
    
    @Test
    public void toStringWorks() {
        assertEquals("21.05.2019: Alcohol, 10.99 euro", cost.toString());
    }

}
