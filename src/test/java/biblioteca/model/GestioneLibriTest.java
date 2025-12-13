package biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GestioneLibriTest {

    private GestioneLibri gestione;

    @BeforeEach
    void setUp() {
        gestione = new GestioneLibri();

        gestione.inserisciLibro(
                9788800000000L,
                "L'amica geniale",
                Arrays.asList("Elena Ferrante"),
                2011,
                5
        );

        gestione.inserisciLibro(
                9788800000001L,
                "Gomorra",
                Arrays.asList("Roberto Saviano"),
                2006,
                2
        );

        gestione.inserisciLibro(
                9788800000002L,
                "Napoli milionaria!",
                Arrays.asList("Eduardo De Filippo"),
                1945,
                3
        );
    }

    @Test
    void testCostruttoreVuoto() {
        GestioneLibri g = new GestioneLibri();
        assertNotNull(g.getLibri());
        assertTrue(g.getLibri().isEmpty());
    }

    @Test
    void testCostruttoreConTreeSetNull() {
        GestioneLibri g = new GestioneLibri(null);
        assertNotNull(g.getLibri());
        assertTrue(g.getLibri().isEmpty());
    }

    @Test
    void testGetLibri() {
        assertEquals(3, gestione.getLibri().size());
        assertNotNull(gestione.getLibri());
    }

    @Test
    void testGetLibriOrdinatiPerTitolo() {
        List<Libro> ordinati = gestione.getLibriOrdinatiPerTitolo();
        assertEquals(3, ordinati.size());

        assertEquals("Gomorra", ordinati.get(0).getTitolo());
        assertEquals("L'amica geniale", ordinati.get(1).getTitolo());
        assertEquals("Napoli milionaria!", ordinati.get(2).getTitolo());
    }

    @Test
    void testInserisciLibroNuovo() {
        Libro nuovo = gestione.inserisciLibro(
                9788800000003L,
                "Il nome della rosa",
                Arrays.asList("Umberto Eco"),
                1980,
                4
        );

        assertNotNull(nuovo);
        assertEquals(4, nuovo.getCopieTotali());
        assertEquals(4, nuovo.getCopieDisponibili());
        assertEquals(4, gestione.getLibri().size());
    }

    @Test
    void testInserisciLibroEsistente_incrementaCopie() {
        Libro prima = gestione.trovaLibro(9788800000001L);
        assertNotNull(prima);
        assertEquals(2, prima.getCopieTotali());
        assertEquals(2, prima.getCopieDisponibili());

        Libro aggiornato = gestione.inserisciLibro(
                9788800000001L,
                "Gomorra",
                Arrays.asList("Roberto Saviano"),
                2006,
                3
        );

        assertSame(prima, aggiornato);
        assertEquals(5, aggiornato.getCopieTotali());
        assertEquals(5, aggiornato.getCopieDisponibili());
        assertEquals(3, gestione.getLibri().size());
    }

    @Test
    void testInserisciLibro_copieNonValide_lanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () ->
                gestione.inserisciLibro(9788800000099L, "Test", Collections.emptyList(), 2000, 0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                gestione.inserisciLibro(9788800000098L, "Test", Collections.emptyList(), 2000, -2)
        );
    }

    @Test
    void testModificaLibro_modificaCampi() {
        Libro l = gestione.trovaLibro(9788800000002L);
        assertNotNull(l);

        gestione.modificaLibro(
                l,
                "Napoli milionaria! (Edizione Teatro)",
                Arrays.asList("Eduardo De Filippo", "Compagnia Napoletana"),
                1950,
                10
        );

        assertEquals("Napoli milionaria! (Edizione Teatro)", l.getTitolo());
        assertEquals(2, l.getAutori().size());
        assertEquals(1950, l.getAnnoPubblicazione());
        assertEquals(10, l.getCopieTotali());
    }

    @Test
    void testModificaLibro_null_nonFaNulla() {
        assertDoesNotThrow(() ->
                gestione.modificaLibro(null, "X", Arrays.asList("Y"), 2000, 1)
        );
        assertEquals(3, gestione.getLibri().size());
    }

    @Test
    void testEliminaLibro_valido_elimina() {
        assertNotNull(gestione.trovaLibro(9788800000000L));
        gestione.eliminaLibro("9788800000000");
        assertNull(gestione.trovaLibro(9788800000000L));
        assertEquals(2, gestione.getLibri().size());
    }

    @Test
    void testEliminaLibro_stringaVuota_oNull_nonFaNulla() {
        gestione.eliminaLibro(null);
        gestione.eliminaLibro("   ");
        assertEquals(3, gestione.getLibri().size());
    }

    @Test
    void testEliminaLibro_formatoNonValido_nonFaNulla() {
        gestione.eliminaLibro("abc");
        assertEquals(3, gestione.getLibri().size());
    }

    @Test
    void testCercaLibri_perIsbn() {
        TreeSet<Libro> trovati = gestione.cercaLibri("9788800000001", "", "");
        assertEquals(1, trovati.size());
        assertEquals(9788800000001L, trovati.first().getIsbn());
    }

    @Test
    void testCercaLibri_isbnNonValido_vieneIgnorato() {
        TreeSet<Libro> trovati = gestione.cercaLibri("NON_NUMERO", "", "");
        assertEquals(3, trovati.size());
    }

    @Test
    void testCercaLibri_perTitolo_parziale_caseInsensitive() {
        TreeSet<Libro> trovati = gestione.cercaLibri("", "gEnIaLe", "");
        assertEquals(1, trovati.size());
        assertEquals("L'amica geniale", trovati.first().getTitolo());
    }

    @Test
    void testCercaLibri_perAutore_parziale_caseInsensitive() {
        TreeSet<Libro> trovati = gestione.cercaLibri("", "", "sav");
        assertEquals(1, trovati.size());
        assertEquals("Gomorra", trovati.first().getTitolo());
    }

    @Test
    void testCercaLibri_conTuttiFiltri() {
        TreeSet<Libro> trovati = gestione.cercaLibri("9788800000002", "napoli", "eduardo");
        assertEquals(1, trovati.size());
        assertEquals(9788800000002L, trovati.first().getIsbn());
    }

    @Test
    void testCercaLibri_nessunRisultato() {
        TreeSet<Libro> trovati = gestione.cercaLibri("", "QuestoNonEsiste", "");
        assertTrue(trovati.isEmpty());
    }

    @Test
    void testTrovaLibro_esistente() {
        Libro l = gestione.trovaLibro(9788800000001L);
        assertNotNull(l);
        assertEquals("Gomorra", l.getTitolo());
    }

    @Test
    void testTrovaLibro_nonEsistente() {
        assertNull(gestione.trovaLibro(9788800000999L));
    }
}
