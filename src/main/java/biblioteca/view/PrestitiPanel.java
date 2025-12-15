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

        campoMatricola.setMaxWidth(Double.MAX_VALUE);
        campoIsbn.setMaxWidth(Double.MAX_VALUE);
        campoDataPrevista.setMaxWidth(Double.MAX_VALUE);

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
        form.add(new Label("Matricola:"), 0, r);
        form.add(campoMatricola, 1, r);
        form.add(new Label("ISBN:"), 2, r);
        form.add(campoIsbn, 3, r);
        r++;

        form.add(new Label("Data prevista:"), 0, r);
        form.add(campoDataPrevista, 1, r, 3, 1);

        GridPane.setHgrow(campoMatricola, Priority.ALWAYS);
        GridPane.setHgrow(campoIsbn, Priority.ALWAYS);
        GridPane.setHgrow(campoDataPrevista, Priority.ALWAYS);

        tabellaPrestiti = new TableView<>();
        tabellaPrestiti.getStyleClass().add("table-in-card");
        tabellaPrestiti.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        TableColumn<ObservableList<String>, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(0)));
        colMatricola.setPrefWidth(120);
        TableColumn<ObservableList<String>, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(1)));
        colNome.setPrefWidth(120);
        TableColumn<ObservableList<String>, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(2)));
        colCognome.setPrefWidth(150);

        TableColumn<ObservableList<String>, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(3)));
        colIsbn.setPrefWidth(130);

        TableColumn<ObservableList<String>, String> colTitolo = new TableColumn<>("Titolo");
        colTitolo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(4)));
        colTitolo.setPrefWidth(220);

        TableColumn<ObservableList<String>, String> colDataInizio = new TableColumn<>("Data inizio");
        colDataInizio.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(5)));
        colDataInizio.setPrefWidth(110);

        TableColumn<ObservableList<String>, String> colDataPrevista = new TableColumn<>("Data prevista");
        colDataPrevista.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(6)));
        colDataPrevista.setPrefWidth(120);
        TableColumn<ObservableList<String>, String> colInRitardo = new TableColumn<>("In ritardo");
        colInRitardo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(7)));
        colInRitardo.setPrefWidth(90);

        TableColumn<ObservableList<String>, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(8)));
        colBlacklist.setPrefWidth(100);

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

        bottoneNuovoPrestito = new Button("Nuovo prestito");
        bottoneRestituzione = new Button("Registrare restituzione");
        bottoneBlacklist = new Button("Blacklist utente");

        bottoneNuovoPrestito.getStyleClass().add("btn-primary");
        bottoneRestituzione.getStyleClass().add("btn-secondary");
        bottoneBlacklist.getStyleClass().add("btn-danger");

        HBox bottoniBox = new HBox(10, bottoneNuovoPrestito, bottoneRestituzione, bottoneBlacklist);
        bottoniBox.getStyleClass().add("card-footer");
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);

        VBox contenuto = new VBox(14, form, tabellaPrestiti, bottoniBox);
        contenuto.getStyleClass().add("card");
        contenuto.setPadding(new Insets(16));

        VBox.setVgrow(tabellaPrestiti, Priority.ALWAYS);

        root.setCenter(contenuto);
        BorderPane.setMargin(contenuto, new Insets(12));
    }

    /** @brief Nodo radice da inserire nella scena principale. */
    public Parent getRoot() { return root; }

    /** @brief metodo getter sulla matricola inserita
     * @return la matricola senza spazi.
     */
    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }
    /**
     * @brief metodo getter sull'isbn
     * @return isbn inserito */
    public String getIsbnInserito() { return campoIsbn.getText().trim(); }
    /** 
     * @brief metodo getter sulla data prevista per la restituzione 
     * @return data prevista per la restituzione */
    public String getDataPrevistaInserita() { return campoDataPrevista.getText().trim(); }

    /** @brief resetta i textfield */
    public void pulisciCampi() { campoMatricola.clear(); campoIsbn.clear(); campoDataPrevista.clear(); }
    /**
     * @brief getter per la tabella dei prestiti.
     * @return la tabella dei prestiti. */
    public TableView<ObservableList<String>> getTabellaPrestiti() { return tabellaPrestiti; }
    /** 
     * @brief getter per il tasto nuovo prestito
     * @return tasto nuovo prestito */
    public Button getBottoneNuovoPrestito() { return bottoneNuovoPrestito; }
    /** 
     * @brief getter per il bottone registra restituzione
     * @return tasto restituzione */
    public Button getBottoneRestituzione() { return bottoneRestituzione; }
    /** 
     * @brief getter per il bottone inserimento nella blacklist.
     * @return tasto blacklist */
    public Button getBottoneBlacklist() { return bottoneBlacklist; }
}
