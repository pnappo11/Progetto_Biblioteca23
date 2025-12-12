/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

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
public class GestioneUtentiTest {
    
    public GestioneUtentiTest() {
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
     * Test of setGestionePrestiti method, of class GestioneUtenti.
     */
    @Test
    public void testSetGestionePrestiti() {
        System.out.println("setGestionePrestiti");
        GestionePrestiti gestionePrestiti = null;
        GestioneUtenti instance = new GestioneUtenti();
        instance.setGestionePrestiti(gestionePrestiti);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUtenti method, of class GestioneUtenti.
     */
    @Test
    public void testGetUtenti() {
        System.out.println("getUtenti");
        GestioneUtenti instance = new GestioneUtenti();
        TreeSet<Utente> expResult = null;
        TreeSet<Utente> result = instance.getUtenti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inserisciUtente method, of class GestioneUtenti.
     */
    @Test
    public void testInserisciUtente() {
        System.out.println("inserisciUtente");
        Utente utente = null;
        GestioneUtenti instance = new GestioneUtenti();
        instance.inserisciUtente(utente);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificaUtente method, of class GestioneUtenti.
     */
    @Test
    public void testModificaUtente() {
        System.out.println("modificaUtente");
        Utente utenteMod = null;
        GestioneUtenti instance = new GestioneUtenti();
        instance.modificaUtente(utenteMod);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminaUtente method, of class GestioneUtenti.
     */
    @Test
    public void testEliminaUtente() {
        System.out.println("eliminaUtente");
        String matricolaStr = "";
        GestioneUtenti instance = new GestioneUtenti();
        instance.eliminaUtente(matricolaStr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cercaUtenti method, of class GestioneUtenti.
     */
    @Test
    public void testCercaUtenti() {
        System.out.println("cercaUtenti");
        String matricola = "";
        String cognome = "";
        String nome = "";
        GestioneUtenti instance = new GestioneUtenti();
        TreeSet<Utente> expResult = null;
        TreeSet<Utente> result = instance.cercaUtenti(matricola, cognome, nome);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trovaUtente method, of class GestioneUtenti.
     */
    @Test
    public void testTrovaUtente() {
        System.out.println("trovaUtente");
        String matricola = "";
        GestioneUtenti instance = new GestioneUtenti();
        Utente expResult = null;
        Utente result = instance.trovaUtente(matricola);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBlacklist method, of class GestioneUtenti.
     */
    @Test
    public void testSetBlacklist() {
        System.out.println("setBlacklist");
        Utente utente = null;
        boolean toBlacklist = false;
        GestioneUtenti instance = new GestioneUtenti();
        instance.setBlacklist(utente, toBlacklist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
