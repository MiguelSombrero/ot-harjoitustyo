
package budgetapp.domain;

import budgetapp.dao.CostDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka, joka tarjoaa metodit kustannusten käsittelyyn.
 */
public class CostController {
    
    private CostDao costDao;
    
    /**
     * Luokan konstruktori.
     * 
     * @param costDao Kustannusten Dao-toiminnallisuudet tarjoava luokka
     */
    public CostController(CostDao costDao) {
        this.costDao = costDao;
    }
    
    /**
     * Metodi, joka lisää käyttäjälle uuden kustannuksen tietokantaan.
     * 
     * @param category Kategoria johon kustannus kuuluu
     * @param price Kustannuksen hinta
     * @param purchased Päivämäärä, jolloin kustannus tehtiin
     * @param user Käyttäjä, johon kustannus liittyy
     * @return Kokonaisluku, joka kertoo kustannuksen luonnin onnistumisesta:
     * 0 - kustannuksen luonti onnistui
     * 2 - virhe
     */
    public Integer addCost(Category category, Double price, LocalDate purchased, String user) {
        try {
            costDao.create(new Cost(category, price, purchased, user));
            return 0;
            
        } catch (SQLException e) {
            return 2;
        }
    }
    
    /**
     * Metodi, joka poistaa kustannuksen tietokannasta.
     * 
     * @param id Kustannuksen pääavain tietokannassa
     * @return Kokonaisluku, joka kertoo kustannuksen poistamisen onnistumisesta:
     * 0 - kustannuksen poisto onnistui
     * 2 - virhe
     */
    public Integer removeCost(Integer id) {
        try {
            costDao.remove(id);
            return 0;
           
        } catch (SQLException e) {
            return 2;
        }
    }
    
    /**
     * Metodi, joka hakee käyttäjään liittyvät kustannukset tietokannasta.
     * 
     * @param user Käyttäjä, jonka kustannuksen haetaan
     * @return Lista käyttäjän kustannuksista tai tyhjä lista, jos kustannuksia ei ole
     */
    public List<Cost> getCosts(String user) {
        try {
            return costDao.listByUser(user);
            
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Apumetodi, joka käsittelee käyttäjän kustannukset ja summaa ne jaoteltuna viikonpäivittäin.
     * 
     * @param costs Käyttäjän kustannukset listana
     * @param money Taulukko johon lasketaan viikonpäivittäin summatut euromääräiset kustannukset
     * @return Viikonpäivittäin summatut euromääräiset kustannukset
     */
    public double[] sumWeekday(List<Cost> costs, double[] money) {
        for (Cost cost : costs) {
            money[cost.getPurchased().getDayOfWeek().getValue()] += cost.getPrice(); 
        }
        return money;
    }
    
    /**
     * Apumetodi, joka käsittelee käyttäjän kustannukset ja summaa ne jaoteltuna kuukausittain.
     * 
     * @param costs Käyttäjän kustannukset listana
     * @param money Taulukko johon lasketaan kuukausittain summatut euromääräiset kustannukset
     * @return Kuukausittain summatut euromääräiset kustannukset
     */
    public double[] sumMonth(List<Cost> costs, double[] money) {
        for (Cost cost : costs) {
            money[cost.getPurchased().getMonthValue()] += cost.getPrice();
        }
        return money;
    }
    
    /**
     * Apumetodi, joka käsittelee käyttäjän kustannukset ja summaa ne jaoteltuna kategorioittain.
     * 
     * @param costs Käyttäjän kustannukset listana
     * @param money Taulukko johon lasketaan kategorioittain summatut euromääräiset kustannukset
     * @return kategorioittain summatut euromääräiset kustannukset
     */
    public double[] sumCategory(List<Cost> costs, double[] money) {
        for (Cost cost : costs) {
            money[cost.getCategory().ordinal()] += cost.getPrice();
        }
        return money;
    }
    
    /**
     * Apumetodi, joka käsittelee käyttäjän kustannukset ja summaa ne jaoteltuna viikonpäivittäin vuosikohtaisesti.
     * 
     * @param costs Käyttäjän kustannukset listana
     * @param money Taulukko johon lasketaan vuosikohtaisesti viikonpäivittäin summatut euromääräiset kustannukset
     * @return Vuosittain viikonpäivittäin summatut euromääräiset kustannukset
     */
    public double[][] sumWeekdayYearly(List<Cost> costs, double[][] money) {
        for (Cost cost : costs) {
            int year = cost.getPurchased().getYear() - 2000;
            money[year][cost.getPurchased().getDayOfWeek().getValue()] += cost.getPrice();
            money[year][0] = 1;
        }
        return money;    
    }
    
    /**
     * Apumetodi, joka käsittelee käyttäjän kustannukset ja summaa ne jaoteltuna kuukausittain vuosikohtaisesti.
     * 
     * @param costs Käyttäjän kustannukset listana
     * @param money Taulukko johon lasketaan vuosikohtaisesti kuukausittain summatut euromääräiset kustannukset
     * @return Vuosikohtaisesti kuukausittain summatut euromääräiset kustannukset
     */
    public double[][] sumMonthYearly(List<Cost> costs, double[][] money) {
        for (Cost cost : costs) {
            int year = cost.getPurchased().getYear() - 2000;
            money[year][cost.getPurchased().getMonthValue()] += cost.getPrice();
            money[year][0] = 1;
        }
        return money;    
    }
    
    /**
     * Apumetodi, joka käsittelee käyttäjän kustannukset ja summaa ne jaoteltuna kategorioittain vuosikohtaisesti.
     * 
     * @param costs Käyttäjän kustannukset listana
     * @param money Taulukko johon lasketaan kategorioittain kuukausittain summatut euromääräiset kustannukset
     * @return Vuosikohtaisesti kategorioittain summatut euromääräiset kustannukset
     */
    public double[][] sumCategoryYearly(List<Cost> costs, double[][] money) {
        for (Cost cost : costs) {
            int year = cost.getPurchased().getYear() - 2000;
            money[year][cost.getCategory().ordinal()] += cost.getPrice();
            money[year][money[0].length - 1] = 1;
        }
        return money;    
    }
    
    /**
     * Metodi, joka tyhjentää käyttäjän kustannukset välimuistista.
     * 
     * @param user Käyttäjä, jonka välimuisti tyhjennetään
     */
    public void emptyCostsCache(String user) {
        costDao.removeByUser(user);
    }
}
