
package biblioteca.model;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionePrestitiTest {

    private GestionePrestiti gestionePrestiti;
    private Utente utenteTest;
    private Libro libroTest;

    @BeforeEach
    void setUp() {
        gestionePrestiti = new GestionePrestiti();
        utenteTest = new Utente("Giovanni", "Feola", "0612709854", "g.feola@unisa.it");
        List<String> autori = new ArrayList<>();
        autori.add("Autore 1");
        libroTest = new Libro(123456789L, "Libro Test", autori, 2023, 5);
    }

    @Test
    void testCostruttoreDefault() {
        assertNotNull(gestionePrestiti.getPrestiti());
        assertTrue(gestionePrestiti.getPrestiti().isEmpty());
    }

    @Test
    void testCostruttoreConParametri() {
        List<Prestito> listaPrestiti = new ArrayList<>();
        Prestito p = new Prestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30));
        listaPrestiti.add(p);

        GestionePrestiti gp = new GestionePrestiti(listaPrestiti);
        assertEquals(1, gp.getPrestiti().size());
    }

    @Test
    void testCostruttoreConParametriNull() {
        GestionePrestiti gp = new GestionePrestiti(null);
        assertNotNull(gp.getPrestiti());
        assertTrue(gp.getPrestiti().isEmpty());
    }

    @Test
    void testRegistraPrestitoSuccesso() {
        LocalDate dataPrestito = LocalDate.now();
        LocalDate dataRestituzione = dataPrestito.plusDays(30);

        Prestito prestito = gestionePrestiti.registraPrestito(utenteTest, libroTest, dataPrestito, dataRestituzione);

        assertNotNull(prestito);
        assertEquals(1, gestionePrestiti.getPrestiti().size());
        assertEquals(utenteTest, prestito.getUtente());
        assertEquals(libroTest, prestito.getLibro());
        assertEquals(4, libroTest.getCopieDisponibili());
    }

    
    @Test
    void testRegistraPrestitoParametriNull() {
        assertThrows(IllegalArgumentException.class, () ->
            gestionePrestiti.registraPrestito(null, libroTest, LocalDate.now(), LocalDate.now().plusDays(30))
        );
        assertThrows(IllegalArgumentException.class, () ->
            gestionePrestiti.registraPrestito(utenteTest, null, LocalDate.now(), LocalDate.now().plusDays(30))
        );
    }

    
    @Test
    void testRegistraPrestitoLibroNonDisponibile() {
        libroTest.setCopieDisponibili(0);
        assertThrows(IllegalStateException.class, () -> gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30))
        );
    }

    
    @Test
    void testRegistraPrestitoLimiteMassimoRaggiunto() {
        for (int i = 0; i < Utente.MAX_PRESTITI; i++) {
            Libro l = new Libro(100L + i, "Titolo " + i, new ArrayList<>(), 2000, 5);
            gestionePrestiti.registraPrestito(utenteTest, l, LocalDate.now(), LocalDate.now().plusDays(30));
        }

        assertEquals(Utente.MAX_PRESTITI, gestionePrestiti.contaPrestitiAttivi(utenteTest));

        assertThrows(IllegalStateException.class, () -> gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30))
        );
    }

    @Test
    void testGetPrestitiAttivi() {
        Prestito p1 = gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30));
        
        Utente utente2 = new Utente("Mirko", "Alessandrini", "0612709875", "m.alessandrini@unisa.it");
        Libro libro2 = new Libro(987654L, "Libro 2", new ArrayList<>(), 2022, 3);
        Prestito p2 = gestionePrestiti.registraPrestito(utente2, libro2, LocalDate.now(), LocalDate.now().plusDays(30));

        gestionePrestiti.registraRestituzione(p1, LocalDate.now());

        List<Prestito> attivi = gestionePrestiti.getPrestitiAttivi();
        
        assertEquals(1, attivi.size());
        assertTrue(attivi.contains(p2));
        assertFalse(attivi.contains(p1));
    }

    
    @Test
    void testRegistraRestituzione() {
        Prestito prestito = gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30));
        int copiePrimaRestituzione = libroTest.getCopieDisponibili();

        gestionePrestiti.registraRestituzione(prestito, LocalDate.now());

        assertNotNull(prestito.getDataRestituzione());
        assertFalse(prestito.isAttivo());
        assertEquals(copiePrimaRestituzione + 1, libroTest.getCopieDisponibili());
    }

    
    @Test
    void testRegistraRestituzioneNullOInattivo() {
        gestionePrestiti.registraRestituzione(null, LocalDate.now());

        Prestito prestito = gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30));
        gestionePrestiti.registraRestituzione(prestito, LocalDate.now());
        
        LocalDate dataPrima = prestito.getDataRestituzione();
        gestionePrestiti.registraRestituzione(prestito, LocalDate.now().plusDays(1));
        
        assertEquals(dataPrima, prestito.getDataRestituzione());
    }

    
    @Test
            
    void testContaPrestitiAttivi() {
        assertEquals(0, gestionePrestiti.contaPrestitiAttivi(utenteTest));

        gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30));
        assertEquals(1, gestionePrestiti.contaPrestitiAttivi(utenteTest));

        Utente altroUtente = new Utente("Andrea", "Merola", "0612709521", "a.merola@unisa.it");
        assertEquals(0, gestionePrestiti.contaPrestitiAttivi(altroUtente));
        
        assertEquals(0, gestionePrestiti.contaPrestitiAttivi(null));
    }

    @Test
    void testHaPrestitiAttiviPer() {
        assertFalse(gestionePrestiti.haPrestitiAttiviPer(utenteTest));

        gestionePrestiti.registraPrestito(utenteTest, libroTest, LocalDate.now(), LocalDate.now().plusDays(30));
        
        assertTrue(gestionePrestiti.haPrestitiAttiviPer(utenteTest));
    }

    @Test
    void testTrovaPrestitoAttivo() {
        LocalDate dataInizio = LocalDate.now();
        gestionePrestiti.registraPrestito(utenteTest, libroTest, dataInizio, dataInizio.plusDays(30));

        Prestito trovato = gestionePrestiti.trovaPrestitoAttivo(utenteTest.getMatricola(), libroTest.getIsbn(), dataInizio);
        
        assertNotNull(trovato);
        assertEquals(utenteTest, trovato.getUtente());
        assertEquals(libroTest, trovato.getLibro());
    }

    @Test
    void testTrovaPrestitoAttivoNonTrovato() {
        Prestito nonTrovato = gestionePrestiti.trovaPrestitoAttivo("MATRICOLA_INESISTENTE", 0L, LocalDate.now());
        assertNull(nonTrovato);
    }
}
