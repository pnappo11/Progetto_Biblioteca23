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
 * @brief Pannello per la gestione delle operazioni di prestito.
 *
 * Questa vista permette agli operatori di:
 * - Visualizzare l'elenco dei prestiti attivi e storici.
 * - Registrare un nuovo prestito (associando Matricola e ISBN).
 * - Registrare la restituzione di un libro.
 * - Gestire lo stato di ritardo o blacklist degli utenti.
 *
 * La classe organizza i componenti in un layout BorderPane.
 */
public class PrestitiPanel {

    /** Contenitore principale del pannello. */
    private final BorderPane root;

    // --- Campi del Form ---
    private final TextField campoMatricola;
    private final TextField campoIsbn;
    private final TextField campoDataPrevista;

    /**
     * Tabella che visualizza i dati dei prestiti.
     *
     * Ogni riga è una ObservableList<String> con questi indici:
     * 0 = Matricola, 1 = Nome, 2 = Cognome,
     * 3 = ISBN, 4 = Titolo,
     * 5 = Data inizio, 6 = Data prevista, 7 = In ritardo.
     */
    private final TableView<ObservableList<String>> tabellaPrestiti;

    // --- Bottoni Azione ---
    private final Button bottoneNuovoPrestito;
    private final Button bottoneRestituzione;
    private final Button bottoneBlacklist;

    /**
     * Costruttore del pannello Prestiti.
     */
    public PrestitiPanel() {
        root = new BorderPane();

        // --- Form (TOP) ---
        campoMatricola = new TextField();
        campoIsbn = new TextField();
        campoDataPrevista = new TextField();

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        int r = 0;
        form.add(new Label("Matricola:"), 0, r);
        form.add(campoMatricola, 1, r);
        form.add(new Label("ISBN:"), 2, r);
        form.add(campoIsbn, 3, r);
        r++;

        form.add(new Label("Data prevista:"), 0, r);
        form.add(campoDataPrevista, 1, r);

        root.setTop(form);

        // --- Tabella (CENTER) ---
        tabellaPrestiti = new TableView<>();

        TableColumn<ObservableList<String>, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(0)));

        TableColumn<ObservableList<String>, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(1)));

        TableColumn<ObservableList<String>, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(2)));

        TableColumn<ObservableList<String>, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(3)));

        TableColumn<ObservableList<String>, String> colTitolo = new TableColumn<>("Titolo");
        colTitolo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(4)));

        TableColumn<ObservableList<String>, String> colDataInizio = new TableColumn<>("Data inizio");
        colDataInizio.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(5)));

        TableColumn<ObservableList<String>, String> colDataPrevista = new TableColumn<>("Data prevista");
        colDataPrevista.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(6)));

        TableColumn<ObservableList<String>, String> colInRitardo = new TableColumn<>("In ritardo");
        colInRitardo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(7)));

        tabellaPrestiti.getColumns().addAll(
                colMatricola, colNome, colCognome, colIsbn, colTitolo,
                colDataInizio, colDataPrevista, colInRitardo
        );

        // --- Dati di prova (come prima) ---
        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();

        datiFinti.add(FXCollections.observableArrayList(
                "0612700001", "Mario", "Rossi",
                "9788800000001", "Programmazione in Java",
                "2025-12-01", "2025-12-15", "No"
        ));

        datiFinti.add(FXCollections.observableArrayList(
                "0612700002", "Giulia", "Bianchi",
                "9788800000002", "Basi di Dati",
                "2025-11-20", "2025-11-30", "Sì"
        ));

        tabellaPrestiti.setItems(datiFinti);

        root.setCenter(tabellaPrestiti);
        BorderPane.setMargin(tabellaPrestiti, new Insets(10));

        // --- Bottoni (BOTTOM) ---
        bottoneNuovoPrestito = new Button("Nuovo prestito");
        bottoneRestituzione = new Button("Registrare restituzione");
        bottoneBlacklist = new Button("Blacklist utente");

        HBox bottoniBox = new HBox(10,
                bottoneNuovoPrestito, bottoneRestituzione, bottoneBlacklist);
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);
        bottoniBox.setPadding(new Insets(10));

        root.setBottom(bottoniBox);
    }

    /** Nodo radice da inserire nella scena principale. */
    public Parent getRoot() { return root; }

    // --- Getter utili per quando farai il controller ---

    public String getMatricolaInserita()   { return campoMatricola.getText().trim(); }
    public String getIsbnInserito()        { return campoIsbn.getText().trim(); }
    public String getDataPrevistaInserita(){ return campoDataPrevista.getText().trim(); }

    public void pulisciCampi() {
        campoMatricola.clear();
        campoIsbn.clear();
        campoDataPrevista.clear();
    }

    public TableView<ObservableList<String>> getTabellaPrestiti() {
        return tabellaPrestiti;
    }

    public Button getBottoneNuovoPrestito() { return bottoneNuovoPrestito; }
    public Button getBottoneRestituzione()  { return bottoneRestituzione; }
    public Button getBottoneBlacklist()     { return bottoneBlacklist; }
}

