package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein () {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein () {
        kortti.lataaRahaa(110);
        assertEquals("saldo: 1.20", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi () {
        kortti.otaRahaa(10);
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void saldoEiVaheneJosRahaaEiOleTarpeeksi () {
        kortti.otaRahaa(20);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void metodiPalauttaaTrueJosRahatRiittavat () {
        assertTrue(kortti.otaRahaa(10));
    }
    
    @Test
    public void metodiPalauttaaFalseJosRahatEiRiita () {
        assertTrue(!kortti.otaRahaa(20));
    }
    
}
