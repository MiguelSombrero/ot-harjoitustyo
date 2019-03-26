
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CostController {
    
    private CostDao costDao;
    
    public CostController(CostDao costDao) {
        this.costDao = costDao;
    }
    
    public Integer addCost(Category category, Double price, LocalDate purchased, String user) {
        try {
            costDao.create(new Cost(category, price, purchased, user));
            return 0;
            
        } catch (SQLException e) {
            return 2;
        }
    }
    
    public List<Cost> getCosts(String user) {
        try {
            List<Cost> costs = costDao.list(user);
            
            if (costs.isEmpty()) {
                return null;
            }
            return costs;
            
        } catch (SQLException e) {
            return null;
        }
    }
    
    public double[][] sum(List<Cost> costs) {
        double[][] money = new double[5][32];
        
        for (Cost cost : costs) {
            int category = cost.getCategory().ordinal();
            int dayofweek = cost.getPurchased().getDayOfWeek().getValue();
            int month = cost.getPurchased().getMonthValue();
            double price = cost.getPrice();
                
            money[0][dayofweek] += price;
            money[1][month] += price;
            money[2][category] += price;
        }
        return money;
    }
    
    public double[][] sumYearly(List<Cost> costs) {
        double[][] money = new double[80][32];
        
        for (Cost cost : costs) {
            int category = cost.getCategory().ordinal();
            int weekday = cost.getPurchased().getDayOfWeek().getValue();
            int month = cost.getPurchased().getMonthValue();
                
            int year = cost.getPurchased().getYear() - 2000;
            int categoryYear = year + 20;
            int weekdayYear = year + 40;
                
            double price = cost.getPrice();
                
            money[weekdayYear][weekday] += price;
            money[year][month] += price;
            money[categoryYear][category] += price;
                
            money[year][0] = 1;
            money[categoryYear][0] = 1;
            money[weekdayYear][0] = 1;
        }
        return money;    
    }
    
    public void emptyCostsCache(String user) {
        costDao.remove(user);
    }
}
