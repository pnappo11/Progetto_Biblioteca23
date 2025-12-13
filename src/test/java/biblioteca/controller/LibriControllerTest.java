package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.view.LibriPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LibriControllerTest {

    private GestioneLibri gestioneLibri;
    private LibriPanel vista;
    private LibriController controller;

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
        gestioneLibri = new GestioneLibri();
        gestioneLibri.inserisciLibro(9788800000000L, "Odissea", Arrays.asList("Matteo Sorbo"), 2020, 5);
        gestioneLibri.inserisciLibro(9788800000001L, "Lilith", Arrays.asList("William Nappo"), 2018, 2);

        eseguiFx(() -> {
            vista = new LibriPanel();
            controller = new LibriController(gestioneLibri, vista, null);
        });
    }

    @Test
    public void aggiornaDaModel_riprendeTuttiILibriInTabella() throws Exception {
        eseguiFx(() -> {
            vista.getBottoneCerca().setText("Indietro");
            if (!vista.getTabellaLibri().getItems().isEmpty()) {
                vista.getTabellaLibri().getSelectionModel().select(0);
            }

            controller.aggiornaDaModel();

            assertEquals("Cerca", vista.getBottoneCerca().getText());

            ObservableList<ObservableList<String>> righe = vista.getTabellaLibri().getItems();
            assertEquals(2, righe.size());

            assertEquals("Lilith", righe.get(0).get(1));
            assertEquals("9788800000001", righe.get(0).get(0));

            assertEquals("Odissea", righe.get(1).get(1));
            assertEquals("9788800000000", righe.get(1).get(0));

        });
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
