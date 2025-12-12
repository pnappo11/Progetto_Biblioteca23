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

/**
 * @brief Controller per la gestione dei prestiti.
 * Questa classe coordina le operazioni relative ai prestiti, collegando la logica di business
 * (GestionePrestiti) con la vista (PrestitiPanel) e interagendo con i controller di Libri e Utenti
 * per mantenere la consistenza dei dati.
 */
public class PrestitiController {

    private final GestionePrestiti gestionePrestiti;
    private final GestioneLibri gestioneLibri;
    private final GestioneUtenti gestioneUtenti;

    private final PrestitiPanel view;
    private final ArchivioFile archivio;

    private final LibriController libriController;
    private final UtentiController utentiController;

    /**
     * @brief Costruttore della classe PrestitiController.
     * Inizializza il controller con tutte le dipendenze necessarie, popola la tabella iniziale
     * e collega gli eventi della vista.
     * @param gestionePrestiti  Il modello per la gestione dei prestiti.
     * @param view              La vista del pannello prestiti.
     * @param archivio          Il gestore della persistenza su file.
     * @param gestioneLibri     Il modello dei libri (necessario per verificare disponibilità).
     * @param gestioneUtenti    Il modello degli utenti (necessario per associare il prestito).
     * @param libriController   Controller dei libri per notificare aggiornamenti (es. copie disponibili).
     * @param utentiController  Controller degli utenti per notificare aggiornamenti (es. stato blacklist).
     */
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

    /** @brief Inizializza la tabella visualizzando i prestiti attivi. */
    private void inizializzaTabella() {
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());
    }

    /** @brief Collega i metodi di gestione agli eventi dei pulsanti della vista. */
    private void collegaEventi() {
        view.getBottoneNuovoPrestito().setOnAction(e -> gestisciNuovoPrestito());
        view.getBottoneRestituzione().setOnAction(e -> gestisciRestituzione());
        view.getBottoneBlacklist().setOnAction(e -> gestisciBlacklist());
    }

    /**
     * @brief Gestisce la creazione di un nuovo prestito.
     * Valida gli input (Matricola, ISBN, Data), verifica l'esistenza di utente e libro,
     * controlla lo stato di blacklist e la disponibilità del libro. Se tutto è valido,
     * registra il prestito, salva su file e aggiorna le viste collegate.
     */
    private void gestisciNuovoPrestito() {
        String matricolaStr    = view.getMatricolaInserita();
        String isbnStr         = view.getIsbnInserito();
        String dataPrevistaStr = view.getDataPrevistaInserita();

        if (isVuoto(matricolaStr) || isVuoto(isbnStr) || isVuoto(dataPrevistaStr)) {
            mostraErrore("Compila Matricola, ISBN e Data prevista per registrare un prestito.");
            return;
        }

        String matricola = matricolaStr.trim();
        Long isbn = parseLong(isbnStr, "ISBN");
        LocalDate dataPrevista = parseData(dataPrevistaStr, "Data prevista");

        if (isbn == null || dataPrevista == null) return;

        Utente utente = gestioneUtenti.trovaUtente(matricola);
        if (utente == null) {
            mostraErrore("Nessun utente trovato con matricola " + matricola);
            return;
        }

        if (utente.isInBlacklist()) {
            mostraErrore("L'utente è in blacklist e non può effettuare nuovi prestiti.");
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

    /**
     * @brief Gestisce la restituzione di un libro.
     * Identifica il prestito selezionato nella tabella e registra la restituzione
     * con la data odierna. Aggiorna successivamente file e viste.
     */
    private void gestisciRestituzione() {
        ObservableList<String> rigaSel =
                view.getTabellaPrestiti().getSelectionModel().getSelectedItem();

        if (rigaSel == null) {
            mostraErrore("Seleziona un prestito dalla tabella.");
            return;
        }

        String matricola = rigaSel.get(0).trim();
        String isbnStr    = rigaSel.get(3);
        String dataInizioStr = rigaSel.get(5);

        Long isbn = parseLong(isbnStr, "ISBN");
        LocalDate dataInizio = parseData(dataInizioStr, "Data inizio");
        if (isbn == null || dataInizio == null) return;

        Prestito daRestituire =
                gestionePrestiti.trovaPrestitoAttivo(matricola, isbn, dataInizio);

        if (daRestituire == null) {
            mostraErrore("Prestito selezionato non trovato.");
            return;
        }

        gestionePrestiti.registraRestituzione(daRestituire, null); // oggi

        archivio.salvaPrestiti(gestionePrestiti);
        archivio.salvaLibri(gestioneLibri);
        archivio.salvaUtenti(gestioneUtenti);

        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());

        if (libriController != null) libriController.aggiornaDaModel();
        if (utentiController != null) utentiController.aggiornaDaModel();
    }

    /**
     * @brief Aggiunge l'utente associato al prestito selezionato alla blacklist.
     * Imposta il flag blacklist dell'utente a true e salva le modifiche.
     */
    private void gestisciBlacklist() {
        ObservableList<String> rigaSel =
                view.getTabellaPrestiti().getSelectionModel().getSelectedItem();

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

        mostraInfo("Utente " + utente.getNome() + " " + utente.getCognome()
                + " inserito in blacklist.");

        if (utentiController != null) utentiController.aggiornaDaModel();
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());
    }

    /**
     * @brief Aggiorna la visualizzazione della tabella con l'elenco dei prestiti fornito.
     * Costruisce le righe della tabella combinando dati dal prestito, dall'utente e dal libro.
     * Calcola dinamicamente se il prestito è in ritardo.
     * @param prestitiDaMostrare La collezione di prestiti da visualizzare.
     */
    private void aggiornaTabella(Collection<Prestito> prestitiDaMostrare) {
        ObservableList<ObservableList<String>> righe = FXCollections.observableArrayList();

        for (Prestito p : prestitiDaMostrare) {

            Utente uPrestito = p.getUtente();
            Utente u = null;
            if (uPrestito != null && uPrestito.getMatricola() != null) {
                u = gestioneUtenti.trovaUtente(uPrestito.getMatricola());
            }
            if (u == null) {
                u = uPrestito; // fallback
            }

            Libro l = p.getLibro();

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

    /** @brief Verifica se una stringa è nulla o vuota. */
    private boolean isVuoto(String s) {
        return s == null || s.trim().isEmpty();
    }

    /** @brief Converte una stringa in Long gestendo le eccezioni. */
    private Long parseLong(String s, String nomeCampo) {
        try {
            return Long.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere un numero intero.");
            return null;
        }
    }

    /** @brief Converte una stringa in LocalDate gestendo il formato ISO (AAAA-MM-GG). */
    private LocalDate parseData(String s, String nomeCampo) {
        try {
            return LocalDate.parse(s.trim());
        } catch (DateTimeParseException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere nel formato AAAA-MM-GG.");
            return null;
        }
    }

    /** @brief Mostra una finestra di alert per segnalare un errore. */
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Errore");
        alert.showAndWait();
    }

    /** @brief Mostra una finestra di alert per messaggi informativi. */
    private void mostraInfo(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Informazione");
        alert.showAndWait();
    }

    /** @brief Forza l'aggiornamento della vista prestiti dal modello. */
    public void aggiornaDaModel() {
        aggiornaTabella(gestionePrestiti.getPrestitiAttivi());
    }
}
