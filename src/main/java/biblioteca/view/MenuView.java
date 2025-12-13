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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @brief Schermata di Menu Principale.
 *
 * Questa classe rappresenta ciò che appare dopo il Login.
 * Permette all'operatore di scegliere tra: gestione utenti, libri e prestiti e la selezione del tasto logout per uscire.
 * @author tommy
 */
public class MenuView {

    /**
     * @brief Nodo radice del layout .
     */
    private final BorderPane root;

    private final Button btnLibri;
    private final Button btnUtenti;
    private final Button btnPrestiti;
    /** Bottone di logout posizionato nella barra superiore in alto a destra. */
    private final Button btnLogout;
    /** Azione da eseguire quando si clicca su Gestione Libri. */
    private Runnable onGestioneLibri;
    /** Azione da eseguire quando si clicca su Gestione Utenti. */
    private Runnable onGestioneUtenti;
    /** Azione da eseguire quando si clicca su Gestione Prestiti. */
    private Runnable onGestionePrestiti;
    /** Azione da eseguire quando si clicca su Logout. */
    private Runnable onLogout;

    /**
     * @brief Costruttore di default.
     */
    public MenuView() {
        this("Bibliotecario");
    }

    /**
     * @brief Costruttore principale contiene l'intera interfaccia grafica.
     *Contiene il tasto per il logout, pulsanti di navigazione, e tutto ciò che ci permette di muoverci all'interno della nostra finestra.
     * @param nomeBibliotecario Il nome dell'utente da mostrare nel messaggio di benvenuto.
     */
    public MenuView(String nomeBibliotecario) {
        root = new BorderPane();
        root.getStyleClass().add("app-root");
        root.getStyleClass().add("app-wood");
        root.setPadding(new Insets(16));

        VBox card = new VBox(20);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_CENTER);

        Label lblTitolo = new Label("Menu Biblioteca");
        lblTitolo.getStyleClass().add("page-title");

        btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("btn-danger");
        btnLogout.setOnAction(e -> { if (onLogout != null) onLogout.run(); });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topLine = new HBox(10, lblTitolo, spacer, btnLogout);
        topLine.setAlignment(Pos.CENTER_LEFT);

        Label lblBenvenuto = new Label("Benvenuto, " + nomeBibliotecario + "!");
        lblBenvenuto.getStyleClass().add("page-title");

        Label lblDescrizione = new Label("Scegli l'area della biblioteca che vuoi gestire:");
        lblDescrizione.getStyleClass().add("muted");

        HBox row1 = new HBox(15);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(15);
        row2.setAlignment(Pos.CENTER);

        btnLibri = new Button("Gestione Libri");
        btnUtenti = new Button("Gestione Utenti");
        btnPrestiti = new Button("Gestione Prestiti");

        btnLibri.getStyleClass().add("tile-button");
        btnUtenti.getStyleClass().add("tile-button");
        btnPrestiti.getStyleClass().add("tile-button");

        btnLibri.setPrefWidth(150);
        btnUtenti.setPrefWidth(150);
        btnPrestiti.setPrefWidth(150);

        btnLibri.setOnAction(e -> { if (onGestioneLibri != null) onGestioneLibri.run(); });
        btnUtenti.setOnAction(e -> { if (onGestioneUtenti != null) onGestioneUtenti.run(); });
        btnPrestiti.setOnAction(e -> { if (onGestionePrestiti != null) onGestionePrestiti.run(); });

        row1.getChildren().addAll(btnLibri, btnUtenti);
        row2.getChildren().addAll(btnPrestiti);

        card.getChildren().addAll(topLine, lblBenvenuto, lblDescrizione, row1, row2);
        root.setCenter(card);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto di tipo Parent da inserire nella Scena.
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * @brief Imposta l'azione da eseguire al click su "Gestione Libri" (setOn Action).
     * @param r Il Runnable contenente la logica di cambio scena.
     */
    public void setOnGestioneLibri(Runnable r) {
        this.onGestioneLibri = r;
    }

    /**
     * @brief Imposta l'azione da eseguire al click su "Gestione Utenti".
     * @param r rappresenta il Runnable contenente la logica di cambio scena.
     */
    public void setOnGestioneUtenti(Runnable r) {
        this.onGestioneUtenti = r;
    }

    /**
     * @brief Imposta l'azione da eseguire al click su "Gestione Prestiti".
     * @param r Il Runnable contenente la logica di cambio scena.
     */
    public void setOnGestionePrestiti(Runnable r) {
        this.onGestionePrestiti = r;
    }

    /**
     * @brief Imposta l'azione da eseguire al click su "Logout".
     * @param r Il Runnable che gestisce il ritorno alla schermata di Login.
     */
    public void setOnLogout(Runnable r) {
        this.onLogout = r;
    }
}
