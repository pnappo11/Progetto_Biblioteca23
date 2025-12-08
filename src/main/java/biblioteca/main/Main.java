
package biblioteca.main;

import biblioteca.view.LoginView;
import biblioteca.view.MainFrame;
import biblioteca.view.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @brief Classe principale dell'applicazione (Entry Point).
 *
 * Agisce come un "Router" di alto livello, decidendo quale schermata mostrare
 * in base agli eventi (login effettuato, click su un bottone del menu, logout).
 *
 * @author tommy
 */
public class Main extends Application {

    // --- Modelli Dati Globali ---
    // Questi riferimenti sono dichiarati qui per essere potenzialmente
    // passati ai vari controller durante la navigazione.
    private GestioneLibri gestioneLibri;
    private GestioneUtenti gestioneUtenti;
    private GestionePrestiti gestionePrestiti;
    private Autenticazione bibliotecario;
    
    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     *
     * Viene chiamato automaticamente dal launcher JavaFX.
     * Imposta la prima schermata visibile, che è quella di Login.
     *
     * @param primaryStage La finestra principale creata dal runtime JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        mostraLogin(primaryStage);
        primaryStage.show();
    }

    // -----------------------------------------------------------
    // SEZIONE 1: LOGIN
    // -----------------------------------------------------------

    /**
     * @brief Configura e mostra la scena di Login.
     *
     * Crea la vista {@link LoginView} e imposta il listener per l'evento di successo.
     * Se il login va a buon fine (callback invocata), transita al Menu.
     *
     * @param stage Lo stage su cui impostare la scena.
     */
    private void mostraLogin(Stage stage) {
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getRoot(), 400, 200);

        stage.setTitle("Login Biblioteca");
        stage.setScene(loginScene);
        stage.centerOnScreen();

        // Callback: se il login va bene → vai al MENU
        loginView.setOnLogin(() -> mostraMenu(stage));
    }

    // -----------------------------------------------------------
    // SEZIONE 2: MENU INTERMEDIO
    // -----------------------------------------------------------

    /**
     * @brief Configura e mostra la scena del Menu principale.
     *
     * Il menu funge da snodo centrale con 3 grandi opzioni (Libri, Utenti, Prestiti)
     * e il tasto Logout.
     *
     * @param stage Lo stage su cui impostare la scena.
     */
    private void mostraMenu(Stage stage) {
        // Inizializza la vista menu (puoi passare il nome utente loggato qui)
        MenuView menu = new MenuView("Bibliotecario"); 
        
        Scene menuScene = new Scene(menu.getRoot(), 500, 400);

        stage.setTitle("Menu Biblioteca");
        stage.setScene(menuScene);
        stage.centerOnScreen();

        // Configurazione Navigazione dai bottoni del Menu:
        // Cliccando su un bottone, si va al MainFrame aprendo il tab specifico.
        menu.setOnGestioneLibri(() -> mostraMain(stage, 0));    // Tab 0: Libri
        menu.setOnGestioneUtenti(() -> mostraMain(stage, 1));   // Tab 1: Utenti
        menu.setOnGestionePrestiti(() -> mostraMain(stage, 2)); // Tab 2: Prestiti

        // Logout: torna alla schermata di login
        menu.setOnLogout(() -> mostraLogin(stage));
    }

    // -----------------------------------------------------------
    // SEZIONE 3: DASHBOARD PRINCIPALE (TAB PANE)
    // -----------------------------------------------------------

    /**
     * @brief Configura e mostra la finestra principale operativa (MainFrame).
     *
     * Questa vista contiene i pannelli gestionali organizzati in schede (Tab).
     *
     * @param stage    Lo stage su cui impostare la scena.
     * @param tabIndex L'indice della scheda da selezionare all'avvio:
     * <ul>
     * <li>0: Gestione Libri</li>
     * <li>1: Gestione Utenti</li>
     * <li>2: Gestione Prestiti</li>
     * </ul>
     */
    private void mostraMain(Stage stage, int tabIndex) {
        MainFrame mainView = new MainFrame(tabIndex);
        Scene mainScene = new Scene(mainView.getRoot(), 1000, 600);

        stage.setTitle("Biblioteca universitaria");
        stage.setScene(mainScene);
        stage.centerOnScreen();

        // 🔹 Gestione tasto "Torna al Menu":
        // Permette di tornare alla selezione macroscopica senza effettuare logout.
        mainView.getBtnMenu().setOnAction(e -> mostraMenu(stage));

        // 🔹 Gestione tasto "Logout" diretto dalla dashboard:
        // Termina la sessione e riporta al login.
        mainView.getBtnLogout().setOnAction(e -> mostraLogin(stage));
    }

    /**
     * @brief Punto di ingresso standard per applicazioni Java.
     *
     * Lancia il runtime JavaFX invocando il metodo {@link #launch(String...)}.
     *
     * @param args Argomenti da riga di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


