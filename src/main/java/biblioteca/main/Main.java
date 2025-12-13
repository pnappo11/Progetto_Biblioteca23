package biblioteca.main;

import biblioteca.controller.LibriController;
import biblioteca.controller.PrestitiController;
import biblioteca.controller.UtentiController;
import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LoginView;
import biblioteca.view.MainFrame;
import biblioteca.view.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @brief Classe principale.
 * Gestisce il ciclo di vita JavaFX e la navigazione: Login -> Menu -> MainFrame.
 */
public class Main extends Application {

    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private GestionePrestiti gestionePrestiti;
    private Autenticazione bibliotecario;
    private ArchivioFile archivio;

    @Override
    public void start(Stage primaryStage) {

        archivio = new ArchivioFile(".");

        gestioneLibri    = archivio.caricaLibri();
        gestioneUtenti   = archivio.caricaUtenti();
        gestionePrestiti = archivio.caricaPrestiti();
        bibliotecario    = archivio.caricaAutenticazione();

        if (gestioneUtenti != null && gestionePrestiti != null) {
            gestioneUtenti.setGestionePrestiti(gestionePrestiti);
        }

        // icona (una sola volta)
        try {
            Image ico = new Image(getClass().getResourceAsStream("/biblioteca/view/img/logo.png"));
            primaryStage.getIcons().add(ico);
        } catch (Exception e) {
            System.out.println("Icona non trovata: /biblioteca/view/img/logo.png");
        }

        mostraLogin(primaryStage);
        primaryStage.show();
    }

    private void applicaTema(Scene scene) {
        try {
            URL css = Main.class.getResource("/biblioteca/view/file.css");
            if (css == null) {
                System.out.println("CSS NON TROVATO: /biblioteca/view/file.css");
                return;
            }
            scene.getStylesheets().add(css.toExternalForm());
        } catch (Exception e) {
            System.out.println("Errore applicazione CSS");
            e.printStackTrace();
        }
    }

    private void mostraLogin(Stage stage) {
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);
        applicaTema(loginScene);

        stage.setTitle("Login Biblioteca");
        stage.setScene(loginScene);
        stage.centerOnScreen();

        loginView.setOnLogin(() -> {
            try {
                String password = loginView.getPassword().trim();
                boolean ok = bibliotecario != null && bibliotecario.login(password);

                System.out.println("LOGIN premuto - ok=" + ok);

                if (ok) {
                    loginView.mostraErrore("");
                    loginView.pulisciCampi();

                    if (archivio != null && bibliotecario != null) {
                        archivio.salvaAutenticazione(bibliotecario);
                    }

                    mostraMenu(stage);
                } else {
                    loginView.mostraErrore("Password errata.");
                    loginView.pulisciCampi();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                loginView.mostraErrore("Errore login (guarda console).");
            }
        });
    }

    private void mostraMenu(Stage stage) {
        try {
            MenuView menu = new MenuView("Bibliotecario");
            Scene menuScene = new Scene(menu.getRoot(), 500, 400);
            applicaTema(menuScene);

            stage.setTitle("Menu Biblioteca");
            stage.setScene(menuScene);
            stage.centerOnScreen();

            menu.setOnGestioneLibri(()    -> mostraMain(stage, 0));
            menu.setOnGestioneUtenti(()   -> mostraMain(stage, 1));
            menu.setOnGestionePrestiti(() -> mostraMain(stage, 2));
            menu.setOnLogout(() -> mostraLogin(stage));

        } catch (Exception ex) {
            ex.printStackTrace();
            // se fallisce il menu, torno al login con messaggio
            mostraLogin(stage);
        }
    }

    private void mostraMain(Stage stage, int tabIndex) {
        try {
            MainFrame mainView = new MainFrame(tabIndex);
            Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);
            applicaTema(mainScene);

            stage.setTitle("Biblioteca universitaria");
            stage.setScene(mainScene);
            stage.centerOnScreen();

            LibriController libriCtrl =
                    new LibriController(gestioneLibri, mainView.getLibriView(), archivio);

            UtentiController utentiCtrl =
                    new UtentiController(gestioneUtenti, gestionePrestiti,
                            mainView.getUtentiView(), archivio);

            PrestitiController prestitiCtrl =
                    new PrestitiController(gestionePrestiti,
                            mainView.getPrestitiView(),
                            archivio,
                            gestioneLibri,
                            gestioneUtenti,
                            libriCtrl,
                            utentiCtrl);

            utentiCtrl.setPrestitiController(prestitiCtrl);

            mainView.getBtnMenu().setOnAction(e -> mostraMenu(stage));
            mainView.getBtnLogout().setOnAction(e -> mostraLogin(stage));

        } catch (Exception ex) {
            ex.printStackTrace();
            mostraMenu(stage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
