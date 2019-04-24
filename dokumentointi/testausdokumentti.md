# Testausdokumentti

Ohjelmaa on testattu automatisoiduin yksikkö- ja integraatiotestein, sekä järjestelmätestein manuaalisesti käyttöliittymän kautta.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Pakkauksessa [budgetapp.domain](https://github.com/MiguelSombrero/ot-harjoitustyo/tree/master/BudgetApp/src/main/java/budgetapp/domain) sijaisevan sovelluslogiikan toimintoja seka luokkia testaa luokat [CostControllerTest.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/test/java/budgetapp/domain/CostControllerTest.java) ja [UserControllerTest.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/test/java/budgetapp/domain/UserControllerTest.java) joiden testit simuloivat vastaavien luokkien tarjoamia toimintoja.

Testiluokat käyttävät tiedon tallennukseen Dao-rajapintojen keskusmuistitoteutuksia [FakeCostDao.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/test/java/budgetapp/domain/FakeCostDao.java) ja [FakeUserDao.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/test/java/budgetapp/domain/FakeUserDao.java).

Luokkia User ja Cost on testattu muutamin yksikkötestein, mm. varmistamaan että toString() metodit toimivat oikein.

### Dao-luokat

User- ja Cost -luokkiin liittyviä Dao-luokkia on testattu testiluokkien [DbCostDaoTest.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/test/java/budgetapp/dao/DbCostDaoTest.java) ja [DbUserDaoTest.java](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/src/test/java/budgetapp/dao/DbUserDaoTest.java) avulla, luoden tietokannan väliaikaiseen kansioon, joka poistetaan testien jälkeen.

### Testauskattavuus

Käyttöliittymäkerrosta lukuun ottamatta sovelluksen testauksen rivikattavuus oli 94% ja haarakattavuus 93%. Testaamatta jäivät tilanteet, joissa tietokanta heittää SQLException poikkeuksen.

![test_coverage](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/test_coverage.png)

## Järjestelmätestaus

Sovelluksen järjestelmätestaus suoritettiin manuaalisesti käyttöliittymän kautta.

### Asennus ja konfigurointi

Sovellus on haettu [käyttöohjeen](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kaytto_ohje.md) kuvaamalla tavalla ja sitä on testattu OSX-käyttöjärjestelmällä. Sovelluksen käynnistyshakemistossa on ollut *config.properties* -tiedosto.

Sovellusta on testattu luomalla erilaisia käyttäjiä ja syöttämällä niille erilaisia kustannuksia sekä tarkastelemalla kustannuksia käyttöliittymästä.

### Toiminnallisuudet

Kaikki [vaatimusmäärittelyssä](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md) mainitut toiminnallisuudet on käyty läpi ja syötekenttiin on yritetty syöttää myös virheellisiä arvoja, kuten tyhjää tai rahakenttiin tekstiä jne.

## Sovellukseen jääneet laatuongelmat

Kustannusten poistaminen *View costs* -painikkeesta ei toimi halutulla tavalla: kustannuksen poistamisen jälkeen lista ei päivity ja poistettu kustannus jää listalle, kunnes käyttäjä käy jollakin toisella näkymällä.