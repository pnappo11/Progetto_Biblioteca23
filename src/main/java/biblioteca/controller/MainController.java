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

/**
 * @brief Controller principale che orchestra la navigazione e il ciclo di vita dell'applicazione.
 *
 * La classe {@code MainController} funge da punto centralizzato per:
 * <ul>
 * <li>Inizializzare e mantenere le istanze condivise dei modelli ({@link GestioneLibri}, {@link GestioneUtenti}, ecc.).</li>
 * <li>Gestire la finestra principale ({@link Stage}) di JavaFX.</li>
 * <li>Gestire le transizioni tra le diverse viste (Login -> Menu -> Dashboard Principale).</li>
 * <li>Gestire la persistenza dei dati tramite {@link ArchivioFile}.</li>
 * </ul>
 *
 * @author tommy
 */
public class MainController {

    /**
     * @brief La finestra principale (top-level container) dell'applicazione JavaFX.
     * Tutte le scene (Login, Menu, MainFrame) vengono caricate qui dentro.
     */
    private final Stage stage;

    // --- Modelli (Business Logic) ---
    /**
     * @brief Modello per la gestione dell'inventario libri.
     */
    private final GestioneLibri gestioneLibri;

    /**
     * @brief Modello per la gestione dell'anagrafica utenti.
     */
    private final GestioneUtenti gestioneUtenti;

    /**
     * @brief Modello per la gestione dei prestiti.
     */
    private final GestionePrestiti gestionePrestiti;

    /**
     * @brief Riferimento al controller dei prestiti (iniettato successivamente per evitare dipendenze circolari).
     */
    private PrestitiController prestitiController;

    /**
     * @brief Modello per la gestione dell'autenticazione (Login).
     */
    private final Autenticazione bibliotecario;

    /**
     * @brief Gestore della persistenza per il salvataggio su file.
     */
    private final ArchivioFile archivio;

    /**
     * @brief Costruttore del MainController.
     *
     * Si occupa di:
     * 1. Inizializzare il sistema di persistenza ({@code ArchivioFile}).
     * 2. Caricare i dati dai file nei rispettivi Modelli.
     * 3. Preparare l'applicazione per l'avvio.
     *
     * @param stage La finestra primaria fornita dal metodo {@code start} di JavaFX Application.
     */
    public MainController(Stage stage) {
       
        // Inizializzazione modelli e caricamento dati...
    }

    /**
     * @brief Setter per iniettare il PrestitiController.
     *
     * @param prestitiController L'istanza del controller dei prestiti.
     */
    public void setPrestitiController(PrestitiController prestitiController) {
        this.prestitiController = prestitiController;
    }

    /**
     * @brief Punto di ingresso logico dell'applicazione.
     *
     * Configura il titolo della finestra e invoca la prima schermata (solitamente il Login).
     */
    public void avvia() {
    }

    /**
     * @brief Mostra la schermata di Login.
     *
     * Crea l'istanza di {@link LoginView} e del relativo {@link AuthController},
     * imposta la scena sullo Stage e la mostra.
     */
    public void mostraLogin() {
    }

    /**
     * @brief Mostra il Menu principale (se previsto come schermata intermedia).
     *
     * Crea e mostra la {@link MenuView}.
     */
    public void mostraMenu() {
    }

    /**
     * @brief Mostra la dashboard principale dell'applicazione (MainFrame).
     *
     * Carica la vista complessa che contiene i pannelli (Libri, Utenti, Prestiti).
     *
     * @param tabIndex L'indice della scheda (Tab) da selezionare all'apertura.
     * <ul>
     * <li>0: Tab Libri</li>
     * <li>1: Tab Utenti</li>
     * <li>2: Tab Prestiti</li>
     * </ul>
     * Utile per navigare direttamente a una sezione specifica dopo un'azione.
     */
    public void mostraMain(int tabIndex) {
    }
}
