package biblioteca.persistence;

import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.GestionePrestiti;

import java.io.*;

/**
 * @brief Classe responsabile della persistenza dei dati su file system.
 *
 * La classe {@code ArchivioFile} implementa il meccanismo di salvataggio e caricamento
 * dei dati dell'applicazione. Utilizza la **Serializzazione Java** per scrivere
 * interi oggetti (i Model) su file binari (`.dat`).
 * Questo permette di mantenere lo stato dell'applicazione (libri, utenti, prestiti)
 * tra un avvio e l'altro.
 *
 * @author tommy
 */
public class ArchivioFile {

    /**
     * @brief La cartella radice dove verranno salvati i file.
     */
    private final String percorsoBase;

    // Costanti per i nomi dei file
    private static final String FILE_LIBRI      = "libri.dat";
    private static final String FILE_UTENTI     = "utenti.dat";
    private static final String FILE_PRESTITI   = "prestiti.dat";
    private static final String FILE_LOGIN      = "login.dat";

    /**
     * @brief Costruttore.
     *
     * @param percorsoBase Il percorso della directory nel file system dove leggere/scrivere i file.
     * Se la stringa è vuota, usa la directory corrente del progetto.
     */
    public ArchivioFile(String percorsoBase) {
    }

    /**
     * @brief Carica l'archivio dei libri dal file su disco.
     *
     * @return L'oggetto {@link GestioneLibri} recuperato dal file, oppure una nuova istanza
     * vuota se il file non esiste o è corrotto.
     */
    public GestioneLibri caricaLibri() {
    }

    /**
     * @brief Salva lo stato corrente dei libri su file.
     *
     * @param gl L'oggetto {@link GestioneLibri} da serializzare e salvare.
     */
    public void salvaLibri(GestioneLibri gl) {
    }

    /**
     * @brief Carica l'anagrafica utenti dal file su disco.
     *
     * @return L'oggetto {@link GestioneUtenti} recuperato, o una nuova istanza se non presente.
     */
    public GestioneUtenti caricaUtenti() {
    }

    /**
     * @brief Salva l'elenco degli utenti su file.
     *
     * @param gu L'oggetto {@link GestioneUtenti} da serializzare.
     */
    public void salvaUtenti(GestioneUtenti gu) {
    }

    /**
     * @brief Carica lo storico dei prestiti dal file su disco.
     *
     * @return L'oggetto {@link GestionePrestiti} recuperato.
     */
    public GestionePrestiti caricaPrestiti() {
    }

    /**
     * @brief Salva la situazione dei prestiti su file.
     *
     * @param gp L'oggetto {@link GestionePrestiti} da serializzare.
     */
    public void salvaPrestiti(GestionePrestiti gp) {
    }

    /**
     * @brief Carica i dati di autenticazione (hash password) dal file.
     *
     * @return L'oggetto {@link Autenticazione} contenente le credenziali.
     */
    public Autenticazione caricaAutenticazione() {
    }

    /**
     * @brief Salva le nuove credenziali di accesso su file.
     *
     * Da invocare quando viene cambiata la password.
     *
     * @param a L'oggetto {@link Autenticazione} da salvare.
     */
    public void salvaAutenticazione(Autenticazione a) {
    }

    // -------------------------------------------------------------------------
    // METODI PRIVATI GENERICI (HELPER)
    // -------------------------------------------------------------------------

    /**
     * @brief Metodo generico per deserializzare un oggetto da file.
     *
     * Apre un {@code ObjectInputStream} e tenta di leggere l'oggetto salvato.
     * Gestisce le eccezioni di IO e ClassNotFoundException internamente.
     *
     * @param <T>      Il tipo della classe da caricare (es. GestioneLibri).
     * @param nomeFile Il nome del file (es. "libri.dat").
     * @param tipo     La classe dell'oggetto atteso (utilizzata per creare istanza vuota in caso di errore).
     * @return L'oggetto letto dal file, oppure una nuova istanza vuota del tipo T se il caricamento fallisce.
     */
    private <T> T caricaOggetto(String nomeFile, Class<T> tipo) {
    }

    /**
     * @brief Metodo generico per serializzare un oggetto su file.
     *
     * Apre un {@code ObjectOutputStream} e scrive l'oggetto su disco.
     *
     * @param nomeFile Il nome del file di destinazione.
     * @param obj      L'oggetto da salvare (deve implementare Serializable).
     */
    private void salvaOggetto(String nomeFile, Object obj) {
    }
}
