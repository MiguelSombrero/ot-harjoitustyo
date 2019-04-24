
package budgetapp.dao;

import budgetapp.domain.Category;
import budgetapp.domain.Cost;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Cost -luokan olioiden pysyväistallennuksesta vastaava luokka.
 * 
 */
public class DbCostDao implements CostDao<Cost, Integer> {

    private String path;
    private String dbUser;
    private String password;
    private String driver;
    private HashMap<String, List<Cost>> cache;
    
    /**
     * Luokan kostruktori.
     * 
     * @param path config.properties -tiedostossa määritelty tietokannan polku
     * @param dbUser config.properties -tiedostossa määritelty tietokannan käyttäjätunnus 
     * @param password config.properties -tiedostossa määritelty tietokannan salasana
     * @param driver config.properties -tiedostossa määritelty tietokannan ajurin nimi
     */
    public DbCostDao(String path, String dbUser, String password, String driver) {
        this.path = path;
        this.dbUser = dbUser;
        this.password = password;
        this.driver = driver;
        this.cache = new HashMap<>();
    }
    
    /**
     * Metodi, joka vastaa Cost -olion tallentamisesta tietokantaan.
     * 
     * @param object Tietokantaan tallennettava Cost-olio
     * @return Tietokantaan tallennettu olio
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public Cost create(Cost object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "INSERT INTO Cost (userId, category, price, purchased) VALUES (?,?,?,?)");
        
        query.setString(1, object.getUser());
        query.setString(2, object.getCategory().toString());
        query.setDouble(3, object.getPrice());
        query.setString(4, object.getPurchased().toString());
        
        query.executeUpdate();
        query.close();
        connection.close();
        
        return object;
    }

    /**
     * Metodi, joka vastaa Cost -olion poistamisesta tietokannasta pääavaimen perusteella.
     * 
     * @param id Tietokannassa oleva pääavain, jonka perusteella kustannut poistetaan
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public void remove(Integer id) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement("DELETE FROM Cost WHERE id = ?");
        query.setInt(1, id);
        
        query.executeUpdate();
        query.close();
        connection.close();
    }
    
    /**
     * Metodi, joka poistaa käyttäjään liittyvät kustannukset (Cost -oliot) sovelluksen välimuistista.
     * @param key Käyttäjätunnus, jonka kustannukset poistetaan
     */
    @Override
    public void removeByUser(String key) {
        // ON DELETE CASCADE will remove Costs from database when user is deleted
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
    }

    /**
     * Metodi, joka poimii kaikki kyseisen käyttäjän kustannukset (Cost-oliot) tietokannasta.
     * 
     * @param key Käyttäjätunnus, jonka kustannukset poimitaan
     * @return Lista käyttäjän kustannuksista
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public List<Cost> listByUser(String key) throws SQLException {
        if (!cache.containsKey(key)) {
            
            Connection connection = DriverManager.getConnection(path, dbUser, password);
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM Cost WHERE userId = ? ORDER BY purchased DESC");
        
            query.setString(1, key);
        
            ResultSet result = query.executeQuery();
            List<Cost> costs = new ArrayList<>();
        
            while (result.next()) {
                costs.add(new Cost(
                        result.getInt("id"),
                        Category.valueOf(result.getString("category").toUpperCase()),
                        result.getDouble("price"),
                        Date.valueOf(result.getString("purchased")).toLocalDate(),
                        result.getString("userId")));
            }
            result.close();
            query.close();
            connection.close();
            
            cache.put(key, costs);
        }
        return cache.get(key);
    }
}
