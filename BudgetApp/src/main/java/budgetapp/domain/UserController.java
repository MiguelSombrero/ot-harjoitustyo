
package budgetapp.domain;

import budgetapp.dao.UserDao;
import java.sql.SQLException;

/**
 * Luokka tarjoaa käyttäjätilin hallintaan tarvittavat toiminnallisuudet.
 */

public class UserController {
    
    private UserDao userDao;
    private User user;
    
    /**
     * Luokan konstruktori.
     * 
     * @param userDao Käyttäjän Dao-toiminnalisuudet tarjoava luokka
     */
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * Metodi palauttaa sovellukseen kirjautuneen käyttäjän.
     * 
     * @return käyttäjä
     */
    public User getUser() {
        return this.user;
    }
    
    /**
     * Metodi kirjaa käyttäjän ulos.
     */
    public void logoutUser() {
        this.user = null;
    }
    
    /**
     * Metodi luo uuden käyttäjän annetulla käyttäjätunnuksella ja salasanalla,
     * mikäli tunnus ei ole käytössä.
     * 
     * @param username  Käyttäjätunnus
     * @param password  Salasana
     * @return Kokonaisluku, joka kertoo käyttäjätilin luonnin onnistumisesta:
     * 0 - käyttäjän luonti onnistui
     * 1 - käyttäjätunnus on jo käytössä
     * 2 - virhe
     */
    public Integer createUser(String username, String password) {
        try {
            if (userDao.read(username) == null) {
                userDao.create(new User(username, password));
                return 0;
            }
            return 1;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    /**
     * Metodi kirjaa käyttäjän sisälle järjestelmään, jos käyttäjä on olemassa ja salasana on syötetty sovelluksessa oikein. 
     *
     * @param username Käyttäjän käyttäjätunnus
     * @param password Käyttäjän salasana
     * @return Kokonaisluku, joka kertoo käyttäjätilin luonnin onnistumisesta:
     * 0 - käyttäjän kirjaaminen onnistui
     * 1 - käyttäjätunnusta ei ole olemassa tai salasana on väärin
     * 2 - virhe
     */
    public Integer loginUser(String username, String password) {
        try {
            User logginIn = (User) userDao.read(username);
            if (logginIn != null && logginIn.getPassword().equals(password)) {
                this.user = logginIn;
                return 0;
            }
            return 1;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    /**
     * Metodi poistaa käyttäjätilin ja muut käyttäjään liittyvät tiedot järjestelmästä ja tietokannasta.
     * 
     * @return Kokonaisluku, joka kertoo käyttäjätilin poistamisen onnistumisesta:
     * 0 - käyttäjän poistaminen onnistui
     * 2 - virhe
     */
    public Integer removeUser() {
        try {
            userDao.remove(user.getUsername());
            return 0;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    /**
     * Metodi päivittää käyttäjän salasanan.
     * 
     * @param newPassword Käyttäjän uusi salasana
     * @return Kokonaisluku, joka kertoo salasanan vaihdon onnistumisesta:
     * 0 - salasanan vaihto onnistui
     * 2 - virhe
     */
    public Integer changePassword(String newPassword) {
        try {
            this.user.setPassword(newPassword);
            userDao.update(this.user);
            return 0;
        
        } catch (SQLException e) {
            return 2;
        }
    }
    
    /**
     * Metodi, joka tarkistaa käyttäjän syöttämän salasanan ja käyttäjätunnuksen muotovaatimukset.
     * 
     * @param string Merkkijono (käyttäjätunnus tai salasana) jonka muotovaatimus tarkistetaan
     * @return palauttaa true, jos muotovaatimukset täyttyvät, muulloin false
     */
    public boolean checkCredentials(String string) {
        return (string.length() > 4 && string.length() < 16);
    }
    
}
