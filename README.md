# Ohjelmistotekniikan harjoitustyö - BudgetApp

Tämä on HY:n *Ohjelmistotekniikka* -kurssin harjoituksia ja harjoitustyötä varten luotu Github repositorio.

BudgetApp -sovelluksen avulla on mahdollista pitää kirjaa käyttäjän menoista. Sovellusta on mahdollista käyttää useampi eri käyttäjä, joilla kaikilla on oma käyttäjätunnus ja salasana sovellukseen. Sovellukseen syötetään käyttäjän tekemiä ostoksia tai muita kuluja. Sovelluksessa voi tarkastella omia menoja erilaisten kaavioiden avulla, esimerkiksi kuukausi, vuosi tai kategoria -tasolla. 

## Dokumentaatio

[Käyttöohje](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kaytto_ohje.md)

[Vaatimusmäärittely](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

## Releaset

[New release - 0.1](https://github.com/MiguelSombrero/ot-harjoitustyo/releases/tag/release01)

[Viikko 5](https://github.com/MiguelSombrero/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

    mvn test

Testikattavuusraportti luodaan komennolla

    mvn test jacoco:report

Kattavuusraporttia voi tarkastella selaimella tiedostostosta target/site/jacoco/index.html

### Suoritettavan jar -tiedoston generointi

komento

    mvn package

generoi hakemistoon target suoritettavan jar-tiedoston BudgetApp-1.0-SNAPSHOT.jar

### JavaDoc

JavaDoc generoidaan komenolla

    mvn javadoc:javadoc

JavaDocia voi tarkastella selaimella tiedostosta target/site/apidocs/index.html

### Checkstyle

Tiedostoon [checkstyle](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

    mvn jxr:jxr checkstyle:checkstyle

Checkstylen generoimaa raporttia voi tarkastella tiedostosta target/site/checkstyle.html