
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FakeCostDao implements CostDao<Cost, Integer> {

    HashMap<String, List<Cost>> costs = new HashMap<>();
    HashMap<Integer, Cost> costsById = new HashMap<>();
    
    @Override
    public Cost create(Cost object) throws SQLException {
        costs.putIfAbsent(object.getUser(), new ArrayList<>());
        object.setId( costs.get(object.getUser()).size() + 1 );
        
        costs.get(object.getUser()).add(object);
        costsById.put(object.getId(), object);
        
        return object;
    }

    @Override
    public void remove(Integer key) {
        costsById.remove(key);
    }

    @Override
    public void removeByUser(String key) {
        costs.remove(key);
    }
    
    @Override
    public List<Cost> listByUser(String key) throws SQLException {
        return costs.get(key);
    }
    
    public Cost getByID(Integer id) {
        return costsById.get(id);
    }
    
}
