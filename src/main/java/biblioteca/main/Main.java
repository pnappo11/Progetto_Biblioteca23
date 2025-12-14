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
 * @brief Classe principale dell'applicazione (Entry Point).
 * * Questa classe estende Application di JavaFX e gestisce il ciclo di vita dell'applicazione.
 * Si occupa di caricare i dati all'avvio, inizializzare i modelli e gestire la navigazione
 * tra le diverse scene (Login, Menu, Schermata Principale).
 */
public class Main extends Application {

    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private GestionePrestiti gestionePrestiti;
    private Autenticazione bibliotecario;
    private ArchivioFile archivio;

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     *  Inizializza il layer di persistenza, carica i dati dai file, imposta l'icona dell'applicazione
     * e mostra la schermata di login iniziale.
     * * @param primaryStage Lo stage primario fornito dalla piattaforma JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        archivio = new ArchivioFile(".");
        gestioneLibri = archivio.caricaLibri();
        gestioneUtenti = archivio.caricaUtenti();
        gestionePrestiti = archivio.caricaPrestiti();
        bibliotecario = archivio.caricaAutenticazione();
        if (gestioneUtenti != null && gestionePrestiti != null) gestioneUtenti.setGestionePrestiti(gestionePrestiti);

        try {
            Image ico = new Image(getClass().getResourceAsStream("/biblioteca/view/img/logo.png"));
            primaryStage.getIcons().add(ico);
        } catch (Exception e) {
            System.out.println("Icona non trovata: /biblioteca/view/img/logo.png");
        }

        mostraLogin(primaryStage);
        primaryStage.show();
    }

    /**
     * @brief Applica lo stile CSS alla scena specificata.
     * * Tenta di caricare il file "file.css" e di aggiungerlo alla lista degli stili della scena, per rendere il layout più piacevole.
     * * @param scene La scena a cui applicare il tema grafico.
     */
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

    /**
     * @brief Configura e mostra la schermata di Login.
     * * Crea la vista di login, imposta la scena e definisce la logica di callback
     * per il tentativo di accesso (verifica password e transizione al menu).
     * * @param stage Lo stage su cui visualizzare la scena di login.
     */
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
                    if (archivio != null && bibliotecario != null) archivio.salvaAutenticazione(bibliotecario);
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

    /**
     * @brief Configura e mostra la schermata del Menu principale.
     * Crea la vista del menu e definisce le azioni di navigazione verso le diverse sezioni
     * (Libri, Utenti, Prestiti) o il logout.
     * @param stage Lo stage su cui visualizzare la scena del menu.
     */
    private void mostraMenu(Stage stage) {
        try {
            MenuView menu = new MenuView("Bibliotecario");
            Scene menuScene = new Scene(menu.getRoot(), 500, 400);
            applicaTema(menuScene);

            stage.setTitle("Menu Biblioteca");
            stage.setScene(menuScene);
            stage.centerOnScreen();

            menu.setOnGestioneLibri(() -> mostraMain(stage, 0));
            menu.setOnGestioneUtenti(() -> mostraMain(stage, 1));
            menu.setOnGestionePrestiti(() -> mostraMain(stage, 2));
            menu.setOnLogout(() -> mostraLogin(stage));
        } catch (Exception ex) {
            ex.printStackTrace();
            mostraLogin(stage);
        }
    }

    /**
     * @brief Configura e mostra la schermata principale operativa .
     * Inizializza i controller (LibriController, UtentiController, PrestitiController),
     * collega le dipendenze tra di essi e imposta la tab selezionata.
     * @param stage rappresenta lo stage su cui visualizzare l'applicazione principale.
     * @param tabIndex L'indice della scheda (tab) da aprire inizialmente (0=Libri, 1=Utenti, 2=Prestiti).
     */
    private void mostraMain(Stage stage, int tabIndex) {
        try {
            MainFrame mainView = new MainFrame(tabIndex);
            Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);
            applicaTema(mainScene);

            stage.setTitle("Biblioteca universitaria");
            stage.setScene(mainScene);
            stage.centerOnScreen();

            LibriController libriCtrl = new LibriController(gestioneLibri, mainView.getLibriView(), archivio);
            UtentiController utentiCtrl = new UtentiController(gestioneUtenti, gestionePrestiti, mainView.getUtentiView(), archivio);
            PrestitiController prestitiCtrl = new PrestitiController(gestionePrestiti, mainView.getPrestitiView(), archivio, gestioneLibri, gestioneUtenti, libriCtrl, utentiCtrl);

            mainView.setOnTabPrestitiSelected(prestitiCtrl::aggiornaDaModel);

            mainView.getBtnMenu().setOnAction(e -> mostraMenu(stage));
            mainView.getBtnLogout().setOnAction(e -> mostraLogin(stage));
        } catch (Exception ex) {
            ex.printStackTrace();
            mostraMenu(stage);
        }
    }

    /**
     * @brief Metodo main standard di Java.
     * Lancia l'applicazione .
     * @param args sono gli argomenti da riga di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}