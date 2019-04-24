# Arkkitehtuurikuvaus

## Rakenne ja sovelluslogiikka

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria, jossa käyttöliittymä, sovelluslogiikka ja tiedon pysyväistallennukseen tarkoitetut Dao-luokat on eriytetty:

![pakkauskaavio](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio.png)

Pakkaus *gui* sisältää JavaFX:llä toteutetun graafisen käyttöliittymän, *domain* sovelluslogiikan ja *dao* tietojen pysyväistallennukseen tarkoitetut Dao-luokat.

Sovelluksen loogisen datamallin muodostavat *User* ja *Cost* -luokat, jotka kuvaavat järjestelmän käyttäjiä ja käyttäjien kustannuksia - sekä kustannuksiin liittyvä *Category* -luokka, joka on Javan ns. *lueteltu tyyppi* ja kuvaa kustannuksen kategoriaa.

Toiminnallisista kokonaisuuksista vastaa *UserController* ja *CostController* -luokat, jotka tarjoavat metodit kaikkiin käyttöliittymän toiminnallisuuksiin. Näitä toiminnallisuuksia on mm.

* void logoutUser()
* Integer createUser(String username, String password)
* Integer addCost(Category category, Double price, LocalDate purchased, String user)

*UserController* ja *CostController* -luokat pääsevät käsiksi käyttäjiin ja kustannuksiin *dao* -pakkauksessa olevien *DbUserDao* ja *DbCostDao* -luokien kautta, jotka toteuttavat *UserDao* ja *CostDao* -rajapinnat. Luokkien toteutukset injektoidaan sovelluslogiikalle konstruktorikutsun yhteydessä. 

## Käyttöliittymä

Käyttöliittymä sisältää neljä erilaista näkymää:

* kirjautuminen
* uuden käyttäjän luominen
* kustannusten lisääminen ja tarkastelu
* käyttäjän tietojen muuttaminen ja tarkastelu

Kirjautuminen ja uuden käyttäjän luominen on toteutettu yhtenä *Scene* -oliona ja tämä määrittely on eriytetty omaan luokkaansa [LoginView.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/main/java/budgetapp/gui/LoginView.java). Kustannusten lisääminen ja tarkastelu sekä käyttäjän tietojen muuttaminen ja tarkastelu on toteutettu yhtenä *Scene* -oliona ja sen määrittely on eriytetty luokkaan [ApplicationView.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/main/java/budgetapp/gui/ApplicationView.java). Kun käyttäjä kirjautuu järjestelmään, siirrytään *LoginView* -näkymästä *ApplicationView* -näkymään - ja kun käyttäjä kirjautuu ulos järjestelmästä, siirrytään *ApplicationView* -näkymästä *LoginView* -näkymään.

Käyttöliittymän toteuttava koodi on pyritty eristämään täysin sovelluslogiikasta ja se lähinnä vain kutsuu sovelluslogiikan toteuttavia luokkia sopivilla parametreilla.

## Tietojen pysyväistallennus

Luokka *DatabaseDao* luo tietokannan ja sen taulut *User* ja *Cost*, mikäli niitä ei ole vielä luotu. Tietokannanhallintajärjestelmänä käytetään *SQLite*ä ja siihen liittyvät konfiguraatiot on määritelty [config.properties](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/config.properties) tiedostossa.

Pakkauksen *budgetapp.dao* luokat *DbCostDao* ja *DbUserDao* huolehtivat käyttäjän tietojen tallentamisesta tietokantaan. Luokkien toteutus on eristetty rajapintojen *CostDao* ja *UserDao*, eikä sovelluslogiikka käytä luokkia suoraan. Näin ollen niiden toteutus voidaan tarvittaessa korvata. Esimerkiksi testaamisessa on käytetty luokkien keskusmuistitoteutuksia *FakeCostDao* ja *FakeUserDao*.

## Päätoiminnallisuudet

Alla on kuvattuna sovelluksen päätoiminnallisuudet sekvenssikaaviona

### Käyttäjän kirjautuminen

Kun kirjautumisnäkymässä syötetään käyttäjätunnus ja salasana ja painetaan *login* -painiketta, etenee sovelluksen kontrolli seuraavasti:

