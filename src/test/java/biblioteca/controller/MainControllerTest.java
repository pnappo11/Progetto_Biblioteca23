package biblioteca.controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest {

    private Stage stage;
    private MainController controller;

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
            stage.setScene(new Scene(new Pane(), 200, 100));
            controller = new MainController(stage);
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        eseguiFx(() -> {
            for (Window w : Window.getWindows()) {
                if (w != null && w.isShowing()) w.hide();
            }
        });
    }

    @Test
    public void setPrestitiController_impostaCampo() throws Exception {
        eseguiFx(() -> controller.setPrestitiController(null));

        Field f = MainController.class.getDeclaredField("prestitiController");
        f.setAccessible(true);
        assertNull(f.get(controller));
    }

    @Test
    public void mostraLogin_impostaTitoloEScena() throws Exception {
        eseguiFx(() -> controller.mostraLogin());

        assertEquals("Login Biblioteca", stage.getTitle());
        assertNotNull(stage.getScene());
    }

    @Test
    public void mostraMenu_impostaTitoloEScena() throws Exception {
        eseguiFx(() -> controller.mostraMenu());

        assertEquals("Menu Biblioteca", stage.getTitle());
        assertNotNull(stage.getScene());
    }

    @Test
    public void mostraMain_impostaTitoloEScena() throws Exception {
        eseguiFx(() -> controller.mostraMain(0));

        assertEquals("Biblioteca universitaria", stage.getTitle());
        assertNotNull(stage.getScene());
    }

    @Test
    public void avvia_mostraLoginEVisualizzaStage() throws Exception {
        eseguiFx(() -> controller.avvia());

        assertEquals("Login Biblioteca", stage.getTitle());
        assertTrue(stage.isShowing());
    }

    @Test
    public void mostraLogin_stageNull_lanciaNPE() {
        MainController c = new MainController(null);
        assertThrows(NullPointerException.class, c::mostraLogin);
    }

    @Test
    public void mostraMenu_stageNull_lanciaNPE() {
        MainController c = new MainController(null);
        assertThrows(NullPointerException.class, c::mostraMenu);
    }

    @Test
    public void mostraMain_stageNull_lanciaNPE() {
        MainController c = new MainController(null);
        assertThrows(NullPointerException.class, () -> c.mostraMain(0));
    }

    @Test
    public void mostraMain_tabIndexFuoriRange_nonBloccaIlTest() throws Exception {
        eseguiFx(() -> {
            try {
                controller.mostraMain(-1);
                assertEquals("Biblioteca universitaria", stage.getTitle());
                assertNotNull(stage.getScene());
            } catch (RuntimeException ex) {
                assertTrue(ex instanceof IndexOutOfBoundsException
                        || ex instanceof IllegalArgumentException
                        || ex.getCause() instanceof IndexOutOfBoundsException);
            }
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
