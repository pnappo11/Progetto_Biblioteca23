/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

import java.time.LocalDate;
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
public class PrestitoTest {
    
    public PrestitoTest() {
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
     * Test of getUtente method, of class Prestito.
     */
    @Test
    public void testGetUtente() {
        System.out.println("getUtente");
        Prestito instance = null;
        Utente expResult = null;
        Utente result = instance.getUtente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLibro method, of class Prestito.
     */
    @Test
    public void testGetLibro() {
        System.out.println("getLibro");
        Prestito instance = null;
        Libro expResult = null;
        Libro result = instance.getLibro();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataInizio method, of class Prestito.
     */
    @Test
    public void testGetDataInizio() {
        System.out.println("getDataInizio");
        Prestito instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getDataInizio();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataPrevistaRestituzione method, of class Prestito.
     */
    @Test
    public void testGetDataPrevistaRestituzione() {
        System.out.println("getDataPrevistaRestituzione");
        Prestito instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getDataPrevistaRestituzione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataRestituzione method, of class Prestito.
     */
    @Test
    public void testGetDataRestituzione() {
        System.out.println("getDataRestituzione");
        Prestito instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getDataRestituzione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDataRestituzione method, of class Prestito.
     */
    @Test
    public void testSetDataRestituzione() {
        System.out.println("setDataRestituzione");
        LocalDate dataRestituzione = null;
        Prestito instance = null;
        instance.setDataRestituzione(dataRestituzione);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAttivo method, of class Prestito.
     */
    @Test
    public void testIsAttivo() {
        System.out.println("isAttivo");
        Prestito instance = null;
        boolean expResult = false;
        boolean result = instance.isAttivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInRitardo method, of class Prestito.
     */
    @Test
    public void testIsInRitardo() {
        System.out.println("isInRitardo");
        LocalDate oggi = null;
        Prestito instance = null;
        boolean expResult = false;
        boolean result = instance.isInRitardo(oggi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Prestito.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        Prestito instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Prestito.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Prestito instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
