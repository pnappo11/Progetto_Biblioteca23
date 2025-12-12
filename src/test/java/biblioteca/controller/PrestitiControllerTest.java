package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Libro;
import biblioteca.model.Utente;
import biblioteca.view.PrestitiPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PrestitiControllerTest {

    private GestionePrestiti gestionePrestiti;
    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private PrestitiPanel view;
    private TableView<ObservableList<String>> tabella;
    private PrestitiController controller;

    @BeforeAll
    public static void setUpClass() throws Exception {
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
        runFxAndWait(() -> {
            gestionePrestiti = new GestionePrestiti();
            gestioneLibri = new GestioneLibri();
            gestioneUtenti = new GestioneUtenti();
            view = new PrestitiPanel();
            tabella = view.getTabellaPrestiti();
            controller = new PrestitiController(
                    gestionePrestiti,
                    view,
                    null,
                    gestioneLibri,
                    gestioneUtenti,
                    null,
                    null
            );
        });
    }

    @Test
    public void testAggiornaDaModel() throws Exception {
        LocalDate inizio = LocalDate.now().minusDays(10);
        LocalDate previstaRitardo = LocalDate.now().minusDays(1);
        LocalDate previstaOk = LocalDate.now().plusDays(5);

        Utente u1 = creaUtente("M1", "Mario", "Rossi", false);
        Libro l1 = creaLibro(111L, "Algoritmi", 3, 3);

        Utente u2 = creaUtente("M2", "Anna", "Bianchi", false);
        Libro l2 = creaLibro(222L, "Basi di Dati", 2, 2);

        gestionePrestiti.registraPrestito(u1, l1, inizio, previstaRitardo);
        gestionePrestiti.registraPrestito(u2, l2, inizio, previstaOk);
        u2.setInBlacklist(true);

        runFxAndWait(() -> {
            controller.aggiornaDaModel();

            ObservableList<ObservableList<String>> righe = tabella.getItems();
            assertEquals(2, righe.size());

            ObservableList<String> rM1 = trovaRigaPerMatricola(righe, "M1");
            assertNotNull(rM1);
            assertEquals("Mario", rM1.get(1));
            assertEquals("Rossi", rM1.get(2));
            assertEquals("111", rM1.get(3));
            assertEquals("Algoritmi", rM1.get(4));
            assertEquals(inizio.toString(), rM1.get(5));
            assertEquals(previstaRitardo.toString(), rM1.get(6));
            assertEquals("Sì", rM1.get(7));
            assertEquals("No", rM1.get(8));

            ObservableList<String> rM2 = trovaRigaPerMatricola(righe, "M2");
            assertNotNull(rM2);
            assertEquals("Anna", rM2.get(1));
            assertEquals("Bianchi", rM2.get(2));
            assertEquals("222", rM2.get(3));
            assertEquals("Basi di Dati", rM2.get(4));
            assertEquals(inizio.toString(), rM2.get(5));
            assertEquals(previstaOk.toString(), rM2.get(6));
            assertEquals("No", rM2.get(7));
            assertEquals("Sì", rM2.get(8));
        });
    }

    private static ObservableList<String> trovaRigaPerMatricola(ObservableList<ObservableList<String>> righe, String matricola) {
        for (ObservableList<String> r : righe) {
            if (r != null && r.size() > 0 && matricola.equals(r.get(0))) return r;
        }
        return null;
    }

    private static Utente creaUtente(String matricola, String nome, String cognome, boolean blacklist) {
        Utente u = newInstance(Utente.class);
        trySetter(u, "setMatricola", String.class, matricola);
        trySetter(u, "setNome", String.class, nome);
        trySetter(u, "setCognome", String.class, cognome);
        trySetter(u, "setInBlacklist", boolean.class, blacklist);
        setByNameContains(u, "matricol", matricola);
        setByNameContains(u, "nome", nome);
        setByNameContains(u, "cognom", cognome);
        setByNameContains(u, "black", blacklist);
        return u;
    }

    private static Libro creaLibro(Long isbn, String titolo, int tot, int disp) {
        Libro l = newInstance(Libro.class);
        trySetter(l, "setIsbn", Long.class, isbn);
        trySetter(l, "setIsbn", long.class, isbn);
        trySetter(l, "setTitolo", String.class, titolo);
        trySetter(l, "setCopieTotali", int.class, tot);
        trySetter(l, "setCopieDisponibili", int.class, disp);
        setByNameContains(l, "isbn", isbn);
        setByNameContains(l, "titolo", titolo);
        setByNameContains(l, "copietotal", tot);
        setByNameContains(l, "copiedisp", disp);
        return l;
    }

    private static void trySetter(Object obj, String name, Class<?> param, Object value) {
        try {
            Method m = obj.getClass().getMethod(name, param);
            m.setAccessible(true);
            m.invoke(obj, value);
        } catch (Throwable ignored) {
        }
    }

    private static void setByNameContains(Object obj, String key, Object value) {
        Class<?> c = obj.getClass();
        while (c != null) {
            for (Field f : c.getDeclaredFields()) {
                String n = f.getName().toLowerCase();
                if (!n.contains(key.toLowerCase())) continue;
                try {
                    f.setAccessible(true);
                    if (value == null || f.getType().isAssignableFrom(value.getClass()) || isPrimitiveBoxMatch(f.getType(), value.getClass())) {
                        if (f.getType().isPrimitive() && value instanceof Boolean b && f.getType() == boolean.class) {
                            f.setBoolean(obj, b);
                        } else if (f.getType().isPrimitive() && value instanceof Long v && f.getType() == long.class) {
                            f.setLong(obj, v);
                        } else if (f.getType().isPrimitive() && value instanceof Integer v && f.getType() == int.class) {
                            f.setInt(obj, v);
                        } else {
                            f.set(obj, value);
                        }
                        return;
                    }
                } catch (Throwable ignored) {
                }
            }
            c = c.getSuperclass();
        }
    }

    private static boolean isPrimitiveBoxMatch(Class<?> fieldType, Class<?> valueType) {
        if (!fieldType.isPrimitive()) return false;
        return (fieldType == boolean.class && valueType == Boolean.class)
                || (fieldType == long.class && valueType == Long.class)
                || (fieldType == int.class && valueType == Integer.class)
                || (fieldType == double.class && valueType == Double.class)
                || (fieldType == float.class && valueType == Float.class)
                || (fieldType == char.class && valueType == Character.class)
                || (fieldType == byte.class && valueType == Byte.class)
                || (fieldType == short.class && valueType == Short.class);
    }

    private static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> c = clazz.getDeclaredConstructor();
            c.setAccessible(true);
            return c.newInstance();
        } catch (Throwable ignored) {
        }
        Constructor<?>[] ctors = clazz.getDeclaredConstructors();
        for (Constructor<?> c : ctors) {
            try {
                c.setAccessible(true);
                Class<?>[] p = c.getParameterTypes();
                Object[] args = new Object[p.length];
                for (int i = 0; i < p.length; i++) args[i] = defaultValue(p[i]);
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

    private static void runFxAndWait(Runnable r) throws Exception {
        if (Platform.isFxApplicationThread()) {
            r.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        final Throwable[] err = new Throwable[1];
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
