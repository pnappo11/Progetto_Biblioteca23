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
 * @brief Pannello per la gestione del catalogo libri (Inventario).
 *
 * Questa classe rappresenta la "View" per le operazioni di create,read,update e delete sui libri.
 * A differenza di altre implementazioni, questa versione utilizza una struttura
 * dati generica (lista di stringhe) per rappresentare le righe della tabella.
 *
 * Struttura:
 * - Top: Form di inserimento.
 * - Center: Tabella riepilogativa.
 * - Bottom: Pulsanti di controllo.
 *
 * @author tommy
 */
public class LibriPanel {

    /**
     * @brief Layout principale (BorderPane).
     */
    private final BorderPane root;

    // ===== Campi di Input =====
    /** Campo per l'inserimento dell'ISBN. */
    private final TextField campoIsbn;
    /** Campo per l'inserimento del Titolo. */
    private final TextField campoTitolo;
    /** Campo per l'inserimento dell'Autore. */
    private final TextField campoAutore;
    /** Campo per l'inserimento dell'Anno di pubblicazione. */
    private final TextField campoAnno;
    /** Campo per l'inserimento delle Copie Totali. */
    private final TextField campoCopieTotali;

    /**
     * @brief Tabella per la visualizzazione dei libri.
     *
     * @note In questa implementazione, ogni riga non è un oggetto specifico,
     * ma una ObservableList<String>. I dati sono acceduti tramite indice:
     * - 0: ISBN
     * - 1: Titolo
     * - 2: Autore
     * - 3: Anno
     * - 4: Copie Totali
     * - 5: Copie Disponibili
     */
    private final TableView<ObservableList<String>> tabellaLibri;

    // ===== Pulsanti Azione =====
    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneCerca;

    /**
     * @brief Costruttore del pannello Libri.
     *
     * Inizializza l'interfaccia grafica:
     * 1. Costruisce il form (GridPane) nella parte superiore.
     * 2. Configura le colonne della TableView mappando gli indici della lista di stringhe.
     * 3. Inserisce dei dati di esempio (mock data).
     * 4. Crea la barra dei pulsanti nella parte inferiore.
     */
    public LibriPanel() {
        root = new BorderPane();

        // ===== form in alto =====
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

        form.add(new Label("Autore:"), 0, r);
        form.add(campoAutore, 1, r);
        form.add(new Label("Anno:"), 2, r);
        form.add(campoAnno, 3, r);
        r++;

        form.add(new Label("Copie totali:"), 0, r);
        form.add(campoCopieTotali, 1, r);

        root.setTop(form);

        // ===== tabella al centro =====
        tabellaLibri = new TableView<>();

        // Configurazione Colonne (Mapping per indice)
        
        TableColumn<ObservableList<String>, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(0)));

        TableColumn<ObservableList<String>, String> colTitolo = new TableColumn<>("Titolo");
        colTitolo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(1)));

        TableColumn<ObservableList<String>, String> colAutori = new TableColumn<>("Autori");
        colAutori.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(2)));

        TableColumn<ObservableList<String>, String> colAnno = new TableColumn<>("Anno");
        colAnno.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(3)));

        TableColumn<ObservableList<String>, String> colCopieTot = new TableColumn<>("Copie totali");
        colCopieTot.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(4)));

        TableColumn<ObservableList<String>, String> colCopieDisp = new TableColumn<>("Copie disp.");
        colCopieDisp.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get(5)));

        tabellaLibri.getColumns().addAll(
                colIsbn, colTitolo, colAutori, colAnno, colCopieTot, colCopieDisp
        );

        // ===== Dati Finti (Mock Data) =====
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
     * @return L'oggetto Parent (BorderPane) da usare nella scena.
     */
    public Parent getRoot() {
        return root;
    }

    // --- Getter per l'Input Utente ---

    /**
     * @brief Recupera l'ISBN inserito.
     * @return Stringa senza spazi iniziali/finali.
     */
    public String getCodiceIsbnInserito() { return campoIsbn.getText().trim(); }

    /**
     * @brief Recupera il titolo inserito.
     * @return Stringa senza spazi iniziali/finali.
     */
    public String getTitoloInserito()     { return campoTitolo.getText().trim(); }

    /**
     * @brief Recupera l'autore inserito.
     * @return Stringa senza spazi iniziali/finali.
     */
    public String getAutoreInserito()     { return campoAutore.getText().trim(); }

    /**
     * @brief Recupera l'anno inserito.
     * @return Stringa senza spazi iniziali/finali.
     */
    public String getAnnoInserito()       { return campoAnno.getText().trim(); }

    /**
     * @brief Recupera il numero di copie totali inserito.
     * @return Stringa senza spazi iniziali/finali.
     */
    public String getCopieTotaliInserite(){ return campoCopieTotali.getText().trim(); }

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

    // --- Getter Componenti UI (per il Controller) ---

    /**
     * @brief Restituisce la TableView.
     * @return La tabella contenente liste di stringhe (ObservableList<String>).
     */
    public TableView<ObservableList<String>> getTabellaLibri() {
        return tabellaLibri;
    }

    /** @return Il bottone Inserisci. */
    public Button getBottoneInserisci() { return bottoneInserisci; }

    /** @return Il bottone Modifica. */
    public Button getBottoneModifica()  { return bottoneModifica; }

    /** @return Il bottone Elimina. */
    public Button getBottoneElimina()   { return bottoneElimina; }

    /** @return Il bottone Cerca. */
    public Button getBottoneCerca()     { return bottoneCerca; }
}