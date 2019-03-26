
package budgetapp.domain;

import java.time.LocalDate;

public class User {
    
    private String username;
    private String password;
    private LocalDate created;
    
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
}
