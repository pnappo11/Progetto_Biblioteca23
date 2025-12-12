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
 * Permette agli operatori di visualizzare l'elenco dei prestiti, registrarne uno nuovo, registrare una restituzione, gestire lo stato
 * ed eventuali ritardi nelle restituzioni.
 */
public class PrestitiPanel {

    private final BorderPane root;

    private final TextField campoMatricola;
    private final TextField campoIsbn;
    private final TextField campoDataPrevista;

    private final TableView<ObservableList<String>> tabellaPrestiti;

    private final Button bottoneNuovoPrestito;
    private final Button bottoneRestituzione;
    private final Button bottoneBlacklist;

    /**
     * @brief Costruttore del pannello Prestiti.
     */
    public PrestitiPanel() {
        root = new BorderPane();

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

        TableColumn<ObservableList<String>, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(8)));

        tabellaPrestiti.getColumns().addAll(
                colMatricola, colNome, colCognome, colIsbn, colTitolo,
                colDataInizio, colDataPrevista, colInRitardo, colBlacklist
        );

        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();

        datiFinti.add(FXCollections.observableArrayList(
                "0612700001", "Mario", "Rossi",
                "9788800000001", "Programmazione in Java",
                "2025-12-01", "2025-12-15", "No", "No"
        ));

        datiFinti.add(FXCollections.observableArrayList(
                "0612700002", "Giulia", "Bianchi",
                "9788800000002", "Basi di Dati",
                "2025-11-20", "2025-11-30", "Sì", "Sì"
        ));

        tabellaPrestiti.setItems(datiFinti);

        root.setCenter(tabellaPrestiti);
        BorderPane.setMargin(tabellaPrestiti, new Insets(10));

        bottoneNuovoPrestito = new Button("Nuovo prestito");
        bottoneRestituzione = new Button("Registrare restituzione");
        bottoneBlacklist = new Button("Blacklist utente");

        HBox bottoniBox = new HBox(10,
                bottoneNuovoPrestito, bottoneRestituzione, bottoneBlacklist);
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);
        bottoniBox.setPadding(new Insets(10));

        root.setBottom(bottoniBox);
    }

    /** @brief Nodo radice da inserire nella scena principale. */
    public Parent getRoot() { return root; }

    /** @brief metodo getter sulla matricola inserita
     * @return la matricola senza spazi.
     */
    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }

    /** @return isbn inserito */
    public String getIsbnInserito() { return campoIsbn.getText().trim(); }

    /** @return data prevista per la restituzione */
    public String getDataPrevistaInserita() { return campoDataPrevista.getText().trim(); }

    /** @brief resetta i textfield */
    public void pulisciCampi() {
        campoMatricola.clear();
        campoIsbn.clear();
        campoDataPrevista.clear();
    }

    /** @return la tabella dei prestiti. */
    public TableView<ObservableList<String>> getTabellaPrestiti() {
        return tabellaPrestiti;
    }

    /** @return tasto nuovo prestito */
    public Button getBottoneNuovoPrestito() { return bottoneNuovoPrestito; }

    /** @return tasto restituzione */
    public Button getBottoneRestituzione() { return bottoneRestituzione; }

    /** @return tasto blacklist */
    public Button getBottoneBlacklist() { return bottoneBlacklist; }
}
