package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.Libro;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LibriPanel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class LibriControllerTest {

    private GestioneLibri gestioneLibri;
    private StubLibriPanel view;
    private StubArchivioFile archivio;
    private LibriController controller;

    @BeforeAll
    static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
           
        }
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            gestioneLibri = new GestioneLibri();
            view = new StubLibriPanel();
            archivio = new StubArchivioFile();
            
            controller = new LibriController(gestioneLibri, view, archivio);
            latch.countDown();
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Timeout durante il setup del test");
        }
    }

    @Test
    void testInserimentoLibroValido() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> errore = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                view.simulaInput("9781234567890", "Libro Test", "Autore Test", "2020", "5");
                view.btnInserisci.fire();
            } catch (Throwable t) {
                errore.set(t);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Il test è andato in timeout");
        if (errore.get() != null) fail("Eccezione nel thread FX: " + errore.get());

        List<Libro> libri = new ArrayList<>(gestioneLibri.getLibri());
        assertEquals(1, libri.size(), "Dovrebbe esserci un libro inserito");
        assertEquals("Libro Test", libri.get(0).getTitolo());
        assertTrue(view.campiPuliti, "I campi dovrebbero essere puliti dopo l'inserimento");
    }

    @Test
    void testModificaLibro() throws InterruptedException {
   
        long isbn = 9781111111111L;
        gestioneLibri.inserisciLibro(isbn, "Titolo Vecchio", List.of("Autore Vecchio"), 2000, 3);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> errore = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
              
                controller.aggiornaDaModel();

                
                view.simulaInput(String.valueOf(isbn), "Titolo Modificato", "", "2025", "10");
                
                
                view.btnModifica.fire();
            } catch (Throwable t) {
                errore.set(t); 
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Il test di modifica è andato in timeout (possibile Alert bloccante)");
        if (errore.get() != null) fail("Errore durante la modifica: " + errore.get().getMessage());

        
        Libro libroModificato = gestioneLibri.getLibri().iterator().next();
        assertEquals("Titolo Modificato", libroModificato.getTitolo(), "Il titolo dovrebbe essere cambiato");
        assertEquals(2025, libroModificato.getAnnoPubblicazione(), "L'anno dovrebbe essere cambiato");
        assertEquals(10, libroModificato.getCopieTotali(), "Le copie dovrebbero essere cambiate");
        assertEquals("Autore Vecchio", libroModificato.getAutori().get(0), "L'autore NON dovrebbe essere cambiato");
    }

    @Test
    void testEliminaLibro() throws InterruptedException {
        gestioneLibri.inserisciLibro(9789999999999L, "Da Eliminare", List.of("X"), 2000, 1);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> errore = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                controller.aggiornaDaModel();
                
              
                if (!view.tabella.getItems().isEmpty()) {
                    view.tabella.getSelectionModel().select(0);
                    view.btnElimina.fire();
                } else {
                    throw new RuntimeException("La tabella è vuota, impossibile testare eliminazione");
                }
            } catch (Throwable t) {
                errore.set(t);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        if (errore.get() != null) fail("Errore eliminazione: " + errore.get());

        assertTrue(gestioneLibri.getLibri().isEmpty(), "Il libro doveva essere rimosso");
    }

    

    static class StubArchivioFile extends ArchivioFile {
        public StubArchivioFile() { super(null); }
        @Override public void salvaLibri(GestioneLibri gestione) { 
        }
    }

    static class StubLibriPanel extends LibriPanel {
        final Button btnInserisci = new Button("Inserisci");
        final Button btnModifica = new Button("Modifica");
        final Button btnElimina = new Button("Elimina");
        final Button btnCerca = new Button("Cerca");
        final TableView<ObservableList<String>> tabella = new TableView<>();

        // Campi di input simulati
        private String isbn = "";
        private String titolo = "";
        private String autore = "";
        private String anno = "";
        private String copie = "";
        
        boolean campiPuliti = false;

        public StubLibriPanel() {
            tabella.setItems(FXCollections.observableArrayList());
        }

        public void simulaInput(String i, String t, String a, String an, String c) {
            this.isbn = i;
            this.titolo = t;
            this.autore = a;
            this.anno = an;
            this.copie = c;
            this.campiPuliti = false;
        }

        // Getter mappati sui campi simulati
        @Override public Button getBottoneInserisci() { return btnInserisci; }
        @Override public Button getBottoneModifica() { return btnModifica; }
        @Override public Button getBottoneElimina() { return btnElimina; }
        @Override public Button getBottoneCerca() { return btnCerca; }
        @Override public TableView<ObservableList<String>> getTabellaLibri() { return tabella; }

        @Override public String getCodiceIsbnInserito() { return isbn; }
        @Override public String getTitoloInserito() { return titolo; }
        @Override public String getAutoreInserito() { return autore; }
        @Override public String getAnnoInserito() { return anno; }
        @Override public String getCopieTotaliInserite() { return copie; }

        @Override
        public void pulisciCampi() {
            this.isbn = "";
            this.titolo = "";
            this.autore = "";
            this.anno = "";
            this.copie = "";
            this.campiPuliti = true;
            this.tabella.getSelectionModel().clearSelection();
        }
        
        @Override
        public void setCampiDaRiga(ObservableList<String> riga) {
            if (riga != null && !riga.isEmpty()) {
                this.isbn = riga.get(0);
                this.titolo = riga.get(1);
            }
        }
    }
}