package biblioteca.controller;

import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Utente;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.UtentiPanel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class UtentiControllerTest {

    private GestioneUtenti gestioneUtenti;
    private StubGestionePrestiti gestionePrestiti;
    private StubUtentiPanel view;
    private StubArchivioFile archivio;
    private StubPrestitiController prestitiController;
    private UtentiController controller;

    @BeforeAll
    static void initToolkit() {
        // Inizializza il toolkit JavaFX una volta sola
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {}
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            gestioneUtenti = new GestioneUtenti();
            gestionePrestiti = new StubGestionePrestiti();
            view = new StubUtentiPanel();
            archivio = new StubArchivioFile();
            prestitiController = new StubPrestitiController();
            
            controller = new UtentiController(gestioneUtenti, gestionePrestiti, view, archivio);
            controller.setPrestitiController(prestitiController);
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timeout setup");
    }

    // --- HAPPY PATH TESTS ---

    @Test
    void testInserimentoUtenteValido() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> ex = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                view.simulaInput("M001", "Mario", "Rossi", "m.rossi@unisa.it");
                view.btnInserisci.fire();
            } catch (Throwable t) { ex.set(t); } 
            finally { latch.countDown(); }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertNull(ex.get());

        List<Utente> utenti = new ArrayList<>(gestioneUtenti.getUtenti());
        assertEquals(1, utenti.size());
        assertEquals("M001", utenti.get(0).getMatricola());
        assertEquals("m.rossi@unisa.it", utenti.get(0).getEmail());
        assertTrue(view.campiPuliti);
    }

    @Test
    void testModificaUtente() throws InterruptedException {
        // Setup: inserimento manuale nel model
        Utente u = new Utente("M001", "Mario", "Rossi", "m.rossi@unisa.it");
        gestioneUtenti.inserisciUtente(u);

        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Aggiorna view
            controller.aggiornaDaModel();
            
            // Simula modifica: cambia nome ed email
            view.simulaInput("M001", "Luigi", "", "l.rossi@unisa.it");
            view.btnModifica.fire();
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        Utente modificato = gestioneUtenti.trovaUtente("M001");
        assertEquals("Luigi", modificato.getNome()); // Cambiato
        assertEquals("Rossi", modificato.getCognome()); // Invariato (input vuoto)
        assertEquals("l.rossi@unisa.it", modificato.getEmail()); // Cambiato
    }

    @Test
    void testEliminaUtente() throws InterruptedException {
        gestioneUtenti.inserisciUtente(new Utente("M001", "Mario", "Rossi", "m.rossi@unisa.it"));

        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            controller.aggiornaDaModel();
            // Simula selezione dalla tabella
            view.tabella.getSelectionModel().select(0);
            view.btnElimina.fire();
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        assertNull(gestioneUtenti.trovaUtente("M001"));
        assertTrue(gestioneUtenti.getUtenti().isEmpty());
    }

    @Test
    void testToggleBlacklist() throws InterruptedException {
        Utente u = new Utente("M001", "Mario", "Rossi", "m.rossi@unisa.it");
        gestioneUtenti.inserisciUtente(u);
        assertFalse(u.isInBlacklist());

        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            controller.aggiornaDaModel();
            view.tabella.getSelectionModel().select(0);
            view.btnBlacklist.fire();
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        assertTrue(u.isInBlacklist(), "L'utente dovrebbe essere in blacklist");
        assertTrue(prestitiController.aggiornato, "Il PrestitiController dovrebbe essere stato notificato");
    }

    @Test
    void testRicercaUtente() throws InterruptedException {
        gestioneUtenti.inserisciUtente(new Utente("M001", "Mario", "Rossi", "m.rossi@unisa.it"));
        gestioneUtenti.inserisciUtente(new Utente("M002", "Luca", "Bianchi", "l.bianchi@unisa.it"));

        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Cerca "Bianchi"
            view.simulaInput("", "", "Bianchi", "");
            view.btnCerca.fire();
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        assertEquals(1, view.tabella.getItems().size());
        assertEquals("M002", view.tabella.getItems().get(0).get(0));
    }

    // --- ERROR PATH TESTS (VALIDAZIONE) ---

    @Test
    void testInserimentoCampiMancanti() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Manca la matricola
            view.simulaInput("", "Mario", "Rossi", "m.rossi@unisa.it");
            try { view.btnInserisci.fire(); } catch (Throwable t) {}
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(0, gestioneUtenti.getUtenti().size());
    }

    @Test
    void testInserimentoEmailDominioErrato() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Email non unisa.it
            view.simulaInput("M001", "Mario", "Rossi", "mario@gmail.com");
            try { view.btnInserisci.fire(); } catch (Throwable t) {}
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(0, gestioneUtenti.getUtenti().size());
    }

    @Test
    void testInserimentoEmailConSpazi() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Spazio nell'email
            view.simulaInput("M001", "Mario", "Rossi", "mario rossi@unisa.it");
            try { view.btnInserisci.fire(); } catch (Throwable t) {}
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(0, gestioneUtenti.getUtenti().size());
    }

    @Test
    void testInserimentoDuplicato() throws InterruptedException {
        gestioneUtenti.inserisciUtente(new Utente("M001", "Mario", "Rossi", "m.rossi@unisa.it"));

        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Tento di inserire di nuovo M001
            view.simulaInput("M001", "Luigi", "Verdi", "l.verdi@unisa.it");
            try { view.btnInserisci.fire(); } catch (Throwable t) {}
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        
        // Deve rimanere solo il primo
        assertEquals(1, gestioneUtenti.getUtenti().size());
        assertEquals("Mario", gestioneUtenti.getUtenti().iterator().next().getNome());
    }

    @Test
    void testModificaEmailNonValida() throws InterruptedException {
        gestioneUtenti.inserisciUtente(new Utente("M001", "Mario", "Rossi", "m.rossi@unisa.it"));

        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            controller.aggiornaDaModel();
            view.simulaInput("M001", "", "", "email.sbagliata@libero.it");
            try { view.btnModifica.fire(); } catch (Throwable t) {}
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        
        Utente u = gestioneUtenti.trovaUtente("M001");
        // L'email non deve essere cambiata
        assertEquals("m.rossi@unisa.it", u.getEmail());
    }

    // --- STUBS E CLASSI DI SUPPORTO ---

    static class StubArchivioFile extends ArchivioFile {
        public StubArchivioFile() { super(null); }
        @Override public void salvaUtenti(GestioneUtenti g) { /* No-op */ }
    }

    static class StubGestionePrestiti extends GestionePrestiti {
        @Override
        public int contaPrestitiAttivi(Utente u) {
            return 0; 
        }
    }
    
    // Simula PrestitiController (che non abbiamo qui)
    static class StubPrestitiController extends PrestitiController {
        boolean aggiornato = false;
        public StubPrestitiController() { super(null, null, null, null); }
        @Override public void aggiornaDaModel() {
            this.aggiornato = true;
        }
    }

    static class StubUtentiPanel extends UtentiPanel {
        final Button btnInserisci = new Button();
        final Button btnModifica = new Button();
        final Button btnElimina = new Button();
        final Button btnBlacklist = new Button();
        final Button btnCerca = new Button();
        final TableView<ObservableList<String>> tabella = new TableView<>();

        private String matricola = "", nome = "", cognome = "", email = "";
        boolean campiPuliti = false;

        public StubUtentiPanel() {
            tabella.setItems(FXCollections.observableArrayList());
        }

        public void simulaInput(String m, String n, String c, String e) {
            this.matricola = m; this.nome = n; this.cognome = c; this.email = e;
            this.campiPuliti = false;
        }

        @Override public Button getBottoneInserisci() { return btnInserisci; }
        @Override public Button getBottoneModifica() { return btnModifica; }
        @Override public Button getBottoneElimina() { return btnElimina; }
        @Override public Button getBottoneBlacklist() { return btnBlacklist; }
        @Override public Button getBottoneCerca() { return btnCerca; }
        @Override public TableView<ObservableList<String>> getTabellaUtenti() { return tabella; }

        @Override public String getMatricolaInserita() { return matricola; }
        @Override public String getNomeInserito() { return nome; }
        @Override public String getCognomeInserito() { return cognome; }
        @Override public String getEmailInserita() { return email; }

        @Override
        public void pulisciCampi() {
            matricola = ""; nome = ""; cognome = ""; email = "";
            campiPuliti = true;
        }

        @Override
        public void setCampiDaRiga(ObservableList<String> riga) {
            if (riga != null && !riga.isEmpty()) {
                this.matricola = riga.get(0);
                this.nome = riga.get(1);
            }
        }
    }
}