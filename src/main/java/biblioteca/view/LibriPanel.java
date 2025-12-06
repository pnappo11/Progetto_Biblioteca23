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

public class LibriPanel {

    private final BorderPane root;

    private final TextField campoIsbn;
    private final TextField campoTitolo;
    private final TextField campoAutore;
    private final TextField campoAnno;
    private final TextField campoCopieTotali;

    // 👇 la tabella contiene semplicemente una lista di String per riga
    private final TableView<ObservableList<String>> tabellaLibri;

    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneCerca;

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

        // Indici delle colonne nelle righe:
        // 0 = ISBN, 1 = Titolo, 2 = Autori, 3 = Anno, 4 = Copie totali, 5 = Copie disp.

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

        // ===== dati FINTI solo per vedere qualcosa (puoi anche toglierli) =====
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

    public Parent getRoot() {
        return root;
    }

    // Getter per la GUI (quando farai il controller)

    public String getCodiceIsbnInserito() { return campoIsbn.getText().trim(); }
    public String getTitoloInserito()     { return campoTitolo.getText().trim(); }
    public String getAutoreInserito()     { return campoAutore.getText().trim(); }
    public String getAnnoInserito()       { return campoAnno.getText().trim(); }
    public String getCopieTotaliInserite(){ return campoCopieTotali.getText().trim(); }

    public void pulisciCampi() {
        campoIsbn.clear();
        campoTitolo.clear();
        campoAutore.clear();
        campoAnno.clear();
        campoCopieTotali.clear();
    }

    public TableView<ObservableList<String>> getTabellaLibri() {
        return tabellaLibri;
    }

    public Button getBottoneInserisci() { return bottoneInserisci; }
    public Button getBottoneModifica()  { return bottoneModifica; }
    public Button getBottoneElimina()   { return bottoneElimina; }
    public Button getBottoneCerca()     { return bottoneCerca; }
}
