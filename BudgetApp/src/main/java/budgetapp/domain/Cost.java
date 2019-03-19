
package budgetapp.domain;

import java.time.LocalDate;

public class Cost {
    
    private String category;
    private Double price;
    private LocalDate purchased;
    private String user;
    
    public Cost (String category, Double price, LocalDate purchased, String user) {
        this.category = category;
        this.price = price;
        this.purchased = purchased;
        this.user = user;
    }
    
    public void setCategory (String category) {
        this.category = category;
    }
    
    public void setPrice (Double price) {
        this.price = price;
    }
    
    public void setPurchased (LocalDate purchased) {
        this.purchased = purchased;
    }
    
    public void setUser (String user) {
        this.user = user;
    }
    
    public String getCategory () {
        return this.category;
    }
    
    public Double getPrice () {
        return this.price;
    }
    
    public LocalDate getPurchased () {
        return this.purchased;
    }
    
    public String getUser () {
        return this.user;
    }
    
}
