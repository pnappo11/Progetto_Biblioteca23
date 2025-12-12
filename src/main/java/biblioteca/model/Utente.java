/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @brief classe che contiene tutte le informazioni relative ad un utente: nome, cognome, matricola, email, se è o meno in blacklist, elenca e conta i prestiti attivi
 * Questa classe inoltre aggiunge o rimuove un prestito per un determinato utente oltre ad informare se un utente può o meno richiedere nuovi prestiti.
 * @author tommy
 */
public class Utente implements Serializable, Comparable<Utente> {

    private static final long serialVersionUID = 1L;

    
    public static final int MAX_PRESTITI = 3;

    
    private final String matricola;

    private String nome;
    private String cognome;
    private String email;


    private boolean inBlacklist;

  
    private final List<Prestito> prestitiAttivi;
/**
 * @brief costruttore per l'inizializzazione degli attributi dell'utente.
 * @param matricola
 * @param nome
 * @param cognome
 * @param email 
 */
    public Utente(String matricola, String nome, String cognome, String email) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.inBlacklist = false;
        this.prestitiAttivi = new ArrayList<>();
    }
/**
 * @brief metodo getter per la matricola dell'utente
 * @return matricola
 */
    public String getMatricola() {
        return matricola;
    }
/**
 * @brief metodo getter per il nome dell'utente
 * @return nome
 */
    public String getNome() {
        return nome;
    }
/**
 * @brief metodo setter, imposta il nome dell'utente
 * @param nome 
 */
    public void setNome(String nome) {
        this.nome = nome;
    }
/**
 * @brief metodo getter per il cognome dell'utente
 * @return cognome dell'utente
 */
    public String getCognome() {
        return cognome;
    }
/**
 * @brief imposta il cognome dell'utente, metodo setter
 * @param cognome 
 */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
/**
 * @brief metodo getter per l'email dell'utente
 * @return l'email
 */
    public String getEmail() {
        return email;
    }
/**
 * @brief imposta l'email dell'utente
 * @param email 
 */
    public void setEmail(String email) {
        this.email = email;
    }
/**
 * @brief metodo che restituisce true se l'utente è in blacklist, false altrimenti
 * @return inBlacklist
 */
    public boolean isInBlacklist() {
        return inBlacklist;
    }
/**
 * @brief imposta il valore di inBlacklist
 * @param valore true o false(true se è in blacklist)
 */
    public void setInBlacklist(boolean valore) {
        this.inBlacklist = valore;
    }
/**
 * @brief metodo getter sul numero di prestiti attivi per un utente
 * @return il numero di prestiti in corso per un utente
 */
    public int getNumPrestitiAttivi() {
        return prestitiAttivi.size();
    }
/**
 * @brief metodo getter per i prestiti attivi
 * @return la lista di prestiti in corso dell'utente
 */
    public List<Prestito> getPrestitiAttivi() {
        return Collections.unmodifiableList(prestitiAttivi);
    }

    /**
     * @brief verifica se un utente può richiedere nuovi prestiti.
     * @return true o false in base all'esito della verifica, true se può richiedere nuovi prestiti altrimenti false.
     */
    public boolean canNuovoPrestito() {
        return !inBlacklist && prestitiAttivi.size() < MAX_PRESTITI;
    }
/**
 * @brief metodo che permette di aggiungere un nuovo prestito.
 * @param prestito prestito da aggiungere
 */
    public void aggiungiPrestito(Prestito prestito) {
        if (prestito != null && !prestitiAttivi.contains(prestito)) {
            prestitiAttivi.add(prestito);
        }
    }
/**
 * @brief metodo che permette di rimuovere un prestito.
 * @param prestito 
 */
    public void rimuoviPrestito(Prestito prestito) {
        prestitiAttivi.remove(prestito);
    }
/**
 * @brief override del metodo compareTo su 2 utenti
 * @param o
 * @return valore intero in base al confronto restituirà 0 se sono uguali.
 */
    @Override
    public int compareTo(Utente o) {
    if (o == null || o.matricola == null) return 1;
    if (this.matricola == null) return -1;
    return this.matricola.compareTo(o.matricola);   // confronto tra stringhe
}
/**
 * @brief override del metodo equals su due utenti.
 * @param o altro utente da confrontare.
 * @return true se coincidono, false altrimenti
 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente utente = (Utente) o;
        return matricola == utente.matricola;
    }
/**
 * @brief restituisce l'hashcode sulla matricola dell'utente
 * @return valore intero dell'hashcode
 */
    @Override
    public int hashCode() {
        return Objects.hash(matricola);
    }
/**
 * @brief restituisce la stringa relativa a tutti gli attributi dell'utente
 * @return stringa utente.
 */
    @Override
    public String toString() {
        return "Utente{" +
                "matricola=" + matricola +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", inBlacklist=" + inBlacklist +
                '}';
    }
}

