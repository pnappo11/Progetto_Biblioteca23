package biblioteca.controller;

import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.Libro;
import biblioteca.model.Prestito;
import biblioteca.model.Utente;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.PrestitiPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;

public class PrestitiController {

    private final GestionePrestiti gestionePrestiti;
    private final GestioneLibri gestioneLibri;
    private final GestioneUtenti gestioneUtenti;
    private final PrestitiPanel view;
    private final ArchivioFile archivio;
    private final LibriController libriController;
    private final UtentiController utentiController;

    public PrestitiController(GestionePrestiti gestionePrestiti,
                               PrestitiPanel view,
                               ArchivioFile archivio,
                               GestioneLibri gestioneLibri,
                               GestioneUtenti gestioneUtenti,
                               LibriController libriController,
                               UtentiController utentiController) {
    }

    private void inizializzaTabella() {
    }

    private void collegaEventi() {
    }

    private void gestisciNuovoPrestito() {
    }

    private void gestisciRestituzione() {
    }

    private void gestisciBlacklist() {
    }

    private void aggiornaTabella(Collection<Prestito> prestitiDaMostrare) {
    }

    private boolean isVuoto(String s) {
    }

    private Long parseLong(String s, String nomeCampo) {
    }

    private LocalDate parseData(String s, String nomeCampo) {
    }

    private void mostraErrore(String messaggio) {
    }

    private void mostraInfo(String messaggio) {
    }

    public void aggiornaDaModel() {
    }
}
