package biblioteca.controller;

import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Utente;
import biblioteca.view.UtentiPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class UtentiControllerTest {

    private GestioneUtenti gestioneUtenti;
    private GestionePrestiti gestionePrestiti;
    private UtentiPanel vista;
    private UtentiController controller;

    @BeforeAll
    public static void avviaJavaFx() throws Exception {
        Platform.setImplicitExit(false);
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException ex) {
            Platform.runLater(latch::countDown);
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @BeforeEach
    public void setUp() throws Exception {
        gestioneUtenti = new GestioneUtenti();

        Utente u1 = new Utente("0612700001", "Matteo", "Menza", "m.menza@unisa.it");
        Utente u2 = new Utente("0612700002", "Pasquale", "Sorbo", "p.sorbo@unisa.it");
        u2.setInBlacklist(true);

        gestioneUtenti.inserisciUtente(u1);
        gestioneUtenti.inserisciUtente(u2);

        gestionePrestiti = new GestionePrestitiFinta();

        eseguiFx(() -> {
            vista = new UtentiPanel();
            controller = new UtentiController(gestioneUtenti, gestionePrestiti, vista, null);
        });
    }

    @Test
    public void aggiornaDaModel_mostraDueUtentiEBlacklist() throws Exception {
        eseguiFx(() -> {
            vista.getBottoneCerca().setText("Indietro");
            if (!vista.getTabellaUtenti().getItems().isEmpty()) {
                vista.getTabellaUtenti().getSelectionModel().select(0);
            }

            controller.aggiornaDaModel();

            assertEquals("Cerca", vista.getBottoneCerca().getText());
            assertNull(vista.getTabellaUtenti().getSelectionModel().getSelectedItem());

            ObservableList<ObservableList<String>> righe = vista.getTabellaUtenti().getItems();
            assertEquals(2, righe.size());

            ObservableList<String> prima = righe.get(0);
            assertEquals("0612700001", prima.get(0));
            assertEquals("3", prima.get(4));
            assertEquals("No", prima.get(5));

            ObservableList<String> seconda = righe.get(1);
            assertEquals("0612700002", seconda.get(0));
            assertEquals("1", seconda.get(4));
            assertEquals("Sì", seconda.get(5));

        });
    }

    private static class GestionePrestitiFinta extends GestionePrestiti {
        @Override
        public int contaPrestitiAttivi(Utente u) {
            if (u == null || u.getMatricola() == null) return 0;
            if ("0612700001".equals(u.getMatricola())) return 3;
            if ("0612700002".equals(u.getMatricola())) return 1;
            return 0;
        }
    }

    private static void eseguiFx(Runnable r) throws Exception {
        if (Platform.isFxApplicationThread()) {
            r.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Throwable[] err = new Throwable[1];
        Platform.runLater(() -> {
            try { r.run(); } catch (Throwable t) { err[0] = t; } finally { latch.countDown(); }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS));
        if (err[0] != null) {
            if (err[0] instanceof RuntimeException re) throw re;
            if (err[0] instanceof Error e) throw e;
            throw new RuntimeException(err[0]);
        }
    }
}
