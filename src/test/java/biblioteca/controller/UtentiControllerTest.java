package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Prestito;
import biblioteca.model.Utente;
import biblioteca.view.PrestitiPanel;
import biblioteca.view.UtentiPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class UtentiControllerTest {

    private GestioneUtenti gestioneUtenti;
    private FakeGestionePrestiti gestionePrestiti;
    private UtentiPanel view;
    private TableView<ObservableList<String>> tabella;
    private UtentiController instance;

    @BeforeAll
    public static void setUpClass() throws Exception {
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
        runFxAndWait(() -> {
            gestioneUtenti = new GestioneUtenti();
            gestionePrestiti = new FakeGestionePrestiti(Map.of(
                    "M2", 1,
                    "M1", 3
            ));

            Utente u1 = new Utente("M1", "Mario", "Rossi", "mario@uni.it");
            Utente u2 = new Utente("M2", "Anna", "Bianchi", "anna@uni.it");
            u2.setInBlacklist(true);

            gestioneUtenti.inserisciUtente(u1);
            gestioneUtenti.inserisciUtente(u2);

            view = new UtentiPanel();
            tabella = view.getTabellaUtenti();
            assertSame(tabella, view.getTabellaUtenti());

            instance = new UtentiController(gestioneUtenti, gestionePrestiti, view, null);
        });
    }

    @Test
    public void testSetPrestitiController() throws Exception {
        PrestitiController pc = runFxAndWaitResult(() ->
                new PrestitiController(
                        new GestionePrestiti(),
                        new PrestitiPanel(),
                        null,
                        new GestioneLibri(),
                        new GestioneUtenti(),
                        null,
                        null
                )
        );

        runFxAndWait(() -> instance.setPrestitiController(pc));

        Field f = UtentiController.class.getDeclaredField("prestitiController");
        f.setAccessible(true);
        assertSame(pc, f.get(instance));
    }

    @Test
    public void testAggiornaDaModel() throws Exception {
        runFxAndWait(() -> {
            view.getBottoneCerca().setText("Indietro");
            if (!tabella.getItems().isEmpty()) {
                tabella.getSelectionModel().select(0);
            }

            instance.aggiornaDaModel();

            assertEquals("Cerca", view.getBottoneCerca().getText());
            assertNull(tabella.getSelectionModel().getSelectedItem());

            ObservableList<ObservableList<String>> righe = tabella.getItems();
            assertEquals(2, righe.size());

            ObservableList<String> r0 = righe.get(0);
            ObservableList<String> r1 = righe.get(1);

            assertEquals("M2", r0.get(0));
            assertEquals("Anna", r0.get(1));
            assertEquals("Bianchi", r0.get(2));
            assertEquals("anna@uni.it", r0.get(3));
            assertEquals("1", r0.get(4));
            assertEquals("Sì", r0.get(5));

            assertEquals("M1", r1.get(0));
            assertEquals("Mario", r1.get(1));
            assertEquals("Rossi", r1.get(2));
            assertEquals("mario@uni.it", r1.get(3));
            assertEquals("3", r1.get(4));
            assertEquals("No", r1.get(5));
        });
    }

    private static class FakeGestionePrestiti extends GestionePrestiti {
        private final Map<String, Integer> counts;
        FakeGestionePrestiti(Map<String, Integer> counts) {
            this.counts = counts;
        }
        @Override
        public int contaPrestitiAttivi(Utente u) {
            if (u == null || u.getMatricola() == null) return 0;
            return counts.getOrDefault(u.getMatricola(), 0);
        }
        @Override
        public java.util.List<Prestito> getPrestitiAttivi() {
        return java.util.Collections.emptyList();
        }

    }

    private static void runFxAndWait(Runnable r) throws Exception {
        if (Platform.isFxApplicationThread()) {
            r.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final Throwable[] err = new Throwable[1];
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

    private static <T> T runFxAndWaitResult(java.util.concurrent.Callable<T> c) throws Exception {
        if (Platform.isFxApplicationThread()) {
            return c.call();
        }
        CountDownLatch latch = new CountDownLatch(1);
        final Object[] out = new Object[1];
        final Throwable[] err = new Throwable[1];
        Platform.runLater(() -> {
            try { out[0] = c.call(); } catch (Throwable t) { err[0] = t; } finally { latch.countDown(); }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS));
        if (err[0] != null) {
            if (err[0] instanceof RuntimeException re) throw re;
            if (err[0] instanceof Error e) throw e;
            throw new RuntimeException(err[0]);
        }
        return (T) out[0];
    }
}
