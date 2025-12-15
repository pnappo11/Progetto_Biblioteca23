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

/**
 * @brief Controller per la gestione degli utenti.
 * 
 * Questa classe gestisce le operazioni di creazione, lettura, aggiornamento ed eliminazione sugli utenti,
 * la gestione della blacklist e la ricerca. Coordina l'interazione tra la view (UtentiPanel)
 * e il modello (GestioneUtenti).
 */
public class UtentiController {

    private final GestioneUtenti gestioneUtenti;
    private final GestionePrestiti gestionePrestiti;
    private final UtentiPanel view;
    private final ArchivioFile archivio;

    private PrestitiController prestitiController;

    private boolean inModalitaRicerca = false;

    /**
     * @brief Costruttore della classe UtentiController.
     * 
     * Inizializza le dipendenze, popola la tabella con gli utenti esistenti.
     * 
     * @param gestioneUtenti   gestisce la lista degli utenti.
     * @param gestionePrestiti modello dei prestiti (utile per visualizzare il numero di prestiti attivi).
     * @param view             view del pannello di gestione utenti.
     * @param archivio         per il salvataggio dei dati su file.
     */
    public UtentiController(GestioneUtenti gestioneUtenti,
                            GestionePrestiti gestionePrestiti,
                            UtentiPanel view,
                            ArchivioFile archivio) {

        this.gestioneUtenti = gestioneUtenti;
        this.gestionePrestiti = gestionePrestiti;
        this.view = view;
        this.archivio = archivio;

        inizializzaTabella();
        collegaEventi();
    }
    /**
     * @brief Imposta il controller dei prestiti.
     * 
     * Necessario per notificare aggiornamenti alla view prestiti quando lo stato di un utente cambia.
     * 
     * @param prestitiController Il controller della gestione prestiti.
     */
    public void setPrestitiController(PrestitiController prestitiController) {
        this.prestitiController = prestitiController;
    }   
    private void inizializzaTabella() {
        aggiornaTabella(gestioneUtenti.getUtenti());
    }
    
