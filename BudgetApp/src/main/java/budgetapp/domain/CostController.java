
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CostController {
    
    private CostDao costDao;
    
    public CostController (CostDao costDao) {
        this.costDao = costDao;
    }
    
    public Integer addCost (String category, Double price, LocalDate purchased, String user) {
        try {
            costDao.create(new Cost(category, price, purchased, user));
            return 0;
            
        } catch (SQLException e) { return 2; }
    }
    
    public double[] getDailyCosts (String user) {
        double[] money = new double[8];
        
        try {
            List<Cost> costs = costDao.list(user);
            
            for (Cost cost : costs) {
                int dayofweek = cost.getPurchased().getDayOfWeek().getValue();
                money[dayofweek] += cost.getPrice();
            }
            return money;
        
        } catch (SQLException e) { return null; }
    }
    
    public double[] getMonthlyCosts (String user) {
        double[] money = new double[13];
        
        try {
            List<Cost> costs = costDao.list(user);
            
            for (Cost cost : costs) {
                int month = cost.getPurchased().getMonthValue();
                money[month] += cost.getPrice();
            }
            return money;
        
        } catch (SQLException e) { return null; }
    }
}
