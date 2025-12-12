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

/**
 * @brief Finestra operativa principale dell'applicazione.
 *
 * Questa classe rappresenta il contenitore generale che ospita le funzionalità
 * di gestione per Libri, Utenti, Prestiti il tutto organizzato in Tab.
 * Include anche una barra superiore di navigazione per tornare al Menu principale
 * o effettuare il Logout.
 *
 * @author tommy
 */
public class MainFrame {

    private final BorderPane root;

    private final TabPane tabPane;

    private final LibriPanel libriView;
    private final UtentiPanel utentiView;
    private final PrestitiPanel prestitiView;

    private final Button btnMenu;
    private final Button btnLogout;

    /**
     * @brief Costruttore di default.
     * Strutturato in modo che la prima finestra ad essere visualizzata è quella relativa ai libri.
     */
    public MainFrame() {
        this(0);
    }

    /**
     * @brief Costruttore principale con selezione della scheda iniziale.
     *
     * Questo costruttore permette di avviare il MainFrame focalizzando subito
     * l'attenzione dell'utente sulla sezione scelta nel Menu precedente.
     *
     * @param tabIndex L'indice della scheda da selezionare all'avvio:
     * 0 per la gestione libri, 1 per la gestione utenti e 2 per la gestiione prestiti.
     */
    public MainFrame(int tabIndex) {
        root = new BorderPane();
        tabPane = new TabPane();

        //  PANNELLI E TAB
        libriView = new LibriPanel();
        utentiView = new UtentiPanel();
        prestitiView = new PrestitiPanel();

        Tab tabLibri = new Tab("Libri", libriView.getRoot());
        Tab tabUtenti = new Tab("Utenti", utentiView.getRoot());
        Tab tabPrestiti = new Tab("Prestiti", prestitiView.getRoot());

        // Disabilitiamo la chiusura delle tab per mantenere la UI consistente
        tabLibri.setClosable(false);
        tabUtenti.setClosable(false);
        tabPrestiti.setClosable(false);

        tabPane.getTabs().addAll(tabLibri, tabUtenti, tabPrestiti);

        // Selezione programmatica della tab richiesta
        tabPane.getSelectionModel().select(tabIndex);

        // BARRA IN ALTO: [Menu]   Gestione Biblioteca   [Logout]
        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(8, 12, 8, 12));

        // Configurazione bottone Menu (stile grigio chiaro), stile CSS.
        btnMenu = new Button("Menu");
        btnMenu.setStyle(
                "-fx-background-color: #e0e0e0;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;"
        );

        Label lblTitolo = new Label("Gestione Biblioteca");
        BorderPane.setAlignment(lblTitolo, Pos.CENTER);

        // Configurazione bottone Logout (stile rosso di allerta) CSS.
        btnLogout = new Button("Logout");
        btnLogout.setStyle(
                "-fx-background-color: #ff4d4d;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;"
        );

        // Posizionamento elementi nella barra superiore
        topBar.setLeft(btnMenu);
        topBar.setCenter(lblTitolo);
        topBar.setRight(btnLogout);

        // Assemblaggio finale del layout
        root.setTop(topBar);
        root.setCenter(tabPane);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto Parent da inserire nella scene.
     */
    public Parent getRoot() {
        return root;
    }

    //  Getter per l'accesso alle sottoviste (per i Controller)

    /** @return Il pannello per la gestione libri. */
    public LibriPanel getLibriView() { return libriView; }

    /** @return Il pannello per la gestione utenti. */
    public UtentiPanel getUtentiView() { return utentiView; }

    /** @return Il pannello per la gestione prestiti. */
    public PrestitiPanel getPrestitiView() { return prestitiView; }

    /**
     * @brief Restituisce il bottone di Logout.
     * Utile per assegnare l'azione di ritorno al Login.
     * @return Il tasto Logout.
     */
    public Button getBtnLogout() {
        return btnLogout;
    }

    /**
     * @brief Restituisce il tasto Menu.
     * Utile per assegnare l'azione di ritorno al Menu principale.
     * @return Il tasto Menu.
     */
    public Button getBtnMenu() {
        return btnMenu;
    }
}