    private void collegaEventi() {
        view.getBottoneInserisci().setOnAction(e -> gestisciInserisci());
        view.getBottoneModifica().setOnAction(e -> gestisciModifica());
        view.getBottoneElimina().setOnAction(e -> gestisciElimina());
        view.getBottoneBlacklist().setOnAction(e -> gestisciToggleBlacklist());
        view.getBottoneCerca().setOnAction(e -> gestisciCercaOIndietro());

        view.getTabellaUtenti().getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        view.setCampiDaRiga(newSel);
                    }
                });
    }  
    private void gestisciInserisci() {
        String matricola = view.getMatricolaInserita();
        String nome      = view.getNomeInserito();
        String cognome   = view.getCognomeInserito();
        String email     = view.getEmailInserita();

        if (isVuoto(matricola) || isVuoto(nome) || isVuoto(cognome) || isVuoto(email)) {
            mostraErrore("Compila tutti i campi per inserire un utente.");
            return;
        }
        matricola = matricola.trim();
        email = email.trim().toLowerCase();
        int at = email.indexOf('@');
        if (at <= 0 || at != email.lastIndexOf('@')) {
            mostraErrore("Email non valida: formato richiesto qualcosa@unisa.it");
            return;
        }
        if (!email.endsWith("@unisa.it")) {
            mostraErrore("Email non valida: deve terminare con @unisa.it");
            return;
        }
        if (email.contains(" ")) {
            mostraErrore("Email non valida: non deve contenere spazi.");
            return;
        }
        if (gestioneUtenti.trovaUtente(matricola) != null) {
            mostraErrore("Esiste già un utente con matricola " + matricola + ".");
            return;
        }

        Utente utente = new Utente(matricola, nome, cognome, email);
        gestioneUtenti.inserisciUtente(utente);

        archivio.salvaUtenti(gestioneUtenti);

        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneUtenti.getUtenti());
    }   
    private void gestisciModifica() {
        String matricola = view.getMatricolaInserita();

        if (isVuoto(matricola)) {
            ObservableList<String> selezionata =
                    view.getTabellaUtenti().getSelectionModel().getSelectedItem();
            if (selezionata != null) {
                matricola = selezionata.get(0);
            }
        }

        if (isVuoto(matricola)) {
            mostraErrore("Per modificare un utente inserisci la matricola o seleziona una riga.");
            return;
        }

        matricola = matricola.trim();
        Utente utente = gestioneUtenti.trovaUtente(matricola);
        if (utente == null) {
            mostraErrore("Nessun utente trovato con matricola " + matricola + ".");
            return;
        }

        String nome    = view.getNomeInserito();
        String cognome = view.getCognomeInserito();
        String email   = view.getEmailInserita();

        if (!isVuoto(nome))    utente.setNome(nome);
        if (!isVuoto(cognome)) utente.setCognome(cognome);

        if (!isVuoto(email)) {
            email = email.trim().toLowerCase();
            int at = email.indexOf('@');
            if (at <= 0 || at != email.lastIndexOf('@')) {
                mostraErrore("Email non valida: formato richiesto qualcosa@unisa.it");
                return;
            }
            if (!email.endsWith("@unisa.it")) {
                mostraErrore("Email non valida: deve terminare con @unisa.it");
                return;
            }
            if (email.contains(" ")) {
                mostraErrore("Email non valida: non deve contenere spazi.");
                return;
            }

            utente.setEmail(email);
        }

        archivio.salvaUtenti(gestioneUtenti);

        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneUtenti.getUtenti());
    }
    private void gestisciElimina() {
        String matricola = view.getMatricolaInserita();

        if (isVuoto(matricola)) {
            ObservableList<String> selezionata =
                    view.getTabellaUtenti().getSelectionModel().getSelectedItem();
            if (selezionata != null) {
                matricola = selezionata.get(0);
            }
        }
        if (isVuoto(matricola)) {
            mostraErrore("Inserisci la matricola dell'utente da eliminare o seleziona una riga.");
            return;
        }
        matricola = matricola.trim();
        gestioneUtenti.eliminaUtente(matricola);

        archivio.salvaUtenti(gestioneUtenti);

        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneUtenti.getUtenti());
    } 
    private void gestisciToggleBlacklist() {
        ObservableList<String> selezionato =
                view.getTabellaUtenti().getSelectionModel().getSelectedItem();

        if (selezionato == null) {
            mostraErrore("Seleziona un utente dalla tabella per modificare la blacklist.");
            return;
        }

        String matricola = selezionato.get(0);
        Utente utente = gestioneUtenti.trovaUtente(matricola);

        if (utente == null) {
            mostraErrore("Nessun utente trovato con matricola " + matricola + ".");
            return;
        }

        boolean nuovoStato = !utente.isInBlacklist();
        gestioneUtenti.setBlacklist(utente, nuovoStato);

        archivio.salvaUtenti(gestioneUtenti);

        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneUtenti.getUtenti());

        if (prestitiController != null) {
            prestitiController.aggiornaDaModel();
        }
    } 
    private void gestisciCercaOIndietro() {
        if (!inModalitaRicerca) {
            String matricola = view.getMatricolaInserita();
            String cognome   = view.getCognomeInserito();
            String nome      = view.getNomeInserito();
            String email     = view.getEmailInserita();

            if (!isVuoto(email)) {
                mostraInfo(
                        "Non è possibile cercare per \"Email\".\n" +
                        "La ricerca viene effettuata solo per Matricola, Cognome e Nome."
                );
            }

            Collection<Utente> trovati =
                    gestioneUtenti.cercaUtenti(matricola, cognome, nome);

            aggiornaTabella(trovati);

            inModalitaRicerca = true;
            view.getBottoneCerca().setText("Indietro");
        } else {
            resetFormESelezione();
            resetModalitaRicerca();
            aggiornaTabella(gestioneUtenti.getUtenti());
        }
    }  
    private void aggiornaTabella(Collection<Utente> utentiDaMostrare) {
        List<Utente> lista = new ArrayList<>(utentiDaMostrare);

        lista.sort(
                Comparator
                        .comparing((Utente u) -> safeLower(u.getCognome()))
                        .thenComparing(u -> safeLower(u.getNome()))
        );

        ObservableList<ObservableList<String>> righe = FXCollections.observableArrayList();

        for (Utente u : lista) {
            ObservableList<String> riga = FXCollections.observableArrayList();

            riga.add(u.getMatricola());
            riga.add(u.getNome());
            riga.add(u.getCognome());
            riga.add(u.getEmail());

            int attivi = (gestionePrestiti != null)
                    ? gestionePrestiti.contaPrestitiAttivi(u)
                    : 0;

            riga.add(String.valueOf(attivi));  // Prestiti attivi
            riga.add(u.isInBlacklist() ? "Sì" : "No");

            righe.add(riga);
        }

        view.getTabellaUtenti().setItems(righe);
    } 
    private String safeLower(String s) {
        return (s == null) ? "" : s.toLowerCase();
    }
    private boolean isVuoto(String s) {
        return s == null || s.trim().isEmpty();
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

    
    private void resetFormESelezione() {
        view.pulisciCampi();
        view.getTabellaUtenti().getSelectionModel().clearSelection();
    }

    
    private void resetModalitaRicerca() {
        inModalitaRicerca = false;
        view.getBottoneCerca().setText("Cerca");
    }
    /**
     * @brief Forza l'aggiornamento della tabella utenti recuperando i dati dal modello.
     * Utile per sincronizzare la view dopo modifiche esterne.
     */
    public void aggiornaDaModel() {
        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneUtenti.getUtenti());
    }
}
