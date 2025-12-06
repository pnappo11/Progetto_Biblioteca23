/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @brief Pannello per la gestione dell'anagrafica utenti.
 *
 * Questa classe (View) fornisce l'interfaccia grafica per le operazioni create, read, update, delete
 * sugli utenti della biblioteca .
 * È composta da un form di inserimento, una tabella riepilogativa e i comandi di azione.
 *
 * @author tommy
 */
public class UtentiPanel {

    /**
     * @brief Contenitore principale del layout .
     */
    private final BorderPane root;

    // ===== Campi del Form =====
    /** Campo di testo per la matricola o ID utente. */
    private final TextField campoMatricola;
    /** Campo di testo per il nome. */
    private final TextField campoNome;
    /** Campo di testo per il cognome. */
    private final TextField campoCognome;
    /** Campo di testo per l'indirizzo email istituzionale. */
    private final TextField campoEmail;

    /**
     * @brief Tabella per visualizzare la lista degli utenti.
     */
    private final TableView<UtenteRow> tabellaUtenti;

    // ===== Pulsanti Azione =====
    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneCerca;

    /**
     * @brief Costruttore del pannello Utenti.
     *
     * Inizializza e assembla i componenti grafici:
     * 1. **Top**: GridPane per l'inserimento dati (Matricola, Nome, Cognome, Email).
     * 2. **Center**: TableView con le colonne configurate per mostrare i dettagli utente.
     * 3. **Bottom**: HBox contenente i pulsanti (Inserisci, Modifica, Elimina, Cerca).
     */
    public UtentiPanel() {
        root = new BorderPane();

        // ===== form in alto =====
        campoMatricola = new TextField();
        campoNome = new TextField();
        campoCognome = new TextField();
        campoEmail = new TextField();

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        int r = 0;
        form.add(new Label("Matricola:"), 0, r);
        form.add(campoMatricola, 1, r);
        form.add(new Label("Nome:"), 2, r);
        form.add(campoNome, 3, r);
        r++;

        form.add(new Label("Cognome:"), 0, r);
        form.add(campoCognome, 1, r);
        form.add(new Label("Email:"), 2, r);
        form.add(campoEmail, 3, r);

        root.setTop(form);

        // ===== tabella al centro =====
        tabellaUtenti = new TableView<>();

        TableColumn<UtenteRow, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(data -> data.getValue().matricolaProperty());

        TableColumn<UtenteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> data.getValue().nomeProperty());

        TableColumn<UtenteRow, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(data -> data.getValue().cognomeProperty());

        TableColumn<UtenteRow, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<UtenteRow, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(data -> data.getValue().blacklistProperty());

        tabellaUtenti.getColumns().addAll(
                colMatricola, colNome, colCognome, colEmail, colBlacklist
        );

        // Dati di prova
        ObservableList<UtenteRow> datiFinti = FXCollections.observableArrayList(
                new UtenteRow("0612700001", "Mario", "Rossi", "m.rossi@unisa.it", "No"),
                new UtenteRow("0612700002", "Giulia", "Bianchi", "g.bianchi@unisa.it", "Sì")
        );
        tabellaUtenti.setItems(datiFinti);

        root.setCenter(tabellaUtenti);
        BorderPane.setMargin(tabellaUtenti, new Insets(10));

        // ===== pulsanti in basso =====
        bottoneInserisci = new Button("Inserisci");
        bottoneModifica = new Button("Modifica");
        bottoneElimina = new Button("Elimina");
        bottoneCerca = new Button("Cerca");

        HBox bottoniBox = new HBox(10,
                bottoneInserisci, bottoneModifica, bottoneElimina, bottoneCerca);
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);
        bottoniBox.setPadding(new Insets(10));

        root.setBottom(bottoniBox);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto Parent (BorderPane) da inserire nella scena principale.
     */
    public Parent getRoot() {
        return root;
    }

    // --- Getter dei dati inseriti (Input) ---

    /**
     * @brief Recupera la matricola inserita nel form.
     * @return La stringa della matricola pulita da spazi (trim).
     */
    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }

    /**
     * @brief Recupera il nome inserito.
     * @return Il nome dell'utente (trim).
     */
    public String getNomeInserito()      { return campoNome.getText().trim(); }

    /**
     * @brief Recupera il cognome inserito.
     * @return Il cognome dell'utente (trim).
     */
    public String getCognomeInserito()   { return campoCognome.getText().trim(); }

    /**
     * @brief Recupera l'email inserita.
     * @return L'indirizzo email (trim).
     */
    public String getEmailInserita()     { return campoEmail.getText().trim(); }

    /**
     * @brief Pulisce i campi di input del form.
     * Da chiamare dopo un inserimento avvenuto con successo.
     */
    public void pulisciCampi() {
        campoMatricola.clear();
        campoNome.clear();
        campoCognome.clear();
        campoEmail.clear();
    }

    // --- Getter Componenti UI (per i Controller) ---

    /**
     * @brief Restituisce la tabella utenti.
     * Permette al Controller di gestire la selezione delle righe o aggiornare la lista.
     * @return L'oggetto TableView.
     */
    public TableView<UtenteRow> getTabellaUtenti() {
        return tabellaUtenti;
    }

    /** @return Il bottone per inserire un nuovo utente. */
    public Button getBottoneInserisci() { return bottoneInserisci; }

    /** @return Il bottone per modificare l'utente selezionato. */
    public Button getBottoneModifica()  { return bottoneModifica; }

    /** @return Il bottone per eliminare l'utente selezionato. */
    public Button getBottoneElimina()   { return bottoneElimina; }

    /** @return Il bottone per cercare utenti nel database. */
    public Button getBottoneCerca()     { return bottoneCerca; }


    /**
     * @brief Classe interna per il Data Model della tabella Utenti.
     *
     * Rappresenta una singola riga nella TableView.
     */
    public static class UtenteRow {
        private final SimpleStringProperty matricola;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty cognome;
        private final SimpleStringProperty email;
        private final SimpleStringProperty blacklist;

        /**
         * @brief Costruttore della riga utente.
         *
         * @param matricola Identificativo univoco (es. 06127...).
         * @param nome Nome dell'utente.
         * @param cognome Cognome dell'utente.
         * @param email Indirizzo email istituzionale.
         * @param blacklist Stato dell'utente (es. "Sì" se bloccato, "No" altrimenti).
         */
        public UtenteRow(String matricola, String nome, String cognome,
                         String email, String blacklist) {
            this.matricola = new SimpleStringProperty(matricola);
            this.nome = new SimpleStringProperty(nome);
            this.cognome = new SimpleStringProperty(cognome);
            this.email = new SimpleStringProperty(email);
            this.blacklist = new SimpleStringProperty(blacklist);
        }

        /** @return Property osservabile della matricola. */
        public SimpleStringProperty matricolaProperty() { return matricola; }

        /** @return Property osservabile del nome. */
        public SimpleStringProperty nomeProperty()      { return nome; }

        /** @return Property osservabile del cognome. */
        public SimpleStringProperty cognomeProperty()   { return cognome; }

        /** @return Property osservabile dell'email. */
        public SimpleStringProperty emailProperty()     { return email; }

        /** @return Property osservabile dello stato blacklist. */
        public SimpleStringProperty blacklistProperty() { return blacklist; }
    }
}
