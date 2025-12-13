package biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GestioneUtentiTest {

    private GestioneUtenti gestione;

    @BeforeEach
    void setUp() {
        gestione = new GestioneUtenti();

        gestione.inserisciUtente(new Utente("0612700001", "Gennaro", "Esposito", "g.esposito@unisa.it"));
        gestione.inserisciUtente(new Utente("0612700002", "Carmela", "Russo", "c.russo@unisa.it"));
        gestione.inserisciUtente(new Utente("0612700003", "Ciro", "Iovine", "c.iovine@unisa.it"));
    }

    @Test
    void testCostruttoreVuoto() {
        GestioneUtenti g = new GestioneUtenti();
        assertNotNull(g.getUtenti());
        assertTrue(g.getUtenti().isEmpty());
    }

    @Test
    void testCostruttoreConTreeSetNull() {
        GestioneUtenti g = new GestioneUtenti(null);
        assertNotNull(g.getUtenti());
        assertTrue(g.getUtenti().isEmpty());
    }

    @Test
    void testGetUtenti() {
        assertNotNull(gestione.getUtenti());
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testInserisciUtente_ok() {
        gestione.inserisciUtente(new Utente("0612700004", "Assunta", "Vitale", "a.vitale@unisa.it"));
        assertEquals(4, gestione.getUtenti().size());
        assertNotNull(gestione.trovaUtente("0612700004"));
    }

    @Test
    void testInserisciUtente_null_nonFaNulla() {
        gestione.inserisciUtente(null);
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testTrovaUtente_esistente() {
        Utente u = gestione.trovaUtente("0612700002");
        assertNotNull(u);
        assertEquals("Carmela", u.getNome());
        assertEquals("Russo", u.getCognome());
    }

    @Test
    void testTrovaUtente_nonEsistente() {
        assertNull(gestione.trovaUtente("0612700999"));
    }

    @Test
    void testTrovaUtente_null() {
        assertNull(gestione.trovaUtente(null));
    }

    @Test
    void testModificaUtente_modificaCampi() {
        Utente mod = new Utente("0612700003", "Ciro", "Iovine", "vecchia@mail.it");
        mod.setNome("Ciro Antonio");
        mod.setCognome("Iovine");
        mod.setEmail("c.antonio@unisa.it");
        mod.setInBlacklist(true);

        gestione.modificaUtente(mod);

        Utente aggiornato = gestione.trovaUtente("0612700003");
        assertNotNull(aggiornato);
        assertEquals("Ciro Antonio", aggiornato.getNome());
        assertEquals("Iovine", aggiornato.getCognome());
        assertEquals("c.antonio@unisa.it", aggiornato.getEmail());
        assertTrue(aggiornato.isInBlacklist());
    }

    @Test
    void testModificaUtente_null_nonFaNulla() {
        assertDoesNotThrow(() -> gestione.modificaUtente(null));
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testModificaUtente_nonEsiste_nonFaNulla() {
        Utente mod = new Utente("0612709999", "Nunzia", "Capasso", "n.capasso@unisa.it");
        gestione.modificaUtente(mod);
        assertNull(gestione.trovaUtente("0612709999"));
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testEliminaUtente_stringaVuota_oNull_nonFaNulla() {
        gestione.eliminaUtente(null);
        gestione.eliminaUtente("   ");
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testEliminaUtente_nonEsiste_nonFaNulla() {
        gestione.eliminaUtente("0612700999");
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testEliminaUtente_senzaPrestiti_attivi_elimina() {
        assertNotNull(gestione.trovaUtente("0612700001"));
        gestione.eliminaUtente("0612700001");
        assertNull(gestione.trovaUtente("0612700001"));
        assertEquals(2, gestione.getUtenti().size());
    }

    @Test
    void testEliminaUtente_conPrestitiAttivi_nonElimina() {
        GestionePrestiti prestitiFinti = new GestionePrestitiFinta("0612700002");
        gestione.setGestionePrestiti(prestitiFinti);

        assertNotNull(gestione.trovaUtente("0612700002"));
        gestione.eliminaUtente("0612700002");

        assertNotNull(gestione.trovaUtente("0612700002"));
        assertEquals(3, gestione.getUtenti().size());
    }

    @Test
    void testCercaUtenti_perMatricola_matchEsatto() {
        TreeSet<Utente> trovati = gestione.cercaUtenti("0612700001", "", "");
        assertEquals(1, trovati.size());
        assertEquals("0612700001", trovati.first().getMatricola());
    }

    @Test
    void testCercaUtenti_perCognome_contains_caseInsensitive() {
        TreeSet<Utente> trovati = gestione.cercaUtenti("", "RUS", "");
        assertEquals(1, trovati.size());
        assertEquals("Russo", trovati.first().getCognome());
    }

    @Test
    void testCercaUtenti_perNome_contains_caseInsensitive() {
        TreeSet<Utente> trovati = gestione.cercaUtenti("", "", "ennar");
        assertEquals(1, trovati.size());
        assertEquals("Gennaro", trovati.first().getNome());
    }

    @Test
    void testCercaUtenti_tuttiVuoti_restituisceTutti() {
        TreeSet<Utente> trovati = gestione.cercaUtenti("", "", "");
        assertEquals(3, trovati.size());
    }

    @Test
    void testSetBlacklist_impostaTrue() {
        Utente u = gestione.trovaUtente("0612700003");
        assertNotNull(u);
        assertFalse(u.isInBlacklist());

        gestione.setBlacklist(u, true);
        assertTrue(u.isInBlacklist());
    }

    @Test
    void testSetBlacklist_utenteNull_nonFaNulla() {
        assertDoesNotThrow(() -> gestione.setBlacklist(null, true));
    }

    @Test
    void testSetGestionePrestiti() {
        GestionePrestiti finta = new GestionePrestitiFinta("0612700001");
        assertDoesNotThrow(() -> gestione.setGestionePrestiti(finta));
    }

    private static class GestionePrestitiFinta extends GestionePrestiti {
        private final String matricolaConPrestito;

        private GestionePrestitiFinta(String matricolaConPrestito) {
            this.matricolaConPrestito = matricolaConPrestito;
        }

        @Override
        public boolean haPrestitiAttiviPer(Utente u) {
            if (u == null || u.getMatricola() == null) return false;
            return u.getMatricola().trim().equals(matricolaConPrestito);
        }
    }
}
