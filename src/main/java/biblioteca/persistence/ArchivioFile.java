package biblioteca.persistence;

import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.GestionePrestiti;

import java.io.*;

/**
 * @brief Gestisce la persistenza dei dati del sistema bibliotecario su file binari.
 * Questa classe si occupa di serializzare e deserializzare gli oggetti principali del modello
 * (libri, utenti, prestiti e credenziali) utilizzando gli stream di oggetti Java.
 */
public class ArchivioFile {

    private final String percorsoBase;

    private static final String FILE_LIBRI      = "libri.dat";
    private static final String FILE_UTENTI     = "utenti.dat";
    private static final String FILE_PRESTITI   = "prestiti.dat";
    private static final String FILE_LOGIN      = "login.dat";

    /**
     * @brief Costruttore della classe.
     * @param percorsoBase Il percorso della cartella dove verranno salvati/letti i file.
     */
    public ArchivioFile(String percorsoBase) {
        this.percorsoBase = percorsoBase;
    }

    /**
     * @brief Carica l'elenco dei libri dal file locale.
     * @return Un'istanza di GestioneLibri. Se il file non esiste, ritorna un nuovo oggetto vuoto.
     */
    public GestioneLibri caricaLibri() {
        GestioneLibri gl = caricaOggetto(FILE_LIBRI, GestioneLibri.class);
        if (gl == null) {
            gl = new GestioneLibri();      // nuovo se il file non esiste
        }
        return gl;
    }

    /**
     * @brief Salva lo stato attuale della gestione libri su file.
     * @param gl L'oggetto GestioneLibri da serializzare.
     */
    public void salvaLibri(GestioneLibri gl) {
        salvaOggetto(FILE_LIBRI, gl);
    }

    /**
     * @brief Carica l'elenco degli utenti dal file locale.
     * @return Un'istanza di GestioneUtenti. Se il file non esiste, ritorna un nuovo oggetto vuoto.
     */
    public GestioneUtenti caricaUtenti() {
        GestioneUtenti gu = caricaOggetto(FILE_UTENTI, GestioneUtenti.class);
        if (gu == null) {
            gu = new GestioneUtenti();
        }
        return gu;
    }

    /**
     * @brief Salva lo stato attuale della gestione utenti su file.
     * @param gu L'oggetto GestioneUtenti da serializzare.
     */
    public void salvaUtenti(GestioneUtenti gu) {
        salvaOggetto(FILE_UTENTI, gu);
    }

    /**
     * @brief Carica l'elenco dei prestiti dal file locale.
     * @return Un'istanza di GestionePrestiti. Se il file non esiste, ritorna un nuovo oggetto vuoto.
     */
    public GestionePrestiti caricaPrestiti() {
        GestionePrestiti gp = caricaOggetto(FILE_PRESTITI, GestionePrestiti.class);
        if (gp == null) {
            gp = new GestionePrestiti();
        }
        return gp;
    }

    /**
     * @brief Salva lo stato attuale dei prestiti su file.
     * @param gp L'oggetto GestionePrestiti da serializzare.
     */
    public void salvaPrestiti(GestionePrestiti gp) {
        salvaOggetto(FILE_PRESTITI, gp);
    }

    /**
     * @brief Carica i dati di autenticazione (credenziali admin) dal file locale.
     * @return Un'istanza di Autenticazione. Se il file non esiste, ritorna un'istanza con valori predefiniti.
     */
    public Autenticazione caricaAutenticazione() {
        Autenticazione a = caricaOggetto(FILE_LOGIN, Autenticazione.class);
        if (a == null) {
            a = new Autenticazione();  // password default (es. "admin")
        }
        return a;
    }

    /**
     * @brief Salva le credenziali di autenticazione su file.
     * @param a L'oggetto Autenticazione da serializzare.
     */
    public void salvaAutenticazione(Autenticazione a) {
        salvaOggetto(FILE_LOGIN, a);
    }

    /**
     * @brief Metodo generico privato per la lettura di un oggetto serializzato.
     * @param <T> Tipo dell'oggetto atteso.
     * @param nomeFile Nome del file da leggere.
     * @param tipo Classe di riferimento per il casting sicuro.
     * @return L'oggetto deserializzato di tipo T, oppure null in caso di errore o file non trovato.
     */
    private <T> T caricaOggetto(String nomeFile, Class<T> tipo) {
        File file = new File(percorsoBase, nomeFile);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (tipo.isInstance(obj)) {
                return tipo.cast(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @brief Metodo generico privato per la scrittura di un oggetto su file,
     * crea automaticamente la directory di destinazione se non esistente.
     * @param nomeFile Nome del file da creare/sovrascrivere.
     * @param obj L'oggetto da serializzare.
     */
    private void salvaOggetto(String nomeFile, Object obj) {
        if (obj == null) return;

        File dir = new File(percorsoBase);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, nomeFile);
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}