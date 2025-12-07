package biblioteca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Prestito implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Utente utente;
    private final Libro libro;
    private final LocalDate dataInizio;
    private final LocalDate dataPrevistaRestituzione;
    private LocalDate dataRestituzione;

    public Prestito(Utente utente, Libro libro,
                     LocalDate dataInizio, LocalDate dataPrevistaRestituzione) {
    }

    public Utente getUtente() {
    }

    public Libro getLibro() {
    }

    public LocalDate getDataInizio() {
    }

    public LocalDate getDataPrevistaRestituzione() {
    }

    public LocalDate getDataRestituzione() {
    }

    public void setDataRestituzione(LocalDate dataRestituzione) {
    }

    public boolean isAttivo() {
    }

    public boolean isInRitardo(LocalDate oggi) {
    }

    public boolean equals(Object o) {
    }

    public int hashCode() {
    }
}
