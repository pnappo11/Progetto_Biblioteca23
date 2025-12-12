package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.view.LibriPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class LibriControllerTest {

    private GestioneLibri gestioneLibri;
    private LibriPanel view;
    private LibriController controller;

    @BeforeAll
    public static void setUpClass() throws Exception {
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
            gestioneLibri = newInstance(GestioneLibri.class);

            gestioneLibri.inserisciLibro(10L, "Zeta", Arrays.asList("Mario Rossi"), 2020, 5);
            gestioneLibri.inserisciLibro(20L, "alpha", Arrays.asList("Anna Bianchi", "Luca Verdi"), 2018, 2);

            view = newInstance(LibriPanel.class);
            controller = new LibriController(gestioneLibri, view, null);
        });
    }

    @Test
    public void testAggiornaDaModel() throws Exception {
        runFxAndWait(() -> {
            view.getBottoneCerca().setText("Indietro");
            if (!view.getTabellaLibri().getItems().isEmpty()) {
                view.getTabellaLibri().getSelectionModel().select(0);
            }

            controller.aggiornaDaModel();

            assertEquals("Cerca", view.getBottoneCerca().getText());
            assertNull(view.getTabellaLibri().getSelectionModel().getSelectedItem());

            ObservableList<ObservableList<String>> righe = view.getTabellaLibri().getItems();
            assertEquals(2, righe.size());

            ObservableList<String> r0 = righe.get(0);
            ObservableList<String> r1 = righe.get(1);

            assertEquals("alpha", r0.get(1));
            assertEquals("20", r0.get(0));
            assertEquals("Anna Bianchi, Luca Verdi", r0.get(2));
            assertEquals("2018", r0.get(3));
            assertEquals("2", r0.get(4));
            assertEquals("2", r0.get(5));

            assertEquals("Zeta", r1.get(1));
            assertEquals("10", r1.get(0));
            assertEquals("Mario Rossi", r1.get(2));
            assertEquals("2020", r1.get(3));
            assertEquals("5", r1.get(4));
            assertEquals("5", r1.get(5));

            assertTrue(righe.stream().map(r -> r.get(1)).sorted(String.CASE_INSENSITIVE_ORDER).toList()
                    .equals(righe.stream().map(r -> r.get(1)).toList()));
        });
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
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        if (err[0] != null) {
            if (err[0] instanceof RuntimeException re) throw re;
            if (err[0] instanceof Error e) throw e;
            throw new RuntimeException(err[0]);
        }
    }

    private static <T> T newInstance(Class<T> clazz) {
        Constructor<?>[] ctors = clazz.getDeclaredConstructors();
        Arrays.sort(ctors, Comparator.comparingInt(Constructor::getParameterCount));
        for (Constructor<?> c : ctors) {
            try {
                c.setAccessible(true);
                Class<?>[] p = c.getParameterTypes();
                Object[] args = new Object[p.length];
                for (int i = 0; i < p.length; i++) {
                    args[i] = defaultValue(p[i]);
                }
                return clazz.cast(c.newInstance(args));
            } catch (Throwable ignored) {
            }
        }
        throw new RuntimeException("Impossibile istanziare " + clazz.getName());
    }

    private static Object defaultValue(Class<?> t) {
        if (!t.isPrimitive()) return null;
        if (t == boolean.class) return false;
        if (t == byte.class) return (byte) 0;
        if (t == short.class) return (short) 0;
        if (t == int.class) return 0;
        if (t == long.class) return 0L;
        if (t == float.class) return 0f;
        if (t == double.class) return 0d;
        if (t == char.class) return '\0';
        return 0;
    }
}
