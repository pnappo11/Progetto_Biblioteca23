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
 * @brief classe per la gestione del catalogo libri.
 *
 * Questa classe rappresenta la "View" per le operazioni di creazione, visualizzazione,modifica e eliminazione.
 * Utilizza una struttura dati generica (ObservableList<String>) per rappresentare le righe della tabella.
 * contiene form di inserimento e modifica, una tabella riepilogativa e pulsanti di controllo.
 */
public class LibriPanel {

    /**
     * @brief Layout principale del pannello libri.
     */
    private final BorderPane root;

    //  Campi di Input

    /**
     * @brief Campo per l'inserimento dell'ISBN.
     */
    private final TextField campoIsbn;

    /**
     * @brief Campo per l'inserimento del Titolo.
     */
    private final TextField campoTitolo;

    /**
     * @brief Campo per l'inserimento dell'Autore o lista di autori(in caso di più autori per un libro).
     */
    private final TextField campoAutore;

    /**
     * @brief Campo per l'inserimento dell'Anno di pubblicazione.
     */
    private final TextField campoAnno;

    /**
     * @brief Campo per l'inserimento del numero di copie totali.
     */
    private final TextField campoCopieTotali;

    /**
     * @brief Tabella per la visualizzazione dei libri.
     *
     * Ogni riga è una ObservableList<String> indicizzata, ad ogni indice corrisponde un campo.
     */
    private final TableView<ObservableList<String>> tabellaLibri;

    //  Pulsanti Azione

    /**
     * @brief tasto per l'inserimento di un nuovo libro.
     */
    private final Button bottoneInserisci;

    /**
     * @brief tasto per la modifica del libro selezionato.
     */
    private final Button bottoneModifica;

    /**
     * @brief tasto per l'eliminazione del libro selezionato.
     */
    private final Button bottoneElimina;

    /**
     * @brief tasto per la ricerca dei libri.
     */
    private final Button bottoneCerca;

