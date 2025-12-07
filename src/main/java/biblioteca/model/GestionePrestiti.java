package biblioteca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestionePrestiti implements Serializable {

    private static final long serialVersionUID = 1L;
    private final List<Prestito> prestiti;

    public GestionePrestiti(List<Prestito> prestiti) {
    }

    public GestionePrestiti() {
    }

    public List<Prestito> getPrestiti() {
    }

    public List<Prestito> getPrestitiAttivi() {
    }

    public Prestito registraPrestito(Utente utente, Libro libro,
                                      LocalDate dataPrestito,
                                      LocalDate dataPrevistaRestituzione) {
    }

    public void registraRestituzione(Prestito prestito, LocalDate dataRestituzione) {
    }

    public int contaPrestitiAttivi(Utente utente) {
    }

    public boolean haPrestitiAttiviPer(Utente utente) {
    }

    public Prestito trovaPrestitoAttivo(String matricola, long isbn, LocalDate dataInizio) {
    }
}
