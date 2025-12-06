/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainFrame {

    private final BorderPane root;
    private final TabPane tabPane;

    private final LibriPanel libriView;
    private final UtentiPanel utentiView;
    private final PrestitiPanel prestitiView;

    // pulsanti in alto
    private final Button btnMenu;    // torna al Menu
    private final Button btnLogout;  // torna al Login

    // costruttore di default → tab Libri
    public MainFrame() {
        this(0);
    }

    // tabIndex: 0=Libri, 1=Utenti, 2=Prestiti
    public MainFrame(int tabIndex) {
        root = new BorderPane();
        tabPane = new TabPane();

        // ====== PANNELLI E TAB ======
        libriView = new LibriPanel();
        utentiView = new UtentiPanel();
        prestitiView = new PrestitiPanel();

        Tab tabLibri = new Tab("Libri", libriView.getRoot());
        Tab tabUtenti = new Tab("Utenti", utentiView.getRoot());
        Tab tabPrestiti = new Tab("Prestiti", prestitiView.getRoot());

        tabLibri.setClosable(false);
        tabUtenti.setClosable(false);
        tabPrestiti.setClosable(false);

        tabPane.getTabs().addAll(tabLibri, tabUtenti, tabPrestiti);
        tabPane.getSelectionModel().select(tabIndex);

        // ====== BARRA IN ALTO: [Menu]   Gestione Biblioteca   [Logout] ======
        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(8, 12, 8, 12));

        btnMenu = new Button("Menu");
        btnMenu.setStyle(
                "-fx-background-color: #e0e0e0;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;"
        );

        Label lblTitolo = new Label("Gestione Biblioteca");
        // lo centriamo bene
        BorderPane.setAlignment(lblTitolo, Pos.CENTER);

        btnLogout = new Button("Logout");
        btnLogout.setStyle(
                "-fx-background-color: #ff4d4d;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;"
        );

        topBar.setLeft(btnMenu);
        topBar.setCenter(lblTitolo);
        topBar.setRight(btnLogout);

        // mettiamo barra in alto e tab al centro
        root.setTop(topBar);
        root.setCenter(tabPane);
    }

    public Parent getRoot() {
        return root;
    }

    public LibriPanel getLibriView()   { return libriView; }
    public UtentiPanel getUtentiView() { return utentiView; }
    public PrestitiPanel getPrestitiView() { return prestitiView; }

    public Button getBtnLogout() {
        return btnLogout;
    }

    public Button getBtnMenu() {
        return btnMenu;
    }
}

