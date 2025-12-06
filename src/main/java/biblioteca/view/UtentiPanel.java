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

public class UtentiPanel{

    private final BorderPane root;

    private final TextField campoMatricola;
    private final TextField campoNome;
    private final TextField campoCognome;
    private final TextField campoEmail;

    private final TableView<UtenteRow> tabellaUtenti;

    private final Button bottoneInserisci;
    private final Button bottoneModifica;
    private final Button bottoneElimina;
    private final Button bottoneCerca;

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

        TableColumn<UtenteRow, String> colMatricola = new TableColumn<>("Matricola");
        colMatricola.setCellValueFactory(data -> data.getValue().matricolaProperty());

        TableColumn<UtenteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> data.getValue().nomeProperty());

        TableColumn<UtenteRow, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setCellValueFactory(data -> data.getValue().cognomeProperty());

        TableColumn<UtenteRow, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<UtenteRow, String> colBlacklist = new TableColumn<>("Blacklist");
        colBlacklist.setCellValueFactory(data -> data.getValue().blacklistProperty());

        tabellaUtenti.getColumns().addAll(
                colMatricola, colNome, colCognome, colEmail, colBlacklist
        );

        ObservableList<UtenteRow> datiFinti = FXCollections.observableArrayList(
                new UtenteRow("0612700001", "Mario", "Rossi", "m.rossi@unisa.it", "No"),
                new UtenteRow("0612700002", "Giulia", "Bianchi", "g.bianchi@unisa.it", "Sì")
        );
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

    public Parent getRoot() {
        return root;
    }

    // getter per quando collegheremo i controller

    public String getMatricolaInserita() { return campoMatricola.getText().trim(); }
    public String getNomeInserito()      { return campoNome.getText().trim(); }
    public String getCognomeInserito()   { return campoCognome.getText().trim(); }
    public String getEmailInserita()     { return campoEmail.getText().trim(); }

    public void pulisciCampi() {
        campoMatricola.clear();
        campoNome.clear();
        campoCognome.clear();
        campoEmail.clear();
    }

    public TableView<UtenteRow> getTabellaUtenti() {
        return tabellaUtenti;
    }

    public Button getBottoneInserisci() { return bottoneInserisci; }
    public Button getBottoneModifica()  { return bottoneModifica; }
    public Button getBottoneElimina()   { return bottoneElimina; }
    public Button getBottoneCerca()     { return bottoneCerca; }

    // classe interna per la tabella
    public static class UtenteRow {
        private final SimpleStringProperty matricola;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty cognome;
        private final SimpleStringProperty email;
        private final SimpleStringProperty blacklist;

        public UtenteRow(String matricola, String nome, String cognome,
                         String email, String blacklist) {
            this.matricola = new SimpleStringProperty(matricola);
            this.nome = new SimpleStringProperty(nome);
            this.cognome = new SimpleStringProperty(cognome);
            this.email = new SimpleStringProperty(email);
            this.blacklist = new SimpleStringProperty(blacklist);
        }

        public SimpleStringProperty matricolaProperty() { return matricola; }
        public SimpleStringProperty nomeProperty()      { return nome; }
        public SimpleStringProperty cognomeProperty()   { return cognome; }
        public SimpleStringProperty emailProperty()     { return email; }
        public SimpleStringProperty blacklistProperty() { return blacklist; }
    }
}
