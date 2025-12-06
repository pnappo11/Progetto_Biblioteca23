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
 * sugli utenti della biblioteca.
 * È composta da un form di inserimento, una tabella riepilogativa e i comandi di azione.
 */
public class UtentiPanel {

    /**
     * @brief Contenitore principale del layout.
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
     *
     * Ogni riga è rappresentata da una ObservableList<String> con questi indici:
     * 0 = Matricola, 1 = Nome, 2 = Cognome, 3 = Email, 4 = Blacklist.
     */
    private final TableView<ObservableList<String>> tabellaUtenti;

    // ===== Pulsanti Azione =====
    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneCerca;

    /**
     * @brief Costruttore del pannello Utenti.
     *
     * Inizializza e assembla i componenti grafici:
     * 1. Top: GridPane per l'inserimento dati (Matricola, Nome, Cognome, Email).
     * 2. Center: TableView con le colonne configurate per mostrare i dettagli utente.
     * 3. Bottom: HBox contenente i pulsanti (Inserisci, Modifica, Elimina, Cerca).
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

        // 0 = Matricola, 1 = Nome, 2 = Cognome, 3 = Email, 4 = Blacklist

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

        TableColumn<ObservableList<String>, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(4)));

        tabellaUtenti.getColumns().addAll(
                colMatricola, colNome, colCognome, colEmail, colBlacklist
        );

        // Dati di prova (puoi toglierli quando collegherai il controller/model)
        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();
        datiFinti.add(FXCollections.observableArrayList(
                "0612700001", "Mario", "Rossi", "m.rossi@unisa.it", "No"
        ));
        datiFinti.add(FXCollections.observableArrayList(
                "0612700002", "Giulia", "Bianchi", "g.bianchi@unisa.it", "Sì"
        ));
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

    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }
    public String getNomeInserito()      { return campoNome.getText().trim(); }
    public String getCognomeInserito()   { return campoCognome.getText().trim(); }
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

    public TableView<ObservableList<String>> getTabellaUtenti() {
        return tabellaUtenti;
    }

    public Button getBottoneInserisci() { return bottoneInserisci; }
    public Button getBottoneModifica()  { return bottoneModifica; }
    public Button getBottoneElimina()   { return bottoneElimina; }
    public Button getBottoneCerca()     { return bottoneCerca; }
}