![sekvenssikaavio_login](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_login.png)

*Login* -painiketta kuunteleva tapahtumankäsittelijä kutsuu sovelluslogiikan metodia loginUser annetulla käyttäjätunnuksella ja salasanalla. Tämän jälkeen sovelluslogiikka kutsuu UserDao -luokan read -metodia, joka hakee käyttäjätunnusta vastaavan käyttäjän tietokannasta. Mikäli käyttäjää ei löydy, palauttaa metodi arvon *null*. Sovelluslogiikka tarkastaa ettei käyttäjä ole *null* ja että annettu salasana vastaa tietokannasta tullutta salasanaa. Mikäli tarkistuksen menevät läpi, sovelluslogiikka palauttaa käyttöliittymään arvon *0*. Jos tarkistukset eivät mene läpi, käyttöliittymään palautetaan arvo *1*. Virhetilanteissa palautetaan arvo *2*. Lopuksi - jos kirjautuminen onnistuu - käyttöliittymä asettaa päänäkymän *appScene*.

### Uuden käyttäjän luominen

Kun luo uusi käyttäjä -näkymässä syötetään käyttäjätunnus ja salasana ja painetaan *create* -painiketta, etenee sovelluksen kontrolli seuraavasti:

![sekvenssikaavio_create_user](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_create_user.png)

*Create* -painiketta kuunteleva tapahtumankäsittelijä kutsuu sovelluslogiikan metodia checkCredentials, joka tarkastaa, että käyttäjätunnus ja salasana vastaavat muotovaatimuksia. Jos tarkistus palauttaa *true*, kutsuu sovelluslogiikka createUser -metodia annetulla käyttäjätunnuksella ja salasanalla. Sovelluslogiikka tarkastaa UserDao -luokan read -metodilla, että käyttäjää ei löydy tietokannasta. Mikäli käyttäjää ei löydy, luo sovelluslogiikka uuden käyttäjän annetuilla tiedoilla ja kutsuu UserDaon:n create -metodia. Mikäli käyttäjän luonti onnistuu, sovelluslogiikka palauttaa käyttöliittymään arvon *0*. Jos käyttäjänimi löytyy jo tietokannasta, käyttöliittymään palautetaan arvo *1*. Virhetilanteissa palautetaan arvo *2*. Käyttäjän luomisen jälkeen aktivoidaan kirjautumisnäkymä.

### Uuden kustannuksen lisääminen

Kun sovellusnäkymässä syötetään uuden kustannuksen tiedot ja painetaan *add* -painiketta, etenee sovelluksen kontrolli seuraavasti:

![sekvenssikaavio_create_new_cost](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio_create_new_cost.png)

*add* -painiketta kuunteleva tapahtumankäsittelijä kutsuu sovelluslogiikan metodia addCost, joka luo kirjatuneelle käyttäjälle uuden kustannuksen. Tämän jälkeen metodi kutsuu CostDao -luokan create -metodia, joka tallentaa luodun kustannuksen tietokantaan. Jos luonti onnistuu, palauttaa metodi käyttöliitymään arvon *0*. Virhetilanteissa palautetaan arvo *2*. Kustannuksen tallentamisen jälkeen käyttöliittymä kutsuu sovelluslogiikan emptyCostsCache -metodia, joka edelleen kutsuu UserDao -luokan removeByUser -metodia. Metodi poistaa kirjautuneen käyttäjän tiedot välimuistista, sillä välimuisti ei ole enää päivityksen jälkeen ajan tasalla.

## Muut toiminnallisuudet

Sama periaate toistuu muidenkin toiminnallisuuksien kohdalla. Käyttöliittymä kutsuu sovelluslogiikkaa, joka kutsuu tarvittaessa Dao -luokan sopivaa metodia. Tilastojen näyttämiseen tarkoitetuissa toiminnallisuuksissa tietokannasta luetaan kirjautuneeseen käyttäjään liittyvät kustannukset, joita käsitellään sovelluslogiikassa ja jotka välitetään käyttöliittymään näytettäväksi kaavioina. 