/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Controller principale dell'applicazione.
 *
 * Si occupa di:
 * - inizializzare i gestori del model e la persistenza;
 * - gestire il passaggio tra Login, Menu e Finestra principale (tab);
 * - creare i controller delle singole sezioni (Libri, Utenti, Prestiti).
 */
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

public class MainController {

    private final Stage stage;

    private final GestioneLibri gestioneLibri;
    private final GestioneUtenti gestioneUtenti;
    private final GestionePrestiti gestionePrestiti;
    private PrestitiController prestitiController;

    private final Autenticazione bibliotecario;

    private final ArchivioFile archivio;

    
    public MainController(Stage stage) {
    }

    public void setPrestitiController(PrestitiController prestitiController) {
    }

    public void avvia() {
    }

    public void mostraLogin() {
    }
    public void mostraMenu() {
    }
    public void mostraMain(int tabIndex) {
    }
}
