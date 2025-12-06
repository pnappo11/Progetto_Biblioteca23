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

public class LoginView {

    private final VBox root;              // <== questo è il "contenitore" che il Main userà
    private final PasswordField campoPassword;
    private final Button bottoneLogin;
    private final Label etichettaMessaggio;

    public LoginView() {
        campoPassword = new PasswordField();
        bottoneLogin = new Button("Login");
        bottoneLogin.setStyle(
        "-fx-background-color: #00C853;" +  // verde
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;"
        );

        etichettaMessaggio = new Label("");

        Label labelPassword = new Label("Password:");

        HBox riga1 = new HBox(10, labelPassword, campoPassword);
        riga1.setAlignment(Pos.CENTER);

        HBox riga2 = new HBox(bottoneLogin);
        riga2.setAlignment(Pos.CENTER);

        root = new VBox(10, riga1, riga2, etichettaMessaggio);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
    }

    // ====== QUESTO È IL METODO CHE MANCAVA ======
    public Parent getRoot() {
        return root;
    }

    public String getPassword() {
        return campoPassword.getText();
    }

    public void mostraErrore(String messaggio) {
        etichettaMessaggio.setText(messaggio);
    }

    public void pulisciCampi() {
        campoPassword.clear();
    }

    public void setOnLogin(Runnable azioneLogin) {
        bottoneLogin.setOnAction(e -> {
            if (azioneLogin != null) {
                azioneLogin.run();
            }
        });
    }
}
