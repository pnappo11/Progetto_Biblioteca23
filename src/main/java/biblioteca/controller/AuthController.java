package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.view.LoginView;

/**
 * @brief Controller per la gestione del processo di autenticazione.
 * Questa classe gestisce la logica di interazione tra la vista di login e il modello
 * di autenticazione, coordinando l'accesso al menu principale.
 */
public class Authcontroller {

    private final Autenticazione bibliotecario;
    private final LoginView loginView;
    private final MainController mainController;

    /**
     * @brief Costruttore della classe Authcontroller2.
     * Inizializza il controller con le dipendenze necessarie e imposta il listener
     * per l'evento di login sulla vista.
     * @param bibliotecario   Il modello che gestisce la verifica delle credenziali.
     * @param loginView       La vista che fornisce l'interfaccia utente per il login.
     * @param mainController  Il controller principale per la navigazione tra le viste.
     */
    public Authcontroller(Autenticazione bibliotecario,
                           LoginView loginView,
                           MainController mainController) {
        this.bibliotecario = bibliotecario;
        this.loginView = loginView;
        this.mainController = mainController;

        this.loginView.setOnLogin(() -> login(this.loginView.getPassword()));
    }

    /**
     * @brief Esegue il tentativo di login verificando la password inserita.
     * * Se la password è corretta, pulisce i campi e passa al menu principale.
     * In caso contrario, mostra un messaggio di errore.
     * * @param passwordInserita La password fornita dall'utente.
     */
    private void login(String passwordInserita) {
        if (passwordInserita == null) {
            passwordInserita = "";
        }

        boolean ok = bibliotecario.login(passwordInserita.trim());

        if (ok) {
            loginView.mostraErrore("");
            loginView.pulisciCampi();
            mainController.mostraMenu();
        } else {
            loginView.mostraErrore("Password errata.");
            loginView.pulisciCampi();
        }
    }
}