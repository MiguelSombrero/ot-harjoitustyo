
package budgetapp.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cost {
    
    private Integer id;
    private Category category;
    private Double price;
    private LocalDate purchased;
    private String user;
    
    public Cost(Integer id, Category category, Double price, LocalDate purchased, String user) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.purchased = purchased;
        this.user = user;
    }
    
    public Cost(Category category, Double price, LocalDate purchased, String user) {
        this(null, category, price, purchased, user);
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public void setPurchased(LocalDate purchased) {
        this.purchased = purchased;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public Double getPrice() {
        return this.price;
    }
    
    public LocalDate getPurchased() {
        return this.purchased;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String toString() {
        return this.purchased.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))
                + ": " + this.category + ", " + this.price + " euro";
    }
    
}
