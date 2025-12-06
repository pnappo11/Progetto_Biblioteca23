/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.main;

import biblioteca.view.LoginView;
import biblioteca.view.MainFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // partiamo SEMPRE dalla schermata di login
        mostraLogin(primaryStage);
        primaryStage.show();
    }

    // --- mostra la schermata di LOGIN ---
    private void mostraLogin(Stage primaryStage) {
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);

        primaryStage.setTitle("Login Biblioteca");
        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();

        // quando il login va a buon fine → passo al main
        loginView.setOnLogin(() -> mostraMain(primaryStage));
    }

    // --- mostra la schermata PRINCIPALE con i tab + logout ---
    private void mostraMain(Stage primaryStage) {
        MainFrame mainView = new MainFrame();
        Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);

        primaryStage.setTitle("Biblioteca universitaria");
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();

        // quando clicchi Logout → torni al login
        mainView.getBtnLogout().setOnAction(e -> mostraLogin(primaryStage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}



