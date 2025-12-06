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
import javafx.scene.control.*;
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
 *
 * @author tommy
 */
public class PrestitiPanel {

    /**
     * @brief Contenitore principale del pannello.
     */
    private final BorderPane root;

    // --- Campi del Form ---
    private final TextField campoMatricola;
    private final TextField campoIsbn;
    private final TextField campoDataPrevista;

    /**
     * @brief Tabella che visualizza i dati dei prestiti.
     * Utilizza la classe interna PrestitoRow come modello dati.
     */
    private final TableView<PrestitoRow> tabellaPrestiti;

    // --- Bottoni Azione ---
    private final Button bottoneNuovoPrestito;
    private final Button bottoneRestituzione;
    private final Button bottoneBlacklist;

    /**
     * @brief Costruttore del pannello Prestiti.
     *
     * Inizializza il layout diviso in tre sezioni:
     * 1. **Top**: Form (GridPane) per l'inserimento dei dati di prestito.
     * 2. **Center**: TableView configurata con le colonne per visualizzare i dettagli.
     * 3. **Bottom**: HBox contenente i pulsanti per le azioni principali.
     */
    public PrestitiPanel() {
        root = new BorderPane();

        // --- Inizializzazione Form (TOP) ---
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

        // --- Inizializzazione Tabella (CENTER) ---
        tabellaPrestiti = new TableView<>();

        // Configurazione delle colonne e binding con le proprietà di PrestitoRow
        TableColumn<PrestitoRow, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(data -> data.getValue().matricolaProperty());

        TableColumn<PrestitoRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> data.getValue().nomeProperty());

        TableColumn<PrestitoRow, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(data -> data.getValue().cognomeProperty());

        TableColumn<PrestitoRow, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(data -> data.getValue().isbnProperty());

        TableColumn<PrestitoRow, String> colTitolo = new TableColumn<>("Titolo");
        colTitolo.setCellValueFactory(data -> data.getValue().titoloProperty());

        TableColumn<PrestitoRow, String> colDataInizio = new TableColumn<>("Data inizio");
        colDataInizio.setCellValueFactory(data -> data.getValue().dataInizioProperty());

        TableColumn<PrestitoRow, String> colDataPrevista = new TableColumn<>("Data prevista");
        colDataPrevista.setCellValueFactory(data -> data.getValue().dataPrevistaProperty());

        TableColumn<PrestitoRow, String> colInRitardo = new TableColumn<>("In ritardo");
        colInRitardo.setCellValueFactory(data -> data.getValue().inRitardoProperty());

        tabellaPrestiti.getColumns().addAll(
                colMatricola, colNome, colCognome, colIsbn, colTitolo,
                colDataInizio, colDataPrevista, colInRitardo
        );

        // Dati di prova
        ObservableList<PrestitoRow> datiFinti = FXCollections.observableArrayList(
                new PrestitoRow("0612700001", "Mario", "Rossi",
                        "9788800000001", "Programmazione in Java",
                        "2025-12-01", "2025-12-15", "No"),
                new PrestitoRow("0612700002", "Giulia", "Bianchi",
                        "9788800000002", "Basi di Dati",
                        "2025-11-20", "2025-11-30", "Sì")
        );
        tabellaPrestiti.setItems(datiFinti);

        root.setCenter(tabellaPrestiti);
        BorderPane.setMargin(tabellaPrestiti, new Insets(10));

        // --- Inizializzazione Bottoni (BOTTOM) ---
        bottoneNuovoPrestito = new Button("Nuovo prestito");
        bottoneRestituzione = new Button("Registrare restituzione");
        bottoneBlacklist = new Button("Blacklist utente");

        HBox bottoniBox = new HBox(10, bottoneNuovoPrestito, bottoneRestituzione, bottoneBlacklist);
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);
        bottoniBox.setPadding(new Insets(10));

        root.setBottom(bottoniBox);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto Parent (BorderPane) da inserire nella scena principale.
     */
    public Parent getRoot() { return root; }

    /**
     * @brief Classe interna per la rappresentazione dei dati nella tabella.
     */
    public static class PrestitoRow {
        private final SimpleStringProperty matricola;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty cognome;
        private final SimpleStringProperty isbn;
        private final SimpleStringProperty titolo;
        private final SimpleStringProperty dataInizio;
        private final SimpleStringProperty dataPrevista;
        private final SimpleStringProperty inRitardo;

        /**
         * @brief Costruttore per creare una riga della tabella.
         *
         * @param matricola Matricola dell'utente.
         * @param nome Nome dell'utente.
         * @param cognome Cognome dell'utente.
         * @param isbn Codice ISBN del libro.
         * @param titolo Titolo del libro.
         * @param dataInizio Data di inizio prestito (formato Stringa per semplicità).
         * @param dataPrevista Data di scadenza prevista.
         * @param inRitardo Stato del ritardo ("Sì"/"No").
         */
        public PrestitoRow(String matricola, String nome, String cognome,
                           String isbn, String titolo,
                           String dataInizio, String dataPrevista, String inRitardo) {
            this.matricola = new SimpleStringProperty(matricola);
            this.nome = new SimpleStringProperty(nome);
            this.cognome = new SimpleStringProperty(cognome);
            this.isbn = new SimpleStringProperty(isbn);
            this.titolo = new SimpleStringProperty(titolo);
            this.dataInizio = new SimpleStringProperty(dataInizio);
            this.dataPrevista = new SimpleStringProperty(dataPrevista);
            this.inRitardo = new SimpleStringProperty(inRitardo);
        }

        // --- Metodi Property per il Binding JavaFX ---

        /** @return Property osservabile per la matricola. */
        public SimpleStringProperty matricolaProperty() { return matricola; }
        
        /** @return Property osservabile per il nome. */
        public SimpleStringProperty nomeProperty() { return nome; }
        
        /** @return Property osservabile per il cognome. */
        public SimpleStringProperty cognomeProperty() { return cognome; }
        
        /** @return Property osservabile per l'ISBN. */
        public SimpleStringProperty isbnProperty() { return isbn; }
        
        /** @return Property osservabile per il titolo. */
        public SimpleStringProperty titoloProperty() { return titolo; }
        
        /** @return Property osservabile per la data di inizio. */
        public SimpleStringProperty dataInizioProperty() { return dataInizio; }
        
        /** @return Property osservabile per la data prevista. */
        public SimpleStringProperty dataPrevistaProperty() { return dataPrevista; }
        
        /** @return Property osservabile per lo stato di ritardo. */
        public SimpleStringProperty inRitardoProperty() { return inRitardo; }
    }
}
