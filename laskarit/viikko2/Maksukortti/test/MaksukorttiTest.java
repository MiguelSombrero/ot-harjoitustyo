/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class MaksukorttiTest {
    
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }
    
    @Test
    public void konstruktoriASettaaSaldonOikein () {
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
     
    @Test
    public void syoEdullisestiVahentaaSaldoaOikein () {
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
    }
     
    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein () {
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
    }
     
    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi () {
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    
    @Test
    public void syoMaukkaastitiEiVieSaldoaNegatiiviseksi () {
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiMuutaKortinArvoa () {
        kortti.lataaRahaa(-5.0);
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa () {
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
    }
     
    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa () {
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortillaPystyyOstamaanEdullisen () {
        Maksukortti koksumartti = new Maksukortti(2.5);
        koksumartti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", koksumartti.toString());
    }
   
    @Test
    public void kortillaPystyyOstamaanMaukkaan () {
        Maksukortti koksumartti = new Maksukortti(4.0);
        koksumartti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 0.0 euroa", koksumartti.toString());
    }
}
