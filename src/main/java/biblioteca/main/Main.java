package biblioteca.main;

import biblioteca.controller.LibriController;
import biblioteca.controller.UtentiController;
import biblioteca.controller.PrestitiController;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.Autenticazione;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LoginView;
import biblioteca.view.MainFrame;
import biblioteca.view.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @brief Classe principale.
 * Questa classe estende Application e gestisce il ciclo di vita dell'interfaccia grafica.
 * Si occupa dell'inizializzazione dei dati da file, della configurazione dello stage primario
 * e della navigazione tra le scene principali: Login, Menu, MainFrame.
 */
public class Main extends Application {

    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private GestionePrestiti gestionePrestiti;
    private Autenticazione bibliotecario;

    private ArchivioFile archivio;

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     * Viene chiamato dal runtime JavaFX dopo l'inizializzazione del sistema.
     * Carica i dati come Libri, Utenti, Prestiti, Autenticazione tramite l'archivio
     * e mostra la schermata di login.
     * @param primaryStage Lo stage principale fornito dalla piattaforma JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {

        archivio = new ArchivioFile(".");

        gestioneLibri    = archivio.caricaLibri();
        gestioneUtenti   = archivio.caricaUtenti();
        gestionePrestiti = archivio.caricaPrestiti();
        bibliotecario    = archivio.caricaAutenticazione();

        gestioneUtenti.setGestionePrestiti(gestionePrestiti);

        mostraLogin(primaryStage);
        primaryStage.show();
    }

    /**
     * @brief Configura e mostra la scena di Login.
     * Imposta la vista di login sullo stage corrente e definisce la logica di autenticazione.
     * Se la password Ã¨ corretta, salva lo stato e passa al menu principale.
     * @param stage Lo stage su cui visualizzare la scena.
     */
    private void mostraLogin(Stage stage) {
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);

        stage.setTitle("Login Biblioteca");
        stage.setScene(loginScene);
        stage.centerOnScreen();

        loginView.setOnLogin(() -> {
            String password = loginView.getPassword().trim();

            boolean ok = bibliotecario.login(password);

            if (ok) {
                loginView.mostraErrore("");
                loginView.pulisciCampi();
                // salvo eventuale cambio di password
                archivio.salvaAutenticazione(bibliotecario);
                mostraMenu(stage);
            } else {
                loginView.mostraErrore("Password errata.");
                loginView.pulisciCampi();
            }
        });
    }

    /**
     * @brief Configura e mostra la scena del Menu principale.
     * Crea la grafica del menu di navigazione intermedio, collegando i pulsanti
     * alle rispettive schermate operative (Libri, Utenti, Prestiti) o al logout.
     * @param stage Lo stage su cui visualizzare la scena.
     */
    private void mostraMenu(Stage stage) {
        MenuView menu = new MenuView("Bibliotecario");

        Scene menuScene = new Scene(menu.getRoot(), 500, 400);

        stage.setTitle("Menu Biblioteca");
        stage.setScene(menuScene);
        stage.centerOnScreen();

        menu.setOnGestioneLibri(()    -> mostraMain(stage, 0));
        menu.setOnGestioneUtenti(()   -> mostraMain(stage, 1));
        menu.setOnGestionePrestiti(() -> mostraMain(stage, 2));

        menu.setOnLogout(() -> mostraLogin(stage));
    }

    /**
     * @brief Configura e mostra la finestra operativa principale.
     * Istanzia i controller specifici per ogni sezione: Libri, Utenti, Prestiti.
     * Risolve le dipendenze tra controller e imposta la scena principale visualizzando
     * il tab richiesto.
     * @param stage Lo stage su cui visualizzare la scena.
     * @param tabIndex L'indice del tab da aprire inizialmente.
     */
    private void mostraMain(Stage stage, int tabIndex) {
        MainFrame mainView = new MainFrame(tabIndex);
        Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);

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
    }

    /**
     * @brief Punto di ingresso standard per l'applicazione Java.
     * Lancia il runtime di JavaFX chiamando il metodo launch().
     * @param args Argomenti da riga di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
