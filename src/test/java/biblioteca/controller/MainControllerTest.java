package biblioteca.controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    private MainController instance;

    @BeforeAll
    public static void setUpClass() throws Exception {
        Platform.setImplicitExit(false);
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException ex) {
            Platform.runLater(latch::countDown);
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @BeforeEach
    public void setUp() throws Exception {
        runFxAndWait(() -> {
            stage = new Stage();
            instance = new MainController(stage);
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        runFxAndWait(() -> {
            if (stage != null) {
                stage.hide();
            }
        });
    }

    @Test
    public void testSetPrestitiController() throws Exception {
        runFxAndWait(() -> instance.setPrestitiController(null));
        Field f = MainController.class.getDeclaredField("prestitiController");
        f.setAccessible(true);
        assertNull(f.get(instance));
    }

    @Test
    public void testAvvia() throws Exception {
        runFxAndWait(() -> instance.avvia());
        assertTrue(runFxAndWaitResult(stage::isShowing));
        assertEquals("Login Biblioteca", runFxAndWaitResult(stage::getTitle));
        Scene sc = runFxAndWaitResult(stage::getScene);
        assertNotNull(sc);
        assertNotNull(sc.getRoot());
        runFxAndWait(stage::hide);
    }

    @Test
    public void testMostraLogin() throws Exception {
        runFxAndWait(() -> instance.mostraLogin());
        assertEquals("Login Biblioteca", runFxAndWaitResult(stage::getTitle));
        Scene sc = runFxAndWaitResult(stage::getScene);
        assertNotNull(sc);
        assertNotNull(sc.getRoot());
    }

    @Test
    public void testMostraMenu() throws Exception {
        runFxAndWait(() -> instance.mostraMenu());
        assertEquals("Menu Biblioteca", runFxAndWaitResult(stage::getTitle));
        Scene sc = runFxAndWaitResult(stage::getScene);
        assertNotNull(sc);
        assertNotNull(sc.getRoot());
    }

    @Test
    public void testMostraMain() throws Exception {
        runFxAndWait(() -> instance.mostraMain(0));
        assertEquals("Biblioteca universitaria", runFxAndWaitResult(stage::getTitle));
        Scene sc = runFxAndWaitResult(stage::getScene);
        assertNotNull(sc);
        assertNotNull(sc.getRoot());
    }

    private static void runFxAndWait(Runnable r) throws Exception {
        if (Platform.isFxApplicationThread()) {
            r.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final Throwable[] err = new Throwable[1];
        Platform.runLater(() -> {
            try {
                r.run();
            } catch (Throwable t) {
                err[0] = t;
            } finally {
                latch.countDown();
            }
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
            try {
                return c.call();
            } catch (Exception e) {
                throw e;
            }
        }
        CountDownLatch latch = new CountDownLatch(1);
        final Object[] out = new Object[1];
        final Throwable[] err = new Throwable[1];
        Platform.runLater(() -> {
            try {
                out[0] = c.call();
            } catch (Throwable t) {
                err[0] = t;
            } finally {
                latch.countDown();
            }
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
