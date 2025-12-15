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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @brief Classe che gestisce l'interfaccia grafica relativa all' Autenticazione .
 *
 * Questa classe rappresenta la "View" iniziale dell'applicazione.
 * Si occupa di mostrare il campo per l'inserimento della password e il pulsante di accesso.
 */
public class LoginView {

    private final VBox root;

    private final PasswordField campoPassword;

    private final TextField campoPasswordVisibile;

    private final CheckBox checkMostraPassword;

    private final Button bottoneLogin;

    private final Label etichettaMessaggio;

    /**
     * @brief Costruttore della LoginView.
     * Inizializza i componenti grafici e definisce il layout della schermata di login.
     */
    public LoginView() {
        campoPassword = new PasswordField();
        campoPassword.setPromptText("Password");

        campoPasswordVisibile = new TextField();
        campoPasswordVisibile.setPromptText("Password");

        campoPasswordVisibile.textProperty().bindBidirectional(campoPassword.textProperty());

        campoPasswordVisibile.setVisible(false);
        campoPasswordVisibile.setManaged(false);

        checkMostraPassword = new CheckBox("Mostra password");

        bottoneLogin = new Button("Login");
        bottoneLogin.getStyleClass().add("btn-primary");

        etichettaMessaggio = new Label("");
        etichettaMessaggio.getStyleClass().add("msg-error");
        etichettaMessaggio.setWrapText(true);

        // --- Password: label sopra + campo sotto (layout estetico)
        Label labelPassword = new Label("Password");
        labelPassword.getStyleClass().add("field-label");

        // Metto entrambi i campi in colonna (uno sarà sempre nascosto/managed=false)
        VBox gruppoPassword = new VBox(6, labelPassword, campoPassword, campoPasswordVisibile);
        gruppoPassword.getStyleClass().add("login-field-group");
        gruppoPassword.setFillWidth(true);

        campoPassword.setMaxWidth(Double.MAX_VALUE);
        campoPasswordVisibile.setMaxWidth(Double.MAX_VALUE);

        // Toggle e bottone (come prima, ma in layout più pulito)
        HBox rigaToggle = new HBox(checkMostraPassword);
        rigaToggle.setAlignment(Pos.CENTER_LEFT);

        bottoneLogin.setMaxWidth(Double.MAX_VALUE);
        HBox riga2 = new HBox(bottoneLogin);
        riga2.setAlignment(Pos.CENTER);

        Hyperlink linkRecupero = new Hyperlink("Hai dimenticato la password?");
        linkRecupero.setId("resetMode");
        linkRecupero.setFocusTraversable(false);
        linkRecupero.getStyleClass().add("link-reset");

        Label domanda = new Label("Domanda di sicurezza: Qual è il tuo cibo preferito?");
        domanda.getStyleClass().add("muted");

        TextField campoRisposta = new TextField();
        campoRisposta.setId("resetAnswer");
        campoRisposta.setPromptText("Risposta");
        campoRisposta.setMaxWidth(Double.MAX_VALUE);

        PasswordField campoNuovaPassword = new PasswordField();
        campoNuovaPassword.setId("resetNewPass");
        campoNuovaPassword.setPromptText("Nuova password");
        campoNuovaPassword.setMaxWidth(Double.MAX_VALUE);

        TextField campoNuovaPasswordVisibile = new TextField();
        campoNuovaPasswordVisibile.setPromptText("Nuova password");
        campoNuovaPasswordVisibile.setMaxWidth(Double.MAX_VALUE);
        campoNuovaPasswordVisibile.textProperty().bindBidirectional(campoNuovaPassword.textProperty());
        campoNuovaPasswordVisibile.setVisible(false);
        campoNuovaPasswordVisibile.setManaged(false);

        Button btnCambia = new Button("Cambia password");
        btnCambia.getStyleClass().add("btn-secondary");
        btnCambia.setMaxWidth(Double.MAX_VALUE);
        btnCambia.setOnAction(e -> bottoneLogin.fire());

        VBox boxReset = new VBox(8, domanda, campoRisposta, campoNuovaPassword, campoNuovaPasswordVisibile, btnCambia);
        boxReset.setVisible(false);
        boxReset.setManaged(false);

        boxReset.setAlignment(Pos.CENTER_LEFT);
        boxReset.getStyleClass().add("reset-box");

        domanda.setVisible(false); domanda.setManaged(false);
        campoRisposta.setVisible(false); campoRisposta.setManaged(false);
        campoNuovaPassword.setVisible(false); campoNuovaPassword.setManaged(false);
        campoNuovaPasswordVisibile.setVisible(false); campoNuovaPasswordVisibile.setManaged(false);
        btnCambia.setVisible(false); btnCambia.setManaged(false);

        checkMostraPassword.selectedProperty().addListener((obs, oldVal, mostra) -> {
            campoPasswordVisibile.setVisible(mostra);
            campoPasswordVisibile.setManaged(mostra);

            campoPassword.setVisible(!mostra);
            campoPassword.setManaged(!mostra);

            if (mostra) {
                campoPasswordVisibile.requestFocus();
                campoPasswordVisibile.positionCaret(campoPasswordVisibile.getText().length());
            } else {
                campoPassword.requestFocus();
                campoPassword.positionCaret(campoPassword.getText().length());
            }

            if (domanda.isVisible()) {
                campoNuovaPasswordVisibile.setVisible(mostra);
                campoNuovaPasswordVisibile.setManaged(mostra);

                campoNuovaPassword.setVisible(!mostra);
                campoNuovaPassword.setManaged(!mostra);
            }
        });

        linkRecupero.setOnAction(e -> {
            boolean open = !domanda.isVisible();
             boxReset.setVisible(open);
            boxReset.setManaged(open);
            domanda.setVisible(open); domanda.setManaged(open);
            campoRisposta.setVisible(open); campoRisposta.setManaged(open);
            btnCambia.setVisible(open); btnCambia.setManaged(open);

            if (open) {
                boolean mostra = checkMostraPassword.isSelected();

                campoNuovaPasswordVisibile.setVisible(mostra);
                campoNuovaPasswordVisibile.setManaged(mostra);

                campoNuovaPassword.setVisible(!mostra);
                campoNuovaPassword.setManaged(!mostra);
            } else {
                campoNuovaPassword.setVisible(false); campoNuovaPassword.setManaged(false);
                campoNuovaPasswordVisibile.setVisible(false); campoNuovaPasswordVisibile.setManaged(false);
            }
        });

        // --- Card estetica (CSS-first): metto tutto dentro una "card"
        VBox card = new VBox(12, gruppoPassword, rigaToggle, riga2, linkRecupero, boxReset, etichettaMessaggio);
        card.getStyleClass().addAll("card", "login-card");
        card.setFillWidth(true);

        root = new VBox(card);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("login-root");
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     * @return L'oggetto di tipo Parent contenente l'interfaccia.
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * @brief Recupera il testo inserito nel campo password.
     * @return La password digitata dall'utente.
     */
    public String getPassword() {
        return campoPassword.getText();
    }

    /**
     * @brief Visualizza un messaggio di errore o feedback all'utente.
     * @param messaggio Il testo da mostrare nell'etichetta dedicata.
     */
    public void mostraErrore(String messaggio) {
        etichettaMessaggio.setText(messaggio);
    }

    /**
     * @brief Pulisce il campo password.
     */
    public void pulisciCampi() {
        campoPassword.clear();
        checkMostraPassword.setSelected(false);
    }

    /**
     * @brief Imposta l'azione da eseguire al momento del click del tasto Login.
     * @param azioneLogin logica da eseguire quando si clicca Login.
     */
    public void setOnLogin(Runnable azioneLogin) {
        bottoneLogin.setOnAction(e -> {
            if (azioneLogin != null) {
                azioneLogin.run();
            }
        });
    }
}
