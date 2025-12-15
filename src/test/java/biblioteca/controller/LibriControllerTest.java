package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.Libro;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LibriPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LibriControllerTest {

    private GestioneLibri gestioneLibri;
    private LibriPanel vista;
    private LibriController controller;
    private ArchivioFile archivio;

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
        Path tmp = Files.createTempDirectory("test-biblio-libri-");
        gestioneLibri = new GestioneLibri();
        archivio = new ArchivioFile(tmp.toAbsolutePath().toString());
        eseguiFx(() -> {
            vista = new LibriPanel();
            controller = new LibriController(gestioneLibri, vista, archivio);
            vista.getTabellaLibri().getItems().clear();
        });
    }

    @Test
    public void inserisciLibro_ok_inserisceInModelloETabella() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000001");
            setCampoDaGetter(vista, "getTitoloInserito", "Pulcinella e il mare");
            setCampoDaGetter(vista, "getAutoreInserito", "Gennaro Esposito");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "3");

            vista.getBottoneInserisci().fire();

            Libro l = gestioneLibri.trovaLibro(9788800000001L);
            assertNotNull(l);
            assertEquals("Pulcinella e il mare", l.getTitolo());

            ObservableList<ObservableList<String>> righe = vista.getTabellaLibri().getItems();
            assertEquals(1, righe.size());
            assertEquals("9788800000001", righe.get(0).get(0));
            assertEquals("Pulcinella e il mare", righe.get(0).get(1));
        });
    }

    @Test
    public void inserisciLibro_error_campiVuoti_nonInserisce() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(250);
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "");
            setCampoDaGetter(vista, "getTitoloInserito", "");
            setCampoDaGetter(vista, "getAutoreInserito", "");
            setCampoDaGetter(vista, "getAnnoInserito", "");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "");

            vista.getBottoneInserisci().fire();

            assertNull(gestioneLibri.trovaLibro(9788800000001L));
            assertEquals(0, vista.getTabellaLibri().getItems().size());
        });
    }

    @Test
    public void inserisciLibro_error_isbnNonValido_nonInserisce() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(250);
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "123");
            setCampoDaGetter(vista, "getTitoloInserito", "Libro fantasma");
            setCampoDaGetter(vista, "getAutoreInserito", "Ciro Russo");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "2");

            vista.getBottoneInserisci().fire();

            assertEquals(0, vista.getTabellaLibri().getItems().size());
        });
    }

    @Test
    public void modificaLibro_ok_modificaTitolo() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000002");
            setCampoDaGetter(vista, "getTitoloInserito", "La pizza perfetta");
            setCampoDaGetter(vista, "getAutoreInserito", "Carmine De Luca");
            setCampoDaGetter(vista, "getAnnoInserito", "2019");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "4");
            vista.getBottoneInserisci().fire();

            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000002");
            setCampoDaGetter(vista, "getTitoloInserito", "Il caffè sospeso");
            vista.getBottoneModifica().fire();

            Libro l = gestioneLibri.trovaLibro(9788800000002L);
            assertNotNull(l);
            assertEquals("Il caffè sospeso", l.getTitolo());
        });
    }

    @Test
    public void modificaLibro_error_senzaIsbnENessunaSelezione_nonModifica() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000003");
            setCampoDaGetter(vista, "getTitoloInserito", "Prima stesura");
            setCampoDaGetter(vista, "getAutoreInserito", "Assunta Esposito");
            setCampoDaGetter(vista, "getAnnoInserito", "2021");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "2");
            vista.getBottoneInserisci().fire();

            chiudiFinestreDopo(250);
            vista.getTabellaLibri().getSelectionModel().clearSelection();
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "");
            setCampoDaGetter(vista, "getTitoloInserito", "Seconda stesura");

            vista.getBottoneModifica().fire();

            Libro l = gestioneLibri.trovaLibro(9788800000003L);
            assertNotNull(l);
            assertEquals("Prima stesura", l.getTitolo());
        });
    }

    @Test
    public void modificaLibro_error_libroNonTrovato_nonModifica() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(250);
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000999");
            setCampoDaGetter(vista, "getTitoloInserito", "Storia che non esiste");
            setCampoDaGetter(vista, "getAutoreInserito", "Salvatore Romano");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "2");

            vista.getBottoneModifica().fire();

            assertNull(gestioneLibri.trovaLibro(9788800000999L));
        });
    }

    @Test
    public void modificaLibro_error_copieTotaliMinoriPrestito_nonModificaCopie() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000004");
            setCampoDaGetter(vista, "getTitoloInserito", "Copie di Partenope");
            setCampoDaGetter(vista, "getAutoreInserito", "Gigi Ferraro");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "5");
            vista.getBottoneInserisci().fire();

            Libro l = gestioneLibri.trovaLibro(9788800000004L);
            assertNotNull(l);
            l.setCopieDisponibili(2);

            chiudiFinestreDopo(250);
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000004");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "2");
            vista.getBottoneModifica().fire();

            Libro after = gestioneLibri.trovaLibro(9788800000004L);
            assertNotNull(after);
            assertEquals(5, after.getCopieTotali());
            assertEquals(2, after.getCopieDisponibili());
        });
    }

    @Test
    public void eliminaLibro_error_senzaSelezione_nonElimina() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000005");
            setCampoDaGetter(vista, "getTitoloInserito", "Da cancellare");
            setCampoDaGetter(vista, "getAutoreInserito", "Raffaele Esposito");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "1");
            vista.getBottoneInserisci().fire();

            assertNotNull(gestioneLibri.trovaLibro(9788800000005L));

            chiudiFinestreDopo(250);
            vista.getTabellaLibri().getSelectionModel().clearSelection();
            vista.getBottoneElimina().fire();

            assertNotNull(gestioneLibri.trovaLibro(9788800000005L));
        });
    }

    @Test
    public void cerca_toggleCercaIndietro() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000006");
            setCampoDaGetter(vista, "getTitoloInserito", "Napoli nel cuore");
            setCampoDaGetter(vista, "getAutoreInserito", "Ciro Esposito");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "2");
            vista.getBottoneInserisci().fire();

            setCampoDaGetter(vista, "getTitoloInserito", "Napoli nel cuore");
            vista.getBottoneCerca().fire();
            assertEquals("Indietro", vista.getBottoneCerca().getText());

            vista.getBottoneCerca().fire();
            assertEquals("Cerca", vista.getBottoneCerca().getText());
        });
    }

    @Test
    public void cerca_conAnnoOMostraInfo_maVaInRicerca() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(250);
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            vista.getBottoneCerca().fire();
            assertEquals("Indietro", vista.getBottoneCerca().getText());
        });
    }

    @Test
    public void aggiornaDaModel_resetRicercaESelezione() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getCodiceIsbnInserito", "9788800000007");
            setCampoDaGetter(vista, "getTitoloInserito", "Vesuvio");
            setCampoDaGetter(vista, "getAutoreInserito", "Carmela Romano");
            setCampoDaGetter(vista, "getAnnoInserito", "2020");
            setCampoDaGetter(vista, "getCopieTotaliInserite", "1");
            vista.getBottoneInserisci().fire();

            vista.getBottoneCerca().setText("Indietro");
            if (!vista.getTabellaLibri().getItems().isEmpty()) {
                vista.getTabellaLibri().getSelectionModel().select(0);
            }

            controller.aggiornaDaModel();

            assertEquals("Cerca", vista.getBottoneCerca().getText());
            assertNull(vista.getTabellaLibri().getSelectionModel().getSelectedItem());
        });
    }

    private static void chiudiFinestreDopo(int ms) {
        Thread t = new Thread(() -> {
            try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
            Platform.runLater(() -> {
                for (Window w : Window.getWindows()) {
                    if (w != null && w.isShowing()) w.hide();
                }
            });
        });
        t.setDaemon(true);
        t.start();
    }

    private static void setCampoDaGetter(Object view, String getterName, String value) {
        try {
            Method getter = view.getClass().getMethod(getterName);
            String probe = "PROBE_" + getterName;
            Field[] fields = view.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (!f.getType().getName().equals("javafx.scene.control.TextField")) continue;
                f.setAccessible(true);
                Object tf = f.get(view);
                Method getText = tf.getClass().getMethod("getText");
                Method setText = tf.getClass().getMethod("setText", String.class);
                String old = (String) getText.invoke(tf);
                setText.invoke(tf, probe);
                String got = (String) getter.invoke(view);
                if (probe.equals(got)) {
                    setText.invoke(tf, value);
                    return;
                }
                setText.invoke(tf, old);
            }
            throw new IllegalStateException("Campo non trovato per " + getterName);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
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
