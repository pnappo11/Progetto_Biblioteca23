package biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatiBibliotecaTest {

    @Test
    @DisplayName("Il costruttore di default crea un oggetto non null")
    void testCostruttoreDefault() {
        DatiBiblioteca dati = new DatiBiblioteca();

        assertNotNull(dati);
        assertNull(dati.gestioneLibri);
        assertNull(dati.gestioneUtenti);
        assertNull(dati.gestionePrestiti);
        assertNull(dati.autenticazione);
    }

    @Test
    @DisplayName("Il costruttore parametrico assegna correttamente i riferimenti")
    void testCostruttoreParametrico() {
        GestioneLibri gl = new GestioneLibri();
        GestioneUtenti gu = new GestioneUtenti();
        GestionePrestiti gp = new GestionePrestiti();
        Autenticazione a = new Autenticazione();

        DatiBiblioteca dati = new DatiBiblioteca(gl, gu, gp, a);

        assertNotNull(dati);
        assertSame(gl, dati.gestioneLibri);
        assertSame(gu, dati.gestioneUtenti);
        assertSame(gp, dati.gestionePrestiti);
        assertSame(a, dati.autenticazione);
    }

    @Test
    @DisplayName("Il costruttore accetta parametri null senza crashare")
    void testCostruttoreConParametriNull() {
        DatiBiblioteca dati = null;
        
        try {
            dati = new DatiBiblioteca(null, null, null, null);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(dati);
        assertNull(dati.gestioneLibri);
        assertNull(dati.gestioneUtenti);
        assertNull(dati.gestionePrestiti);
        assertNull(dati.autenticazione);
    }
    
    @Test
    @DisplayName("Modifica dei campi pubblici con valori null")
    void testModificaCampiPubblici() {
        DatiBiblioteca dati = new DatiBiblioteca(new GestioneLibri(), new GestioneUtenti(), new GestionePrestiti(), new Autenticazione());
        
        dati.gestioneLibri = null;
        
        assertNull(dati.gestioneLibri);
        assertNotNull(dati.gestioneUtenti);
    }
}