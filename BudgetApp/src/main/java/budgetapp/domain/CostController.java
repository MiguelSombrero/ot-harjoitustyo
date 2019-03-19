
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.time.LocalDate;

public class CostController {
    
    private CostDao costDao;
    
    public CostController (CostDao costDao) {
        this.costDao = costDao;
    }
    
    public Integer addCost (String category, Double price, LocalDate purchased, String user) {
        try {
            costDao.create(new Cost(category, price, purchased, user));
            return 0;
        }
        catch (SQLException e) { return 2; }
    }
}
