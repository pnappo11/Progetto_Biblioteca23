/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.controller;

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
public class MainControllerTest {
    
    public MainControllerTest() {
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
     * Test of setPrestitiController method, of class MainController.
     */
    @Test
    public void testSetPrestitiController() {
        System.out.println("setPrestitiController");
        PrestitiController prestitiController = null;
        MainController instance = null;
        instance.setPrestitiController(prestitiController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of avvia method, of class MainController.
     */
    @Test
    public void testAvvia() {
        System.out.println("avvia");
        MainController instance = null;
        instance.avvia();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostraLogin method, of class MainController.
     */
    @Test
    public void testMostraLogin() {
        System.out.println("mostraLogin");
        MainController instance = null;
        instance.mostraLogin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostraMenu method, of class MainController.
     */
    @Test
    public void testMostraMenu() {
        System.out.println("mostraMenu");
        MainController instance = null;
        instance.mostraMenu();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostraMain method, of class MainController.
     */
    @Test
    public void testMostraMain() {
        System.out.println("mostraMain");
        int tabIndex = 0;
        MainController instance = null;
        instance.mostraMain(tabIndex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
