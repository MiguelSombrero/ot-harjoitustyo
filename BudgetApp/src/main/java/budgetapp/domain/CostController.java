
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.time.LocalDate;
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
    
    public double[] sumWeekday(List<Cost> costs, double[] money) {
        for (Cost cost : costs) {
            money[cost.getPurchased().getDayOfWeek().getValue()] += cost.getPrice(); 
        }
        return money;
    }
    
    public double[] sumMonth(List<Cost> costs, double[] money) {
        for (Cost cost : costs) {
            money[cost.getPurchased().getMonthValue()] += cost.getPrice();
        }
        return money;
    }
    
    public double[] sumCategory(List<Cost> costs, double[] money) {
        for (Cost cost : costs) {
            money[cost.getCategory().ordinal()] += cost.getPrice();
        }
        return money;
    }
    
    public double[][] sumWeekdayYearly(List<Cost> costs, double[][] money) {
        for (Cost cost : costs) {
            int year = cost.getPurchased().getYear() - 2000;
            money[year][cost.getPurchased().getDayOfWeek().getValue()] += cost.getPrice();
            money[year][0] = 1;
        }
        return money;    
    }
    
    public double[][] sumMonthYearly(List<Cost> costs, double[][] money) {
        for (Cost cost : costs) {
            int year = cost.getPurchased().getYear() - 2000;
            money[year][cost.getPurchased().getMonthValue()] += cost.getPrice();
            money[year][0] = 1;
        }
        return money;    
    }
    
    public double[][] sumCategoryYearly(List<Cost> costs, double[][] money) {
        for (Cost cost : costs) {
            int year = cost.getPurchased().getYear() - 2000;
            money[year][cost.getCategory().ordinal()] += cost.getPrice();
            money[year][money[0].length - 1] = 1;
        }
        return money;    
    }
    
    public void emptyCostsCache(String user) {
        costDao.remove(user);
    }
}
