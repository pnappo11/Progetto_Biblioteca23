/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tommy
 */
public class GestioneLibriTest {
    
    public GestioneLibriTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLibri method, of class GestioneLibri.
     */
    @Test
    public void testGetLibri() {
        System.out.println("getLibri");
        GestioneLibri instance = new GestioneLibri();
        TreeSet<Libro> expResult = null;
        TreeSet<Libro> result = instance.getLibri();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLibriOrdinatiPerTitolo method, of class GestioneLibri.
     */
    @Test
    public void testGetLibriOrdinatiPerTitolo() {
        System.out.println("getLibriOrdinatiPerTitolo");
        GestioneLibri instance = new GestioneLibri();
        List<Libro> expResult = null;
        List<Libro> result = instance.getLibriOrdinatiPerTitolo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserisciLibro method, of class GestioneLibri.
     */
    @Test
    public void testInserisciLibro() {
        System.out.println("inserisciLibro");
        long isbn = 0L;
        String titolo = "";
        List<String> autori = null;
        int annoPubblicazione = 0;
        int copieTotali = 0;
        GestioneLibri instance = new GestioneLibri();
        Libro expResult = null;
        Libro result = instance.inserisciLibro(isbn, titolo, autori, annoPubblicazione, copieTotali);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificaLibro method, of class GestioneLibri.
     */
    @Test
    public void testModificaLibro() {
        System.out.println("modificaLibro");
        Libro libro = null;
        String titolo = "";
        List<String> autori = null;
        int annoPubblicazione = 0;
        int copieTotali = 0;
        GestioneLibri instance = new GestioneLibri();
        instance.modificaLibro(libro, titolo, autori, annoPubblicazione, copieTotali);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminaLibro method, of class GestioneLibri.
     */
    @Test
    public void testEliminaLibro() {
        System.out.println("eliminaLibro");
        String codiceIsbn = "";
        GestioneLibri instance = new GestioneLibri();
        instance.eliminaLibro(codiceIsbn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cercaLibri method, of class GestioneLibri.
     */
    @Test
    public void testCercaLibri() {
        System.out.println("cercaLibri");
        String codiceIsbn = "";
        String titolo = "";
        String autore = "";
        GestioneLibri instance = new GestioneLibri();
        TreeSet<Libro> expResult = null;
        TreeSet<Libro> result = instance.cercaLibri(codiceIsbn, titolo, autore);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trovaLibro method, of class GestioneLibri.
     */
    @Test
    public void testTrovaLibro() {
        System.out.println("trovaLibro");
        long isbn = 0L;
        GestioneLibri instance = new GestioneLibri();
        Libro expResult = null;
        Libro result = instance.trovaLibro(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
