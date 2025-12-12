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
 * Questa classe (View) fornisce l'interfaccia grafica per le operazioni di creazione, visualizzazione, modifica ed eliminazione
 * sugli utenti della biblioteca.
 * È composta da un form di inserimento, una tabella riepilogativa e i comandi di azione.
 */
public class UtentiPanel {

    /**
     * @brief Contenitore principale del layout.
     */
    private final BorderPane root;

    private final TextField campoMatricola;

    private final TextField campoNome;

    private final TextField campoCognome;

    private final TextField campoEmail;

    /**
     * @brief Tabella per visualizzare la lista degli utenti.
     *
     * Ogni riga è rappresentata da una ObservableList<String> indicizzata.
     * 0: Matricola, 1: Nome, 2: cognome, 3: email, 4: prestiti attivi, 5: blacklist.
     */
    private final TableView<ObservableList<String>> tabellaUtenti;

    //  Pulsanti Azione
    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneBlacklist;
    private final Button bottoneCerca;

    /**
     * @brief Costruttore del pannello Utenti.
     *
     * Inizializza e assembla i componenti grafici: inserimento dat, dettagli utenti, pulsanti(Inserisci, Modifica, Elimina, Blacklist, Cerca).
     */
    public UtentiPanel() {
        root = new BorderPane();

        //  form in alto
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

        //  tabella al centro
        tabellaUtenti = new TableView<>();

        // 0 = Matricola, 1 = Nome, 2 = Cognome, 3 = Email, 4 = Prestiti attivi, 5 = Blacklist

        TableColumn<ObservableList<String>, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(0)));

        TableColumn<ObservableList<String>, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(1)));

        TableColumn<ObservableList<String>, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(2)));

        TableColumn<ObservableList<String>, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(3)));

        // nuova colonna: Prestiti attivi (indice 4)
        TableColumn<ObservableList<String>, String> colPrestitiAttivi =
                new TableColumn<>("Prestiti attivi");
        colPrestitiAttivi.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(4)));

        TableColumn<ObservableList<String>, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(5)));

        tabellaUtenti.getColumns().addAll(
                colMatricola, colNome, colCognome, colEmail, colPrestitiAttivi, colBlacklist
        );

        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();
        datiFinti.add(FXCollections.observableArrayList(
                "0612700001", "Mario", "Rossi", "m.rossi@unisa.it", "1", "No"
        ));
        datiFinti.add(FXCollections.observableArrayList(
                "0612700002", "Giulia", "Bianchi", "g.bianchi@unisa.it", "0", "Sì"
        ));
        tabellaUtenti.setItems(datiFinti);

        root.setCenter(tabellaUtenti);
        BorderPane.setMargin(tabellaUtenti, new Insets(10));

        //  pulsanti in basso
        bottoneInserisci  = new Button("Inserisci");
        bottoneModifica   = new Button("Modifica");
        bottoneElimina    = new Button("Elimina");
        bottoneBlacklist  = new Button("Blacklist");
        bottoneCerca      = new Button("Cerca");

        HBox bottoniBox = new HBox(10,
                bottoneInserisci,
                bottoneModifica,
                bottoneElimina,
                bottoneBlacklist,
                bottoneCerca);
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);
        bottoniBox.setPadding(new Insets(10));

        root.setBottom(bottoniBox);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto Parent da inserire nella scena principale.
     */
    public Parent getRoot() {
        return root;
    }

    //  Getter dei dati inseriti (Input)

    /** @brief getter per la matricola
     * @return campo matricola senza spazi
     */
    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }

    /** @brief getter per il nome
     * @return campo nome senza spazi
     */
    public String getNomeInserito() { return campoNome.getText().trim(); }

    /** @brief getter per il cognome
     * @return campo cognome senza spazi
     */
    public String getCognomeInserito() { return campoCognome.getText().trim(); }

    /** @brief getter per il campo mail
     * @return campo mail
     */
    public String getEmailInserita() { return campoEmail.getText().trim(); }

    /**
     * @brief Pulisce i campi di input del form.
     * Da chiamare dopo un inserimento avvenuto con successo per resettare i campi .
     */
    public void pulisciCampi() {
        campoMatricola.clear();
        campoNome.clear();
        campoCognome.clear();
        campoEmail.clear();
    }

    //  Getter Componenti UI (per i Controller)

    public TableView<ObservableList<String>> getTabellaUtenti() {
        return tabellaUtenti;
    }

    /** @return tasto Inserisci */
    public Button getBottoneInserisci() { return bottoneInserisci; }

    /** @return tasto Modifica */
    public Button getBottoneModifica() { return bottoneModifica; }

    /** @return tasto Elimina */
    public Button getBottoneElimina() { return bottoneElimina; }

    /** @return tasto Blacklist */
    public Button getBottoneBlacklist() { return bottoneBlacklist; }

    /** @return tasto Cerca */
    public Button getBottoneCerca() { return bottoneCerca; }

    /**
     * @brief Imposta i campi del form a partire da una riga selezionata nella tabella.
     *
     * Struttura riga:
     * 0 = Matricola, 1 = Nome, 2 = Cognome, 3 = Email, 4 = Prestiti attivi, 5 = Blacklist.
     *
     * @param riga Riga selezionata nella TableView utenti.
     */
    public void setCampiDaRiga(ObservableList<String> riga) {
        if (riga == null || riga.size() < 4) {
            return;
        }

        campoMatricola.setText(riga.get(0));
        campoNome.setText(riga.get(1));
        campoCognome.setText(riga.get(2));
        campoEmail.setText(riga.get(3));
    }
}
