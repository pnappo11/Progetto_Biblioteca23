
package biblioteca.model;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GestioneUtentiTest {

    private GestioneUtenti gestioneUtenti;
    private Utente utenteTest;
    private GestionePrestitiStub gestionePrestitiStub;

    /**
     * Classe Stub interna per simulare il comportamento di GestionePrestiti
     * senza dover istanziare la classe reale o dipendere dai suoi dati.
     */
    static class GestionePrestitiStub extends GestionePrestiti {
        private boolean hasPrestitiAttivi;

        void setHasPrestitiAttivi(boolean hasPrestitiAttivi) {
            this.hasPrestitiAttivi = hasPrestitiAttivi;
        }

        @Override
        public boolean haPrestitiAttiviPer(Utente utente) {
            return hasPrestitiAttivi;
        }
    }

    @BeforeEach
    void setUp() {
        gestioneUtenti = new GestioneUtenti();
        
        // Inizializza lo stub e lo imposta nel gestore
        gestionePrestitiStub = new GestionePrestitiStub();
        gestioneUtenti.setGestionePrestiti(gestionePrestitiStub);

        // Creazione Utente con i 4 attributi corretti: matricola, nome, cognome, email
        utenteTest = new Utente("M12345", "Mario", "Rossi", "mario.rossi@email.it");
    }

    @Test
    void testCostruttoreDefault() {
        assertNotNull(gestioneUtenti.getUtenti());
        assertTrue(gestioneUtenti.getUtenti().isEmpty());
    }

    
    @Test
    void testCostruttoreConParametri() {
        TreeSet<Utente> utentiIniziali = new TreeSet<>();
        utentiIniziali.add(utenteTest);
        
        GestioneUtenti gu = new GestioneUtenti(utentiIniziali);
        assertEquals(1, gu.getUtenti().size());
        assertEquals(utenteTest, gu.trovaUtente("M12345"));
    }

    
    @Test
    void testInserisciUtente() {
        gestioneUtenti.inserisciUtente(utenteTest);
        
        assertEquals(1, gestioneUtenti.getUtenti().size());
        Utente trovato = gestioneUtenti.trovaUtente("M12345");
        assertNotNull(trovato);
        assertEquals("Mario", trovato.getNome());
    }

    
    @Test
    void testInserisciUtenteNull() {
        gestioneUtenti.inserisciUtente(null);
        assertTrue(gestioneUtenti.getUtenti().isEmpty());
    }

    
    @Test
    void testInserisciUtenteDuplicato() {
        gestioneUtenti.inserisciUtente(utenteTest);
        // Utente con stessa matricola (per il TreeSet/Comparable conta la matricola)
        Utente duplicato = new Utente("M12345", "Luigi", "Bianchi", "luigi@email.it");
        
        gestioneUtenti.inserisciUtente(duplicato);
        
        assertEquals(1, gestioneUtenti.getUtenti().size());
        // Il TreeSet non sostituisce l'elemento esistente se compareTo restituisce 0
        assertEquals("Mario", gestioneUtenti.trovaUtente("M12345").getNome());
    }

    
    @Test
    void testModificaUtente() {
        gestioneUtenti.inserisciUtente(utenteTest);

        // Creo un oggetto con la stessa matricola ma dati diversi
        Utente modifiche = new Utente("M12345", "Giovanni", "Verdi", "giovanni@email.it");
        modifiche.setInBlacklist(true);

        gestioneUtenti.modificaUtente(modifiche);

        Utente aggiornato = gestioneUtenti.trovaUtente("M12345");
        assertEquals("Giovanni", aggiornato.getNome());
        assertEquals("Verdi", aggiornato.getCognome());
        assertEquals("giovanni@email.it", aggiornato.getEmail());
        assertTrue(aggiornato.isInBlacklist());
    }

    
    @Test
    void testModificaUtenteInesistente() {
        Utente fantasma = new Utente("999999", "Fantasma", "Casper", "ghost@email.it");
        gestioneUtenti.modificaUtente(fantasma);
        
        assertNull(gestioneUtenti.trovaUtente("999999"));
        assertTrue(gestioneUtenti.getUtenti().isEmpty());
    }

    
    @Test
    void testEliminaUtenteSenzaPrestiti() {
        gestioneUtenti.inserisciUtente(utenteTest);
        
        // Simulo che l'utente NON abbia prestiti attivi
        gestionePrestitiStub.setHasPrestitiAttivi(false);

        gestioneUtenti.eliminaUtente("M12345");
        
        assertTrue(gestioneUtenti.getUtenti().isEmpty());
    }

    
    @Test
    void testEliminaUtenteConPrestitiAttivi() {
        gestioneUtenti.inserisciUtente(utenteTest);
        
        // Simulo che l'utente ABBIA prestiti attivi
        gestionePrestitiStub.setHasPrestitiAttivi(true);

        gestioneUtenti.eliminaUtente("M12345");
        
        // L'utente non deve essere stato eliminato
        assertEquals(1, gestioneUtenti.getUtenti().size());
        assertNotNull(gestioneUtenti.trovaUtente("M12345"));
    }

    @Test
    void testEliminaUtenteNullOVuoto() {
        gestioneUtenti.inserisciUtente(utenteTest);
        
        gestioneUtenti.eliminaUtente(null);
        assertEquals(1, gestioneUtenti.getUtenti().size());

        gestioneUtenti.eliminaUtente("");
        assertEquals(1, gestioneUtenti.getUtenti().size());
        
        gestioneUtenti.eliminaUtente("   ");
        assertEquals(1, gestioneUtenti.getUtenti().size());
    }

    @Test
    void testCercaUtenti() {
        Utente u1 = new Utente("A001", "Anna", "Neri", "anna@mail.it");
        Utente u2 = new Utente("B002", "Marco", "Neri", "marco@mail.it");
        Utente u3 = new Utente("C003", "Luca", "Rossi", "luca@mail.it");

        gestioneUtenti.inserisciUtente(u1);
        gestioneUtenti.inserisciUtente(u2);
        gestioneUtenti.inserisciUtente(u3);

        // Ricerca per cognome "Neri"
        TreeSet<Utente> risultatiCognome = gestioneUtenti.cercaUtenti(null, "neri", null);
        assertEquals(2, risultatiCognome.size());

        // Ricerca per matricola esatta
        TreeSet<Utente> risultatiMatricola = gestioneUtenti.cercaUtenti("C003", null, null);
        assertEquals(1, risultatiMatricola.size());
        assertEquals("Luca", risultatiMatricola.first().getNome());

        // Ricerca combinata (nome e cognome)
        TreeSet<Utente> risultatiCombinati = gestioneUtenti.cercaUtenti(null, "rossi", "luca");
        assertEquals(1, risultatiCombinati.size());
    }

    @Test
    void testTrovaUtente() {
        gestioneUtenti.inserisciUtente(utenteTest);
        
        Utente trovato = gestioneUtenti.trovaUtente("M12345");
        assertNotNull(trovato);
        assertEquals("M12345", trovato.getMatricola());

        Utente nonTrovato = gestioneUtenti.trovaUtente("NON_ESISTE");
        assertNull(nonTrovato);
    }

    @Test
    void testSetBlacklist() {
        gestioneUtenti.inserisciUtente(utenteTest);
        assertFalse(utenteTest.isInBlacklist());

        gestioneUtenti.setBlacklist(utenteTest, true);
        assertTrue(utenteTest.isInBlacklist());

        gestioneUtenti.setBlacklist(utenteTest, false);
        assertFalse(utenteTest.isInBlacklist());
    }
}
