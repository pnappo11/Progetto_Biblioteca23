package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.view.LoginView;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    private Stage stage;
    private MainControllerSpia mainController;
    private AutenticazioneFinta autenticazione;
    private LoginViewFinta loginView;
    private AuthController controller;

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
        eseguiFx(() -> {
            stage = new Stage();
            mainController = new MainControllerSpia(stage);
            autenticazione = new AutenticazioneFinta();
            loginView = new LoginViewFinta();

            controller = new AuthController(autenticazione, loginView, mainController);
        });
    }

    @Test
    public void loginCorretto_pulisceCampiEPassaAlMenu() throws Exception {
        eseguiFx(() -> {
            autenticazione.setEsito(true);
            loginView.impostaPassword("qualsiasi");

            loginView.cliccaLogin();

            assertEquals("", loginView.leggiUltimoErrore());
            assertTrue(loginView.campiPuliti());
            assertTrue(mainController.menuMostrato());
        });
    }

    @Test
    public void loginErrato_mostraErroreEPulisce() throws Exception {
        eseguiFx(() -> {
            autenticazione.setEsito(false);
            loginView.impostaPassword("sbagliata");

            loginView.cliccaLogin();

            assertEquals("Password errata.", loginView.leggiUltimoErrore());
            assertTrue(loginView.campiPuliti());
            assertFalse(mainController.menuMostrato());
        });
    }

    private static class AutenticazioneFinta extends Autenticazione {
        private boolean esito = false;

        public void setEsito(boolean esito) {
            this.esito = esito;
        }

        @Override
        public boolean login(String password) {
            return esito;
        }
    }

    private static class MainControllerSpia extends MainController {
        private boolean mostrato = false;

        public MainControllerSpia(Stage stage) {
            super(stage);
        }

        @Override
        public void mostraMenu() {
            mostrato = true;
        }

        public boolean menuMostrato() {
            return mostrato;
        }
    }

    private static class LoginViewFinta extends LoginView {

        private Runnable onLogin;
        private String password = "";
        private String ultimoErrore = null;
        private boolean pulito = false;

        public void impostaPassword(String p) {
            password = (p == null) ? "" : p;
            pulito = false;
        }

        public void cliccaLogin() {
            if (onLogin != null) onLogin.run();
        }

        public String leggiUltimoErrore() {
            return ultimoErrore;
        }

        public boolean campiPuliti() {
            return pulito;
        }

        @Override
        public void setOnLogin(Runnable r) {
            onLogin = r;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public void mostraErrore(String messaggio) {
            ultimoErrore = messaggio;
        }

        @Override
        public void pulisciCampi() {
            password = "";
            pulito = true;
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
