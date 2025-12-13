
package biblioteca.model;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    private Utente utente;
    private final String MATRICOLA = "0612709423";

    @BeforeEach
    void setUp() {
        utente = new Utente(MATRICOLA, "Mario", "Rossi", "m.rossi@unisa.it");
    }

    @Test
            
    void testCostruttore() {
        assertEquals(MATRICOLA, utente.getMatricola());
        assertEquals("Mario", utente.getNome());
        assertEquals("Rossi", utente.getCognome());
        assertEquals("m.rossi@unisa.it", utente.getEmail());
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

        utente.setEmail("l.bianchi@unisa.it");
        assertEquals("l.bianchi@unisa.it", utente.getEmail());

        utente.setInBlacklist(true);
        assertTrue(utente.isInBlacklist());
        
    
    }    
    
    
    
    @Test
    void testAggiungiPrestito() {
        Libro libro = new Libro(1L, "La storia del mocho", new ArrayList<>(), 2020, 5, 5);
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
        Libro libro = new Libro(1L, "La storia del mocho", new ArrayList<>(), 2020, 5, 5);
        Prestito prestito = new Prestito(utente, libro, LocalDate.now(), LocalDate.now().plusDays(30));

        utente.aggiungiPrestito(prestito);
        utente.aggiungiPrestito(prestito);

        assertEquals(1, utente.getNumPrestitiAttivi());
    }

    
    @Test
    void testRimuoviPrestito() {
        Libro libro = new Libro(1L, "La storia del mocho", new ArrayList<>(), 2020, 5, 5);
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
            Libro l = new Libro((long) i, "Torno a casa", new ArrayList<>(), 2000, 1, 1);
            Prestito p = new Prestito(utente, l, LocalDate.now(), LocalDate.now().plusDays(30));
            utente.aggiungiPrestito(p);
        }

        assertEquals(Utente.MAX_PRESTITI, utente.getNumPrestitiAttivi());
        assertFalse(utente.canNuovoPrestito());
    }

    
    @Test
    void testEquals() {
        Utente stessoUtente = new Utente(MATRICOLA, "Giovanni", "Guercia", "g.guercia@unisa.it");
        Utente diversoUtente = new Utente("0612709412", "Mario", "Belli", "m.belli@unisa.it");

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
        Utente u1 = new Utente("0612708415", "Nicola", "Ventrone", "n.ventrone@unisa.it");
        Utente u2 = new Utente("0612708453", "Giovanni", "Bonaccini", "g.bonaccini@unisa.it");
        Utente u3 = new Utente("0612708415", "Andrea", "Muzi", "a.muzi@unisa.it");

        assertTrue(u1.compareTo(u2) < 0);
        assertTrue(u2.compareTo(u1) > 0);
        assertEquals(0, u1.compareTo(u3));
    }

    
    @Test
    void testCompareToNull() {
        assertEquals(1, utente.compareTo(null));
        
        Utente utenteNullMatricola = new Utente(null, "Gennaro", "LaPuzza", "g.LaPuzza@unisa.it");
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
