
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FakeCostDao implements CostDao<Cost, String> {

    HashMap<String, List<Cost>> costs = new HashMap<>();
    
    @Override
    public Cost create(Cost object) throws SQLException {
        costs.putIfAbsent(object.getUser(), new ArrayList<>());
        costs.get(object.getUser()).add(object);
        return object;
    }

    @Override
    public void remove(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cost> list(String key) throws SQLException {
        return costs.get(key);
    }
    
}
