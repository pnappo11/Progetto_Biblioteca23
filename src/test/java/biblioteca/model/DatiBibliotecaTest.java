package biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatiBibliotecaTest {

    @Test
    @DisplayName("Il costruttore vuoto deve inizializzare l'oggetto ma lasciare i campi null")
    void testCostruttoreDefault() {
        DatiBiblioteca dati = new DatiBiblioteca();

        assertNotNull(dati, "L'istanza di DatiBiblioteca non deve essere null");
        
        
        assertNull(dati.gestioneLibri, "GestioneLibri dovrebbe essere null");
        assertNull(dati.gestioneUtenti, "GestioneUtenti dovrebbe essere null");
        assertNull(dati.gestionePrestiti, "GestionePrestiti dovrebbe essere null");
        assertNull(dati.autenticazione, "Autenticazione dovrebbe essere null");
    }

    @Test
    @DisplayName("Il costruttore con parametri deve assegnare correttamente gli oggetti passati")
    void testCostruttoreParametrico() {
        
        GestioneLibri gl = new GestioneLibri();
        GestioneUtenti gu = new GestioneUtenti();
        GestionePrestiti gp = new GestionePrestiti();
        Autenticazione a = new Autenticazione();

   
        DatiBiblioteca dati = new DatiBiblioteca(gl, gu, gp, a);

        
        assertSame(gl, dati.gestioneLibri, "Il campo gestioneLibri non corrisponde all'oggetto passato");
        assertSame(gu, dati.gestioneUtenti, "Il campo gestioneUtenti non corrisponde all'oggetto passato");
        assertSame(gp, dati.gestionePrestiti, "Il campo gestionePrestiti non corrisponde all'oggetto passato");
        assertSame(a, dati.autenticazione, "Il campo autenticazione non corrisponde all'oggetto passato");
    }
}
