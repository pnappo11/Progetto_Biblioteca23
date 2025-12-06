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
 * @brief Schermata di Menu Principale (Dashboard).
 *
 * Questa classe rappresenta la vista intermedia che appare dopo il Login.
 * Funge da "hub" di navigazione permettendo all'operatore di scegliere tra:
 * - Gestione Libri
 * - Gestione Utenti
 * - Gestione Prestiti
 * - Logout
 * @author tommy
 */
public class MenuView {

    /**
     * @brief Nodo radice del layout (BorderPane).
     */
    private final BorderPane root;

    // --- Componenti UI ---
    private final Button btnLibri;
    private final Button btnUtenti;
    private final Button btnPrestiti;
    /** Bottone di logout posizionato nella barra superiore. */
    private final Button btnLogout;

    // --- Callback (Gestori Eventi) ---
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
     *
     * Richiama il costruttore principale passando un nome generico ("Bibliotecario").
     * Utile se non si dispone del nome specifico dell'utente loggato.
     */
    public MenuView() {
        this("Bibliotecario");
    }

    /**
     * @brief Costruttore principale con personalizzazione.
     *
     * Costruisce l'intera interfaccia grafica:
     * 1. Crea la barra superiore con il titolo e il tasto Logout (stilizzato in rosso).
     * 2. Mostra un messaggio di benvenuto personalizzato con il nome dell'operatore.
     * 3. Dispone i pulsanti di navigazione in una griglia o righe centrate.
     * 4. Collega i bottoni ai rispettivi `Runnable` (se definiti).
     *
     * @param nomeBibliotecario Il nome dell'utente da mostrare nel messaggio di benvenuto.
     */
    public MenuView(String nomeBibliotecario) {
        root = new BorderPane();

        VBox card = new VBox(20);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_CENTER);

        // --- riga in alto: titolo + logout piccolo a destra ---
        Label lblTitolo = new Label("Menu Biblioteca");
        lblTitolo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        btnLogout = new Button("Logout");
        // Stile inline per rendere il tasto logout rosso e ben visibile
        btnLogout.setStyle(
                "-fx-background-color: #ff4d4d;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;"
        );
        
        // Esecuzione callback di logout se definita
        btnLogout.setOnAction(e -> {
            if (onLogout != null) onLogout.run();
        });

        // Spacer per spingere il logout tutto a destra
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topLine = new HBox(10, lblTitolo, spacer, btnLogout);
        topLine.setAlignment(Pos.CENTER_LEFT);

        // --- Sezione Benvenuto ---
        Label lblBenvenuto = new Label("Benvenuto, " + nomeBibliotecario + "!");
        lblBenvenuto.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblDescrizione = new Label("Scegli l'area della biblioteca che vuoi gestire:");
        lblDescrizione.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        // --- Bottoni di Navigazione ---
        HBox row1 = new HBox(15);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(15);
        row2.setAlignment(Pos.CENTER);

        btnLibri = new Button("Gestione Libri");
        btnUtenti = new Button("Gestione Utenti");
        btnPrestiti = new Button("Gestione Prestiti");

        // Dimensione uniforme per i bottoni
        btnLibri.setPrefWidth(150);
        btnUtenti.setPrefWidth(150);
        btnPrestiti.setPrefWidth(150);

        // Collegamento eventi ai Runnable
        btnLibri.setOnAction(e -> {
            if (onGestioneLibri != null) onGestioneLibri.run();
        });
        btnUtenti.setOnAction(e -> {
            if (onGestioneUtenti != null) onGestioneUtenti.run();
        });
        btnPrestiti.setOnAction(e -> {
            if (onGestionePrestiti != null) onGestionePrestiti.run();
        });

        row1.getChildren().addAll(btnLibri, btnUtenti);
        row2.getChildren().addAll(btnPrestiti);

        // Composizione finale della card centrale
        card.getChildren().addAll(topLine, lblBenvenuto, lblDescrizione, row1, row2);

        root.setCenter(card);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto Parent (BorderPane) da inserire nella Scena.
     */
    public Parent getRoot() {
        return root;
    }

    // --- Setter delle Callback (Injecting Behavior) ---

    /**
     * @brief Imposta l'azione da eseguire al click su "Gestione Libri".
     * @param r Il Runnable contenente la logica di cambio scena.
     */
    public void setOnGestioneLibri(Runnable r) {
        this.onGestioneLibri = r;
    }

    /**
     * @brief Imposta l'azione da eseguire al click su "Gestione Utenti".
     * @param r Il Runnable contenente la logica di cambio scena.
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