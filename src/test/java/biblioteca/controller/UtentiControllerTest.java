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
public class UtentiControllerTest {
    
    public UtentiControllerTest() {
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
     * Test of setPrestitiController method, of class UtentiController.
     */
    @Test
    public void testSetPrestitiController() {
        System.out.println("setPrestitiController");
        PrestitiController prestitiController = null;
        UtentiController instance = null;
        instance.setPrestitiController(prestitiController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiornaDaModel method, of class UtentiController.
     */
    @Test
    public void testAggiornaDaModel() {
        System.out.println("aggiornaDaModel");
        UtentiController instance = null;
        instance.aggiornaDaModel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
