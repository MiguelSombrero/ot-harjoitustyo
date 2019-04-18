
package budgetapp.dao;

import budgetapp.domain.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Luokka, joka tarjoaa metodit käyttäjän tallentamiseen tietokantaan.
 */
public class DbUserDao implements UserDao<User, String> {

    private String path;
    private String dbUser;
    private String password;
    private String driver;
    
    /**
     * Luokan konstruktori.
     * 
     * @param path config.properties tiedostossa määritelty polku tietokantaan
     * @param dbUser tietokannan käyttäjätunnus
     * @param password tietokannan salasana
     * @param driver  tietokannan ajurin nimi
     */
    public DbUserDao(String path, String dbUser, String password, String driver) {
        this.path = path;
        this.dbUser = dbUser;
        this.password = password;
        this.driver = driver;
    }
    
    /**
     * Metodi, joka tallentaa käyttäjän tietokantaan.
     * 
     * @param object Sovelluksessa luotu tietokantaan tallennettava käyttäjä-olio
     * @return Tietokantaan tallennettu käyttäjä
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public User create(User object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "INSERT INTO User (username, password, created) VALUES (?,?,?)");
        
        query.setString(1, object.getUsername());
        query.setString(2, object.getPassword());
        query.setString(3, object.getCreated().toString());
        
        query.executeUpdate();
        query.close();
        connection.close();
        
        return object;
    }

    /**
     * Metodi, joka poimii käyttäjän tietokannasta käyttäjätunnuksen perusteella
     * @param key Käyttäjätunnus, jonka perusteella poimittava käyttäjä tunnistetaan
     * @return Tietokannasta poimittu käyttäjä
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public User read(String key) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "SELECT username, password, created FROM User WHERE username = ?");
        
        query.setString(1, key);
        ResultSet result = query.executeQuery();
        
        if (!result.next()) {
            return null;
        }
        
        User user = new User(
                result.getString("username"),
                result.getString("password"),
                Date.valueOf(result.getString("created")).toLocalDate());
        
        result.close();
        query.close();
        connection.close();
        
        return user;
    }

    /**
     * Metodi, joka päivittää tietokannassa olevan käyttäjän salasanan.
     * 
     * @param object Käyttäjä, jonka salasana päivitetään
     * @return Käyttäjä, jonka salasana päivitettiin
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public User update(User object) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        PreparedStatement query = connection.prepareStatement(
                "UPDATE User SET password = ? WHERE username = ?");
        
        query.setString(1, object.getPassword());
        query.setString(2, object.getUsername());
        query.executeUpdate();
        
        query.close();
        connection.close();
        
        return object;
    }

    /**
     * Metodi, joka poistaa käyttäjän ja kaikki sen tiedot tietokannasta.
     * 
     * @param key Käyttäjätunnus, jonka perusteella poistettava käyttäjä tunnistetaan
     * @throws SQLException Tietokannan mahdollisesti heittämä poikkeus
     */
    @Override
    public void remove(String key) throws SQLException {
        Connection connection = DriverManager.getConnection(path, dbUser, password);
        connection.prepareStatement("PRAGMA foreign_keys = ON;").execute();
            
        PreparedStatement query = connection.prepareStatement("DELETE FROM User WHERE username = ?");
        query.setString(1, key);
        query.executeUpdate();
        
        query.close();
        connection.close();
    }

}
