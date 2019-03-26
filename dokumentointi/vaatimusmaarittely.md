#Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoitus on pitää kirjaa käyttäjän kuluista ja tarjota erilaisia tilastoja niiden pohjalta. Sovelluksella voi olla useita käyttäjiä; jokainen käyttäjä luo käyttäjätilin ennen sovellukseen kirjautumista.

## Käyttäjät

Alkuvaiheessa sovelluksella on vain *peruskäyttäjä* -rooli. Jatkokehityksessä sovellukseen voidaan lisätä myös *pääkäyttäjä* -rooli, jonka kautta voidaan esimerkiksi hallinnoida käyttäjätilejä, sekä erilaisia kulutuskategorioita jne. 

## Sovelluksen toiminnallisuudet

### Ennen kirjautumista

* Käyttäjä voi luoda järjestelmään uuden käyttäjätunnuksen
    * Käyttäjätunnuksen ja salasanan tulee olla 5-15 merkkiä
    * Käyttäjätunnuksen tulee olla uniikki, eli se ei saa olla vielä käytössä
    * Käyttöliittymä antaa palautetta uuden käyttäjän luomisen onnistumisesta

* Käyttäjä voi kirjautua järjestelmään
    * Jos kirjautuminen onnistuu, avataan ns. sovellusnäkymä
    * Jos kirjautuminen ei onnistu, antaa käyttöliittymä palautetta epäonnistumisen syystä

### Kirjautumisen jälkeen

* Käyttäjä voi lisätä uuden kuluerän
* Käyttäjä voi katsoa tilastot omasta kulutushistoriastaan viikonpäivitäin, kuukausittain tai vuosittain, joko summattuna kaikilta vuosilta, tai jaoteltuna vuosittain.
* Käyttäjä voi vaihtaa salasanan
    * Uusi salasana pitää kirjoittaa 2 kertaa oikein, jotta salasana vaihdetaan
    * Jos uuden salasanan kirjoittaa toisella kertaa väärin, käyttöliittymä antaa tästä ilmoituksen, eikä salasanaa vaihdeta
* Käyttäjä voi kirjautua ulos järjestelmästä
* Käyttäjä voi poistaa tunnuksensa
    * Käyttöliittymä kysyy varmistuksen, ennen kuin käyttäjätunnus poistetaan
    * Tunnuksen poistaminen poistaa myös kaikki tunnukseen liittyvän datan

## Jatkokehitys

* Toiminnallisuus, jonka avulla käyttäjä/pääkäyttäjä voi lisätä uusia kulutuskategorioita
* Toiminnallisuus, jolla käyttäjä pääsee tarkastelemaan lisättyjä kuluja yksitellen
* Toiminnallisuus yksittäisten kulujen poistamiseen
* Salasanojen tallentaminen tietokantaan salattuna


