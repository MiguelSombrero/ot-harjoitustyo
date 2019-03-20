
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    private Kassapaate kassa;
    private Maksukortti rahakasKortti;
    private Maksukortti koyhaKortti;
    
    @Before
    public void setUp() {
        this.kassa = new Kassapaate();
        this.rahakasKortti = new Maksukortti(1000);
        this.koyhaKortti = new Maksukortti(100);
    }
    
    @Test
    public void kassanRahamaaraOnOikein () {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaitaMyytyNolla () {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisiaMyytyNolla () {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiVaihtorahaOikeinKunMaksuRiittaa () {
        assertEquals(160, kassa.syoEdullisesti(400));
    }
    
    @Test
    public void syoEdullisestiVaihtorahaOikeinKunMaksuEiRiita () {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void syoMaukkaastiVaihtorahaOikeinKunMaksuRiittaa () {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void syoMaukkaastiVaihtorahaOikeinKunMaksuEiRiita () {
        assertEquals(300, kassa.syoMaukkaasti(300));
    }
    
    @Test
    public void syoEdullisestiKasvattaaLounaitaKunMaksuRiittaa () {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKasvattaaLounaitaKunMaksuKortillaRiittaa () {
        kassa.syoEdullisesti(rahakasKortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiEiKasvataLounaitaKunMaksuEiRiita () {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiEiKasvataLounaitaKunMaksuKortillaEiRiita () {
        kassa.syoEdullisesti(koyhaKortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaLounaitaKunMaksuRiittaa () {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaLounaitaKunMaksuKortillaRiittaa () {
        kassa.syoMaukkaasti(rahakasKortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiKasvataLounaitaKunMaksuEiRiita () {
        kassa.syoMaukkaasti(390);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiKasvataLounaitaKunMaksuKortillaEiRiita () {
        kassa.syoMaukkaasti(koyhaKortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKasvattaaKassaaKunMaksuRiittaa () {
        kassa.syoEdullisesti(1000);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiEiKasvataKassaaKunMaksuEiRiita () {
        kassa.syoEdullisesti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiEiKasvataKassaaKortillaOstaessa () {
        kassa.syoEdullisesti(rahakasKortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaKassaaKunMaksuRiittaa () {
        kassa.syoMaukkaasti(1000);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiEiKasvataKassaaKunMaksuEiRiita () {
        kassa.syoMaukkaasti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiEiKasvataKassaaKortillaOstaessa () {
        kassa.syoMaukkaasti(rahakasKortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiVahentaaKortinSaldoaJosMaksuRiittaa () {
        kassa.syoEdullisesti(rahakasKortti);
        assertEquals(760, rahakasKortti.saldo());
    }
    
    @Test
    public void syoEdullisestiEiVahennaKortinSaldoaJosMaksuEiRiita () {
        kassa.syoEdullisesti(koyhaKortti);
        assertEquals(100, koyhaKortti.saldo());
    }
    
    @Test
    public void syoEdullisestiPalauttaaTrueJosMaksuRiittaa () {
        assertTrue(kassa.syoEdullisesti(rahakasKortti));
    }
    
    @Test
    public void syoEdullisestiPalauttaaFalseJosMaksuEiRiita () {
        assertTrue(!kassa.syoEdullisesti(koyhaKortti));
    }
    
    @Test
    public void syoMaukkaastiVahentaaKortinSaldoaJosMaksuRiittaa () {
        kassa.syoMaukkaasti(rahakasKortti);
        assertEquals(600, rahakasKortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiEiVahennaKortinSaldoaJosMaksuEiRiita () {
        kassa.syoMaukkaasti(koyhaKortti);
        assertEquals(100, koyhaKortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiPalauttaaTrueJosMaksuRiittaa () {
        assertTrue(kassa.syoMaukkaasti(rahakasKortti));
    }
    
    @Test
    public void syoMaukkaastiPalauttaaFalseJosMaksuEiRiita () {
        assertTrue(!kassa.syoMaukkaasti(koyhaKortti));
    }
    
    @Test
    public void rahanLataaminenKasvattaaKortinSaldoa () {
        kassa.lataaRahaaKortille(koyhaKortti, 400);
        assertEquals(500, koyhaKortti.saldo());
    }
    
    @Test
    public void negatiivisenSaldonLataaminenEiKasvataKortinSaldoa () {
        kassa.lataaRahaaKortille(koyhaKortti, -100);
        assertEquals(100, koyhaKortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaKassaa () {
        kassa.lataaRahaaKortille(koyhaKortti, 400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSaldonLataaminenEiKasvataKassaa () {
        kassa.lataaRahaaKortille(koyhaKortti, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
