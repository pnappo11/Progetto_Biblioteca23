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
 * @brief classe libro permette di personalizzare libro in base ai suoi attributi
 * contiene tutti i getter e setter dei suoi campi.
 * ha come attributi titolo, isbn , autore, anno di pubblicazione, copie totali e copie disponibili.
 * @author tommy
 */
public class Libro implements Serializable, Comparable<Libro> {
    private static final long serialVersionUID = 1L;
    private final long isbn;
    private String titolo;
    private List<String> autori;
    private int annoPubblicazione;
    private int copieTotali;
    private int copieDisponibili;
    /**
     * @brief costruttore per l'inizializzazione dei suoi attributi
     * @param isbn
     * @param titolo
     * @param autori
     * @param annoPubblicazione
     * @param copieTotali
     * @param copieDisponibili 
     */
    public Libro(long isbn, String titolo, List<String> autori,
                  int annoPubblicazione, int copieTotali, int copieDisponibili) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.autori = new ArrayList<String>(
                autori != null ? autori : Collections.<String>emptyList()
        );
        this.annoPubblicazione = annoPubblicazione;
        this.copieTotali = copieTotali;
        this.copieDisponibili = copieDisponibili;
    }

    /**
     * @brief costruttore che non contiene le copie disponibili di un determinato libro
     * @param isbn
     * @param titolo
     * @param autori
     * @param annoPubblicazione
     * @param copieTotali 
     */
    public Libro(long isbn, String titolo, List<String> autori,
                  int annoPubblicazione, int copieTotali) {
        this(isbn, titolo, autori, annoPubblicazione, copieTotali, copieTotali);
    }
/**
 * @brief metodo getter per l'isbn del libro
 * @return ritorna il codice isbn
 */
    public long getIsbn() {
        return isbn;
    }
/**
 * @brief metodo getter
 * @return ritorna il titolo del libro
 */
    public String getTitolo() {
        return titolo;
    }
/**
 * @brief metodo setter, imposta il titolo del libro
 * @param titolo 
 */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
/**
 * @brief metodo getter, ritorna la lista di autori del libro
 * @return la lista di autori del libro
 */
    public List<String> getAutori() {
        return Collections.unmodifiableList(autori);
    }
/**
 * @brief imposta gli autori del libro
 * @param autori lista contenente gli autori del libro
 */
    public void setAutori(List<String> autori) {
        this.autori = new ArrayList<String>(
                autori != null ? autori : Collections.<String>emptyList()
        );
    }
/**
 * @brief metodo getter sull'anno di pubblicazione del libro.
 * @return anno di pubblicazione del libro
 */
    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
/**
 * @brief imposta l'anno di pubblicazione del libro
 * @param annoPubblicazione 
 */
    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }
/**
 * @brief getter per le copie 
 * @return numero totale di copie disponibili
 */
    public int getCopieTotali() {
        return copieTotali;
    }
/**
 * @brief imposta il numero di copie totali del libro
 * @param copieTotali 
 */
    public void setCopieTotali(int copieTotali) {
        this.copieTotali = copieTotali;
        if (copieDisponibili > copieTotali) {
            copieDisponibili = copieTotali;
        }
    }
/**
 * @brief metodo getter sulle copie
 * @return il numero di copie disponibili
 */
    public int getCopieDisponibili() {
        return copieDisponibili;
    }
/**
 * @brief setter per le copie disponibili, imposta il numero di copie disponibili per un determinato libro.
 * @param copieDisponibili 
 */
    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili = copieDisponibili;
    }

    /** Restituisce il numero di autori come stringa. */
    public String getNumAutori() {
        return String.valueOf(autori.size());
    }

    /**
     * 
     * @return true o false in base alla disponibilità del libro
     */
    public boolean isDisponibile() {
        return copieDisponibili > 0;
    }

    /**
     * @brief metodo che serve a decrementare le copie disponibili, richiamato quando prestiamo un libro.
     */
    public void decrementaCopiaDisponibile() {
        if (copieDisponibili <= 0) {
            throw new IllegalStateException("Nessuna copia disponibile");
        }
        copieDisponibili--;
    }

    /**
     * @brief incrementa le copie disponibili quando un libro viene restituito.
     */
    public void incrementaCopiaDisponibile() {
        if (copieDisponibili < copieTotali) {
            copieDisponibili++;
        }
    }
/**
 * @brief override del metodo compareTo per confrontare tra loto due isbn
 * @param other un altro oggetto libro
 * @return ritorna valore intero, 0 se coincidono
 */
    @Override
    public int compareTo(Libro other) {
        return Long.compare(this.isbn, other.isbn);
    }
/**
 * @brief override del metodo equals per valutare i riferimenti degli oggetti
 * @param o altro oggetto che serve per il confronto
 * @return true o false in base all'esito del confronto
 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return isbn == libro.isbn;
    }
/**
 * @return hashcode sull'isbn
 */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
/**
 * @brief override del metodo to string per la stampa delle componenti del libro
 * @return stringa contenente tutti gli attributi
 */
    @Override
    public String toString() {
        return "Libro{" +
                "isbn=" + isbn +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", copieTotali=" + copieTotali +
                ", copieDisponibili=" + copieDisponibili +
                '}';
    }
}