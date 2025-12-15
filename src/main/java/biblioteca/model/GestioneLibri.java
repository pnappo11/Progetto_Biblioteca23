package biblioteca.model;

import java.io.Serializable;
import java.util.*;

/**
 * @brief Gestore dei libri della biblioteca.
 * Incapsula il TreeSet<Libro2> .
 */
public class GestioneLibri implements Serializable {
    private static final long serialVersionUID = 1L;
    private final TreeSet<Libro> libri;
    /**
     * @brief costruttore per la gestione dei libri
     * @param libri è un treeset che contieme l'insieme di libri ordinati.
     */
    public GestioneLibri(TreeSet<Libro> libri) {
        this.libri = (libri != null) ? libri : new TreeSet<Libro>();}
    /**
     * @brief Costruttore senza paramteri di ingresso: crea un nuovo TreeSet.
     */
    public GestioneLibri() {
        this(new TreeSet<Libro>());}
    /** @brief Ritorna l’insieme dei libri (ordinati per ISBN). */
    public TreeSet<Libro> getLibri() {
        return libri;
    }
    /** @brief Restituisce i libri ordinati per titolo (A-Z, case insensitive). */
    public List<Libro> getLibriOrdinatiPerTitolo() {
        List<Libro> lista = new ArrayList<Libro>(libri);
        Collections.sort(
                lista,
                Comparator.comparing(
                        Libro::getTitolo,
                        String.CASE_INSENSITIVE_ORDER
                ));
        return lista;}
    /**
     * @brief Inserisce un nuovo libro.
     * Se esiste già un libro con lo stesso ISBN, incrementa le copie totali e disponibili.
     * @param isbn codice isbn del libro.
     * @param titolo titolo del libro.
     * @param autori lista che contiene gli autori del libro.
     * @param annoPubblicazione contiene l'anno di pubblicazione del libro.
     * @param copieTotali rappresenta i numero di copie totali di quel libro disponibili.
     * @return libro aggiunto alla collezione
     */
    public Libro inserisciLibro(long isbn, String titolo, List<String> autori,
                                 int annoPubblicazione, int copieTotali) {
        if (copieTotali <= 0) {
            throw new IllegalArgumentException("Le copie totali devono essere > 0");
        }
        Libro esistente = trovaLibro(isbn);
        if (esistente != null) {
            // logica "aggiungo copie" se il libro esiste già
            esistente.setCopieTotali(esistente.getCopieTotali() + copieTotali);
            esistente.setCopieDisponibili(
                    esistente.getCopieDisponibili() + copieTotali
            );
            return esistente;
        }
        Libro libro = new Libro(isbn, titolo, autori, annoPubblicazione, copieTotali);
        libri.add(libro);
        return libro;
    }
    /**
     * @brief Modifica di un libro già esistente.
     * (Qui si passa direttamente l'oggetto libro da aggiornare.)
     * @param codiceIsbn codice isbn del libro.
     * @param titolo titolo del libro.
     * @param autori lista che contiene gli autori del libro.
     * @param annoPubblicazione contiene l'anno di pubblicazione del libro.
     * @param copieTotali rappresenta i numero di copie totali di quel libro disponibili.
     */
    public void modificaLibro(Libro libro, String titolo, List<String> autori,
                              int annoPubblicazione, int copieTotali) {
        if (libro == null) return;
        libro.setTitolo(titolo);
        libro.setAutori(autori);
        libro.setAnnoPubblicazione(annoPubblicazione);
        libro.setCopieTotali(copieTotali);
    }
    /** @brief Elimina un libro dato il codice ISBN (formato stringa).
     *@param codiceIsbn codice isbn del libro di cui ci serviamo per procedere all'eliminazione del libro.
    */
    public void eliminaLibro(String codiceIsbn) {
        if (codiceIsbn == null || codiceIsbn.trim().isEmpty()) {
            return;}
        long isbn;
        try {
            isbn = Long.parseLong(codiceIsbn.trim());
        } catch (NumberFormatException ex) {
            return;}
        libri.removeIf(l -> l.getIsbn() == isbn);}
    /**
     * @brief Ricerca libri per ISBN / Titolo / Autore.
     * @param codiceIsbn codice isbn del libro.
     * @param titolo titolo del libro.
     * @param autore rappresenta l' autore del libro.
     * @return libro trovato
     */
    public TreeSet<Libro> cercaLibri(String codiceIsbn, String titolo, String autore) {
        Long isbn = null;
        if (codiceIsbn != null && !codiceIsbn.trim().isEmpty()) {
            try {
                isbn = Long.parseLong(codiceIsbn.trim());
            } catch (NumberFormatException ignored) {
            }
        }
        String titoloNorm = (titolo == null) ? "" : titolo.trim().toLowerCase();
        String autoreNorm = (autore == null) ? "" : autore.trim().toLowerCase();
        TreeSet<Libro> risultato = new TreeSet<Libro>();
        for (Libro l : libri) {
            if (isbn != null && l.getIsbn() != isbn.longValue()) {
                continue;
            }
            if (!titoloNorm.isEmpty() &&
                    (l.getTitolo() == null ||
                            !l.getTitolo().toLowerCase().contains(titoloNorm))) {
                continue;
            }
            if (!autoreNorm.isEmpty()) {
                boolean matchAutore = false;
                for (String a : l.getAutori()) {
                    if (a != null && a.toLowerCase().contains(autoreNorm)) {
                        matchAutore = true;
                        break;
                    }
                }
                if (!matchAutore) continue;
            }
            risultato.add(l);
        }
        return risultato;
    }
    /** @brief Trova un libro per ISBN oppure null se non esiste.
     * @param isbn codice isbn del libro.
     @return libro trovato
     */
    public Libro trovaLibro(long isbn) {
        for (Libro l : libri) {
            if (l.getIsbn() == isbn) return l;
        }
        return null;
    }
}