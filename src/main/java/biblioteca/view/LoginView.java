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
        campoPasswordVisibile = new TextField();

        campoPasswordVisibile.textProperty().bindBidirectional(campoPassword.textProperty());

        campoPasswordVisibile.setVisible(false);
        campoPasswordVisibile.setManaged(false);

        checkMostraPassword = new CheckBox("Mostra password");

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
        });

        bottoneLogin = new Button("Login");
        bottoneLogin.getStyleClass().add("btn-primary");

        etichettaMessaggio = new Label("");
        etichettaMessaggio.getStyleClass().add("msg-error");

        Label labelPassword = new Label("Password:");

        HBox riga1 = new HBox(10, labelPassword, campoPassword, campoPasswordVisibile);
        riga1.setAlignment(Pos.CENTER);

        HBox rigaToggle = new HBox(checkMostraPassword);
        rigaToggle.setAlignment(Pos.CENTER_LEFT);

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
