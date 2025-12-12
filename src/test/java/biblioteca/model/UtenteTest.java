/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

import java.util.List;
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
public class UtenteTest {
    
    public UtenteTest() {
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
     * Test of getMatricola method, of class Utente.
     */
    @Test
    public void testGetMatricola() {
        System.out.println("getMatricola");
        Utente instance = null;
        String expResult = "";
        String result = instance.getMatricola();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNome method, of class Utente.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Utente instance = null;
        String expResult = "";
        String result = instance.getNome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNome method, of class Utente.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "";
        Utente instance = null;
        instance.setNome(nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCognome method, of class Utente.
     */
    @Test
    public void testGetCognome() {
        System.out.println("getCognome");
        Utente instance = null;
        String expResult = "";
        String result = instance.getCognome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCognome method, of class Utente.
     */
    @Test
    public void testSetCognome() {
        System.out.println("setCognome");
        String cognome = "";
        Utente instance = null;
        instance.setCognome(cognome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class Utente.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Utente instance = null;
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail method, of class Utente.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Utente instance = null;
        instance.setEmail(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInBlacklist method, of class Utente.
     */
    @Test
    public void testIsInBlacklist() {
        System.out.println("isInBlacklist");
        Utente instance = null;
        boolean expResult = false;
        boolean result = instance.isInBlacklist();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInBlacklist method, of class Utente.
     */
    @Test
    public void testSetInBlacklist() {
        System.out.println("setInBlacklist");
        boolean valore = false;
        Utente instance = null;
        instance.setInBlacklist(valore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumPrestitiAttivi method, of class Utente.
     */
    @Test
    public void testGetNumPrestitiAttivi() {
        System.out.println("getNumPrestitiAttivi");
        Utente instance = null;
        int expResult = 0;
        int result = instance.getNumPrestitiAttivi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrestitiAttivi method, of class Utente.
     */
    @Test
    public void testGetPrestitiAttivi() {
        System.out.println("getPrestitiAttivi");
        Utente instance = null;
        List<Prestito> expResult = null;
        List<Prestito> result = instance.getPrestitiAttivi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canNuovoPrestito method, of class Utente.
     */
    @Test
    public void testCanNuovoPrestito() {
        System.out.println("canNuovoPrestito");
        Utente instance = null;
        boolean expResult = false;
        boolean result = instance.canNuovoPrestito();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiungiPrestito method, of class Utente.
     */
    @Test
    public void testAggiungiPrestito() {
        System.out.println("aggiungiPrestito");
        Prestito prestito = null;
        Utente instance = null;
        instance.aggiungiPrestito(prestito);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rimuoviPrestito method, of class Utente.
     */
    @Test
    public void testRimuoviPrestito() {
        System.out.println("rimuoviPrestito");
        Prestito prestito = null;
        Utente instance = null;
        instance.rimuoviPrestito(prestito);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Utente.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Utente o = null;
        Utente instance = null;
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Utente.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        Utente instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Utente.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Utente instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Utente.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Utente instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
