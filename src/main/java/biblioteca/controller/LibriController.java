package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.Libro;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LibriPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class LibriController {

    private final GestioneLibri gestioneLibri;
    private final LibriPanel view;
    private final ArchivioFile archivio;

    private boolean inModalitaRicerca = false;

    public LibriController(GestioneLibri gestioneLibri,
                            LibriPanel view,
                            ArchivioFile archivio) {
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

    private void gestisciCercaOIndietro() {
    }

    private void aggiornaTabella(Collection<Libro> libriDaMostrare) {
    }

    private void resetFormESelezione() {
    }

    private void resetModalitaRicerca() {
    }

    private boolean isVuoto(String s) {
    }

    private Integer parseInt(String s, String nomeCampo) {
    }

    private Long parseLong(String s, String nomeCampo) {
    }

    private List<String> parseAutori(String testo) {
    }

    private String joinAutori(List<String> autori) {
    }

    private Libro trovaLibroPerIsbn(Long isbn) {
    }

    private void mostraErrore(String messaggio) {
    }

    private void mostraInfo(String messaggio) {
    }

    public void aggiornaDaModel() {
    }
}
