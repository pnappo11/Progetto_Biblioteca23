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

public class MenuView {

    private final BorderPane root;

    private final Button btnLibri;
    private final Button btnUtenti;
    private final Button btnPrestiti;
    private final Button btnLogout;   // piccolo logout in alto

    private Runnable onGestioneLibri;
    private Runnable onGestioneUtenti;
    private Runnable onGestionePrestiti;
    private Runnable onLogout;

    // se non passi il nome, usa "Bibliotecario"
    public MenuView() {
        this("Bibliotecario");
    }

    // 👇 costruttore con il nome del bibliotecario
    public MenuView(String nomeBibliotecario) {
        root = new BorderPane();

        VBox card = new VBox(20);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_CENTER);

        // riga in alto: titolo + logout piccolo a destra
        Label lblTitolo = new Label("Menu Biblioteca");
        lblTitolo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        btnLogout = new Button("Logout");
        btnLogout.setStyle(
                "-fx-background-color: #ff4d4d;" +
                "-fx-text-fill: black;" +
                "-fx-font-weight: bold;"
        );
        btnLogout.setOnAction(e -> {
            if (onLogout != null) onLogout.run();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topLine = new HBox(10, lblTitolo, spacer, btnLogout);
        topLine.setAlignment(Pos.CENTER_LEFT);

        // 🔹 NUOVA SCRITTA: “Benvenuto, <nome>!”
        Label lblBenvenuto = new Label("Benvenuto, bibliotecario!");
        lblBenvenuto.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 🔹 Frase sotto, più piccola
        Label lblDescrizione = new Label("Scegli l'area della biblioteca che vuoi gestire:");
        lblDescrizione.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555;");

        // bottoni
        HBox row1 = new HBox(15);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(15);
        row2.setAlignment(Pos.CENTER);

        btnLibri = new Button("Gestione Libri");
        btnUtenti = new Button("Gestione Utenti");
        btnPrestiti = new Button("Gestione Prestiti");

        btnLibri.setPrefWidth(150);
        btnUtenti.setPrefWidth(150);
        btnPrestiti.setPrefWidth(150);

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

        // niente più “Seleziona una sezione” 👇
        card.getChildren().addAll(topLine, lblBenvenuto, lblDescrizione, row1, row2);

        root.setCenter(card);
    }

    public Parent getRoot() {
        return root;
    }

    // --- setter delle callback ---
    public void setOnGestioneLibri(Runnable r) {
        this.onGestioneLibri = r;
    }

    public void setOnGestioneUtenti(Runnable r) {
        this.onGestioneUtenti = r;
    }

    public void setOnGestionePrestiti(Runnable r) {
        this.onGestionePrestiti = r;
    }

    public void setOnLogout(Runnable r) {
        this.onLogout = r;
    }
}
