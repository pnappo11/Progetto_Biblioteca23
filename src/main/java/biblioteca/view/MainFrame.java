/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MainFrame {

    private final BorderPane root;
    private final TabPane tabPane;

    private final LibriPanel libriView;
    private final UtentiPanel utentiView;
    private final PrestitiPanel prestitiView;

    // bottone Logout
    private final Button btnLogout;

    public MainFrame() {
        root = new BorderPane();
        tabPane = new TabPane();

        // ====== PANNELLI E TAB COME PRIMA ======
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

        // ====== BARRA IN ALTO CON TITOLO + LOGOUT ======
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(8, 12, 8, 12));
        topBar.setSpacing(10);

        Label lblTitolo = new Label("Gestione Biblioteca");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnLogout = new Button("Logout");
        // SOLO colore rosso al logout
        btnLogout.setStyle(
                "-fx-background-color: #ff4d4d;" +
                "-fx-text-fill: black;"+
                "-fx-font-weight: bold;"
        );

        topBar.getChildren().addAll(lblTitolo, spacer, btnLogout);

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
}
