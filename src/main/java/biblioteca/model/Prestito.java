/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @brief classe prestito. 
 * Questa classe contiene tutte le informazioni relative ad un prestito come l'utente che riceve il prestito, il libro prestato,
 * la data di inizio e prevista per la restituzione.
 * @author tommy
 */
public class Prestito implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Utente utente;
    private final Libro libro;
    private final LocalDate dataInizio;
    private final LocalDate dataPrevistaRestituzione;
    private LocalDate dataRestituzione;
/**
 * @brief costruttore per l'inizializzazione dell'oggetto prestito con tutti i suoi attributi.
 * @param utente
 * @param libro
 * @param dataInizio
 * @param dataPrevistaRestituzione 
 */
    public Prestito(Utente utente, Libro libro,
                     LocalDate dataInizio, LocalDate dataPrevistaRestituzione) {
        this.utente = utente;
        this.libro = libro;
        this.dataInizio = dataInizio;
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
        this.dataRestituzione = null;
    }
    /**
     * @brief metodo getter dell'utente
     * @return l'utente del prestito
     */
    public Utente getUtente() {
        return utente;
    }
/**
 * @brief metodo getter sul libro
 * @return libro prestato
 */
    public Libro getLibro() {
        return libro;
    }
/**
 * @brief metodo getter per la data di inizio prestito
 * @return data di inizio prestito
 */
    public LocalDate getDataInizio() {
        return dataInizio;
    }
/**
 * @brief metodo getter per la data prevista  per la restituzione
 * @return data della prevista restituzione
 */
    public LocalDate getDataPrevistaRestituzione() {
        return dataPrevistaRestituzione;
    }
/**
 * @brief metodo getter per la data della restituzione
 * @return la data in cui un libro è stato restituito
 */
    public LocalDate getDataRestituzione() {
        return dataRestituzione;
    }
/**
 * @brief imposta la data d restituzione.
 * @param dataRestituzione 
 */
    public void setDataRestituzione(LocalDate dataRestituzione) {
        this.dataRestituzione = dataRestituzione;
    }

    /** Indica se il prestito è ancora attivo (non restituito). */
    public boolean isAttivo() {
        return dataRestituzione == null;
    }

   /**
    * @brief metodo che informa il bibliotecario in caso di ritardo per la restituzione, valutando la data attuale e quella prevista per la restituzione.
    * @param oggi data attuale
    * @return true o false, se è in ritardo o meno
    */
    public boolean isInRitardo(LocalDate oggi) {
        if (!isAttivo()) {
            return false;
        }
        LocalDate riferimento = (oggi != null) ? oggi : LocalDate.now();
        return riferimento.isAfter(dataPrevistaRestituzione);
    }
/**
 * @brief metodo equals su oggetti di tipo prestito, valuta i riferimenti dei suoi attributi.
 * @param o
 * @return true o false
 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestito)) return false;
        Prestito that = (Prestito) o;
        return Objects.equals(utente, that.utente) &&
               Objects.equals(libro, that.libro) &&
               Objects.equals(dataInizio, that.dataInizio);
    }
/**
 * @brief hashcode sull'oggetto prestito.
 * @return hashcode su utente,libro,dataInizio.
 */
    @Override
    public int hashCode() {
        return Objects.hash(utente, libro, dataInizio);
    }
}
