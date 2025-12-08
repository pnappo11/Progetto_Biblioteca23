package biblioteca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @brief Rappresenta un socio o utente registrato nella biblioteca.
 *
 * La classe {@code Utente} mantiene i dati anagrafici, lo stato di abilitazione (blacklist)
 * e la lista dei prestiti attualmente in corso.
 * Implementa {@link Comparable} per l'ordinamento (solitamente per cognome/nome o matricola)
 * e {@link Serializable} per la persistenza su file.
 */
public class Utente implements Serializable, Comparable<Utente> {

    private static final long serialVersionUID = 1L;

    /**
     * @brief Numero massimo di libri che un utente può tenere in prestito contemporaneamente.
     * Costante di business rule impostata a 3.
     */
    public static final int MAX_PRESTITI = 3;

    /**
     * @brief Codice identificativo univoco dell'utente.
     */
    private final String matricola;

    private String nome;
    private String cognome;
    private String email;

    /**
     * @brief Flag che indica se l'utente è bloccato (es. per ritardi o sanzioni).
     * Se true, l'utente non può richiedere nuovi prestiti.
     */
    private boolean inBlacklist;

    /**
     * @brief Lista dei prestiti attualmente attivi associati a questo utente.
     */
    private final List<Prestito> prestitiAttivi;

    /**
     * @brief Costruttore per creare un nuovo utente.
     *
     * Inizializza i dati anagrafici e crea una lista vuota per i prestiti.
     * Di default, un nuovo utente non è in blacklist.
     *
     * @param matricola Codice univoco (ID).
     * @param nome      Nome dell'utente.
     * @param cognome   Cognome dell'utente.
     * @param email     Indirizzo email di contatto.
     */
    public Utente(String matricola, String nome, String cognome, String email) {
    }

    /**
     * @return La matricola univoca dell'utente.
     */
    public String getMatricola() {
    }

    /**
     * @return Il nome dell'utente.
     */
    public String getNome() {
    }

    /**
     * @param nome Il  nome da impostare.
     */
    public void setNome(String nome) {
    }

    /**
     * @return Il cognome dell'utente.
     */
    public String getCognome() {
    }

    /**
     * @param cognome Il cognome da impostare.
     */
    public void setCognome(String cognome) {
    }

    /**
     * @return L'indirizzo email dell'utente.
     */
    public String getEmail() {
    }

    /**
     * @param email La mail da impostare.
     */
    public void setEmail(String email) {
    }

    /**
     * @brief Verifica se l'utente è attualmente bloccato.
     *
     * @return {@code true} se l'utente è in blacklist, {@code false} altrimenti.
     */
    public boolean isInBlacklist() {
    }

    /**
     * @brief Imposta lo stato di blocco dell'utente.
     *
     * @param valore {@code true} per bloccare l'utente, {@code false} per riabilitarlo.
     */
    public void setInBlacklist(boolean valore) {
    }

    /**
     * @brief Restituisce il numero di prestiti attualmente in corso.
     *
     * @return La dimensione della lista dei prestiti attivi.
     */
    public int getNumPrestitiAttivi() {
    }

    /**
     * @brief Restituisce la lista dei prestiti attivi.
     *
     * @return La lista degli oggetti {@link Prestito} associati all'utente.
     */
    public List<Prestito> getPrestitiAttivi() {
    }

    /**
     * @brief Verifica se l'utente ha i requisiti per prendere un nuovo libro in prestito.
     *
     * Controlla due condizioni:
     * 1. L'utente non deve essere in blacklist.
     * 2. Il numero di prestiti attivi deve essere inferiore a {@link #MAX_PRESTITI}.
     *
     * @return {@code true} se è possibile erogare un nuovo prestito.
     */
    public boolean canNuovoPrestito() {
    }

    /**
     * @brief Aggiunge un prestito alla lista personale dell'utente.
     *
     * Da invocare quando viene registrato un nuovo prestito nel sistema.
     *
     * @param prestito L'oggetto prestito da aggiungere.
     */
    public void aggiungiPrestito(Prestito prestito) {
    }

    /**
     * @brief Rimuove un prestito dalla lista personale dell'utente.
     *
     * Da invocare quando un libro viene restituito.
     *
     * @param prestito L'oggetto prestito da rimuovere.
     */
    public void rimuoviPrestito(Prestito prestito) {
    }

    /**
     * @brief Definisce l'ordinamento naturale degli utenti.
     *
     * Solitamente ordina per cognome e nome, oppure per matricola.
     *
     * @param o L'altro utente con cui confrontare.
     * @return Un intero negativo, zero o positivo.
     */
    public int compareTo(Utente o) {
    }

    /**
     * @brief Verifica l'uguaglianza tra due utenti.
     *
     * Due utenti sono uguali se hanno la stessa matricola.
     *
     * @param o L'oggetto da confrontare.
     * @return {@code true} se le matricole coincidono.
     */
    public boolean equals(Object o) {
    }

    /**
     * @brief Calcola l'hash code dell'utente.
     *
     * Basato sulla matricola per coerenza con equals.
     *
     * @return Il valore hash.
     */
    public int hashCode() {
    }

    /**
     * @brief Restituisce una rappresentazione testuale dell'utente.
     *
     * @return Stringa contenente matricola, nome, cognome e stato.
     */
    public String toString() {
    }
}
