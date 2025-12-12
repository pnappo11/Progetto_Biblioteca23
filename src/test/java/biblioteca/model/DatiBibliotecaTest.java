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
        
        // Verifica che i campi siano null (comportamento predefinito)
        assertNull(dati.gestioneLibri, "GestioneLibri dovrebbe essere null");
        assertNull(dati.gestioneUtenti, "GestioneUtenti dovrebbe essere null");
        assertNull(dati.gestionePrestiti, "GestionePrestiti dovrebbe essere null");
        assertNull(dati.autenticazione, "Autenticazione dovrebbe essere null");
    }

    @Test
    @DisplayName("Il costruttore con parametri deve assegnare correttamente gli oggetti passati")
    void testCostruttoreParametrico() {
        /* * Creiamo le istanze reali delle classi dipendenti.
         * NOTA: Se queste classi (GestioneLibri, ecc.) hanno bisogno di argomenti complessi
         * nei loro costruttori, puoi modificarle qui sotto. 
         * Al momento assumiamo che abbiano un costruttore vuoto o che possiamo crearle facilmente.
         */
        GestioneLibri gl = new GestioneLibri();
        GestioneUtenti gu = new GestioneUtenti();
        GestionePrestiti gp = new GestionePrestiti();
        Autenticazione a = new Autenticazione();

        // Passiamo queste istanze al costruttore di DatiBiblioteca
        DatiBiblioteca dati = new DatiBiblioteca(gl, gu, gp, a);

        // Verifichiamo che i campi di DatiBiblioteca puntino esattamente agli oggetti creati sopra
        assertSame(gl, dati.gestioneLibri, "Il campo gestioneLibri non corrisponde all'oggetto passato");
        assertSame(gu, dati.gestioneUtenti, "Il campo gestioneUtenti non corrisponde all'oggetto passato");
        assertSame(gp, dati.gestionePrestiti, "Il campo gestionePrestiti non corrisponde all'oggetto passato");
        assertSame(a, dati.autenticazione, "Il campo autenticazione non corrisponde all'oggetto passato");
    }
}
