package biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AutenticazioneTest {

    private Autenticazione autenticazione;
    private final String DEFAULT_PASSWORD_CHIARO = "admin";

    @BeforeEach
    void setUp() {
        autenticazione = new Autenticazione();
    }

    @Test
    void testCostruttoreDefault() {
        assertNotNull(autenticazione.getPasswordHash());
        assertTrue(autenticazione.login(DEFAULT_PASSWORD_CHIARO));
    }

    @Test
    void testLoginSuccesso() {
        assertTrue(autenticazione.login(DEFAULT_PASSWORD_CHIARO));
    }

    @Test
    void testLoginFallito() {
        assertFalse(autenticazione.login("passwordSbagliata"));
    }

    @Test
    void testCambiaPasswordSuccesso() {
        autenticazione.cambiaPassword(DEFAULT_PASSWORD_CHIARO, "nuovaPassword123");
        assertTrue(autenticazione.login("nuovaPassword123"));
        assertFalse(autenticazione.login(DEFAULT_PASSWORD_CHIARO));
    }

    @Test
    void testCambiaPasswordSenzaVecchiaPassword() {
        autenticazione.cambiaPassword(null, "nuovaPassword");
        assertTrue(autenticazione.login("nuovaPassword"));
    }

    @Test
    void testCambiaPasswordVecchiaErrata() {
        assertThrows(IllegalArgumentException.class, () -> 
            autenticazione.cambiaPassword("errata", "nuova")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void testCambiaPasswordNuovaNonValida(String nuovaPassword) {
        assertThrows(IllegalArgumentException.class, () -> 
            autenticazione.cambiaPassword(DEFAULT_PASSWORD_CHIARO, nuovaPassword)
        );
    }

    @Test
    void testCambiaPasswordNuovaNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            autenticazione.cambiaPassword(DEFAULT_PASSWORD_CHIARO, null)
        );
    }

    @Test
    void testCalcolaHashCoerenza() {
        String hash1 = Autenticazione.calcolaHash("test");
        String hash2 = Autenticazione.calcolaHash("test");
        assertEquals(hash1, hash2);
    }

    @Test
    void testCalcolaHashDifferenza() {
        String hash1 = Autenticazione.calcolaHash("password1");
        String hash2 = Autenticazione.calcolaHash("password2");
        assertNotEquals(hash1, hash2);
    }

    @Test
    void testGetPasswordHash() {
        String hashIniziale = autenticazione.getPasswordHash();
        assertEquals(Autenticazione.calcolaHash(DEFAULT_PASSWORD_CHIARO), hashIniziale);
    }
}