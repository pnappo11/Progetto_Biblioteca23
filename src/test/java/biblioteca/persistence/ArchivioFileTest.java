
package biblioteca.persistence;

import biblioteca.model.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioFileTest {

    @TempDir
    Path tempDir;

    private ArchivioFile archivioFile;
    private String percorsoTest;

    @BeforeEach
    void setUp() {
        percorsoTest = tempDir.toString();
        archivioFile = new ArchivioFile(percorsoTest);
    }

    @Test
    void testCaricaLibriFileNonEsistente() {
        GestioneLibri gl = archivioFile.caricaLibri();
        assertNotNull(gl);
        assertTrue(gl.getLibri().isEmpty());
    }

    
    @Test
    void testSalvaECaricaLibri() {
        GestioneLibri gl = new GestioneLibri();
        Libro libro = new Libro(123L, "Titolo", new ArrayList<>(), 2020, 5, 5);
        gl.inserisciLibro(libro.getIsbn(), libro.getTitolo(), libro.getAutori(), libro.getAnnoPubblicazione(), libro.getCopieTotali());

        archivioFile.salvaLibri(gl);

        GestioneLibri caricato = archivioFile.caricaLibri();
        assertNotNull(caricato);
        assertEquals(1, caricato.getLibri().size());
        assertEquals("Titolo", caricato.trovaLibro(123L).getTitolo());
    }

    
    @Test
    void testCaricaUtentiFileNonEsistente() {
        GestioneUtenti gu = archivioFile.caricaUtenti();
        assertNotNull(gu);
        assertTrue(gu.getUtenti().isEmpty());
    }

    
    @Test
    void testSalvaECaricaUtenti() {
        GestioneUtenti gu = new GestioneUtenti();
        Utente utente = new Utente("M1", "Mario", "Rossi", "email@test.com");
        gu.inserisciUtente(utente);

        archivioFile.salvaUtenti(gu);

        GestioneUtenti caricato = archivioFile.caricaUtenti();
        assertNotNull(caricato);
        assertEquals(1, caricato.getUtenti().size());
        assertEquals("Mario", caricato.trovaUtente("M1").getNome());
    }

    
    @Test
    void testCaricaPrestitiFileNonEsistente() {
        GestionePrestiti gp = archivioFile.caricaPrestiti();
        assertNotNull(gp);
        assertTrue(gp.getPrestiti().isEmpty());
    }

    
    @Test
    void testSalvaECaricaPrestiti() {
        
        GestionePrestiti gp = new GestionePrestiti();
     
        
        archivioFile.salvaPrestiti(gp);

        GestionePrestiti caricato = archivioFile.caricaPrestiti();
        assertNotNull(caricato);
        assertTrue(caricato.getPrestiti().isEmpty());
    }

    
    @Test
    void testCaricaAutenticazioneFileNonEsistente() {
        Autenticazione a = archivioFile.caricaAutenticazione();
        assertNotNull(a);
      
        assertEquals(new Autenticazione().getPasswordHash(), a.getPasswordHash());
    }

    
    @Test
    void testSalvaECaricaAutenticazione() {
        Autenticazione a = new Autenticazione();
        a.cambiaPassword("admin", "nuovaPassword123"); 

        archivioFile.salvaAutenticazione(a);

        Autenticazione caricata = archivioFile.caricaAutenticazione();
        assertNotNull(caricata);
        assertEquals(a.getPasswordHash(), caricata.getPasswordHash());
        assertTrue(caricata.login("nuovaPassword123"));
    }

    
    @Test
    void testCreazioneDirectorySeNonEsiste() {
        File dir = new File(percorsoTest, "sottocartella");
        ArchivioFile archivioNuovo = new ArchivioFile(dir.getAbsolutePath());
        
        GestioneLibri gl = new GestioneLibri();
        archivioNuovo.salvaLibri(gl);
        
        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());
        assertTrue(new File(dir, "libri.dat").exists());
    }

    
    @Test
    void testGestioneFileCorrotto() {
    
        GestioneLibri gl = new GestioneLibri();
        archivioFile.salvaLibri(gl);
        
        File fileLibri = new File(percorsoTest, "libri.dat");

        
        try (java.io.FileWriter writer = new java.io.FileWriter(fileLibri)) {
            writer.write("QUESTO NON E UN OGGETTO SERIALIZZATO MA SOLO TESTO");
        } catch (Exception e) {
            fail("Impossibile creare il file corrotto per il test: " + e.getMessage());
        }

      
        GestioneLibri risultato = archivioFile.caricaLibri();
        
      
        assertNotNull(risultato, "Il metodo dovrebbe restituire un oggetto vuoto (new), non null");
        assertTrue(risultato.getLibri().isEmpty(), "La lista libri dovrebbe essere vuota in caso di errore di lettura");
    }@Test
    void testSalvaOggettoNull() {
      
        archivioFile.salvaLibri(null);
        
        File file = new File(percorsoTest, "libri.dat");
        
        assertFalse(file.exists());
    }

    
    @Test
    void testCaricaOggettoTipoErrato() {
        GestioneLibri gl = new GestioneLibri();
    
        File fileLibri = new File(percorsoTest, "libri.dat");
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(fileLibri))) {
            oos.writeObject("SonoUnaStringaNonGestioneLibri");
        } catch (Exception e) {
            fail("Impossibile creare file di test");
        }

        
        GestioneLibri risultato = archivioFile.caricaLibri();
        
        assertNotNull(risultato);
        assertTrue(risultato.getLibri().isEmpty());
    }

    @Test
    void testEccezioneInSalvataggio() {

        
        File fileBloccante = new File(percorsoTest, "libri.dat");
        fileBloccante.mkdir(); 
        
        GestioneLibri gl = new GestioneLibri();
     
        assertDoesNotThrow(() -> archivioFile.salvaLibri(gl));
   
        assertTrue(fileBloccante.isDirectory());
    }
    
}
