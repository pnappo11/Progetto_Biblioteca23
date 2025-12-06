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
        // scena di login
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);

        primaryStage.setTitle("Login Biblioteca");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // quando fai login → vai alla finestra principale
        loginView.setOnLogin(() -> {
            MainFrame mainView = new MainFrame();
            Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);
            primaryStage.setTitle("Biblioteca universitaria");
            primaryStage.setScene(mainScene);
            primaryStage.centerOnScreen();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}


