package biblioteca.model;

import java.io.Serializable;
import java.util.*;

public class GestioneLibri implements Serializable {

    private static final long serialVersionUID = 1L;
    private final TreeSet<Libro> libri;

    public GestioneLibri(TreeSet<Libro> libri) {
    }

    public GestioneLibri() {
    }

    public TreeSet<Libro> getLibri() {
    }

    public List<Libro> getLibriOrdinatiPerTitolo() {
    }

    public Libro inserisciLibro(long isbn, String titolo, List<String> autori,
                                 int annoPubblicazione, int copieTotali) {
    }

    public void modificaLibro(Libro libro, String titolo, List<String> autori,
                              int annoPubblicazione, int copieTotali) {
    }

    public void eliminaLibro(String codiceIsbn) {
    }

    public TreeSet<Libro> cercaLibri(String codiceIsbn, String titolo, String autore) {
    }

    public Libro trovaLibro(long isbn) {
    }
}
