# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria, jossa käyttöliittymä, sovelluslogiikka ja tiedon pysyväistallennukseen tarkoitetut Dao-luokat on eriytetty:

![pakkauskaavio](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio.png)

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