package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Libro;
import biblioteca.model.Prestito;
import biblioteca.model.Utente;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.PrestitiPanel;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitiControllerTest {

    private GestionePrestiti gestionePrestiti;
    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private PrestitiPanel vista;
    private PrestitiController controller;
    private ArchivioFile archivio;

    private Utente uOk;
    private Utente uBlack;

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
        Path tmp = Files.createTempDirectory("test-biblio-prestiti-");
        archivio = new ArchivioFile(tmp.toAbsolutePath().toString());

        gestionePrestiti = new GestionePrestiti();
        gestioneLibri = new GestioneLibri();
        gestioneUtenti = new GestioneUtenti();

        uOk = new Utente("0612700001", "Mario", "Rossi", "mario.rossi@unisa.it");
        uBlack = new Utente("0612700002", "Giulia", "Bianchi", "giulia.bianchi@unisa.it");
        uBlack.setInBlacklist(true);

        gestioneUtenti.inserisciUtente(uOk);
        gestioneUtenti.inserisciUtente(uBlack);

        inserisciLibro(9788800000001L, "LibroA", 2020, 5);
        inserisciLibro(9788800000002L, "LibroB", 2021, 5);
        inserisciLibro(9788800000003L, "LibroC", 2022, 5);
        inserisciLibro(9788800000004L, "LibroD", 2023, 5);

        eseguiFx(() -> {
            vista = new PrestitiPanel();
            controller = new PrestitiController(
                    gestionePrestiti,
                    vista,
                    archivio,
                    gestioneLibri,
                    gestioneUtenti,
                    null,
                    null
            );
            vista.getTabellaPrestiti().getItems().clear();
        });
    }

    @Test
    public void nuovoPrestito_ok_registraEaggiornaTabella() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800000001");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().plusDays(7).toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(1, gestionePrestiti.getPrestitiAttivi().size());
            assertEquals(1, vista.getTabellaPrestiti().getItems().size());
            assertEquals("", vista.getMatricolaInserita());
            assertEquals("", vista.getIsbnInserito());
            assertEquals("", vista.getDataPrevistaInserita());
        });
    }

    @Test
    public void nuovoPrestito_error_campiVuoti_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "");
            setCampoDaGetter(vista, "getIsbnInserito", "");
            setCampoDaGetter(vista, "getDataPrevistaInserita", "");

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_isbnNonNumero_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getIsbnInserito", "ABC");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().plusDays(7).toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_dataNonValida_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800000001");
            setCampoDaGetter(vista, "getDataPrevistaInserita", "12/31/2025");

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_dataPrevistaNonSuccessiva_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800000001");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_utenteNonTrovato_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700999");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800000001");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().plusDays(7).toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_utenteInBlacklist_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700002");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800000001");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().plusDays(7).toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_limiteTrePrestiti_nonRegistra() throws Exception {
        eseguiFx(() -> {
            Libro a = gestioneLibri.trovaLibro(9788800000001L);
            Libro b = gestioneLibri.trovaLibro(9788800000002L);
            Libro c = gestioneLibri.trovaLibro(9788800000003L);
            assertNotNull(a);
            assertNotNull(b);
            assertNotNull(c);

            gestionePrestiti.registraPrestito(uOk, a, LocalDate.now().minusDays(3), LocalDate.now().plusDays(10));
            gestionePrestiti.registraPrestito(uOk, b, LocalDate.now().minusDays(2), LocalDate.now().plusDays(11));
            gestionePrestiti.registraPrestito(uOk, c, LocalDate.now().minusDays(1), LocalDate.now().plusDays(12));

            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800000004");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().plusDays(7).toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(3, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void nuovoPrestito_error_libroNonTrovato_nonRegistra() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getIsbnInserito", "9788800009999");
            setCampoDaGetter(vista, "getDataPrevistaInserita", LocalDate.now().plusDays(7).toString());

            vista.getBottoneNuovoPrestito().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void restituzione_error_nessunaSelezione_nonFaNulla() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            vista.getTabellaPrestiti().getSelectionModel().clearSelection();
            vista.getBottoneRestituzione().fire();
            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void restituzione_error_isbnNonNumero_inRiga_nonFaNulla() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            ObservableList<ObservableList<String>> items = vista.getTabellaPrestiti().getItems();
            items.setAll(
                    javafx.collections.FXCollections.observableArrayList(
                            javafx.collections.FXCollections.observableArrayList(
                                    "0612700001", "Mario", "Rossi", "ABC", "X",
                                    LocalDate.now().minusDays(1).toString(),
                                    LocalDate.now().plusDays(1).toString(),
                                    "No", "No"
                            )
                    )
            );
            vista.getTabellaPrestiti().getSelectionModel().select(0);
            vista.getBottoneRestituzione().fire();
            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void restituzione_error_dataInizioNonValida_inRiga_nonFaNulla() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            ObservableList<ObservableList<String>> items = vista.getTabellaPrestiti().getItems();
            items.setAll(
                    javafx.collections.FXCollections.observableArrayList(
                            javafx.collections.FXCollections.observableArrayList(
                                    "0612700001", "Mario", "Rossi", "9788800000001", "X",
                                    "not-a-date",
                                    LocalDate.now().plusDays(1).toString(),
                                    "No", "No"
                            )
                    )
            );
            vista.getTabellaPrestiti().getSelectionModel().select(0);
            vista.getBottoneRestituzione().fire();
            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void restituzione_error_prestitoNonTrovato_nonFaNulla() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            ObservableList<ObservableList<String>> items = vista.getTabellaPrestiti().getItems();
            items.setAll(
                    javafx.collections.FXCollections.observableArrayList(
                            javafx.collections.FXCollections.observableArrayList(
                                    "0612700001", "Mario", "Rossi", "9788800000001", "X",
                                    LocalDate.now().minusDays(10).toString(),
                                    LocalDate.now().plusDays(1).toString(),
                                    "No", "No"
                            )
                    )
            );
            vista.getTabellaPrestiti().getSelectionModel().select(0);
            vista.getBottoneRestituzione().fire();
            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void restituzione_ok_rimuoveDaAttivi() throws Exception {
        eseguiFx(() -> {
            Libro l = gestioneLibri.trovaLibro(9788800000001L);
            LocalDate inizio = LocalDate.now().minusDays(1);
            LocalDate prevista = LocalDate.now().plusDays(7);
            gestionePrestiti.registraPrestito(uOk, l, inizio, prevista);

            controller.aggiornaDaModel();
            assertEquals(1, vista.getTabellaPrestiti().getItems().size());

            vista.getTabellaPrestiti().getSelectionModel().select(0);
            vista.getBottoneRestituzione().fire();

            assertEquals(0, gestionePrestiti.getPrestitiAttivi().size());
        });
    }

    @Test
    public void blacklist_error_nessunaSelezione_nonCambia() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            vista.getTabellaPrestiti().getSelectionModel().clearSelection();
            vista.getBottoneBlacklist().fire();
            assertFalse(uOk.isInBlacklist());
        });
    }

    @Test
    public void blacklist_error_utenteNonTrovato_nonCambia() throws Exception {
        eseguiFx(() -> {
            chiudiFinestreDopo(80);
            ObservableList<ObservableList<String>> items = vista.getTabellaPrestiti().getItems();
            items.setAll(
                    javafx.collections.FXCollections.observableArrayList(
                            javafx.collections.FXCollections.observableArrayList(
                                    "0612700999", "X", "Y", "9788800000001", "T",
                                    LocalDate.now().minusDays(1).toString(),
                                    LocalDate.now().plusDays(7).toString(),
                                    "No", "No"
                            )
                    )
            );
            vista.getTabellaPrestiti().getSelectionModel().select(0);
            vista.getBottoneBlacklist().fire();
            assertFalse(uOk.isInBlacklist());
        });
    }

    @Test
    public void blacklist_ok_impostaFlagUtente() throws Exception {
        eseguiFx(() -> {
            Libro l = gestioneLibri.trovaLibro(9788800000001L);
            gestionePrestiti.registraPrestito(uOk, l, LocalDate.now().minusDays(1), LocalDate.now().plusDays(7));
            controller.aggiornaDaModel();

            chiudiFinestreDopo(80);
            vista.getTabellaPrestiti().getSelectionModel().select(0);
            vista.getBottoneBlacklist().fire();

            assertTrue(gestioneUtenti.trovaUtente("0612700001").isInBlacklist());
        });
    }

    private void inserisciLibro(long isbn, String titolo, int anno, int copie) {
        gestioneLibri.inserisciLibro(isbn, titolo, new ArrayList<>(List.of("Autore")), anno, copie);
    }

    private static void chiudiFinestreDopo(int ms) {
        PauseTransition pt = new PauseTransition(Duration.millis(ms));
        pt.setOnFinished(e -> {
            for (Window w : Window.getWindows()) {
                if (w != null && w.isShowing()) w.hide();
            }
        });
        pt.play();
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
