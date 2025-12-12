package biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    private Libro libro;
    private final long ISBN_DEFAULT = 1234567890123L;

    @BeforeEach
    void setUp() {
        List<String> autori = new ArrayList<>();
        autori.add("Autore 1");
        autori.add("Autore 2");
        libro = new Libro(ISBN_DEFAULT, "Titolo Test", autori, 2023, 5, 5);
    }

    @Test
    void testCostruttoreCompleto() {
        assertEquals(ISBN_DEFAULT, libro.getIsbn());
        assertEquals("Titolo Test", libro.getTitolo());
        assertEquals(2, libro.getAutori().size());
        assertEquals(2023, libro.getAnnoPubblicazione());
        assertEquals(5, libro.getCopieTotali());
        assertEquals(5, libro.getCopieDisponibili());
    }

    @Test
    void testCostruttoreParziale() {
        Libro libroParziale = new Libro(999L, "Titolo 2", null, 2020, 10);
        
        assertEquals(999L, libroParziale.getIsbn());
        assertEquals(10, libroParziale.getCopieTotali());
        assertEquals(10, libroParziale.getCopieDisponibili());
        assertNotNull(libroParziale.getAutori());
        assertTrue(libroParziale.getAutori().isEmpty());
    }

    
    @Test
    void testSetGetTitolo() {
        libro.setTitolo("Nuovo Titolo");
        assertEquals("Nuovo Titolo", libro.getTitolo());
    }

    
    @Test
    void testSetGetAnnoPubblicazione() {
        libro.setAnnoPubblicazione(1999);
        assertEquals(1999, libro.getAnnoPubblicazione());
    }

    
    @Test
    void testSetGetAutori() {
        List<String> nuoviAutori = Arrays.asList("Nuovo Autore");
        libro.setAutori(nuoviAutori);
        
        assertEquals(1, libro.getAutori().size());
        assertEquals("Nuovo Autore", libro.getAutori().get(0));
    }

    
    @Test
    void testSetAutoriNull() {
        libro.setAutori(null);
        assertNotNull(libro.getAutori());
        assertTrue(libro.getAutori().isEmpty());
    }

    
    @Test
    void testGetAutoriUnmodifiable() {
        List<String> autori = libro.getAutori();
        assertThrows(UnsupportedOperationException.class, () -> autori.add("Nuovo"));
    }

    
    @Test
    void testGetNumAutori() {
        assertEquals("2", libro.getNumAutori());
        libro.setAutori(Collections.emptyList());
        assertEquals("0", libro.getNumAutori());
    }

    
    @Test
    void testSetCopieTotaliStandard() {
        libro.setCopieTotali(10);
        assertEquals(10, libro.getCopieTotali());
        assertEquals(5, libro.getCopieDisponibili());
    }

    
    @Test
    void testSetCopieTotaliRiduzione() {
        libro.setCopieTotali(3);
        assertEquals(3, libro.getCopieTotali());
        assertEquals(3, libro.getCopieDisponibili());
    }

    @Test
    void testSetCopieDisponibili() {
        libro.setCopieDisponibili(2);
        assertEquals(2, libro.getCopieDisponibili());
    }

    
    @Test
    void testIsDisponibile() {
        assertTrue(libro.isDisponibile());
        
        libro.setCopieDisponibili(0);
        assertFalse(libro.isDisponibile());
    }

    
    @Test
    void testDecrementaCopiaDisponibileSuccesso() {
        libro.decrementaCopiaDisponibile();
        assertEquals(4, libro.getCopieDisponibili());
    }

    
    @Test
    void testDecrementaCopiaDisponibileErrore() {
        libro.setCopieDisponibili(0);
        assertThrows(IllegalStateException.class, () -> libro.decrementaCopiaDisponibile());
    }

    
    @Test
    void testIncrementaCopiaDisponibileSuccesso() {
        libro.setCopieDisponibili(3);
        libro.incrementaCopiaDisponibile();
        assertEquals(4, libro.getCopieDisponibili());
    }

    
    @Test
    void testIncrementaCopiaDisponibileLimiteRaggiunto() {
        libro.setCopieDisponibili(5);
        libro.incrementaCopiaDisponibile();
        assertEquals(5, libro.getCopieDisponibili());
    }

    
    @Test
    void testEquals() {
        Libro stessoIsbn = new Libro(ISBN_DEFAULT, "Altro Titolo", null, 2000, 1);
        Libro diversoIsbn = new Libro(11111L, "Titolo Test", null, 2023, 5);

        assertEquals(libro, stessoIsbn);
        assertNotEquals(libro, diversoIsbn);
        assertNotEquals(libro, null);
        assertNotEquals(libro, new Object());
    }

    
    @Test
    void testHashCode() {
        Libro stessoIsbn = new Libro(ISBN_DEFAULT, "Altro", null, 2000, 1);
        assertEquals(libro.hashCode(), stessoIsbn.hashCode());
    }

    
    @Test
    void testCompareTo() {
        Libro libroMinore = new Libro(100L, "A", null, 2000, 1);
        Libro libroMaggiore = new Libro(200L, "B", null, 2000, 1);

        assertTrue(libroMinore.compareTo(libroMaggiore) < 0);
        assertTrue(libroMaggiore.compareTo(libroMinore) > 0);
        assertEquals(0, libroMinore.compareTo(new Libro(100L, "C", null, 2000, 1)));
    }

    
    @Test
    void testToString() {
        String s = libro.toString();
        assertNotNull(s);
        assertTrue(s.contains(String.valueOf(ISBN_DEFAULT)));
        assertTrue(s.contains("Titolo Test"));
    }
}
