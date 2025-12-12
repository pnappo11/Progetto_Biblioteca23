/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.persistence;

import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
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
public class ArchivioFileTest {
    
    public ArchivioFileTest() {
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
     * Test of caricaLibri method, of class ArchivioFile.
     */
    @Test
    public void testCaricaLibri() {
        System.out.println("caricaLibri");
        ArchivioFile instance = null;
        GestioneLibri expResult = null;
        GestioneLibri result = instance.caricaLibri();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvaLibri method, of class ArchivioFile.
     */
    @Test
    public void testSalvaLibri() {
        System.out.println("salvaLibri");
        GestioneLibri gl = null;
        ArchivioFile instance = null;
        instance.salvaLibri(gl);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of caricaUtenti method, of class ArchivioFile.
     */
    @Test
    public void testCaricaUtenti() {
        System.out.println("caricaUtenti");
        ArchivioFile instance = null;
        GestioneUtenti expResult = null;
        GestioneUtenti result = instance.caricaUtenti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvaUtenti method, of class ArchivioFile.
     */
    @Test
    public void testSalvaUtenti() {
        System.out.println("salvaUtenti");
        GestioneUtenti gu = null;
        ArchivioFile instance = null;
        instance.salvaUtenti(gu);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of caricaPrestiti method, of class ArchivioFile.
     */
    @Test
    public void testCaricaPrestiti() {
        System.out.println("caricaPrestiti");
        ArchivioFile instance = null;
        GestionePrestiti expResult = null;
        GestionePrestiti result = instance.caricaPrestiti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvaPrestiti method, of class ArchivioFile.
     */
    @Test
    public void testSalvaPrestiti() {
        System.out.println("salvaPrestiti");
        GestionePrestiti gp = null;
        ArchivioFile instance = null;
        instance.salvaPrestiti(gp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of caricaAutenticazione method, of class ArchivioFile.
     */
    @Test
    public void testCaricaAutenticazione() {
        System.out.println("caricaAutenticazione");
        ArchivioFile instance = null;
        Autenticazione expResult = null;
        Autenticazione result = instance.caricaAutenticazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvaAutenticazione method, of class ArchivioFile.
     */
    @Test
    public void testSalvaAutenticazione() {
        System.out.println("salvaAutenticazione");
        Autenticazione a = null;
        ArchivioFile instance = null;
        instance.salvaAutenticazione(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
