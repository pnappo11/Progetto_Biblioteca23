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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
/**
 * @brief Pannello per la gestione del catalogo libri .
 * Questa classe rappresenta la "View" per le operazioni di create, read, update e delete sui libri.
 * Utilizza una struttura dati generica (ObservableList<String>) per rappresentare le righe della tabella.
 * Struttura:
 * - Top: Form di inserimento e modifica.
 * - Center: Tabella riepilogativa dei libri.
 * - Bottom: Pulsanti di controllo (CRUD + ricerca).
 */
public class LibriPanel {
    private final BorderPane root;
    private final TextField campoIsbn;
    private final TextField campoTitolo;
    private final TextField campoAutore;
    private final TextField campoAnno;
    private final TextField campoCopieTotali;
    private final TableView<ObservableList<String>> tabellaLibri;
    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneCerca;
    /**
     * @brief Costruttore del pannello Libri.
     *
     * Inizializza:
     * -il form di input (parte superiore),
     * -la tabella dei libri (parte centrale),
     * -la barra dei pulsanti (parte inferiore).
     */
    public LibriPanel() {
        root = new BorderPane();
        campoIsbn = new TextField();
        campoTitolo = new TextField();
        campoAutore = new TextField();
        campoAnno = new TextField();
        campoCopieTotali = new TextField();
        campoIsbn.setMaxWidth(Double.MAX_VALUE);
        campoTitolo.setMaxWidth(Double.MAX_VALUE);
        campoAutore.setMaxWidth(Double.MAX_VALUE);
        campoAnno.setMaxWidth(Double.MAX_VALUE);
        campoCopieTotali.setMaxWidth(Double.MAX_VALUE);
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));
        ColumnConstraints c0 = new ColumnConstraints(); c0.setMinWidth(90);
        ColumnConstraints c1 = new ColumnConstraints(); c1.setHgrow(Priority.ALWAYS);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setMinWidth(70);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().addAll(c0, c1, c2, c3);
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
        form.add(campoCopieTotali, 1, r, 3, 1);
        GridPane.setHgrow(campoIsbn, Priority.ALWAYS);
        GridPane.setHgrow(campoTitolo, Priority.ALWAYS);
        GridPane.setHgrow(campoAutore, Priority.ALWAYS);
        GridPane.setHgrow(campoAnno, Priority.ALWAYS);
        GridPane.setHgrow(campoCopieTotali, Priority.ALWAYS);

        tabellaLibri = new TableView<>();
        tabellaLibri.getStyleClass().add("table-in-card");
        tabellaLibri.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<ObservableList<String>, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(0)));
        colIsbn.setPrefWidth(120);

        TableColumn<ObservableList<String>, String> colTitolo = new TableColumn<>("Titolo");
        colTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(1)));
        colTitolo.setPrefWidth(250);

        TableColumn<ObservableList<String>, String> colAutori = new TableColumn<>("Autori");
        colAutori.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(2)));
        colAutori.setPrefWidth(250);

        TableColumn<ObservableList<String>, String> colAnno = new TableColumn<>("Anno");
        colAnno.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(3)));
        colAnno.setPrefWidth(80);

        TableColumn<ObservableList<String>, String> colCopieTot = new TableColumn<>("Copie totali");
        colCopieTot.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(4)));
        colCopieTot.setPrefWidth(100);

        TableColumn<ObservableList<String>, String> colCopieDisp = new TableColumn<>("Copie disp.");
        colCopieDisp.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(5)));
        colCopieDisp.setPrefWidth(100);

        tabellaLibri.getColumns().addAll(colIsbn, colTitolo, colAutori, colAnno, colCopieTot, colCopieDisp);

        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();
        datiFinti.add(FXCollections.observableArrayList("9788800000001","Programmazione in Java","Rossi","2020","10","7"));
        datiFinti.add(FXCollections.observableArrayList("9788800000002","Basi di Dati","Verdi","2019","5","2"));
        tabellaLibri.setItems(datiFinti);

        bottoneInserisci = new Button("Inserisci");
        bottoneModifica = new Button("Modifica");
        bottoneElimina = new Button("Elimina");
        bottoneCerca = new Button("Cerca");

        bottoneInserisci.getStyleClass().add("btn-primary");
        bottoneModifica.getStyleClass().add("btn-secondary");
        bottoneElimina.getStyleClass().add("btn-danger");
        bottoneCerca.getStyleClass().add("btn-secondary");

        HBox bottoniBox = new HBox(10, bottoneInserisci, bottoneModifica, bottoneElimina, bottoneCerca);
        bottoniBox.getStyleClass().add("card-footer");
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);

        VBox contenuto = new VBox(14, form, tabellaLibri, bottoniBox);
        contenuto.getStyleClass().add("card");
        contenuto.setPadding(new Insets(16));
        VBox.setVgrow(tabellaLibri, Priority.ALWAYS);

        root.setCenter(contenuto);
        BorderPane.setMargin(contenuto, new Insets(12));
    }

    /**
     * @brief Restituisce il nodo radice del pannello libri.
     * @return Nodo Parent da usare nella scena.
     */
    public Parent getRoot() { return root; }

    /**
     * @brief Recupera l'ISBN inserito nel form.
     * @return ISBN senza spazi iniziali o finali.
     */
    public String getCodiceIsbnInserito() { return campoIsbn.getText().trim(); }
    /**
     * @brief Recupera il titolo inserito nel form.
     * @return Titolo senza spazi iniziali o finali.
     */
    public String getTitoloInserito() { return campoTitolo.getText().trim(); }
    /**
     * @brief Recupera l'autore o gli autori inseriti nel form.
     * @return Stringa degli autori senza spazi iniziali/finali.
     */
    public String getAutoreInserito() { return campoAutore.getText().trim(); }
    /**
     * @brief Recupera l'anno inserito nel form.
     * @return Anno senza spazi iniziali/finali.
     */
    public String getAnnoInserito() { return campoAnno.getText().trim(); }
    /**
     * @brief Recupera il numero di copie totali inserito nel form.
     * @return Copie totali senza spazi iniziali/finali.
     */
    public String getCopieTotaliInserite() { return campoCopieTotali.getText().trim(); }

    /**
     * @brief Pulisce tutti i campi di testo del form.
     */
    public void pulisciCampi() { campoIsbn.clear(); campoTitolo.clear(); campoAutore.clear(); campoAnno.clear(); campoCopieTotali.clear(); }

    /**
     * @brief Restituisce la TableView contenente i libri.
     * @return TableView con righe rappresentate da ObservableList<String>.
     */
    public TableView<ObservableList<String>> getTabellaLibri() { return tabellaLibri; }
    /**
     * @brief Restituisce il pulsante di inserimento libro.
     * @return tasto "Inserisci".
     */
    public Button getBottoneInserisci() { return bottoneInserisci; }
    /**
     * @brief Restituisce il pulsante di modifica libro.
     * @return tasto "Modifica".
     */
    public Button getBottoneModifica() { return bottoneModifica; }
    /**
     * @brief Restituisce il pulsante di eliminazione libro.
     * @return tasto "Elimina".
     */
    public Button getBottoneElimina() { return bottoneElimina; }
    /**
     * @brief Restituisce il pulsante di ricerca libro.
     * @return tasto "Cerca".
     */
    public Button getBottoneCerca() { return bottoneCerca; }

    /**
     * @brief Imposta i campi di input del form a partire da una riga della tabella.
     *
     * La riga deve rispettare la struttura della TableView:
     * -indice 0: ISBN
     * -indice 1: Titolo
     * -indice 2: Autore/i
     * -indice 3: Anno
     * -indice 4: Copie totali
     * -indice 5: Copie disponibili
     *
     * @param riga corrisponde alla riga selezionata nella tabella libri.
     */
    public void setCampiDaRiga(ObservableList<String> riga) {
        if (riga == null || riga.size() < 6) return;
        campoIsbn.setText(riga.get(0));
        campoTitolo.setText(riga.get(1));
        campoAutore.setText(riga.get(2));
        campoAnno.setText(riga.get(3));
        campoCopieTotali.setText(riga.get(4));
        }
}
