package biblioteca.model;

import java.io.Serializable;
import java.util.*;

public class GestioneUtenti implements Serializable {

    private static final long serialVersionUID = 1L;
    private final TreeSet<Utente> utenti;
    private transient GestionePrestiti gestionePrestiti;

    public GestioneUtenti() {
    }

    public GestioneUtenti(TreeSet<Utente> utenti) {
    }

    public void setGestionePrestiti(GestionePrestiti gestionePrestiti) {
    }

    public TreeSet<Utente> getUtenti() {
    }

    public void inserisciUtente(Utente utente) {
    }

    public void modificaUtente(Utente utenteMod) {
    }

    public void eliminaUtente(String matricolaStr) {
    }

    public TreeSet<Utente> cercaUtenti(String matricola, String cognome, String nome) {
    }

    public Utente trovaUtente(String matricola) {
    }

    public void setBlacklist(Utente utente, boolean toBlacklist) {
    }
}
