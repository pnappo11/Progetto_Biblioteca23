
package biblioteca.model;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    private Utente utente;
    private final String MATRICOLA = "123456";

    @BeforeEach
    void setUp() {
        utente = new Utente(MATRICOLA, "Mario", "Rossi", "mario.rossi@test.com");
    }

    @Test
            
    void testCostruttore() {
        assertEquals(MATRICOLA, utente.getMatricola());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("mario.rossi@test.com", utente.getEmail());
        assertFalse(utente.isInBlacklist());
        assertNotNull(utente.getPrestitiAttivi());
        assertEquals(0, utente.getNumPrestitiAttivi());
    }
    @Test
            
    void testSetters() {
        utente.setNome("Luigi");
        assertEquals("Luigi", utente.getNome());

        utente.setCognome("Bianchi");
        assertEquals("Bianchi", utente.getCognome());

        utente.setEmail("luigi.bianchi@test.com");
        assertEquals("luigi.bianchi@test.com", utente.getEmail());

        utente.setInBlacklist(true);
        assertTrue(utente.isInBlacklist());
        
    
    }    
    
    
    
    @Test
    void testAggiungiPrestito() {
        Libro libro = new Libro(1L, "Titolo", new ArrayList<>(), 2020, 5, 5);
        Prestito prestito = new Prestito(utente, libro, LocalDate.now(), LocalDate.now().plusDays(30));

        utente.aggiungiPrestito(prestito);

        assertEquals(1, utente.getNumPrestitiAttivi());
        assertTrue(utente.getPrestitiAttivi().contains(prestito));
    }

    
    @Test
    void testAggiungiPrestitoNull() {
        utente.aggiungiPrestito(null);
        assertEquals(0, utente.getNumPrestitiAttivi());
    }

    
    @Test
    void testAggiungiPrestitoDuplicato() {
        Libro libro = new Libro(1L, "Titolo", new ArrayList<>(), 2020, 5, 5);
        Prestito prestito = new Prestito(utente, libro, LocalDate.now(), LocalDate.now().plusDays(30));

        utente.aggiungiPrestito(prestito);
        utente.aggiungiPrestito(prestito);

        assertEquals(1, utente.getNumPrestitiAttivi());
    }

    
    @Test
    void testRimuoviPrestito() {
        Libro libro = new Libro(1L, "Titolo", new ArrayList<>(), 2020, 5, 5);
        Prestito prestito = new Prestito(utente, libro, LocalDate.now(), LocalDate.now().plusDays(30));

        utente.aggiungiPrestito(prestito);
        utente.rimuoviPrestito(prestito);

        assertEquals(0, utente.getNumPrestitiAttivi());
        assertFalse(utente.getPrestitiAttivi().contains(prestito));
    }

    
    @Test
    void testGetPrestitiAttiviUnmodifiable() {
        List<Prestito> prestiti = utente.getPrestitiAttivi();
        assertThrows(UnsupportedOperationException.class, () -> prestiti.add(null));
    }

    
    @Test
    void testCanNuovoPrestitoSuccesso() {
        assertTrue(utente.canNuovoPrestito());
    }

    
    @Test
    void testCanNuovoPrestitoBlacklist() {
        utente.setInBlacklist(true);
        assertFalse(utente.canNuovoPrestito());
    }

    
    @Test
    void testCanNuovoPrestitoMaxRaggiunto() {
        for (int i = 0; i < Utente.MAX_PRESTITI; i++) {
            Libro l = new Libro((long) i, "T", new ArrayList<>(), 2000, 1, 1);
            Prestito p = new Prestito(utente, l, LocalDate.now(), LocalDate.now().plusDays(30));
            utente.aggiungiPrestito(p);
        }

        assertEquals(Utente.MAX_PRESTITI, utente.getNumPrestitiAttivi());
        assertFalse(utente.canNuovoPrestito());
    }

    
    @Test
    void testEquals() {
        Utente stessoUtente = new Utente(MATRICOLA, "Altro", "Nome", "email");
        Utente diversoUtente = new Utente("999999", "Mario", "Rossi", "email");

        assertEquals(utente, stessoUtente);
        assertNotEquals(utente, diversoUtente);
        assertNotEquals(utente, null);
        assertNotEquals(utente, new Object());
    }

    
    @Test
    void testHashCode() {
        Utente stessoUtente = new Utente(MATRICOLA, "X", "Y", "Z");
        assertEquals(utente.hashCode(), stessoUtente.hashCode());
    }

    
    @Test
    void testCompareTo() {
        Utente u1 = new Utente("A100", "N", "C", "E");
        Utente u2 = new Utente("B100", "N", "C", "E");
        Utente u3 = new Utente("A100", "X", "Y", "Z");

        assertTrue(u1.compareTo(u2) < 0);
        assertTrue(u2.compareTo(u1) > 0);
        assertEquals(0, u1.compareTo(u3));
    }

    
    @Test
    void testCompareToNull() {
        assertEquals(1, utente.compareTo(null));
        
        Utente utenteNullMatricola = new Utente(null, "A", "B", "C");
        assertEquals(1, utente.compareTo(utenteNullMatricola));
        assertEquals(-1, utenteNullMatricola.compareTo(utente));
    }

    
    @Test
    void testToString() {
        String s = utente.toString();
        assertNotNull(s);
        assertTrue(s.contains(MATRICOLA));
        assertTrue(s.contains("Mario"));
        assertTrue(s.contains("Rossi"));
    }
    
}
