
package budgetapp.domain;

public enum Category {
    ALCOHOL(1), CAFE(2), CLOTHES(3), COSMETICS(4), CULTURE(5), DONATIONS(6), EDUCATION(7),
    EVENTS(8), FOOD(9), GIFTS(10), HAIR(11), HEALTHCARE(12), HOLIDAY(13), HOUSE(14),
    INSURANCES(15), MOBILE(16), RANDOM(17), RESTAURANTS(18), HOBBIES(19), SPORTSGEAR(20),
    SUPLEMENTS(21), TRANSPORTATION(22);
    
    private int value;
    
    private Category(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
    
}
