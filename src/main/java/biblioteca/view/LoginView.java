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
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @brief Classe che gestisce l'interfaccia grafica di Autenticazione (Login).
 *
 * Questa classe rappresenta la "View" iniziale dell'applicazione.
 * Si occupa di mostrare il campo per l'inserimento della password e il pulsante di conferma.
 *
 * @author tommy
 */
public class LoginView {

    /**
     * @brief Contenitore principale (layout verticale) che raggruppa gli elementi della schermata.
     */
    private final VBox root;

    /**
     * @brief Campo di input mascherato per la password.
     */
    private final PasswordField campoPassword;

    /**
     * @brief Bottone per inviare la richiesta di login.
     */
    private final Button bottoneLogin;

    /**
     * @brief Etichetta per mostrare messaggi di feedback (es. "Password errata").
     */
    private final Label etichettaMessaggio;

    /**
     * @brief Costruttore della LoginView.
     *
     * Inizializza i componenti grafici e definisce il layout.
     * In questa versione, viene applicato uno stile CSS inline al bottone di login
     * per renderlo verde (#00C853) con testo bianco e grassetto.
     */
    public LoginView() {
        campoPassword = new PasswordField();
        bottoneLogin = new Button("Login");
        
        // Applicazione stile personalizzato (Verde Material Design)
        bottoneLogin.setStyle(
            "-fx-background-color: #00C853;" +  // verde
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );

        etichettaMessaggio = new Label("");

        Label labelPassword = new Label("Password:");

        // Layout riga input
        HBox riga1 = new HBox(10, labelPassword, campoPassword);
        riga1.setAlignment(Pos.CENTER);

        // Layout riga bottone
        HBox riga2 = new HBox(bottoneLogin);
        riga2.setAlignment(Pos.CENTER);

        // Assemblaggio nel contenitore principale
        root = new VBox(10, riga1, riga2, etichettaMessaggio);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia.
     *
     * Metodo necessario per inserire questa vista all'interno di una Scene JavaFX.
     * @return L'oggetto Parent (VBox) contenente l'interfaccia.
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
     * @param messaggio Il testo da mostrare nella label dedicata.
     */
    public void mostraErrore(String messaggio) {
        etichettaMessaggio.setText(messaggio);
    }

    /**
     * @brief Pulisce il campo password.
     * Utile dopo un tentativo fallito o al momento del logout.
     */
    public void pulisciCampi() {
        campoPassword.clear();
    }

    /**
     * @brief Imposta l'azione da eseguire al click del tasto Login.
     *
     * Questo metodo permette di collegare la View al Controller (o al Main)
     * senza creare dipendenze dirette.
     *
     * @param azioneLogin Un'interfaccia funzionale (Runnable) contenente la logica di verifica.
     */
    public void setOnLogin(Runnable azioneLogin) {
        bottoneLogin.setOnAction(e -> {
            if (azioneLogin != null) {
                azioneLogin.run();
            }
        });
    }
}
