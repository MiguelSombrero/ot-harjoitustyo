
package budgetapp.domain;

/**
 * Javan Enum -luokka, joka vastaa kustannukseen liittyvää kategoriaa.
 * 
 */
public enum Category {
    ALCOHOL, CAFE, CLOTHES, COSMETICS, CULTURE, DONATIONS, EDUCATION, EVENTS, FOOD, GIFTS,
    HAIR, HEALTHCARE, HOLIDAY, HOUSE, INSURANCES, MOBILE, RANDOM, RESTAURANTS, HOBBIES, SPORTSGEAR,
    SUPLEMENTS, TRANSPORTATION;
    
    @Override
    public String toString() {
        return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
    
}
