package biblioteca.model;

import java.io.Serializable;

/**
 * @brief Contenitore serializzabile di tutti i dati della biblioteca,
 * contiene i dati relativi a libri, utenti, prestiti e relativi anche all'autenticazione.
 */

public class DatiBiblioteca implements Serializable {

    private static final long serialVersionUID = 1L;

    public GestioneLibri gestioneLibri;
    
    public GestioneUtenti gestioneUtenti;
    
    public GestionePrestiti gestionePrestiti;
    
    public Autenticazione autenticazione;
    
/**
 * @brief costruttere default
 */
    public DatiBiblioteca() {
    }
    
/**
 *@brief costruttore che inizializza tutti i parametri da gestire, grazie ai parametri che riceve può accedere a moltissime informazioni relative
 * a libri, utenti, prestiti e autenticazione.
 * @param gl gestione libri, porta con sè tutte le informazioni relative ai libri.
 * @param gu gestione utenti, porta con sè tutte le informazioni relative agli utenti
 * @param gp gestione prestiti porta con sè tutte le informazioni sui prestiti
 * @param a porta con sè tutte le informazioni reative ad autenticazione
 */
    public DatiBiblioteca(GestioneLibri gl,
                           GestioneUtenti gu,
                           GestionePrestiti gp,
                           Autenticazione a) {
        this.gestioneLibri = gl;
        this.gestioneUtenti = gu;
        this.gestionePrestiti = gp;
        this.autenticazione = a;
    }
}
