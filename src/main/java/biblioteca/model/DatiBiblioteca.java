package biblioteca.model;

import java.io.Serializable;

/**
 * @brief Classe contenitore  per il salvataggio e caricamento dei dati.
 *
 * Raggruppa in un unico oggetto le istanze di tutti i gestori (Libri, Utenti, Prestiti, Autenticazione).
 * Questo permette di serializzare (salvare) l'intero stato dell'applicazione in un singolo file
 * invece di gestire file separati.
 */
public class DatiBiblioteca implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @brief Oggetto che contiene la lista dei libri e la logica associata.
     */
    public GestioneLibri gestioneLibri;

    /**
     * @brief Oggetto che contiene la lista degli utenti e la logica associata.
     */
    public GestioneUtenti gestioneUtenti;

    /**
     * @brief Oggetto che contiene la lista dei prestiti e la logica associata.
     */
    public GestionePrestiti gestionePrestiti;

    /**
     * @brief Oggetto che contiene le credenziali di accesso (password hash).
     */
    public Autenticazione autenticazione;

    /**
     * @brief Costruttore vuoto.
     *
     * Necessario per la serializzazione e per creare un'istanza vuota da popolare successivamente.
     */
    public DatiBiblioteca() {
    }

    /**
     * @brief Costruttore completo per inizializzare il contenitore con dati esistenti.
     *
     * @param gl L'istanza corrente di {@link GestioneLibri}.
     * @param gu L'istanza corrente di {@link GestioneUtenti}.
     * @param gp L'istanza corrente di {@link GestionePrestiti}.
     * @param a  L'istanza corrente di {@link Autenticazione}.
     */
    public DatiBiblioteca(GestioneLibri gl,
                          GestioneUtenti gu,
                          GestionePrestiti gp,
                          Autenticazione a) {
    }
}
