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
    private final long ISBN_BASE = 9788800000000L;

    @BeforeEach
    void setUp() {
        List<String> autori = new ArrayList<>();
        autori.add("Elena Ferrante");
        autori.add("Roberto Saviano");
        libro = new Libro(ISBN_BASE, "L'amica geniale", autori, 2011, 5, 5);
    }

    @Test
    void testCostruttoreCompleto() {
        assertEquals(ISBN_BASE, libro.getIsbn());
        assertEquals("L'amica geniale", libro.getTitolo());
        assertEquals(2, libro.getAutori().size());
        assertEquals(2011, libro.getAnnoPubblicazione());
        assertEquals(5, libro.getCopieTotali());
        assertEquals(5, libro.getCopieDisponibili());
    }

    @Test
    void testCostruttoreParziale_copieDisponibiliUgualiTotali() {
        Libro l = new Libro(ISBN_BASE + 1, "Gomorra", null, 2006, 2);

        assertEquals(ISBN_BASE + 1, l.getIsbn());
        assertEquals("Gomorra", l.getTitolo());
        assertNotNull(l.getAutori());
        assertTrue(l.getAutori().isEmpty());
        assertEquals(2, l.getCopieTotali());
        assertEquals(2, l.getCopieDisponibili());
    }

    @Test
    void testGetIsbn() {
        assertEquals(ISBN_BASE, libro.getIsbn());
    }

    @Test
    void testSetGetTitolo() {
        libro.setTitolo("L'amica geniale (Edizione Napoli)");
        assertEquals("L'amica geniale (Edizione Napoli)", libro.getTitolo());
    }

    @Test
    void testSetGetAnnoPubblicazione() {
        libro.setAnnoPubblicazione(2012);
        assertEquals(2012, libro.getAnnoPubblicazione());
    }

    @Test
    void testGetAutori() {
        assertEquals(2, libro.getAutori().size());
        assertEquals("Elena Ferrante", libro.getAutori().get(0));
    }

    @Test
    void testSetGetAutori() {
        List<String> nuovi = Arrays.asList("Umberto Eco");
        libro.setAutori(nuovi);

        assertEquals(1, libro.getAutori().size());
        assertEquals("Umberto Eco", libro.getAutori().get(0));
    }

    @Test
    void testSetAutoriNull_diventaListaVuota() {
        libro.setAutori(null);
        assertNotNull(libro.getAutori());
        assertTrue(libro.getAutori().isEmpty());
    }

    @Test
    void testGetAutoriUnmodifiable() {
        List<String> a = libro.getAutori();
        assertThrows(UnsupportedOperationException.class, () -> a.add("Gennaro Esposito"));
    }

    @Test
    void testAutori_copiaDifensiva_costruttore() {
        List<String> autori = new ArrayList<>();
        autori.add("Eduardo De Filippo");

        Libro l = new Libro(ISBN_BASE + 2, "Napoli milionaria!", autori, 1945, 3, 3);
        autori.add("Aggiunto dopo");

        assertEquals(1, l.getAutori().size());
        assertEquals("Eduardo De Filippo", l.getAutori().get(0));
    }

    @Test
    void testAutori_copiaDifensiva_setter() {
        List<String> autori = new ArrayList<>();
        autori.add("Totò");

        libro.setAutori(autori);
        autori.add("Aggiunto dopo");

        assertEquals(1, libro.getAutori().size());
        assertEquals("Totò", libro.getAutori().get(0));
    }

    @Test
    void testGetNumAutori() {
        assertEquals("2", libro.getNumAutori());

        libro.setAutori(Collections.emptyList());
        assertEquals("0", libro.getNumAutori());
    }

    @Test
    void testGetCopieTotali() {
        assertEquals(5, libro.getCopieTotali());
    }

    @Test
    void testSetCopieTotali_aumenta_nonToccaDisponibiliSeGiaValide() {
        libro.setCopieTotali(10);
        assertEquals(10, libro.getCopieTotali());
        assertEquals(5, libro.getCopieDisponibili());
    }

    @Test
    void testSetCopieTotali_riduce_tagliaDisponibiliSeSuperanoTotali() {
        libro.setCopieTotali(3);
        assertEquals(3, libro.getCopieTotali());
        assertEquals(3, libro.getCopieDisponibili());
    }

    @Test
    void testGetCopieDisponibili() {
        assertEquals(5, libro.getCopieDisponibili());
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
    void testDecrementaCopiaDisponibile_ok() {
        libro.decrementaCopiaDisponibile();
        assertEquals(4, libro.getCopieDisponibili());
    }

    @Test
    void testDecrementaCopiaDisponibile_quandoZero_lanciaEccezione() {
        libro.setCopieDisponibili(0);
        assertThrows(IllegalStateException.class, () -> libro.decrementaCopiaDisponibile());
    }

    @Test
    void testIncrementaCopiaDisponibile_ok() {
        libro.setCopieDisponibili(3);
        libro.incrementaCopiaDisponibile();
        assertEquals(4, libro.getCopieDisponibili());
    }

    @Test
    void testIncrementaCopiaDisponibile_seGiaAlMassimo_nonSuperaTotali() {
        libro.setCopieDisponibili(5);
        libro.incrementaCopiaDisponibile();
        assertEquals(5, libro.getCopieDisponibili());
    }

    @Test
    void testCompareTo() {
        Libro l1 = new Libro(9788800000000L, "L'amica geniale", null, 2011, 1);
        Libro l2 = new Libro(9788800000001L, "Gomorra", null, 2006, 1);

        assertTrue(l1.compareTo(l2) < 0);
        assertTrue(l2.compareTo(l1) > 0);

        Libro stessoIsbn = new Libro(9788800000000L, "Titolo diverso", null, 1990, 1);
        assertEquals(0, l1.compareTo(stessoIsbn));
    }

    @Test
    void testEquals() {
        Libro stessoIsbn = new Libro(ISBN_BASE, "Altro titolo", null, 2000, 1);
        Libro diversoIsbn = new Libro(ISBN_BASE + 5, "Il nome della rosa", null, 1980, 1);

        assertEquals(libro, stessoIsbn);
        assertNotEquals(libro, diversoIsbn);
        assertNotEquals(libro, null);
        assertNotEquals(libro, new Object());
    }

    @Test
    void testHashCode() {
        Libro stessoIsbn = new Libro(ISBN_BASE, "Qualcosa", null, 2010, 1);
        assertEquals(libro.hashCode(), stessoIsbn.hashCode());
    }

    @Test
    void testToString() {
        String s = libro.toString();
        assertNotNull(s);
        assertTrue(s.contains(String.valueOf(ISBN_BASE)));
        assertTrue(s.contains("L'amica geniale"));
        assertTrue(s.contains("copieTotali"));
        assertTrue(s.contains("copieDisponibili"));
    }
}
