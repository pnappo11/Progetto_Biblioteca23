package biblioteca.controller;

import biblioteca.model.GestioneUtenti;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.Utente;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.UtentiPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class UtentiController {

    private final GestioneUtenti gestioneUtenti;
    private final GestionePrestiti gestionePrestiti;
    private final UtentiPanel view;
    private final ArchivioFile archivio;

    private PrestitiController prestitiController;

    private boolean inModalitaRicerca = false;

    public UtentiController(GestioneUtenti gestioneUtenti,
                             GestionePrestiti gestionePrestiti,
                             UtentiPanel view,
                             ArchivioFile archivio) {
    }

    public void setPrestitiController(PrestitiController prestitiController) {
    }

    private void inizializzaTabella() {
    }

    private void collegaEventi() {
    }

    private void gestisciInserisci() {
    }

    private void gestisciModifica() {
    }

    private void gestisciElimina() {
    }

    private void gestisciToggleBlacklist() {
    }

    private void gestisciCercaOIndietro() {
    }

    private void aggiornaTabella(Collection<Utente> utentiDaMostrare) {
    }

    private String safeLower(String s) {
    }

    private boolean isVuoto(String s) {
    }

    private void mostraErrore(String messaggio) {
    }

    private void mostraInfo(String messaggio) {
    }

    private void resetFormESelezione() {
    }

    private void resetModalitaRicerca() {
    }

    public void aggiornaDaModel() {
    }
}
