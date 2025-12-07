package biblioteca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Libro implements Serializable, Comparable<Libro> {

    private static final long serialVersionUID = 1L;
    private final long isbn;
    private String titolo;
    private List<String> autori;
    private int annoPubblicazione;
    private int copieTotali;
    private int copieDisponibili;

    public Libro(long isbn, String titolo, List<String> autori,
                  int annoPubblicazione, int copieTotali, int copieDisponibili) {
    }

    public Libro(long isbn, String titolo, List<String> autori,
                  int annoPubblicazione, int copieTotali) {
    }

    public long getIsbn() {
    }

    public String getTitolo() {
    }

    public void setTitolo(String titolo) {
    }

    public List<String> getAutori() {
    }

    public void setAutori(List<String> autori) {
    }

    public int getAnnoPubblicazione() {
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
    }

    public int getCopieTotali() {
    }

    public void setCopieTotali(int copieTotali) {
    }

    public int getCopieDisponibili() {
    }

    public void setCopieDisponibili(int copieDisponibili) {
    }

    public String getNumAutori() {
    }

    public boolean isDisponibile() {
    }

    public void decrementaCopiaDisponibile() {
    }

    public void incrementaCopiaDisponibile() {
    }

    public int compareTo(Libro other) {
    }

    public boolean equals(Object o) {
    }

    public int hashCode() {
    }

    public String toString() {
    }
}
