package biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutenticazioneTest {

    private Autenticazione autenticazione;
    // L'hash di default "8c6976e5..." corrisponde alla stringa "admin"
    private static final String PASSWORD_DEFAULT = "admin";

    @BeforeEach
    void setUp() {
        // Inizializza una nuova istanza prima di ogni test
        autenticazione = new Autenticazione();
    }

    @Test
    @DisplayName("Il costruttore deve inizializzare l'hash con il valore di default")
    void testCostruttore() {
        assertNotNull(autenticazione.getPasswordHash(), "L'hash della password non dovrebbe essere nullo");
        // Verifica che l'hash iniziale corrisponda all'hash di "admin"
        assertEquals(Autenticazione.calcolaHash(PASSWORD_DEFAULT), autenticazione.getPasswordHash());
    }

    @Test
    @DisplayName("Login deve restituire true con la password corretta")
    void testLoginSuccesso() {
        boolean risultato = autenticazione.login(PASSWORD_DEFAULT);
        assertTrue(risultato, "Il login dovrebbe riuscire con la password di default");
    }

    @Test
    @DisplayName("Login deve restituire false con una password errata")
    void testLoginFallimento() {
        boolean risultato = autenticazione.login("passwordSbagliata");
        assertFalse(risultato, "Il login dovrebbe fallire con una password errata");
    }

    @Test
    @DisplayName("CambiaPassword deve aggiornare l'hash se la vecchia password è corretta")
    void testCambiaPasswordSuccesso() {
        String nuovaPassword = "nuovaPasswordSegreta";
        
        // Esegue il cambio password
        autenticazione.cambiaPassword(PASSWORD_DEFAULT, nuovaPassword);

        // Verifica che la vecchia password non funzioni più
        assertFalse(autenticazione.login(PASSWORD_DEFAULT), "La vecchia password non dovrebbe più funzionare");
        
        // Verifica che la nuova password funzioni
        assertTrue(autenticazione.login(nuovaPassword), "La nuova password dovrebbe permettere il login");
        
        // Verifica che l'hash interno sia stato aggiornato correttamente
        assertEquals(Autenticazione.calcolaHash(nuovaPassword), autenticazione.getPasswordHash());
    }

    @Test
    @DisplayName("CambiaPassword deve lanciare eccezione se la vecchia password è errata")
    void testCambiaPasswordFallimento() {
        String passwordErrata = "nonSonoAdmin";
        String nuovaPassword = "nuovaPassword";

        // Verifica che venga lanciata l'eccezione attesa
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            autenticazione.cambiaPassword(passwordErrata, nuovaPassword);
        });

        assertEquals("Vecchia password non corretta", exception.getMessage());

        // Verifica che la password non sia stata cambiata
        assertTrue(autenticazione.login(PASSWORD_DEFAULT), "La password non dovrebbe cambiare se la vecchia password era errata");
    }

    @Test
    @DisplayName("CalcolaHash deve generare l'hash SHA-256 corretto")
    void testCalcolaHash() {
        String input = "test";
        // Hash SHA-256 noto per la stringa "test"
        String expectedHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        
        String actualHash = Autenticazione.calcolaHash(input);
        
        assertEquals(expectedHash, actualHash, "Il calcolo dell'hash SHA-256 non è corretto");
    }
}
