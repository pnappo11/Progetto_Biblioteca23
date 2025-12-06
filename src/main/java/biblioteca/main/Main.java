/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.main;

import biblioteca.view.LoginView;
import biblioteca.view.MainFrame;
import biblioteca.view.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        mostraLogin(primaryStage);
        primaryStage.show();
    }

    // 1) LOGIN
    private void mostraLogin(Stage stage) {
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);

        stage.setTitle("Login Biblioteca");
        stage.setScene(loginScene);
        stage.centerOnScreen();

        // se il login va bene → vai al MENU
        loginView.setOnLogin(() -> mostraMenu(stage));
    }

    // 2) MENU con 3 opzioni + logout piccolo
    private void mostraMenu(Stage stage) {
        // nome che appare nel "Benvenuto, ..."
        MenuView menu = new MenuView("Mario Rossi");
        // oppure: MenuView menu = new MenuView("PasaCistopero");

        Scene menuScene = new Scene(menu.getRoot(), 500, 400);

        stage.setTitle("Menu Biblioteca");
        stage.setScene(menuScene);
        stage.centerOnScreen();

        // dai bottoni grandi vai alle varie sezioni (MainFrame)
        menu.setOnGestioneLibri(() -> mostraMain(stage, 0));
        menu.setOnGestioneUtenti(() -> mostraMain(stage, 1));
        menu.setOnGestionePrestiti(() -> mostraMain(stage, 2));

        // logout dal menu → torni al login
        menu.setOnLogout(() -> mostraLogin(stage));
    }

    // 3) FINESTRA PRINCIPALE con tab, Menu e Logout
    private void mostraMain(Stage stage, int tabIndex) {
        MainFrame mainView = new MainFrame(tabIndex);
        Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);

        stage.setTitle("Biblioteca universitaria");
        stage.setScene(mainScene);
        stage.centerOnScreen();

        // 🔹 tasto Menu: torni al Menu (senza fare logout)
        mainView.getBtnMenu().setOnAction(e -> mostraMenu(stage));

        // 🔹 tasto Logout: torni al login
        mainView.getBtnLogout().setOnAction(e -> mostraLogin(stage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}


