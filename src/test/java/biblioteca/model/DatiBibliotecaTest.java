package biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatiBibliotecaTest {

    @Test
    @DisplayName("il costruttore vuoto deve inizializzare l'oggetto ma lasciare i campi null")
    void testCostruttoreDefault() {
        DatiBiblioteca dati = new DatiBiblioteca();

        assertNotNull(dati, "l'istanza di DatiBiblioteca non deve essere null");
        
        
        assertNull(dati.gestioneLibri, "gestioneLibri dovrebbe essere null");
        assertNull(dati.gestioneUtenti, "gestioneUtenti dovrebbe essere null");
        assertNull(dati.gestionePrestiti, "gestionePrestiti dovrebbe essere null");
        assertNull(dati.autenticazione, "autenticazione dovrebbe essere null");
    }

    @Test
    @DisplayName("il costruttore con parametri deve assegnare correttamente gli oggetti passati")
    void testCostruttoreParametrico() {
        
        GestioneLibri gl = new GestioneLibri();
        GestioneUtenti gu = new GestioneUtenti();
        GestionePrestiti gp = new GestionePrestiti();
        Autenticazione a = new Autenticazione();

   
        DatiBiblioteca dati = new DatiBiblioteca(gl, gu, gp, a);

        
        assertSame(gl, dati.gestioneLibri, "il campo gestioneLibri non corrisponde all'oggetto passato");
        assertSame(gu, dati.gestioneUtenti, "il campo gestioneUtenti non corrisponde all'oggetto passato");
        assertSame(gp, dati.gestionePrestiti, "il campo gestionePrestiti non corrisponde all'oggetto passato");
        assertSame(a, dati.autenticazione, "il campo autenticazione non corrisponde all'oggetto passato");
    }
}
