
package budgetapp.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Luokka vastaa sovelluksen käyttäjää.
 */
public class User {
    
    private String username;
    private String password;
    private LocalDate created;
    
    /**
     * Luokan konstruktori.
     * 
     * @param username Käyttäjän valitsema käytäjätunnus
     * @param password Käyttäjän valitsema salasana
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.created = LocalDate.now();
    }
    
    /**
     * Luokan konstruktori.
     * 
     * @param username Käyttäjän valitsema käytäjätunnus
     * @param password Käyttäjän valitsema salasana
     * @param created Päivämäärä, jolloin käyttäjätunnus on luotu
     */
    public User(String username, String password, LocalDate created) {
        this.username = username;
        this.password = password;
        this.created = created;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public LocalDate getCreated() {
        return this.created;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setCreated(LocalDate created) {
        this.created = created;
    }
    
    /**
     * Käyttäjän tulostamiseen tarvittava metodi.
     * 
     * @return Merkkijono, jossa käyttäjän käyttäjätunnus sekä päivämäärä, jolloin tunnus on luotu
     */
    public String toString() {
        return "Username " + this.username + ", created "
                + this.created.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    }
    
    
}
