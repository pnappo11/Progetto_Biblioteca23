package biblioteca.persistence;

import biblioteca.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioFileTest {

    @TempDir(cleanup = CleanupMode.NEVER)
    Path tempDir;

    private ArchivioFile archivio;

    @BeforeEach
    void setUp() {
        archivio = new ArchivioFile(tempDir.toString());
    }

    @Test
    void caricaLibri_senzaFile_restituisceVuoto() {
        GestioneLibri gl = archivio.caricaLibri();
        assertNotNull(gl);
        assertTrue(gl.getLibri().isEmpty());
    }

    @Test
    void salvaECaricaLibri_ritrovoTitolo() {
        GestioneLibri gl = new GestioneLibri();
        gl.inserisciLibro(9788800000000L, "Odissea", new ArrayList<>(), 2020, 5);
        gl.inserisciLibro(9788800000001L, "Lilith", new ArrayList<>(), 2018, 2);

        archivio.salvaLibri(gl);

        GestioneLibri letto = archivio.caricaLibri();
        assertNotNull(letto);
        assertEquals("Odissea", letto.trovaLibro(9788800000000L).getTitolo());
        assertEquals("Lilith", letto.trovaLibro(9788800000001L).getTitolo());
    }

    @Test
    void caricaUtenti_senzaFile_restituisceVuoto() {
        GestioneUtenti gu = archivio.caricaUtenti();
        assertNotNull(gu);
        assertTrue(gu.getUtenti().isEmpty());
    }

    @Test
    void salvaECaricaUtenti_ritrovoNome() {
        GestioneUtenti gu = new GestioneUtenti();
        gu.inserisciUtente(new Utente("0612700001", "Matteo", "Menza", "m.menza@unisa.it"));
        gu.inserisciUtente(new Utente("0612700002", "Pasquale", "Sorbo", "p.sorbo@unisa.it"));

        archivio.salvaUtenti(gu);

        GestioneUtenti letto = archivio.caricaUtenti();
        assertNotNull(letto);
        assertEquals("Matteo", letto.trovaUtente("0612700001").getNome());
        assertEquals("Pasquale", letto.trovaUtente("0612700002").getNome());
    }

    @Test
    void caricaPrestiti_senzaFile_restituisceVuoto() {
        GestionePrestiti gp = archivio.caricaPrestiti();
        assertNotNull(gp);
        assertTrue(gp.getPrestiti().isEmpty());
    }

    @Test
    void salvaECaricaPrestiti_listaVuotaRestaVuota() {
        GestionePrestiti gp = new GestionePrestiti();
        archivio.salvaPrestiti(gp);

        GestionePrestiti letto = archivio.caricaPrestiti();
        assertNotNull(letto);
        assertTrue(letto.getPrestiti().isEmpty());
    }

    @Test
    void salvaECaricaAutenticazione_passwordFunziona() {
        Autenticazione a = new Autenticazione();
        a.cambiaPassword("admin", "nuovaPassword123");
        archivio.salvaAutenticazione(a);

        Autenticazione letta = archivio.caricaAutenticazione();
        assertNotNull(letta);
        assertTrue(letta.login("nuovaPassword123"));
    }

    @Test
    void caricaLibri_fileCorrotto_restituisceVuoto() {
        GestioneLibri gl = new GestioneLibri();
        archivio.salvaLibri(gl);

        File fileLibri = new File(tempDir.toFile(), "libri.dat");
        try (java.io.FileWriter w = new java.io.FileWriter(fileLibri)) {
            w.write("NON_SERIALIZZATO");
        } catch (Exception e) {
            fail();
        }

        GestioneLibri letto = archivio.caricaLibri();
        assertNotNull(letto);
        assertTrue(letto.getLibri().isEmpty());
    }

    @Test
    void salvaLibri_null_nonCreaFile() {
        archivio.salvaLibri(null);
        assertFalse(new File(tempDir.toFile(), "libri.dat").exists());
    }

    @Test
    void salvaLibri_seFileEUnaCartella_nonLancia() {
        File f = new File(tempDir.toFile(), "libri.dat");
        assertTrue(f.mkdir());

        assertDoesNotThrow(() -> archivio.salvaLibri(new GestioneLibri()));
        assertTrue(f.isDirectory());
    }
}
