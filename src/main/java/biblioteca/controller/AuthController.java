package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.view.LoginView;

public class AuthController {

    private final Autenticazione bibliotecario;
    private final LoginView loginView;
    private final MainController mainController;

    public AuthController(Autenticazione bibliotecario,
                           LoginView loginView,
                           MainController mainController) {
    }

    private void login(String passwordInserita) {
    }
}
