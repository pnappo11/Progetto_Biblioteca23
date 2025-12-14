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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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

        this.gestionePrestiti = gestionePrestiti;
        this.view = view;
        this.archivio = archivio;
        this.gestioneLibri = gestioneLibri;
        this.gestioneUtenti = gestioneUtenti;
        this.libriController = libriController;
        this.utentiController = utentiController;

        inizializzaTabella();
        collegaEventi();
    }

    private void inizializzaTabella() {
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());
    }

    private void collegaEventi() {
        view.getBottoneNuovoPrestito().setOnAction(e -> gestisciNuovoPrestito());
        view.getBottoneRestituzione().setOnAction(e -> gestisciRestituzione());
        view.getBottoneBlacklist().setOnAction(e -> gestisciBlacklist());
    }

    private void gestisciNuovoPrestito() {
        String matricolaStr = view.getMatricolaInserita();
        String isbnStr = view.getIsbnInserito();
        String dataPrevistaStr = view.getDataPrevistaInserita();

        if (isVuoto(matricolaStr) || isVuoto(isbnStr) || isVuoto(dataPrevistaStr)) {
            mostraErrore("Compila Matricola, ISBN e Data prevista per registrare un prestito.");
            return;
        }

        String matricola = matricolaStr.trim();
        Long isbn = parseLong(isbnStr, "ISBN");
        LocalDate dataPrevista = parseData(dataPrevistaStr, "Data prevista");
        if (isbn == null || dataPrevista == null) return;

        LocalDate oggi = LocalDate.now();
        if (!dataPrevista.isAfter(oggi)) {
            mostraErrore("La data prevista di restituzione deve essere successiva alla data di inizio.");
            return;
        }

        Utente utente = gestioneUtenti.trovaUtente(matricola);
        if (utente == null) {
            mostraErrore("Nessun utente trovato con matricola " + matricola);
            return;
        }

        if (utente.isInBlacklist()) {
            mostraErrore("L'utente è in blacklist e non può effettuare nuovi prestiti.");
            return;
        }

        int attivi = 0;
        for (Prestito p : gestionePrestiti.getPrestitiAttivi()) {
            if (p.getUtente() != null && matricola.equals(p.getUtente().getMatricola())) attivi++;
        }
        if (attivi >= 3) {
            mostraErrore("Impossibile registrare il prestito: l'utente ha già 3 prestiti attivi.");
            return;
        }

        Libro libro = gestioneLibri.trovaLibro(isbn);
        if (libro == null) {
            mostraErrore("Nessun libro trovato con ISBN " + isbn);
            return;
        }

        try {
            gestionePrestiti.registraPrestito(utente, libro, LocalDate.now(), dataPrevista);
        } catch (Exception ex) {
            mostraErrore(ex.getMessage());
            return;
        }

        archivio.salvaPrestiti(gestionePrestiti);
        archivio.salvaLibri(gestioneLibri);
        archivio.salvaUtenti(gestioneUtenti);

        view.pulisciCampi();
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());

        if (libriController != null) libriController.aggiornaDaModel();
        if (utentiController != null) utentiController.aggiornaDaModel();
    }

    private void gestisciRestituzione() {
        ObservableList<String> rigaSel = view.getTabellaPrestiti().getSelectionModel().getSelectedItem();
        if (rigaSel == null) {
            mostraErrore("Seleziona un prestito dalla tabella.");
            return;
        }

        String matricola = rigaSel.get(0).trim();
        String isbnStr = rigaSel.get(3);
        String dataInizioStr = rigaSel.get(5);

        Long isbn = parseLong(isbnStr, "ISBN");
        LocalDate dataInizio = parseData(dataInizioStr, "Data inizio");
        if (isbn == null || dataInizio == null) return;

        Prestito daRestituire = gestionePrestiti.trovaPrestitoAttivo(matricola, isbn, dataInizio);
        if (daRestituire == null) {
            mostraErrore("Prestito selezionato non trovato.");
            return;
        }

        gestionePrestiti.registraRestituzione(daRestituire, null);

        archivio.salvaPrestiti(gestionePrestiti);
        archivio.salvaLibri(gestioneLibri);
        archivio.salvaUtenti(gestioneUtenti);

        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());

        if (libriController != null) libriController.aggiornaDaModel();
        if (utentiController != null) utentiController.aggiornaDaModel();
    }

    private void gestisciBlacklist() {
        ObservableList<String> rigaSel = view.getTabellaPrestiti().getSelectionModel().getSelectedItem();
        if (rigaSel == null) {
            mostraErrore("Seleziona un prestito per mettere in blacklist l'utente.");
            return;
        }

        String matricola = rigaSel.get(0).trim();

        Utente utente = gestioneUtenti.trovaUtente(matricola);
        if (utente == null) {
            mostraErrore("Utente non trovato.");
            return;
        }

        utente.setInBlacklist(true);

        archivio.salvaUtenti(gestioneUtenti);

        mostraInfo("Utente " + utente.getNome() + " " + utente.getCognome() + " inserito in blacklist.");

        if (utentiController != null) utentiController.aggiornaDaModel();
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());
    }

    private void aggiornaTabella(Collection<Prestito> prestitiDaMostrare) {
        List<Prestito> lista = new ArrayList<>(prestitiDaMostrare);
        lista.sort(Comparator.comparing(Prestito::getDataPrevistaRestituzione));

        ObservableList<ObservableList<String>> righe = FXCollections.observableArrayList();

        for (Prestito p : lista) {
            Utente uPrestito = p.getUtente();
            Utente u = null;
            if (uPrestito != null && uPrestito.getMatricola() != null) {
                u = gestioneUtenti.trovaUtente(uPrestito.getMatricola());
            }
            if (u == null) u = uPrestito;

            Libro lPrestito = p.getLibro();
            Libro l = null;
            if (lPrestito != null) {
                l = gestioneLibri.trovaLibro(lPrestito.getIsbn());
            }
            if (l == null) l = lPrestito;

            ObservableList<String> riga = FXCollections.observableArrayList();
            riga.add(u != null ? u.getMatricola() : "");
            riga.add(u != null ? u.getNome() : "");
            riga.add(u != null ? u.getCognome() : "");
            riga.add(l != null ? String.valueOf(l.getIsbn()) : "");
            riga.add(l != null ? l.getTitolo() : "");
            riga.add(p.getDataInizio().toString());
            riga.add(p.getDataPrevistaRestituzione().toString());

            boolean inRitardo = LocalDate.now().isAfter(p.getDataPrevistaRestituzione());
            riga.add(inRitardo ? "Sì" : "No");

            boolean inBlacklist = (u != null && u.isInBlacklist());
            riga.add(inBlacklist ? "Sì" : "No");

            righe.add(riga);
        }

        view.getTabellaPrestiti().setItems(righe);
    }

    private boolean isVuoto(String s) {
        return s == null || s.trim().isEmpty();
    }

    private Long parseLong(String s, String nomeCampo) {
        try {
            return Long.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere un numero intero.");
            return null;
        }
    }

    private LocalDate parseData(String s, String nomeCampo) {
        try {
            return LocalDate.parse(s.trim());
        } catch (DateTimeParseException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere nel formato AAAA-MM-GG.");
            return null;
        }
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Errore");
        alert.showAndWait();
    }

    private void mostraInfo(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Informazione");
        alert.showAndWait();
    }

    public void aggiornaDaModel() {
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());
    }
}
