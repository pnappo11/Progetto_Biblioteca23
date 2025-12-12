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
public class LibroTest {
    
    public LibroTest() {
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
     * Test of getIsbn method, of class Libro.
     */
    @Test
    public void testGetIsbn() {
        System.out.println("getIsbn");
        Libro instance = null;
        long expResult = 0L;
        long result = instance.getIsbn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitolo method, of class Libro.
     */
    @Test
    public void testGetTitolo() {
        System.out.println("getTitolo");
        Libro instance = null;
        String expResult = "";
        String result = instance.getTitolo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTitolo method, of class Libro.
     */
    @Test
    public void testSetTitolo() {
        System.out.println("setTitolo");
        String titolo = "";
        Libro instance = null;
        instance.setTitolo(titolo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutori method, of class Libro.
     */
    @Test
    public void testGetAutori() {
        System.out.println("getAutori");
        Libro instance = null;
        List<String> expResult = null;
        List<String> result = instance.getAutori();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAutori method, of class Libro.
     */
    @Test
    public void testSetAutori() {
        System.out.println("setAutori");
        List<String> autori = null;
        Libro instance = null;
        instance.setAutori(autori);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnnoPubblicazione method, of class Libro.
     */
    @Test
    public void testGetAnnoPubblicazione() {
        System.out.println("getAnnoPubblicazione");
        Libro instance = null;
        int expResult = 0;
        int result = instance.getAnnoPubblicazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnnoPubblicazione method, of class Libro.
     */
    @Test
    public void testSetAnnoPubblicazione() {
        System.out.println("setAnnoPubblicazione");
        int annoPubblicazione = 0;
        Libro instance = null;
        instance.setAnnoPubblicazione(annoPubblicazione);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCopieTotali method, of class Libro.
     */
    @Test
    public void testGetCopieTotali() {
        System.out.println("getCopieTotali");
        Libro instance = null;
        int expResult = 0;
        int result = instance.getCopieTotali();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCopieTotali method, of class Libro.
     */
    @Test
    public void testSetCopieTotali() {
        System.out.println("setCopieTotali");
        int copieTotali = 0;
        Libro instance = null;
        instance.setCopieTotali(copieTotali);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCopieDisponibili method, of class Libro.
     */
    @Test
    public void testGetCopieDisponibili() {
        System.out.println("getCopieDisponibili");
        Libro instance = null;
        int expResult = 0;
        int result = instance.getCopieDisponibili();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCopieDisponibili method, of class Libro.
     */
    @Test
    public void testSetCopieDisponibili() {
        System.out.println("setCopieDisponibili");
        int copieDisponibili = 0;
        Libro instance = null;
        instance.setCopieDisponibili(copieDisponibili);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumAutori method, of class Libro.
     */
    @Test
    public void testGetNumAutori() {
        System.out.println("getNumAutori");
        Libro instance = null;
        String expResult = "";
        String result = instance.getNumAutori();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDisponibile method, of class Libro.
     */
    @Test
    public void testIsDisponibile() {
        System.out.println("isDisponibile");
        Libro instance = null;
        boolean expResult = false;
        boolean result = instance.isDisponibile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrementaCopiaDisponibile method, of class Libro.
     */
    @Test
    public void testDecrementaCopiaDisponibile() {
        System.out.println("decrementaCopiaDisponibile");
        Libro instance = null;
        instance.decrementaCopiaDisponibile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementaCopiaDisponibile method, of class Libro.
     */
    @Test
    public void testIncrementaCopiaDisponibile() {
        System.out.println("incrementaCopiaDisponibile");
        Libro instance = null;
        instance.incrementaCopiaDisponibile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Libro.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Libro other = null;
        Libro instance = null;
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Libro.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        Libro instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Libro.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Libro instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Libro.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Libro instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
