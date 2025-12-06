/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.view;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainFrame {

    private final BorderPane root;
    private final TabPane tabPane;

    private final LibriPanel libriView;
    private final UtentiPanel utentiView;
    private final PrestitiPanel prestitiView;

    public MainFrame() {
        root = new BorderPane();
        tabPane = new TabPane();

        libriView = new LibriPanel();
        utentiView = new UtentiPanel();
        prestitiView = new PrestitiPanel();   // questo è quello che hai appena sistemato

        Tab tabLibri = new Tab("Libri", libriView.getRoot());
        Tab tabUtenti = new Tab("Utenti", utentiView.getRoot());
        Tab tabPrestiti = new Tab("Prestiti", prestitiView.getRoot());

        tabLibri.setClosable(false);
        tabUtenti.setClosable(false);
        tabPrestiti.setClosable(false);

        tabPane.getTabs().addAll(tabLibri, tabUtenti, tabPrestiti);
        root.setCenter(tabPane);
    }

    public Parent getRoot() {
        return root;
    }

    public LibriPanel getLibriView()   { return libriView; }
    public UtentiPanel getUtentiView() { return utentiView; }
    public PrestitiPanel getPrestitiView() { return prestitiView; }
}
