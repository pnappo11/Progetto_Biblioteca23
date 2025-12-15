package biblioteca.model;

import java.io.Serializable;
import java.util.*;

/**
 * @brief gestisce gli utenti: inserisce, modifica, elimina e trova un utente nel sistema.
 * @author tommy
 */
public class GestioneUtenti implements Serializable {

    private static final long serialVersionUID = 1L;
    private final TreeSet<Utente> utenti;
    private transient GestionePrestiti gestionePrestiti;
/**
 * costruttore di default che inizializza il treeset di utenti.
 */
    public GestioneUtenti() {
        this.utenti = new TreeSet<Utente>();
    }  
/**
 * costruttore che inizializza un treeset di utenti
 * @param utenti, il treeset che contiene gli utenti.
 */
    public GestioneUtenti(TreeSet<Utente> utenti) {
        this.utenti = (utenti != null) ? utenti : new TreeSet<Utente>();
    } 
/**
 * @brief imposta l'attributo gestione prestiti
 * @param gestionePrestiti 
 */
    public void setGestionePrestiti(GestionePrestiti gestionePrestiti) {
        this.gestionePrestiti = gestionePrestiti;
    }
   /**
    * @brief metodo getter, ritorna il treeset contenente gli utenti
    * @return 
    */
    public TreeSet<Utente> getUtenti() {
        return utenti;
    }
    /**
     * @brief inserisce un nuovo utente nella collezione
     * @param utente nuovo utente da inserire
     */
    public void inserisciUtente(Utente utente) {
        if (utente != null) {
            utenti.add(utente);
        }
    }
   /**
    * @brief modifica un utente esistente
    * @param utenteMod  utente da modificare
    */
    public void modificaUtente(Utente utenteMod) {
        if (utenteMod == null) return;

        Utente esistente = trovaUtente(utenteMod.getMatricola());
        if (esistente != null) {
            esistente.setNome(utenteMod.getNome());
            esistente.setCognome(utenteMod.getCognome());
            esistente.setEmail(utenteMod.getEmail());
            esistente.setInBlacklist(utenteMod.isInBlacklist());
        }
    }
  /**
   * @brief metodo che elimina un utente dalla collezione
   * @param matricolaStr  matricola tramite la quale procediamo all'eliminazione dell'utente.
   */
    public void eliminaUtente(String matricolaStr) {
        if (matricolaStr == null || matricolaStr.trim().isEmpty()) {
            return;
        }
        String matricola = matricolaStr.trim();
        Utente utente = trovaUtente(matricola);
        if (utente == null) return;

        boolean haPrestitiAttivi = false;
        if (gestionePrestiti != null) {
            haPrestitiAttivi = gestionePrestiti.haPrestitiAttiviPer(utente);
        }
        if (!haPrestitiAttivi) {
            utenti.remove(utente);
        }
    }
    /**
     * @brief metodo che ci permette di cercare un determinato utenti in base a determinati parametri
     * @param matricola matricola dell'utente
     * @param cognome cognome dell'utente
     * @param nome nome dell'utente.
     * @return utente trovato ed aggiunto alla collezione altrimenti null
     */
    public TreeSet<Utente> cercaUtenti(String matricola, String cognome, String nome) {
        String matrNorm    = (matricola == null) ? "" : matricola.trim();
        String cognomeNorm = (cognome   == null) ? "" : cognome.trim().toLowerCase();
        String nomeNorm    = (nome      == null) ? "" : nome.trim().toLowerCase();
        TreeSet<Utente> risultato = new TreeSet<Utente>();
        for (Utente u : utenti) {
            if (!matrNorm.isEmpty()) {
                String m = (u.getMatricola() != null) ? u.getMatricola().trim() : "";
                if (!m.equals(matrNorm)) {
                    continue; }}
            if (!cognomeNorm.isEmpty()) {
                String c = (u.getCognome() != null) ? u.getCognome().toLowerCase() : "";
                if (!c.contains(cognomeNorm)) {
                    continue;
                }
            }
            if (!nomeNorm.isEmpty()) {
                String n = (u.getNome() != null) ? u.getNome().toLowerCase() : "";
                if (!n.contains(nomeNorm)) {
                    continue;
                }
            }risultato.add(u);
        }return risultato;
    }

   /**
    * @brief permette di trovare un utente in base alla matricola
    * @param matricola
    * @return l'utente trovato altrimenti null
    */
    public Utente trovaUtente(String matricola) {
        if (matricola == null) return null;
        String mNorm = matricola.trim();
        for (Utente u : utenti) {
            if (mNorm.equals(u.getMatricola())) {
                return u;
            }
        }
        return null;
    }
/**
 * @brief inserisce un determinato utente in blacklist oppure se gia presente lo rimuove dalla blacklist
 * @param utente utente in questione
 * @param toBlacklist true o false 
 */
    public void setBlacklist(Utente utente, boolean toBlacklist) {
        if (utente != null) {
            utente.setInBlacklist(toBlacklist);
        }
    }
}