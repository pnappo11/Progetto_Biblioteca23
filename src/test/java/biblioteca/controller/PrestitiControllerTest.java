package biblioteca.controller;

import biblioteca.model.*;
import biblioteca.view.PrestitiPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitiControllerTest {

    private GestionePrestiti gestionePrestiti;
    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private PrestitiPanel vista;
    private PrestitiController controller;

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
        gestionePrestiti = new GestionePrestiti();
        gestioneLibri = new GestioneLibri();
        gestioneUtenti = new GestioneUtenti();

        Utente u1 = new Utente("0612700001", "Matteo", "Menza", "m.menza@unisa.it");
        Utente u2 = new Utente("0612700002", "Pasquale", "Sorbo", "p.sorbo@unisa.it");
        u2.setInBlacklist(true);

        gestioneUtenti.inserisciUtente(u1);
        gestioneUtenti.inserisciUtente(u2);

        Libro l1 = new Libro(9788800000000L, "Odissea", new ArrayList<>(), 2020, 5, 5);
        Libro l2 = new Libro(9788800000001L, "Lilith",  new ArrayList<>(), 2018, 2, 2);

        LocalDate inizio = LocalDate.now().minusDays(10);
        gestionePrestiti.registraPrestito(u1, l1, inizio, LocalDate.now().minusDays(1));  // in ritardo
        gestionePrestiti.registraPrestito(u2, l2, inizio, LocalDate.now().plusDays(5));   // non in ritardo

        eseguiFx(() -> {
            vista = new PrestitiPanel();
            controller = new PrestitiController(gestionePrestiti, vista, null, gestioneLibri, gestioneUtenti, null, null);
        });
    }

    @Test
    public void aggiornaDaModel_riempieLaTabellaConDuePrestiti() throws Exception {
        eseguiFx(() -> {
            controller.aggiornaDaModel();

            ObservableList<ObservableList<String>> righe = vista.getTabellaPrestiti().getItems();
            assertEquals(2, righe.size());

            ObservableList<String> r1 = trovaRiga(righe, "0612700001");
            assertNotNull(r1);
            assertEquals("9788800000000", r1.get(3));
            assertEquals("Odissea", r1.get(4));
            assertEquals("Sì", r1.get(7));
            assertEquals("No", r1.get(8));

            ObservableList<String> r2 = trovaRiga(righe, "0612700002");
            assertNotNull(r2);
            assertEquals("9788800000001", r2.get(3));
            assertEquals("Lilith", r2.get(4));
            assertEquals("No", r2.get(7));
            assertEquals("Sì", r2.get(8));
        });
    }

    private static ObservableList<String> trovaRiga(ObservableList<ObservableList<String>> righe, String matricola) {
        for (ObservableList<String> r : righe) {
            if (r != null && !r.isEmpty() && matricola.equals(r.get(0))) return r;
        }
        return null;
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
