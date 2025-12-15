package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.view.LoginView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

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

    @Test
    public void login_ok_vaAlMenu_pulisceCampi_eAzzeraErrore() throws Exception {
        eseguiFx(() -> {
            Stage s = new Stage();
            s.setScene(new Scene(new Pane(), 100, 80));

            MainControllerSpy main = new MainControllerSpy(s);
            LoginViewStub view = new LoginViewStub();
            AutenticazioneStub auth = new AutenticazioneStub("pw");

            new AuthController(auth, view, main);

            view.password = "  pw  ";
            view.fireLogin();

            assertTrue(main.menuShown);
            assertEquals("", view.lastError);
            assertTrue(view.cleared);
        });
    }

    @Test
    public void login_error_passwordErrata_mostraErrore_ePulisceCampi() throws Exception {
        eseguiFx(() -> {
            Stage s = new Stage();
            s.setScene(new Scene(new Pane(), 100, 80));

            MainControllerSpy main = new MainControllerSpy(s);
            LoginViewStub view = new LoginViewStub();
            AutenticazioneStub auth = new AutenticazioneStub("pw");

            new AuthController(auth, view, main);

            view.password = "wrong";
            view.fireLogin();

            assertFalse(main.menuShown);
            assertEquals("Password errata.", view.lastError);
            assertTrue(view.cleared);
        });
    }

    @Test
    public void login_error_passwordNull_trattataComeVuota() throws Exception {
        eseguiFx(() -> {
            Stage s = new Stage();
            s.setScene(new Scene(new Pane(), 100, 80));

            MainControllerSpy main = new MainControllerSpy(s);
            LoginViewStub view = new LoginViewStub();
            AutenticazioneStub auth = new AutenticazioneStub("pw");

            new AuthController(auth, view, main);

            view.password = null;
            view.fireLogin();

            assertFalse(main.menuShown);
            assertEquals("Password errata.", view.lastError);
            assertTrue(view.cleared);
        });
    }

    private static class AutenticazioneStub extends Autenticazione {
        private final String expected;
        AutenticazioneStub(String expected) { this.expected = expected; }
        @Override
        public boolean login(String password) {
            if (password == null) password = "";
            return expected.equals(password.trim());
        }
    }

    private static class MainControllerSpy extends MainController {
        boolean menuShown = false;
        MainControllerSpy(Stage s) { super(s); }
        @Override
        public void mostraMenu() { menuShown = true; }
    }

    private static class LoginViewStub extends LoginView {
        Runnable onLogin;
        String password;
        String lastError;
        boolean cleared = false;

        @Override
        public void setOnLogin(Runnable r) { this.onLogin = r; }

        @Override
        public String getPassword() { return password; }

        @Override
        public void mostraErrore(String msg) { lastError = msg; }

        @Override
        public void pulisciCampi() { cleared = true; password = ""; }

        void fireLogin() { if (onLogin != null) onLogin.run(); }
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
