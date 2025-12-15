package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestionePrestiti;
import biblioteca.model.GestioneUtenti;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LoginView;
import biblioteca.view.MainFrame;
import biblioteca.view.MenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @brief Controller principale dell'applicazione.
 * Questa classe funge da punto di ingresso e gestore dell'intera applicazione.
 * Gestisce il ciclo di vita della finestra principale, inizializza i modelli e
 * gestisce la navigazione tra le diverse scene (Login, Menu, Gestione principale).
 */
public class MainController {
    private final Stage stage;
    private final GestioneLibri gestioneLibri;
    private final GestioneUtenti gestioneUtenti;
    private final GestionePrestiti gestionePrestiti;
    private PrestitiController prestitiController;
    private final Autenticazione bibliotecario;
    private final ArchivioFile archivio;
    /**
     * @brief Costruttore della classe MainController.
     * Inizializza lo stage, il sistema di persistenza (ArchivioFile) e i modelli
     * principali (Libri, Utenti, Prestiti, Autenticazione).
     * @param stage Rappresenta lo stage primario di JavaFX su cui vengono caricate le scene.
     */
    public MainController(Stage stage) {
        this.stage = stage;
        this.archivio = new ArchivioFile(".");
        this.gestioneLibri    = new GestioneLibri();
        this.gestioneUtenti   = new GestioneUtenti();
        this.gestionePrestiti = new GestionePrestiti();
        this.gestioneUtenti.setGestionePrestiti(gestionePrestiti);
        this.bibliotecario = new Autenticazione();
    }
    /**
     * @brief Imposta il controller dei prestiti.
     * @param prestitiController L'istanza del controller per la gestione dei prestiti.
     */
    public void setPrestitiController(PrestitiController prestitiController) {
        this.prestitiController = prestitiController;
    }
    /**
     * @brief Avvia l'applicazione.
     * Mostra la schermata di login e rende visibile lo stage principale.
     */
    public void avvia() {
        mostraLogin();
        stage.show();
    }
    /**
     * @brief Configura e mostra la scena di Login.
     * Istanzia la vista di login e il relativo controller di autenticazione,
     * impostando la scena sullo stage.
     */
    public void mostraLogin() {
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);
        stage.setTitle("Login Biblioteca");
        stage.setScene(loginScene);
        stage.centerOnScreen();
        new AuthController(bibliotecario, loginView, this);
    }
    /**
     * @brief Configura e mostra la scena del Menu principale.
     * Crea la view del menu intermedio, collegando i pulsanti alle relative funzioni
     * di navigazione.
     */
    public void mostraMenu() {
        
        MenuView menu = new MenuView("Bibliotecario");
        Scene menuScene = new Scene(menu.getRoot(), 500, 400);
        stage.setTitle("Menu Biblioteca");
        stage.setScene(menuScene);
        stage.centerOnScreen();
        menu.setOnGestioneLibri(()    -> mostraMain(0));
        menu.setOnGestioneUtenti(()   -> mostraMain(1));
        menu.setOnGestionePrestiti(() -> mostraMain(2));
        menu.setOnLogout(this::mostraLogin);
    }
    /**
     * @brief Configura e mostra la finestra operativa principale (MainFrame).
     * Inizializza i controller specifici (Libri, Utenti, Prestiti), gestisce le dipendenze
     * incrociate tra essi e seleziona il tab specificato.
     * @param tabIndex L'indice del tab da aprire inizialmente (0=Libri, 1=Utenti, 2=Prestiti).
     */
   public void mostraMain(int tabIndex) {
    MainFrame mainView = new MainFrame(tabIndex);
    Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);
    stage.setTitle("Biblioteca universitaria");
    stage.setScene(mainScene);
    stage.centerOnScreen();
    LibriController libriCtrl = new LibriController(
            gestioneLibri,
            mainView.getLibriView(),
            archivio
    );
    UtentiController utentiCtrl = new UtentiController(
            gestioneUtenti,
            gestionePrestiti,
            mainView.getUtentiView(),
            archivio
    );
    PrestitiController prestitiCtrl = new PrestitiController(
            gestionePrestiti,
            mainView.getPrestitiView(),
            archivio,
            gestioneLibri,
            gestioneUtenti,
            libriCtrl,
            utentiCtrl
    );
    utentiCtrl.setPrestitiController(prestitiCtrl);
    mainView.getBtnMenu().setOnAction(e -> mostraMenu());
    mainView.getBtnLogout().setOnAction(e -> mostraLogin());
}


}
