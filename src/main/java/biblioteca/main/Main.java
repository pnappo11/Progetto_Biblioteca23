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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @brief Classe principale dell'applicazione.
 * Questa classe estende Application di JavaFX e gestisce il ciclo di vita dell'applicazione.
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
     *  Inizializza la persistenza, carica i dati dai file, imposta l'icona dell'applicazione
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
        Scene loginScene = new Scene(loginView.getRoot());
        applicaTema(loginScene);

        stage.setTitle("Login Biblioteca");
        stage.setScene(loginScene);
 
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.setResizable(false);

        stage.sizeToScene();
        stage.centerOnScreen();


        javafx.scene.control.Hyperlink cb =
                (javafx.scene.control.Hyperlink) loginView.getRoot().lookup("#resetMode");
        if (cb != null) {
            cb.addEventHandler(javafx.event.ActionEvent.ACTION, e -> {
                javafx.application.Platform.runLater(() -> {
                    stage.sizeToScene();
                    stage.centerOnScreen();
                });
            });
        }

        loginView.setOnLogin(() -> {
            try {
                PasswordField np = (PasswordField) loginView.getRoot().lookup("#resetNewPass");
                TextField ans = (TextField) loginView.getRoot().lookup("#resetAnswer");

                // ✅ FIX: reset affidabile (se il campo risposta è visibile, siamo in reset)
                boolean reset = (ans != null && ans.isVisible());

                if (reset) {
                    String risposta = ans != null ? ans.getText().trim().toLowerCase() : "";

                    // ✅ FIX: mancava la variabile "nuova"
                    String nuova = (np != null && np.getText() != null) ? np.getText().trim() : "";
                    if (nuova.isEmpty()) {
                        loginView.mostraErrore("Inserisci una nuova password.");
                        return;
                    }

                    final String RISPOSTA_ATTESA_HASH =
                            "509f5bf1875f0450879589acdf17f77eacc217315fa1f1a9a04b9b830f2ac8b2";

                    String rispostaHash = biblioteca.model.Autenticazione.calcolaHash(risposta);

                    if (!RISPOSTA_ATTESA_HASH.equals(rispostaHash)) {
                        loginView.mostraErrore("Risposta di sicurezza errata.");
                        return;
                    }

                    if (bibliotecario == null) {
                        loginView.mostraErrore("Autenticazione non disponibile.");
                        return;
                    }

                    bibliotecario.cambiaPassword("", nuova);
                    if (archivio != null) archivio.salvaAutenticazione(bibliotecario);

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Password aggiornata");
                    ok.setHeaderText(null);
                    ok.setContentText("Password cambiata con successo.");
                    ok.showAndWait();

                    loginView.pulisciCampi();
                    if (ans != null) ans.clear();
                    if (np != null) np.clear();
                    loginView.mostraErrore("");

                    javafx.scene.control.Hyperlink link =
                            (javafx.scene.control.Hyperlink) loginView.getRoot().lookup("#resetMode");
                    if (link != null && ans != null && ans.isVisible()) {
                        link.fire();
                    }

                    stage.sizeToScene();
                    stage.centerOnScreen();
                    return;
                }

                String password = loginView.getPassword().trim();
                boolean ok = bibliotecario != null && bibliotecario.login(password);

                if (ok) {
                    loginView.mostraErrore("");
                    loginView.pulisciCampi();
                    if (archivio != null && bibliotecario != null) archivio.salvaAutenticazione(bibliotecario);
                    mostraMenu(stage);
                } else {
                    loginView.mostraErrore("Password errata.");
                    loginView.pulisciCampi();
                }

            } catch (IllegalArgumentException ex) {
                loginView.mostraErrore(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                loginView.mostraErrore("Errore (guarda console).");
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

            menu.setOnGestioneLibri(() -> mostraMain(stage, 0));
            menu.setOnGestioneUtenti(() -> mostraMain(stage, 1));
            menu.setOnGestionePrestiti(() -> mostraMain(stage, 2));
            menu.setOnLogout(() -> mostraLogin(stage));
        } catch (Exception ex) {
            ex.printStackTrace();
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
