package biblioteca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Utente implements Serializable, Comparable<Utente> {

    private static final long serialVersionUID = 1L;
    public static final int MAX_PRESTITI = 3;
    private final String matricola;
    private String nome;
    private String cognome;
    private String email;
    private boolean inBlacklist;
    private final List<Prestito> prestitiAttivi;

    public Utente(String matricola, String nome, String cognome, String email) {
    }

    public String getMatricola() {
    }

    public String getNome() {
    }

    public void setNome(String nome) {
    }

    public String getCognome() {
    }

    public void setCognome(String cognome) {
    }

    public String getEmail() {
    }

    public void setEmail(String email) {
    }

    public boolean isInBlacklist() {
    }

    public void setInBlacklist(boolean valore) {
    }

    public int getNumPrestitiAttivi() {
    }

    public List<Prestito> getPrestitiAttivi() {
    }

    public boolean canNuovoPrestito() {
    }

    public void aggiungiPrestito(Prestito prestito) {
    }

    public void rimuoviPrestito(Prestito prestito) {
    }

    public int compareTo(Utente o) {
    }

    public boolean equals(Object o) {
    }

    public int hashCode() {
    }

    public String toString() {
    }
}
