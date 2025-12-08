
package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.view.LoginView;

/**
 * @brief Controller responsabile della gestione del flusso di autenticazione.
 *
 * La classe {@code AuthController} funge da intermediario tra la vista di login ({@link LoginView})
 * e il modello di autenticazione ({@link Autenticazione}). Si occupa di catturare l'input dell'utente,
 * verificare le credenziali tramite il modello e, in caso di successo, delegare la navigazione
 * al {@link MainController}.
 *
 * @author tommy
 */
public class AuthController {

    /**
     * @brief Riferimento al modello che gestisce la logica di verifica delle credenziali.
     */
    private final Autenticazione bibliotecario;

    /**
     * @brief Riferimento alla vista che mostra l'interfaccia di login.
     */
    private final LoginView loginView;

    /**
     * @brief Riferimento al controller principale per gestire il cambio di vista post-login.
     */
    private final MainController mainController;

    /**
     * @brief Costruttore della classe AuthController.
     *
     * Inizializza il controller con le dipendenze necessarie e configura i listener
     * sulla vista per intercettare le azioni dell'utente (es. click sul bottone di login).
     *
     * @param bibliotecario  Istanza del modello {@link Autenticazione} per verificare la password.
     * @param loginView      Istanza della vista {@link LoginView} per interagire con l'utente.
     * @param mainController Istanza del {@link MainController} per la navigazione tra le viste.
     */
    public AuthController(Autenticazione bibliotecario,
                          LoginView loginView,
                          MainController mainController) {
        
        
    }

    /**
     * @brief Gestisce il tentativo di login dell'utente.
     *
     * Questo metodo viene invocato quando l'utente conferma l'inserimento della password.
     * Verifica la correttezza della password tramite il modello {@code bibliotecario}.
     * Se la password è corretta, notifica il {@code mainController} per mostrare la dashboard;
     * altrimenti, mostra un messaggio di errore sulla vista.
     *
     * @param passwordInserita La stringa contenente la password digitata dall'utente nella vista.
     */
    private void login(String passwordInserita) {
        // Implementazione logica di login...
    }
}
