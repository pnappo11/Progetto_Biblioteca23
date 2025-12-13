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
import javafx.scene.layout.VBox;

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

        campoMatricola = new TextField();
        campoNome = new TextField();
        campoCognome = new TextField();
        campoEmail = new TextField();

        campoMatricola.setMaxWidth(Double.MAX_VALUE);
        campoNome.setMaxWidth(Double.MAX_VALUE);
        campoCognome.setMaxWidth(Double.MAX_VALUE);
        campoEmail.setMaxWidth(Double.MAX_VALUE);

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        ColumnConstraints c0 = new ColumnConstraints(); c0.setMinWidth(90);
        ColumnConstraints c1 = new ColumnConstraints(); c1.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setMinWidth(70);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        form.getColumnConstraints().addAll(c0, c1, c2, c3);

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

        GridPane.setHgrow(campoMatricola, javafx.scene.layout.Priority.ALWAYS);
        GridPane.setHgrow(campoNome, javafx.scene.layout.Priority.ALWAYS);
        GridPane.setHgrow(campoCognome, javafx.scene.layout.Priority.ALWAYS);
        GridPane.setHgrow(campoEmail, javafx.scene.layout.Priority.ALWAYS);

        tabellaUtenti = new TableView<>();
        tabellaUtenti.getStyleClass().add("table-in-card");
        tabellaUtenti.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<ObservableList<String>, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(0)));
        colMatricola.setPrefWidth(120);

        TableColumn<ObservableList<String>, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(1)));
        colNome.setPrefWidth(140);

        TableColumn<ObservableList<String>, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(2)));
        colCognome.setPrefWidth(160);

        TableColumn<ObservableList<String>, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(3)));
        colEmail.setPrefWidth(240);

        TableColumn<ObservableList<String>, String> colPrestitiAttivi = new TableColumn<>("Prestiti attivi");
        colPrestitiAttivi.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(4)));
        colPrestitiAttivi.setPrefWidth(120);

        TableColumn<ObservableList<String>, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(5)));
        colBlacklist.setPrefWidth(100);

        tabellaUtenti.getColumns().addAll(colMatricola, colNome, colCognome, colEmail, colPrestitiAttivi, colBlacklist);

        ObservableList<ObservableList<String>> datiFinti = FXCollections.observableArrayList();
        datiFinti.add(FXCollections.observableArrayList("0612700001","Mario","Rossi","m.rossi@unisa.it","1","No"));
        datiFinti.add(FXCollections.observableArrayList("0612700002","Giulia","Bianchi","g.bianchi@unisa.it","0","Sì"));
        tabellaUtenti.setItems(datiFinti);

        bottoneInserisci = new Button("Inserisci");
        bottoneModifica = new Button("Modifica");
        bottoneElimina = new Button("Elimina");
        bottoneBlacklist = new Button("Blacklist");
        bottoneCerca = new Button("Cerca");

        bottoneInserisci.getStyleClass().add("btn-primary");
        bottoneModifica.getStyleClass().add("btn-secondary");
        bottoneElimina.getStyleClass().add("btn-danger");
        bottoneBlacklist.getStyleClass().add("btn-secondary");
        bottoneCerca.getStyleClass().add("btn-secondary");

        HBox bottoniBox = new HBox(10, bottoneInserisci, bottoneModifica, bottoneElimina, bottoneBlacklist, bottoneCerca);
        bottoniBox.getStyleClass().add("card-footer");
        bottoniBox.setAlignment(Pos.CENTER_RIGHT);

        VBox contenuto = new VBox(14, form, tabellaUtenti, bottoniBox);
        contenuto.getStyleClass().add("card");
        contenuto.setPadding(new Insets(16));

        VBox.setVgrow(tabellaUtenti, javafx.scene.layout.Priority.ALWAYS);

        root.setCenter(contenuto);
        BorderPane.setMargin(contenuto, new Insets(12));
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto Parent da inserire nella scena principale.
     */
    public Parent getRoot() { return root; }

    // Getter dei dati inseriti (Input)
    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }
    public String getNomeInserito() { return campoNome.getText().trim(); }
    public String getCognomeInserito() { return campoCognome.getText().trim(); }
    public String getEmailInserita() { return campoEmail.getText().trim(); }

    /**
     * @brief Pulisce i campi di input del form.
     * Da chiamare dopo un inserimento avvenuto con successo per resettare i campi .
     */
    public void pulisciCampi() { campoMatricola.clear(); campoNome.clear(); campoCognome.clear(); campoEmail.clear(); }

    // Getter Componenti UI (per i Controller)
    public TableView<ObservableList<String>> getTabellaUtenti() { return tabellaUtenti; }
    public Button getBottoneInserisci() { return bottoneInserisci; }
    public Button getBottoneModifica() { return bottoneModifica; }
    public Button getBottoneElimina() { return bottoneElimina; }
    public Button getBottoneBlacklist() { return bottoneBlacklist; }
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
        if (riga == null || riga.size() < 4) return;
        campoMatricola.setText(riga.get(0));
        campoNome.setText(riga.get(1));
        campoCognome.setText(riga.get(2));
        campoEmail.setText(riga.get(3));
    }
}
