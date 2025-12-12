package biblioteca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @brief classe che si occupa di gestire i prestiti di libri permette di registrare nuovi prestiti, contarli e trovarli nel sistema.
 * @author tommy
 */
public class GestionePrestiti implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Elenco di tutti i prestiti (attivi e storici). */
    private final List<Prestito> prestiti;
    
/**
 * @brief costruttore che crea una nuova lista di prestiti.
 * @param prestiti parametro che contiene tutti i prestiti del sistema
 */
    public GestionePrestiti(List<Prestito> prestiti) {
        this.prestiti = (prestiti != null) ? prestiti : new ArrayList<Prestito>();
    }
    
/**
 * @brief istanzia un nuovo array list di prestiti.
 */
    public GestionePrestiti() {
        this(new ArrayList<Prestito>());
    }

    /** @brief Restituisce la lista di tutti i prestiti (solo lettura). */
    public List<Prestito> getPrestiti() {
        return Collections.unmodifiableList(prestiti);
    }

    /** @brief Restituisce solo i prestiti ancora attivi. */
    public List<Prestito> getPrestitiAttivi() {
        List<Prestito> attivi = new ArrayList<Prestito>();
        for (Prestito p : prestiti) {
            if (p.isAttivo()) {
                attivi.add(p);
            }
        }
        return attivi;
    }

   /**
    * @brief permette di registrare un nuovo prestito.
    * @param utente colui che riceve il libro in prestito
    * @param libro libro ricevuto in prestito.
    * @param dataPrestito data in cui il prestito viene registrato
    * @param dataPrevistaRestituzione data prevista per effettuare la restituzione del libro.
    * @return 
    */
    public Prestito registraPrestito(Utente utente, Libro libro,
                                      LocalDate dataPrestito,
                                      LocalDate dataPrevistaRestituzione) {
        if (utente == null || libro == null) {
            throw new IllegalArgumentException("Utente o libro null");
        }
        if (!libro.isDisponibile()) {
            throw new IllegalStateException("Nessuna copia disponibile del libro");
        }

        int attivi = contaPrestitiAttivi(utente);
        if (attivi >= Utente.MAX_PRESTITI) {
            throw new IllegalStateException(
                    "Impossibile registrare il prestito: l'utente ha già "
                            + attivi + " prestiti attivi (massimo consentito: "
                            + Utente.MAX_PRESTITI + ")."
            );
        }

        Prestito prestito = new Prestito(utente, libro, dataPrestito, dataPrevistaRestituzione);
        prestiti.add(prestito);

        libro.decrementaCopiaDisponibile();
        utente.aggiungiPrestito(prestito);

        return prestito;
    }
/**
 * @brief permette di registrare una restituzione.
 * @param prestito porta con sè tutte le informazioni del prestito
 * @param dataRestituzione data in cui l'utente restituisce il libro
 */
    public void registraRestituzione(Prestito prestito, LocalDate dataRestituzione) {
        if (prestito == null || !prestito.isAttivo()) {
            return;
        }

        LocalDate dataEffettiva = (dataRestituzione != null) ? dataRestituzione : LocalDate.now();
        prestito.setDataRestituzione(dataEffettiva);

        prestito.getLibro().incrementaCopiaDisponibile();
        prestito.getUtente().rimuoviPrestito(prestito);
    }

    /**
     * @brief conta quanti prestiti in corso ha un determinato utente
     * @param utente utente in questione
     * @return numero di presiti attivi 
     */
    public int contaPrestitiAttivi(Utente utente) {
        if (utente == null) return 0;
        int count = 0;
        for (Prestito p : prestiti) {
            if (p.isAttivo() && p.getUtente().equals(utente)) {
                count++;
            }
        }
        return count;
    }

    /**
     * @brief rappresenta se un utente ha o meno prestiti in corso.
     * @param utente 
     * @return true o false se un utente ha o meno prestiti attivi
     */
    public boolean haPrestitiAttiviPer(Utente utente) {
        return contaPrestitiAttivi(utente) > 0;
    }

    /**
     * @brief metodo che permette di trovare un determinato prestito.
     * @param matricola matricola dell'utente
     * @param isbn isbn del libro prestato
     * @param dataInizio data del prestito
     * @return ritorna il prestito trovato secondo i parametri inseriti.
     */
    public Prestito trovaPrestitoAttivo(String matricola, long isbn, LocalDate dataInizio) {
        for (Prestito p : prestiti) {
            if (p.isAttivo()
                    && p.getUtente().getMatricola().equals(matricola)
                    && p.getLibro().getIsbn() == isbn
                    && p.getDataInizio().equals(dataInizio)) {
                return p;
            }
        }
        return null;
    }
}
