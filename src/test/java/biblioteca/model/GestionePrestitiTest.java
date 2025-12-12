/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

import java.time.LocalDate;
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
public class GestionePrestitiTest {
    
    public GestionePrestitiTest() {
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
     * Test of getPrestiti method, of class GestionePrestiti.
     */
    @Test
    public void testGetPrestiti() {
        System.out.println("getPrestiti");
        GestionePrestiti instance = new GestionePrestiti();
        List<Prestito> expResult = null;
        List<Prestito> result = instance.getPrestiti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrestitiAttivi method, of class GestionePrestiti.
     */
    @Test
    public void testGetPrestitiAttivi() {
        System.out.println("getPrestitiAttivi");
        GestionePrestiti instance = new GestionePrestiti();
        List<Prestito> expResult = null;
        List<Prestito> result = instance.getPrestitiAttivi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registraPrestito method, of class GestionePrestiti.
     */
    @Test
    public void testRegistraPrestito() {
        System.out.println("registraPrestito");
        Utente utente = null;
        Libro libro = null;
        LocalDate dataPrestito = null;
        LocalDate dataPrevistaRestituzione = null;
        GestionePrestiti instance = new GestionePrestiti();
        Prestito expResult = null;
        Prestito result = instance.registraPrestito(utente, libro, dataPrestito, dataPrevistaRestituzione);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registraRestituzione method, of class GestionePrestiti.
     */
    @Test
    public void testRegistraRestituzione() {
        System.out.println("registraRestituzione");
        Prestito prestito = null;
        LocalDate dataRestituzione = null;
        GestionePrestiti instance = new GestionePrestiti();
        instance.registraRestituzione(prestito, dataRestituzione);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contaPrestitiAttivi method, of class GestionePrestiti.
     */
    @Test
    public void testContaPrestitiAttivi() {
        System.out.println("contaPrestitiAttivi");
        Utente utente = null;
        GestionePrestiti instance = new GestionePrestiti();
        int expResult = 0;
        int result = instance.contaPrestitiAttivi(utente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of haPrestitiAttiviPer method, of class GestionePrestiti.
     */
    @Test
    public void testHaPrestitiAttiviPer() {
        System.out.println("haPrestitiAttiviPer");
        Utente utente = null;
        GestionePrestiti instance = new GestionePrestiti();
        boolean expResult = false;
        boolean result = instance.haPrestitiAttiviPer(utente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trovaPrestitoAttivo method, of class GestionePrestiti.
     */
    @Test
    public void testTrovaPrestitoAttivo() {
        System.out.println("trovaPrestitoAttivo");
        String matricola = "";
        long isbn = 0L;
        LocalDate dataInizio = null;
        GestionePrestiti instance = new GestionePrestiti();
        Prestito expResult = null;
        Prestito result = instance.trovaPrestitoAttivo(matricola, isbn, dataInizio);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
