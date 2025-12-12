
package biblioteca.model;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GestioneLibriTest {

    private GestioneLibri gestioneLibri;

    @BeforeEach
    void setUp() {
        gestioneLibri = new GestioneLibri();
    }

    @Test
    void testCostruttoreDefault() {
        assertNotNull(gestioneLibri.getLibri());
        assertTrue(gestioneLibri.getLibri().isEmpty());
    }

    @Test
    void testCostruttoreConParametri() {
        TreeSet<Libro> setIniziale = new TreeSet<>();
        setIniziale.add(new Libro(12345L, "Titolo", new ArrayList<>(), 2020, 5));
        
        GestioneLibri gl = new GestioneLibri(setIniziale);
        assertEquals(1, gl.getLibri().size());
    }

    @Test
    void testCostruttoreConParametriNull() {
        GestioneLibri gl = new GestioneLibri(null);
        assertNotNull(gl.getLibri());
        assertTrue(gl.getLibri().isEmpty());
    }

    @Test
    void testInserisciLibroNuovo() {
        long isbn = 978880000L;
        String titolo = "Java Programming";
        List<String> autori = Arrays.asList("Rossi", "Bianchi");
        int anno = 2023;
        int copie = 10;

        Libro libro = gestioneLibri.inserisciLibro(isbn, titolo, autori, anno, copie);

        assertNotNull(libro);
        assertEquals(1, gestioneLibri.getLibri().size());
        assertEquals(isbn, libro.getIsbn());
        assertEquals(titolo, libro.getTitolo());
        assertEquals(copie, libro.getCopieTotali());
        assertEquals(copie, libro.getCopieDisponibili()); 
    }

    @Test
    void testInserisciLibroEsistenteIncrementaCopie() {
        long isbn = 978880000L;
        gestioneLibri.inserisciLibro(isbn, "Titolo 1", new ArrayList<>(), 2020, 5);

        Libro aggiornato = gestioneLibri.inserisciLibro(isbn, "Titolo 1", new ArrayList<>(), 2020, 3);

        assertEquals(1, gestioneLibri.getLibri().size());
        assertEquals(8, aggiornato.getCopieTotali()); 
        assertEquals(8, aggiornato.getCopieDisponibili()); 
    }

    @Test
    void testInserisciLibroCopieNegative() {
        assertThrows(IllegalArgumentException.class, () -> 
            gestioneLibri.inserisciLibro(111L, "Test", new ArrayList<>(), 2021, 0)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            gestioneLibri.inserisciLibro(111L, "Test", new ArrayList<>(), 2021, -5)
        );
    }

    @Test
    void testModificaLibro() {
        long isbn = 12345L;
        Libro libro = gestioneLibri.inserisciLibro(isbn, "Vecchio Titolo", new ArrayList<>(), 2000, 1);
        
        String nuovoTitolo = "Nuovo Titolo";
        List<String> nuoviAutori = Arrays.asList("Nuovo Autore");
        int nuovoAnno = 2024;
        int nuoveCopie = 5;

        gestioneLibri.modificaLibro(libro, nuovoTitolo, nuoviAutori, nuovoAnno, nuoveCopie);

        assertEquals(nuovoTitolo, libro.getTitolo());
        assertEquals(nuoviAutori, libro.getAutori());
        assertEquals(nuovoAnno, libro.getAnnoPubblicazione());
        assertEquals(nuoveCopie, libro.getCopieTotali());
    }

    @Test
    void testModificaLibroNull() {
        assertDoesNotThrow(() -> 
            gestioneLibri.modificaLibro(null, "A", new ArrayList<>(), 2020, 1)
        );
    }

    @Test
    void testEliminaLibroEsistente() {
        long isbn = 11111L;
        gestioneLibri.inserisciLibro(isbn, "Da Eliminare", new ArrayList<>(), 2020, 1);
        
        gestioneLibri.eliminaLibro(String.valueOf(isbn));
        
        assertTrue(gestioneLibri.getLibri().isEmpty());
    }

    @Test
    void testEliminaLibroNonEsistente() {
        gestioneLibri.inserisciLibro(123L, "Da Tenere", new ArrayList<>(), 2020, 1);
        
        gestioneLibri.eliminaLibro("99999");
        
        assertEquals(1, gestioneLibri.getLibri().size());
    }

    @Test
    void testEliminaLibroInputNonValido() {
        gestioneLibri.inserisciLibro(123L, "Libro", new ArrayList<>(), 2020, 1);
        
        gestioneLibri.eliminaLibro(null);
        assertEquals(1, gestioneLibri.getLibri().size());

        gestioneLibri.eliminaLibro("");
        assertEquals(1, gestioneLibri.getLibri().size());

        gestioneLibri.eliminaLibro("abc");
        assertEquals(1, gestioneLibri.getLibri().size());
    }

    @Test
    void testTrovaLibro() {
        long isbn = 555L;
        gestioneLibri.inserisciLibro(isbn, "Cercami", new ArrayList<>(), 2021, 1);

        Libro trovato = gestioneLibri.trovaLibro(isbn);
        assertNotNull(trovato);
        assertEquals("Cercami", trovato.getTitolo());

        Libro nonTrovato = gestioneLibri.trovaLibro(999L);
        assertNull(nonTrovato);
    }

    @Test
    void testGetLibriOrdinatiPerTitolo() {
        gestioneLibri.inserisciLibro(1L, "Zebra", new ArrayList<>(), 2020, 1);
        gestioneLibri.inserisciLibro(2L, "albero", new ArrayList<>(), 2020, 1);
        gestioneLibri.inserisciLibro(3L, "Casa", new ArrayList<>(), 2020, 1);

        List<Libro> ordinati = gestioneLibri.getLibriOrdinatiPerTitolo();

        assertEquals(3, ordinati.size());
        assertEquals("albero", ordinati.get(0).getTitolo()); 
        assertEquals("Casa", ordinati.get(1).getTitolo());
        assertEquals("Zebra", ordinati.get(2).getTitolo());
    }

    @Test
    void testCercaLibriPerIsbn() {
        gestioneLibri.inserisciLibro(100L, "Libro A", new ArrayList<>(), 2020, 1);
        gestioneLibri.inserisciLibro(200L, "Libro B", new ArrayList<>(), 2020, 1);

        TreeSet<Libro> result = gestioneLibri.cercaLibri("100", null, null);
        assertEquals(1, result.size());
        assertEquals(100L, result.first().getIsbn());
    }

    @Test
    void testCercaLibriPerTitoloParziale() {
        gestioneLibri.inserisciLibro(1L, "Harry Potter 1", new ArrayList<>(), 2000, 1);
        gestioneLibri.inserisciLibro(2L, "Harry Potter 2", new ArrayList<>(), 2002, 1);
        gestioneLibri.inserisciLibro(3L, "Lord of the Rings", new ArrayList<>(), 1954, 1);

        TreeSet<Libro> result = gestioneLibri.cercaLibri(null, "potter", null);
        assertEquals(2, result.size());
    }

    @Test
    void testCercaLibriPerAutore() {
        gestioneLibri.inserisciLibro(1L, "Libro 1", Arrays.asList("Tolkien"), 1954, 1);
        gestioneLibri.inserisciLibro(2L, "Libro 2", Arrays.asList("Rowling"), 2000, 1);

        TreeSet<Libro> result = gestioneLibri.cercaLibri(null, null, "tolkien");
        assertEquals(1, result.size());
        assertEquals("Libro 1", result.first().getTitolo());
    }

    @Test
    void testCercaLibriInputVuoti() {
        gestioneLibri.inserisciLibro(1L, "A", new ArrayList<>(), 2000, 1);
        gestioneLibri.inserisciLibro(2L, "B", new ArrayList<>(), 2000, 1);

        TreeSet<Libro> result = gestioneLibri.cercaLibri(null, "", "   ");
        assertEquals(2, result.size());
    }
}
