package biblioteca.controller;

import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Utente;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.UtentiPanel;
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

public class UtentiControllerTest {

    private GestioneUtenti gestioneUtenti;
    private GestionePrestiti gestionePrestiti;
    private UtentiPanel vista;
    private UtentiController controller;
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
        Path tmp = Files.createTempDirectory("test-biblio-utenti-");
        gestioneUtenti = new GestioneUtenti();
        gestionePrestiti = new GestionePrestitiFinta();
        archivio = new ArchivioFile(tmp.toAbsolutePath().toString());
        eseguiFx(() -> {
            vista = new UtentiPanel();
            controller = new UtentiController(gestioneUtenti, gestionePrestiti, vista, archivio);
            vista.getTabellaUtenti().getItems().clear();
        });
    }

    @Test
    public void inserisci_ok_inserisceInModelloETabella() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700001");
            setCampoDaGetter(vista, "getNomeInserito", "Gennaro");
            setCampoDaGetter(vista, "getCognomeInserito", "Esposito");
            setCampoDaGetter(vista, "getEmailInserita", "G.ESPOSITO@UNISA.IT");

            vista.getBottoneInserisci().fire();

            Utente u = gestioneUtenti.trovaUtente("0612700001");
            assertNotNull(u);
            assertEquals("Gennaro", u.getNome());
            assertEquals("Esposito", u.getCognome());
            assertEquals("g.esposito@unisa.it", u.getEmail());

            ObservableList<ObservableList<String>> righe = vista.getTabellaUtenti().getItems();
            assertEquals(1, righe.size());
            ObservableList<String> r = righe.get(0);
            assertEquals("0612700001", r.get(0));
            assertEquals("Gennaro", r.get(1));
            assertEquals("Esposito", r.get(2));
            assertEquals("g.esposito@unisa.it", r.get(3));
            assertEquals("0", r.get(4));
            assertEquals("No", r.get(5));
        });
    }

    @Test
    public void inserisci_error_campiVuoti_nonInserisce() throws Exception {
        eseguiFx(() -> {
            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "");
            setCampoDaGetter(vista, "getNomeInserito", "");
            setCampoDaGetter(vista, "getCognomeInserito", "");
            setCampoDaGetter(vista, "getEmailInserita", "");
            vista.getBottoneInserisci().fire();
            assertEquals(0, vista.getTabellaUtenti().getItems().size());
        });
    }

    @Test
    public void inserisci_error_emailSenzaAt_nonInserisce() throws Exception {
        eseguiFx(() -> {
            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700002");
            setCampoDaGetter(vista, "getNomeInserito", "Ciro");
            setCampoDaGetter(vista, "getCognomeInserito", "Russo");
            setCampoDaGetter(vista, "getEmailInserita", "ciro.unisa.it");
            vista.getBottoneInserisci().fire();
            assertNull(gestioneUtenti.trovaUtente("0612700002"));
            assertEquals(0, vista.getTabellaUtenti().getItems().size());
        });
    }

    @Test
    public void inserisci_error_emailNonUnisa_nonInserisce() throws Exception {
        eseguiFx(() -> {
            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700003");
            setCampoDaGetter(vista, "getNomeInserito", "Carmela");
            setCampoDaGetter(vista, "getCognomeInserito", "Iovine");
            setCampoDaGetter(vista, "getEmailInserita", "carmela@gmail.com");
            vista.getBottoneInserisci().fire();
            assertNull(gestioneUtenti.trovaUtente("0612700003"));
        });
    }

    @Test
    public void inserisci_error_emailConSpazi_nonInserisce() throws Exception {
        eseguiFx(() -> {
            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700004");
            setCampoDaGetter(vista, "getNomeInserito", "Salvatore");
            setCampoDaGetter(vista, "getCognomeInserito", "Ferraro");
            setCampoDaGetter(vista, "getEmailInserita", "salvatore ferraro@unisa.it");
            vista.getBottoneInserisci().fire();
            assertNull(gestioneUtenti.trovaUtente("0612700004"));
        });
    }

    @Test
    public void inserisci_error_matricolaDuplicata_nonInserisceSecondo() throws Exception {
        eseguiFx(() -> {
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700005");
            setCampoDaGetter(vista, "getNomeInserito", "Antonio");
            setCampoDaGetter(vista, "getCognomeInserito", "DeLuca");
            setCampoDaGetter(vista, "getEmailInserita", "antonio@unisa.it");
            vista.getBottoneInserisci().fire();
            assertNotNull(gestioneUtenti.trovaUtente("0612700005"));
            assertEquals(1, vista.getTabellaUtenti().getItems().size());

            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700005");
            setCampoDaGetter(vista, "getNomeInserito", "Vincenzo");
            setCampoDaGetter(vista, "getCognomeInserito", "DellaRagione");
            setCampoDaGetter(vista, "getEmailInserita", "vincenzo@unisa.it");
            vista.getBottoneInserisci().fire();

            assertEquals(1, vista.getTabellaUtenti().getItems().size());
            Utente u = gestioneUtenti.trovaUtente("0612700005");
            assertEquals("Antonio", u.getNome());
        });
    }

    @Test
    public void modifica_ok_daMatricola_modificaNome() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700006", "Gennaro", "Esposito", "gennaro@unisa.it");
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700006");
            setCampoDaGetter(vista, "getNomeInserito", "Gaetano");
            vista.getBottoneModifica().fire();
            Utente u = gestioneUtenti.trovaUtente("0612700006");
            assertNotNull(u);
            assertEquals("Gaetano", u.getNome());
        });
    }

    @Test
    public void modifica_ok_daSelezione_modificaCognome() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700007", "Ciro", "Russo", "ciro@unisa.it");
            vista.getTabellaUtenti().getSelectionModel().select(0);
            setCampoDaGetter(vista, "getMatricolaInserita", "");
            setCampoDaGetter(vista, "getCognomeInserito", "Marino");
            vista.getBottoneModifica().fire();
            Utente u = gestioneUtenti.trovaUtente("0612700007");
            assertNotNull(u);
            assertEquals("Marino", u.getCognome());
        });
    }

    @Test
    public void modifica_error_senzaMatricolaENessunaSelezione_nonModifica() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700008", "Carmela", "Iovine", "carmela@unisa.it");
            autoChiudiFinestre(1500);
            vista.getTabellaUtenti().getSelectionModel().clearSelection();
            setCampoDaGetter(vista, "getMatricolaInserita", "");
            setCampoDaGetter(vista, "getNomeInserito", "Nunzia");
            vista.getBottoneModifica().fire();
            Utente u = gestioneUtenti.trovaUtente("0612700008");
            assertNotNull(u);
            assertEquals("Carmela", u.getNome());
        });
    }

    @Test
    public void modifica_error_utenteNonTrovato_nonModifica() throws Exception {
        eseguiFx(() -> {
            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612709999");
            setCampoDaGetter(vista, "getNomeInserito", "Gigi");
            vista.getBottoneModifica().fire();
            assertNull(gestioneUtenti.trovaUtente("0612709999"));
        });
    }

    @Test
    public void modifica_error_emailNonValida_nonModificaEmail() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700010", "Salvatore", "Ferraro", "salvatore@unisa.it");
            autoChiudiFinestre(1500);
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700010");
            setCampoDaGetter(vista, "getEmailInserita", "salvatore@gmail.com");
            vista.getBottoneModifica().fire();
            Utente u = gestioneUtenti.trovaUtente("0612700010");
            assertNotNull(u);
            assertEquals("salvatore@unisa.it", u.getEmail());
        });
    }

    @Test
    public void elimina_ok_daMatricola_elimina() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700011", "Antonio", "DeLuca", "antonio@unisa.it");
            setCampoDaGetter(vista, "getMatricolaInserita", "0612700011");
            vista.getBottoneElimina().fire();
            assertNull(gestioneUtenti.trovaUtente("0612700011"));
            assertEquals(0, vista.getTabellaUtenti().getItems().size());
        });
    }

    @Test
    public void elimina_ok_daSelezione_elimina() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700012", "Vincenzo", "DellaRagione", "vincenzo@unisa.it");
            vista.getTabellaUtenti().getSelectionModel().select(0);
            setCampoDaGetter(vista, "getMatricolaInserita", "");
            vista.getBottoneElimina().fire();
            assertNull(gestioneUtenti.trovaUtente("0612700012"));
        });
    }

    @Test
    public void elimina_error_senzaMatricolaENessunaSelezione_nonElimina() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700013", "Nunzia", "Marino", "nunzia@unisa.it");
            autoChiudiFinestre(1500);
            vista.getTabellaUtenti().getSelectionModel().clearSelection();
            setCampoDaGetter(vista, "getMatricolaInserita", "");
            vista.getBottoneElimina().fire();
            assertNotNull(gestioneUtenti.trovaUtente("0612700013"));
        });
    }

    @Test
    public void blacklist_ok_toggleDaNoASi() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700014", "Ciro", "Esposito", "ciro@unisa.it");
            assertFalse(gestioneUtenti.trovaUtente("0612700014").isInBlacklist());

            vista.getTabellaUtenti().getSelectionModel().select(0);
            vista.getBottoneBlacklist().fire();

            Utente u = gestioneUtenti.trovaUtente("0612700014");
            assertNotNull(u);
            assertTrue(u.isInBlacklist());

            ObservableList<String> r = trovaRiga(vista.getTabellaUtenti().getItems(), "0612700014");
            assertNotNull(r);
            assertEquals("Sì", r.get(5));
        });
    }

    @Test
    public void blacklist_error_senzaSelezione_nonCambia() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700015", "Carmela", "Russo", "carmela@unisa.it");
            autoChiudiFinestre(1500);
            vista.getTabellaUtenti().getSelectionModel().clearSelection();
            vista.getBottoneBlacklist().fire();
            Utente u = gestioneUtenti.trovaUtente("0612700015");
            assertNotNull(u);
            assertFalse(u.isInBlacklist());
        });
    }

    @Test
    public void cerca_toggleCercaIndietro() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700016", "Gennaro", "Esposito", "gennaro@unisa.it");
            inserisciUtenteBase("0612700017", "Ciro", "Russo", "ciro@unisa.it");
            assertEquals(2, vista.getTabellaUtenti().getItems().size());

            setCampoDaGetter(vista, "getCognomeInserito", "Esposito");
            vista.getBottoneCerca().fire();
            assertEquals("Indietro", vista.getBottoneCerca().getText());

            vista.getBottoneCerca().fire();
            assertEquals("Cerca", vista.getBottoneCerca().getText());
            assertEquals(2, vista.getTabellaUtenti().getItems().size());
        });
    }

    @Test
    public void cerca_conEmailMostraInfo_maVaInRicerca() throws Exception {
        eseguiFx(() -> {
            autoChiudiFinestre(2000);
            setCampoDaGetter(vista, "getEmailInserita", "carmela@unisa.it");
            vista.getBottoneCerca().fire();
            assertEquals("Indietro", vista.getBottoneCerca().getText());
        });
    }

    @Test
    public void aggiornaDaModel_resetRicercaESelezione() throws Exception {
        eseguiFx(() -> {
            inserisciUtenteBase("0612700018", "Salvatore", "Ferraro", "salvatore@unisa.it");
            vista.getBottoneCerca().setText("Indietro");
            vista.getTabellaUtenti().getSelectionModel().select(0);
            controller.aggiornaDaModel();
            assertEquals("Cerca", vista.getBottoneCerca().getText());
            assertNull(vista.getTabellaUtenti().getSelectionModel().getSelectedItem());
        });
    }

    private void inserisciUtenteBase(String matricola, String nome, String cognome, String email) {
        setCampoDaGetter(vista, "getMatricolaInserita", matricola);
        setCampoDaGetter(vista, "getNomeInserito", nome);
        setCampoDaGetter(vista, "getCognomeInserito", cognome);
        setCampoDaGetter(vista, "getEmailInserita", email);
        vista.getBottoneInserisci().fire();
    }

    private static ObservableList<String> trovaRiga(ObservableList<ObservableList<String>> righe, String matricola) {
        for (ObservableList<String> r : righe) {
            if (r != null && !r.isEmpty() && matricola.equals(r.get(0))) return r;
        }
        return null;
    }

    private static void autoChiudiFinestre(int durataMs) {
        Thread t = new Thread(() -> {
            long end = System.currentTimeMillis() + durataMs;
            while (System.currentTimeMillis() < end) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ignored) {
                }
                Platform.runLater(() -> {
                    for (Window w : Window.getWindows()) {
                        if (w != null && w.isShowing()) w.hide();
                    }
                });
            }
        }, "auto-close-alert");
        t.setDaemon(true);
        t.start();
    }

    private static void setCampoDaGetter(Object view, String getterName, String value) {
        try {
            Method getter = view.getClass().getMethod(getterName);
            String probe = "PROBE_" + getterName;
            Field[] fields = view.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (!"javafx.scene.control.TextField".equals(f.getType().getName())) continue;
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

    private static class GestionePrestitiFinta extends GestionePrestiti {
        @Override
        public int contaPrestitiAttivi(Utente u) {
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
