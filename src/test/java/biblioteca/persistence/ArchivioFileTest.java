package biblioteca.persistence;

import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;

public class ArchivioFileTest {

    private final Path baseDir;

    private static final String FILE_LIBRI = "libri.dat";
    private static final String FILE_UTENTI = "utenti.dat";
    private static final String FILE_PRESTITI = "prestiti.dat";
    private static final String FILE_AUTENTICAZIONE = "autenticazione.dat";

    public ArchivioFileTest(String directory) {
        if (directory == null || directory.trim().isEmpty()) {
            directory = ".";
        }
        this.baseDir = Paths.get(directory);
    }

    public void salvaLibri(GestioneLibri gl) {
        salvaOggetto(gl, FILE_LIBRI);
    }

    public GestioneLibri caricaLibri() {
        return caricaOggetto(FILE_LIBRI, GestioneLibri.class, GestioneLibri::new);
    }

    public void salvaUtenti(GestioneUtenti gu) {
        salvaOggetto(gu, FILE_UTENTI);
    }

    public GestioneUtenti caricaUtenti() {
        return caricaOggetto(FILE_UTENTI, GestioneUtenti.class, GestioneUtenti::new);
    }

    public void salvaPrestiti(GestionePrestiti gp) {
        salvaOggetto(gp, FILE_PRESTITI);
    }

    public GestionePrestiti caricaPrestiti() {
        return caricaOggetto(FILE_PRESTITI, GestionePrestiti.class, GestionePrestiti::new);
    }

    public void salvaAutenticazione(Autenticazione a) {
        salvaOggetto(a, FILE_AUTENTICAZIONE);
    }

    public Autenticazione caricaAutenticazione() {
        return caricaOggetto(FILE_AUTENTICAZIONE, Autenticazione.class, Autenticazione::new);
    }

    private Path pathFile(String nomeFile) {
        return baseDir.resolve(nomeFile);
    }

    private void salvaOggetto(Object obj, String nomeFile) {
        if (obj == null) return;

        try {
            Files.createDirectories(baseDir);
            Path p = pathFile(nomeFile);

            if (Files.exists(p) && Files.isDirectory(p)) return;

            try (OutputStream os = Files.newOutputStream(
                    p,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
                 BufferedOutputStream bos = new BufferedOutputStream(os);
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {

                oos.writeObject(obj);
                oos.flush();
            }
        } catch (Exception ignored) {
        }
    }

    private <T> T caricaOggetto(String nomeFile, Class<T> tipo, Supplier<T> fallback) {
        try {
            Path p = pathFile(nomeFile);

            if (!Files.exists(p) || Files.isDirectory(p)) {
                return fallback.get();
            }

            try (InputStream is = Files.newInputStream(p, StandardOpenOption.READ);
                 BufferedInputStream bis = new BufferedInputStream(is);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {

                Object letto = ois.readObject();
                if (tipo.isInstance(letto)) {
                    return tipo.cast(letto);
                }
            }
        } catch (Exception ignored) {
        }
        return fallback.get();
    }
}