    /**
     * @brief Costruttore del pannello Libri.
     * permette di inizializzare form di input, tabella dei libri, i vari pulsanti.
     */
    public LibriPanel() {
        root = new BorderPane();

        //  Form in alto
        campoIsbn = new TextField();
        campoTitolo = new TextField();
        campoAutore = new TextField();
        campoAnno = new TextField();
        campoCopieTotali = new TextField();

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        int r = 0;
        form.add(new Label("ISBN:"), 0, r);
        form.add(campoIsbn, 1, r);
        form.add(new Label("Titolo:"), 2, r);
        form.add(campoTitolo, 3, r);
        r++;

        form.add(new Label("Autore/i:"), 0, r);
        form.add(campoAutore, 1, r);
        form.add(new Label("Anno:"), 2, r);
        form.add(campoAnno, 3, r);
        r++;

        form.add(new Label("Copie totali:"), 0, r);
        form.add(campoCopieTotali, 1, r);

        root.setTop(form);

        //  Tabella al centro
        tabellaLibri = new TableView<>();

        /**
         * @note Si utilizza la politica UNCONSTRAINED_RESIZE_POLICY per consentire
         *       alle colonne di avere una larghezza prefissata, con eventuale
         *       comparsa della barra di scorrimento orizzontale per poter visualizzare tutta la riga.
         */
        tabellaLibri.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Colonna ISBN (indice 0)
        TableColumn<ObservableList<String>, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(0)));
        colIsbn.setPrefWidth(120);

        // Colonna Titolo (indice 1)
        TableColumn<ObservableList<String>, String> colTitolo = new TableColumn<>("Titolo");
        colTitolo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(1)));
        colTitolo.setPrefWidth(250);

        // Colonna Autori (indice 2)
        TableColumn<ObservableList<String>, String> colAutori = new TableColumn<>("Autori");
        colAutori.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(2)));
        colAutori.setPrefWidth(250);

        // Colonna Anno (indice 3)
        TableColumn<ObservableList<String>, String> colAnno = new TableColumn<>("Anno");
        colAnno.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(3)));
        colAnno.setPrefWidth(80);

        // Colonna Copie totali (indice 4)
        TableColumn<ObservableList<String>, String> colCopieTot =
                new TableColumn<>("Copie totali");
        colCopieTot.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(4)));
        colCopieTot.setPrefWidth(100);

        // Colonna Copie disponibili (indice 5)
        TableColumn<ObservableList<String>, String> colCopieDisp =
                new TableColumn<>("Copie disp.");
        colCopieDisp.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(5)));
        colCopieDisp.setPrefWidth(100);

        tabellaLibri.getColumns().addAll(
                colIsbn, colTitolo, colAutori, colAnno, colCopieTot, colCopieDisp
        );

        /**
         * @note I dati di esempio sono solo segnaposto, verranno sovrascritti
         *    .
         */
        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();
        datiFinti.add(FXCollections.observableArrayList(
                "9788800000001", "Programmazione in Java", "Rossi", "2020", "10", "7"
        ));
        datiFinti.add(FXCollections.observableArrayList(
                "9788800000002", "Basi di Dati", "Verdi", "2019", "5", "2"
        ));
        tabellaLibri.setItems(datiFinti);

        root.setCenter(tabellaLibri);
        BorderPane.setMargin(tabellaLibri, new Insets(10));

        //  Pulsanti in basso
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
     * @brief Restituisce il nodo radice del pannello libri.
     * @return Nodo Parent da usare nella scena.
     */
    public Parent getRoot() {
        return root;
    }

    //  Getter per l'input utente

    /**
     * @brief Recupera l'ISBN inserito nel form.
     * @return ISBN senza spazi iniziali o finali.
     */
    public String getCodiceIsbnInserito() {
        return campoIsbn.getText().trim();
    }

    /**
     * @brief Recupera il titolo inserito nel form.
     * @return Titolo senza spazi iniziali o finali.
     */
    public String getTitoloInserito() {
        return campoTitolo.getText().trim();
    }

    /**
     * @brief Recupera l'autore o gli autori inseriti nel form.
     * @return Stringa degli autori senza spazi iniziali o finali.
     */
    public String getAutoreInserito() {
        return campoAutore.getText().trim();
    }

    /**
     * @brief Recupera l'anno inserito.
     * @return Anno senza spazi iniziali o finali.
     */
    public String getAnnoInserito() {
        return campoAnno.getText().trim();
    }

    /**
     * @brief Recupera il numero di copie totali inserito nel form.
     * @return Copie totali senza spazi iniziali o finali.
     */
    public String getCopieTotaliInserite() {
        return campoCopieTotali.getText().trim();
    }

    /**
     * @brief Pulisce tutti i campi di testo del form.
     */
    public void pulisciCampi() {
        campoIsbn.clear();
        campoTitolo.clear();
        campoAutore.clear();
        campoAnno.clear();
        campoCopieTotali.clear();
    }

    //  Getter componenti UI (per il Controller)

    /**
     * @brief Restituisce la TableView contenente i libri.
     * @return TableView con righe rappresentate da ObservableList<String>.
     */
    public TableView<ObservableList<String>> getTabellaLibri() {
        return tabellaLibri;
    }

    /**
     * @brief Restituisce il pulsante di inserimento libro.
     * @return tasto "Inserisci".
     */
    public Button getBottoneInserisci() {
        return bottoneInserisci;
    }

    /**
     * @brief Restituisce il pulsante di modifica libro.
     * @return tasto "Modifica".
     */
    public Button getBottoneModifica() {
        return bottoneModifica;
    }

    /**
     * @brief Restituisce il pulsante di eliminazione libro.
     * @return tasto "Elimina".
     */
    public Button getBottoneElimina() {
        return bottoneElimina;
    }

    /**
     * @brief Restituisce il pulsante di ricerca libro.
     * @return tasto "Cerca".
     */
    public Button getBottoneCerca() {
        return bottoneCerca;
    }

    /**
     * @brief Imposta i campi di input del form a partire da una riga della tabella.
     *
     * La riga deve rispettare la struttura della TableView: dunque deve essere indicizzata con lo stesso ordine.
     * - indice 0: ISBN
     * - indice 1: Titolo
     * - indice 2: Autore/i
     * - indice 3: Anno
     * - indice 4: Copie totali
     * - indice 5: Copie disponibili
     *
     * @param riga ovvero la riga selezionata nella tabella libri.
     */
    public void setCampiDaRiga(ObservableList<String> riga) {
        if (riga == null || riga.size() < 6) {
            return;
        }
        campoIsbn.setText(riga.get(0));
        campoTitolo.setText(riga.get(1));
        campoAutore.setText(riga.get(2));
        campoAnno.setText(riga.get(3));
        campoCopieTotali.setText(riga.get(4));
    }

}
