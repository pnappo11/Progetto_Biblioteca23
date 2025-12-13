package biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Test della classe GestionePrestiti.
 */
class GestionePrestitiTest {

    private GestionePrestiti gestionePrestiti;

    private Utente utente1;
    private Utente utente2;

    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    void setUp() {
        gestionePrestiti = new GestionePrestiti();

        utente1 = new Utente("0612700101", "Gennaro", "Esposito", "g.esposito@unisa.it");
        utente2 = new Utente("0612700102", "Nunzia", "Capasso", "n.capasso@unisa.it");

        libro1 = new Libro(9788800000000L, "L'amica geniale", new ArrayList<String>(), 2011, 2, 2);
        libro2 = new Libro(9788800000001L, "Gomorra", new ArrayList<String>(), 2006, 1, 1);
    }

    @Test
    void testGetPrestiti_listaNonModificabile() {
        assertThrows(UnsupportedOperationException.class, () -> gestionePrestiti.getPrestiti().add(null));
    }

    @Test
    void testGetPrestitiAttivi_inizioVuoto() {
        assertTrue(gestionePrestiti.getPrestitiAttivi().isEmpty());
    }

    @Test
    void testRegistraPrestito_ok_aggiornaTutto() {
        LocalDate oggi = LocalDate.of(2025, 12, 1);
        LocalDate prevista = LocalDate.of(2025, 12, 15);

        Prestito p = gestionePrestiti.registraPrestito(utente1, libro1, oggi, prevista);

        assertNotNull(p);
        assertEquals(1, gestionePrestiti.getPrestiti().size());
        assertEquals(1, gestionePrestiti.getPrestitiAttivi().size());

        assertEquals(1, libro1.getCopieDisponibili());
        assertEquals(1, gestionePrestiti.contaPrestitiAttivi(utente1));
        assertTrue(utente1.getPrestitiAttivi().contains(p));
    }

    @Test
    void testRegistraPrestito_utenteNull_lancia() {
        assertThrows(IllegalArgumentException.class, () ->
                gestionePrestiti.registraPrestito(null, libro1, LocalDate.now(), LocalDate.now().plusDays(7))
        );
    }

    @Test
    void testRegistraPrestito_libroNull_lancia() {
        assertThrows(IllegalArgumentException.class, () ->
                gestionePrestiti.registraPrestito(utente1, null, LocalDate.now(), LocalDate.now().plusDays(7))
        );
    }

    @Test
    void testRegistraPrestito_libroNonDisponibile_lancia() {
        libro2.setCopieDisponibili(0);
        assertThrows(IllegalStateException.class, () ->
                gestionePrestiti.registraPrestito(utente1, libro2, LocalDate.now(), LocalDate.now().plusDays(7))
        );
    }

    @Test
    void testRegistraPrestito_superaMassimo_lancia() {
        LocalDate oggi = LocalDate.of(2025, 12, 1);
        LocalDate prevista = LocalDate.of(2025, 12, 20);

        List<Libro> tantiLibri = new ArrayList<>();
        for (int i = 0; i < Utente.MAX_PRESTITI + 1; i++) {
            tantiLibri.add(new Libro(
                    9788800000100L + i,
                    "Titolo " + (i + 1),
                    new ArrayList<String>(),
                    2000 + (i % 20),
                    1,
                    1
            ));
        }

        for (int i = 0; i < Utente.MAX_PRESTITI; i++) {
            gestionePrestiti.registraPrestito(utente1, tantiLibri.get(i), oggi, prevista);
        }

        assertEquals(Utente.MAX_PRESTITI, gestionePrestiti.contaPrestitiAttivi(utente1));

        assertThrows(IllegalStateException.class, () ->
                gestionePrestiti.registraPrestito(utente1, tantiLibri.get(Utente.MAX_PRESTITI), oggi, prevista)
        );
    }

    @Test
    void testRegistraRestituzione_ok_rendePrestitoNonAttivo() {
        LocalDate inizio = LocalDate.of(2025, 12, 1);
        LocalDate prevista = LocalDate.of(2025, 12, 10);

        Prestito p = gestionePrestiti.registraPrestito(utente1, libro1, inizio, prevista);

        assertEquals(1, libro1.getCopieDisponibili());
        assertEquals(1, gestionePrestiti.getPrestitiAttivi().size());

        gestionePrestiti.registraRestituzione(p, LocalDate.of(2025, 12, 5));

        assertEquals(2, libro1.getCopieDisponibili());
        assertTrue(gestionePrestiti.getPrestitiAttivi().isEmpty());
        assertEquals(0, gestionePrestiti.contaPrestitiAttivi(utente1));
        assertFalse(utente1.getPrestitiAttivi().contains(p));
    }

    @Test
    void testRegistraRestituzione_null_nonFaNulla() {
        assertDoesNotThrow(() -> gestionePrestiti.registraRestituzione(null, LocalDate.now()));
    }

    @Test
    void testRegistraRestituzione_suPrestitoGiaRestituito_nonDoppiaIncremento() {
        LocalDate inizio = LocalDate.of(2025, 12, 1);
        LocalDate prevista = LocalDate.of(2025, 12, 10);

        Libro l = new Libro(9788800000999L, "Il mare non bagna Napoli", new ArrayList<String>(), 1953, 1, 1);
        Prestito p = gestionePrestiti.registraPrestito(utente2, l, inizio, prevista);

        assertEquals(0, l.getCopieDisponibili());

        gestionePrestiti.registraRestituzione(p, LocalDate.of(2025, 12, 2));
        assertEquals(1, l.getCopieDisponibili());

        gestionePrestiti.registraRestituzione(p, LocalDate.of(2025, 12, 3));
        assertEquals(1, l.getCopieDisponibili());
    }

    @Test
    void testContaPrestitiAttivi_eHaPrestitiAttiviPer() {
        assertEquals(0, gestionePrestiti.contaPrestitiAttivi(utente1));
        assertFalse(gestionePrestiti.haPrestitiAttiviPer(utente1));

        Prestito p = gestionePrestiti.registraPrestito(
                utente1,
                libro1,
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 10)
        );

        assertEquals(1, gestionePrestiti.contaPrestitiAttivi(utente1));
        assertTrue(gestionePrestiti.haPrestitiAttiviPer(utente1));

        gestionePrestiti.registraRestituzione(p, LocalDate.of(2025, 12, 2));

        assertEquals(0, gestionePrestiti.contaPrestitiAttivi(utente1));
        assertFalse(gestionePrestiti.haPrestitiAttiviPer(utente1));
    }

    @Test
    void testTrovaPrestitoAttivo_trovato() {
        LocalDate inizio = LocalDate.of(2025, 12, 1);
        LocalDate prevista = LocalDate.of(2025, 12, 10);

        Prestito p = gestionePrestiti.registraPrestito(utente1, libro1, inizio, prevista);

        Prestito trovato = gestionePrestiti.trovaPrestitoAttivo(
                utente1.getMatricola(),
                libro1.getIsbn(),
                inizio
        );

        assertNotNull(trovato);
        assertEquals(p, trovato);
    }

    @Test
    void testTrovaPrestitoAttivo_nonTrovato() {
        LocalDate inizio = LocalDate.of(2025, 12, 1);

        Prestito trovato = gestionePrestiti.trovaPrestitoAttivo(
                "0612709999",
                9788800000000L,
                inizio
        );

        assertNull(trovato);
    }
}
