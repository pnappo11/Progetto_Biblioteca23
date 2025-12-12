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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @brief Classe che gestisce l'interfaccia grafica relativa all' Autenticazione .
 *
 * Questa classe rappresenta la "View" iniziale dell'applicazione.
 * Si occupa di mostrare il campo per l'inserimento della password e il pulsante di accesso(login).
 */
public class LoginView {

    /**
     * @brief Contenitore principale con layout verticale(VBox) che raggruppa gli elementi della schermata.
     */
    private final VBox root;

    /**
     * @brief Campo di input mascherato per la password.
     */
    private final PasswordField campoPassword;

    /**
     * @brief Campo di input NON mascherato (per mostrare la password).
     */
    private final TextField campoPasswordVisibile;

    /**
     * @brief Checkbox per mostrare/nascondere la password.
     */
    private final CheckBox checkMostraPassword;

    /**
     * @brief tasto per inviare la richiesta di login.
     */
    private final Button bottoneLogin;

    /**
     * @brief Etichetta per mostrare messaggi di feedback sull'esito del login (es. "Password errata").
     */
    private final Label etichettaMessaggio;

    /**
     * @brief Costruttore della LoginView.
     *
     * Inizializza i componenti grafici e definisce il layout della schermata di login.
     */
    public LoginView() {
        campoPassword = new PasswordField();
        campoPasswordVisibile = new TextField();

        // stessa password in entrambi i campi
        campoPasswordVisibile.textProperty().bindBidirectional(campoPassword.textProperty());

        // di default si vede solo il PasswordField (mascherato)
        campoPasswordVisibile.setVisible(false);
        campoPasswordVisibile.setManaged(false);

        checkMostraPassword = new CheckBox("Mostra password");

        checkMostraPassword.selectedProperty().addListener((obs, oldVal, mostra) -> {
            campoPasswordVisibile.setVisible(mostra);
            campoPasswordVisibile.setManaged(mostra);

            campoPassword.setVisible(!mostra);
            campoPassword.setManaged(!mostra);

            // focus sul campo corretto (solo UX, non tocca il messaggio errore)
            if (mostra) {
                campoPasswordVisibile.requestFocus();
                campoPasswordVisibile.positionCaret(campoPasswordVisibile.getText().length());
            } else {
                campoPassword.requestFocus();
                campoPassword.positionCaret(campoPassword.getText().length());
            }
        });

        bottoneLogin = new Button("Login");
        bottoneLogin.setStyle(
            "-fx-background-color: #00C853;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );

        etichettaMessaggio = new Label("");
        etichettaMessaggio.setStyle("-fx-text-fill: red;");

        Label labelPassword = new Label("Password:");

        // riga password: label + (uno dei due campi, che si alternano)
        HBox riga1 = new HBox(10, labelPassword, campoPassword, campoPasswordVisibile);
        riga1.setAlignment(Pos.CENTER);

        // riga toggle (sotto)
        HBox rigaToggle = new HBox(checkMostraPassword);
        rigaToggle.setAlignment(Pos.CENTER_LEFT);

        // riga bottone
        HBox riga2 = new HBox(bottoneLogin);
        riga2.setAlignment(Pos.CENTER);

        root = new VBox(10, riga1, rigaToggle, riga2, etichettaMessaggio);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
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
        // sono sempre uguali grazie al bind
        return campoPassword.getText();
    }

    /**
     * @brief Visualizza un messaggio di errore o feedback all'utente.
     * @param messaggio Il testo da mostrare nell'etichetta dedicata.
     */
    public void mostraErrore(String messaggio) {
        etichettaMessaggio.setText(messaggio);
        if (messaggio == null || messaggio.isEmpty()) {
            etichettaMessaggio.setStyle("");
        } else {
            etichettaMessaggio.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * @brief Pulisce il campo password.
     */
    public void pulisciCampi() {
        campoPassword.clear(); // l'altro si svuota da solo (bind)
        checkMostraPassword.setSelected(false); // torna mascherata
    }

    /**
     * @brief Imposta l'azione da eseguire al momento del click del tasto Login.
     * @param azioneLogin logica da eseguire quando si clicca Login.
     */
    public void setOnLogin(Runnable azioneLogin) {
        bottoneLogin.setOnAction(e -> {
            if (azioneLogin != null) {
                azioneLogin.run();   // il controller farà il resto
            }
        });
    }
}
