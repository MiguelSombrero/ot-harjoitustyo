# Sovelluksen käyttöohje

Lataa tiedosto [BudgetApp.jar](https://github.com/MiguelSombrero/ot-harjoitustyo/releases/tag/release01) ja siihen liittyvä *config.properties* -tiedosto.

## Konfigurointi

Sovellus olettaa, että sen käynnistyshakemistossa on [config.properties](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/BudgetApp/config.properties) -tiedosto, joka määrittelee tietokannan, johon käyttäjän ja niihin liittyvät tiedot tallennetaan. Muodostuvan tietokantatiedoston nimi on *budget.db* ja se muodostetaan samaan kansioon, missä sovellus käynnistetään.

## Sovelluksen käynnistäminen

Sovellus käynnistetään komennolla:

    java -jar BudgetApp.jar

## Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään:

![login_view](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/login_view.png)

Kirjautuminen onnistuu kirjoittamalla olemassa oleva käyttäjätunnus ja salasana ja painamalla *login* -painiketta.

## Uuden käyttäjän luominen

Kirjautumisnäkymästä voi siirtyä uuden käyttäjän luomiseen tarkoitettuun näkymään *New user ?* -painikkeesta.

![create_user_view](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/create_user_view.png)

Uusi käyttäjä luodaan syöttämällä uusi käyttäjätunnus ja salasana ja painamalla *Create* -painiketta. Onnistuneen käyttäjätilin luomisen jälkeen sovellus aktivoi kirjautumisnäkymän. Kirjautumisnäkymään voi siirtyä myös manuaalisesti *Login* -painikkeesta.

## Uuden kustannuksen lisääminen

Onnistuneen kirjautumisen jälkeen avautuu sovellusnäkymä, josta käyttäjä voi syöttää ja tarkastella kustannuksia.

![add_new_cost_view](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/add_new_cost_view.png)

Uusi kustannus syötetään antamalla kustannuksen kategoria, hinta ja päivä jolloin kustannus on tehty. Tämän jälkeen painetaan *Add* -painiketta.

## Kustannusten tarkastelu

Sovellusnäkymässä *Costs* -välilehdeltä käyttäjä voi tarkastella kustannuksia sekä yksittäin, että erilaisten kaavioiden avulla summatasolla.

### Yksittäisten kustannusten tarkastelu ja poistaminen

*View costs* -painikkeesta käyttäjä voi tarkastella kaikkia syöttämiään kustannuksia luettelona.

![view_costs_view](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/view_costs_view.png)

Käyttäjä voi poistaa yksittäisen kustannuksen klikkaamalla listaa kyseisen kustannuksen kohdalla. Käyttöliittymä kysyy varmistuksen, haluaako käyttäjä poistaa kyseisen kustannuksen.

### Kustannusten tarkastelu summatasolla

*Weekday* -painikkeesta käyttäjä voi tarkastella kustannuksia summattuna viikonpäivittäin.

![stats_per_weekday_view](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/stats_per_weekday_view.png)

Pylväskaaviossa on summattuna kaikki käyttäjän kustannukset jokaiselle viikonpäivälle.

Vastaavalla tavalla *Month* ja *Category* -painikkeista voi tarkastella kustannusten jakautumista kuukausille ja kategorioihin.

*Weekday yearly*, *Month yearly* ja *Category yearly* -painikkeista käyttäjä voi tarkastella kustannuksia vastaavasti, mutta eriteltynä vuosittain - esimerkiksi vertaillakseen kustannuksia eri vuosien välillä. 

## Käyttäjätietojen tarkastelu ja muuttaminen

Sovellusnäkymässä *User* -välilehdeltä käyttäjä voi vaihtaa salasanan, kirjautua ulos järjestelmästä tai poistaa käyttäjätilinsä kokonaan.

![change_password_view](https://github.com/MiguelSombrero/ot-harjoitustyo/blob/master/dokumentointi/kuvat/change_password_view.png)

*Change password* -painikkeesta avautuu näkymä, johon käyttäjä voi syöttää uuden salasana sekä vahvistuksena saman salasanan toiseen kertaan. Tämän jälkeen *Change* -painikkeesta järjestelmä vaihtaa käyttäjän salasanan, jos salasana täyttää siltä odotetut muotovaatimukset.

*Logout* -painikkeesta käyttäjä kirjataan ulos järjestelmästä ja ohjataan kirjautumisnäkymään.

*Remove user* -painikkeesta käyttäjä ja kaikki sen tiedot poistetaan järjestelmästä. Käyttöliittymä kysyy varmistuksen, halutaanko käyttäjä varmasti poistaa.