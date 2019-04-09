
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FakeCostDao implements CostDao<Cost, Integer> {

    HashMap<String, List<Cost>> costs = new HashMap<>();
    
    @Override
    public Cost create(Cost object) throws SQLException {
        costs.putIfAbsent(object.getUser(), new ArrayList<>());
        object.setId( costs.get(object.getUser()).size() + 1 );
        costs.get(object.getUser()).add(object);
        return object;
    }

    @Override
    public void remove(Integer key) {
        
    }

    @Override
    public void removeByUser(String key) {
        costs.remove(key);
    }
    
    @Override
    public List<Cost> listByUser(String key) throws SQLException {
        return costs.get(key);
    }
    
}
