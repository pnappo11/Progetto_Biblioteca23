package biblioteca.controller;

import biblioteca.model.GestioneLibri;
import biblioteca.model.Libro;
import biblioteca.persistence.ArchivioFile;
import biblioteca.view.LibriPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @brief Controller per la gestione delle operazioni sui libri.
 * Questa classe gestisce la logica di business relativa ai libri,
 * coordinando l'interazione tra la vista (LibriPanel2), il modello (GestioneLibri)
 * e la persistenza dei dati (ArchivioFile2).
 */
public class LibriController {

    private final GestioneLibri gestioneLibri;
    private final LibriPanel view;
    private final ArchivioFile archivio;

    private boolean inModalitaRicerca = false;

    /**
     * @brief Costruttore della classe Libricontroller2.
     * Inizializza il controller, popola la tabella con i dati iniziali e collega
     * gli eventi dei pulsanti e della tabella.
     * @param gestioneLibri Il modello per la gestione logica dei libri.
     * @param view          La view che contiene il pannello di gestione libri.
     * @param archivio      Il gestore della persistenza su file.
     */
    public Libricontroller(GestioneLibri gestioneLibri,
                            LibriPanel view,
                            ArchivioFile archivio) {
        this.gestioneLibri = gestioneLibri;
        this.view = view;
        this.archivio = archivio;

        inizializzaTabella();
        collegaEventi();
    }

    /**
     * @brief Inizializza la tabella con l'elenco dei libri.
     * Recupera i libri dal modello ordinati per titolo e aggiorna la tabella.
     */
    private void inizializzaTabella() {
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }

    /**
     * @brief Collega gli handler agli eventi della vista.
     * Imposta le azioni per i pulsanti (Inserisci, Modifica, Elimina, Cerca) e aggiunge un listener per la selezione delle righe nella tabella.
     */
    private void collegaEventi() {
        view.getBottoneInserisci().setOnAction(e -> gestisciInserisci());
        view.getBottoneModifica().setOnAction(e -> gestisciModifica());
        view.getBottoneElimina().setOnAction(e -> gestisciElimina());

        view.getBottoneCerca().setOnAction(e -> gestisciCercaOIndietro());

        view.getTabellaLibri().getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        // [ISBN, Titolo, Autori, Anno, CopieTot, CopieDisp]
                        view.setCampiDaRiga(newSel);
                    }
                });
    }

    /**
     * @brief Gestisce l'inserimento di un nuovo libro.
     * Valida i campi di input, crea un nuovo libro nel modello e salva le modifiche su file.
     * In caso di successo, resetta il form e aggiorna la tabella.
     */
    private void gestisciInserisci() {
        String isbnStr    = view.getCodiceIsbnInserito();
        String titolo     = view.getTitoloInserito();
        String autoreStr  = view.getAutoreInserito();
        String annoStr    = view.getAnnoInserito();
        String copieStr   = view.getCopieTotaliInserite();

        if (isVuoto(isbnStr) || isVuoto(titolo) || isVuoto(autoreStr)
                || isVuoto(annoStr) || isVuoto(copieStr)) {
            mostraErrore("Compila tutti i campi per inserire un libro.");
            return;
        }

        Long isbn   = parseLong(isbnStr, "ISBN");
        Integer anno  = parseInt(annoStr, "Anno");
        Integer copie = parseInt(copieStr, "Copie totali");
        if (isbn == null || anno == null || copie == null) {
            return; // errore già mostrato
        }

        List<String> autori = parseAutori(autoreStr);

        gestioneLibri.inserisciLibro(isbn, titolo, autori, anno, copie);

        archivio.salvaLibri(gestioneLibri);

        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }

    /**
     * @brief Gestisce la modifica di un libro esistente.
     * Identifica il libro tramite ISBN (da input o selezione), valida i nuovi dati
     * (controllando la coerenza delle copie in prestito) e aggiorna modello e file.
     */
    private void gestisciModifica() {
        String isbnStr    = view.getCodiceIsbnInserito();
        String titolo     = view.getTitoloInserito();
        String autoreStr  = view.getAutoreInserito();
        String annoStr    = view.getAnnoInserito();
        String copieStr   = view.getCopieTotaliInserite();

        if (isVuoto(isbnStr)) {
            ObservableList<String> selezionata =
                    view.getTabellaLibri().getSelectionModel().getSelectedItem();
            if (selezionata != null) {
                isbnStr = selezionata.get(0); // colonna ISBN
            }
        }

        if (isVuoto(isbnStr)) {
            mostraErrore("Per modificare un libro inserisci l'ISBN oppure seleziona una riga dalla tabella.");
            return;
        }

        Long isbn = parseLong(isbnStr, "ISBN");
        if (isbn == null) return;

        Libro2 libro = trovaLibroPerIsbn(isbn);
        if (libro == null) {
            mostraErrore("Nessun libro trovato con ISBN " + isbn);
            return;
        }

        if (!isVuoto(titolo)) {
            libro.setTitolo(titolo);
        }
        if (!isVuoto(autoreStr)) {
            libro.setAutori(parseAutori(autoreStr));
        }
        if (!isVuoto(annoStr)) {
            Integer anno = parseInt(annoStr, "Anno");
            if (anno == null) return;
            libro.setAnnoPubblicazione(anno);
        }

        if (!isVuoto(copieStr)) {
            Integer nuoveTot = parseInt(copieStr, "Copie totali");
            if (nuoveTot == null) return;

            int vecchieTot  = libro.getCopieTotali();
            int vecchieDisp = libro.getCopieDisponibili();
            int inPrestito  = vecchieTot - vecchieDisp;  // copie già in prestito

            if (nuoveTot < inPrestito) {
                mostraErrore(
                        "Non puoi impostare le copie totali a " + nuoveTot +
                        " perché ci sono già " + inPrestito +
                        " copie in prestito."
                );
                return;
            }

            libro.setCopieTotali(nuoveTot);
            int nuoveDisp = nuoveTot - inPrestito;
            libro.setCopieDisponibili(nuoveDisp);
        }

        archivio.salvaLibri(gestioneLibri);

        view.pulisciCampi();
        view.getTabellaLibri().getSelectionModel().clearSelection();
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }

    /**
     * @brief Gestisce l'eliminazione di un libro selezionato.
     * Rimuove il libro selezionato nella tabella dal modello e aggiorna il file di salvataggio.
     */
    private void gestisciElimina() {
        ObservableList<String> selezionata =
                view.getTabellaLibri().getSelectionModel().getSelectedItem();
        if (selezionata == null) {
            mostraErrore("Seleziona un libro dalla tabella da eliminare.");
            return;
        }

        String isbnStr = selezionata.get(0);
        gestioneLibri.eliminaLibro(isbnStr);

        archivio.salvaLibri(gestioneLibri);

        resetFormESelezione();
        resetModalitaRicerca();
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }

    /**
     * @brief Gestisce la logica del pulsante Cerca/Indietro.
     * Alterna tra la modalità di ricerca (filtrando i libri) e la visualizzazione completa.
     * Cambia il testo del pulsante in base allo stato corrente.
     */
    private void gestisciCercaOIndietro() {
        if (!inModalitaRicerca) {
            // --- CERCA ---
            String isbnStr    = view.getCodiceIsbnInserito();
            String titolo     = view.getTitoloInserito();
            String autoreStr  = view.getAutoreInserito();
            String annoStr    = view.getAnnoInserito();
            String copieStr   = view.getCopieTotaliInserite();

            if (!isVuoto(annoStr) || !isVuoto(copieStr)) {
                mostraInfo(
                        "Non è possibile cercare per \"Anno\" o \"Copie totali\".\n" +
                        "La ricerca viene effettuata solo per ISBN, Titolo e Autore."
                );
            }

            Collection<Libro2> trovati =
                    gestioneLibri.cercaLibri(isbnStr, titolo, autoreStr);
            aggiornaTabella(trovati);

            inModalitaRicerca = true;
            view.getBottoneCerca().setText("Indietro");
        } else {
            // --- INDIETRO ---
            resetFormESelezione();
            resetModalitaRicerca();
            aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
        }
    }

    /**
     * @brief Aggiorna la tabella della vista con una collezione di libri.
     * Converte la collezione di oggetti Libro2 in ObservableList compatibile con JavaFX,
     * ordinandoli alfabeticamente per titolo.
     * * @param libriDaMostrare La collezione di libri da visualizzare in tabella.
     */
    private void aggiornaTabella(Collection<Libro2> libriDaMostrare) {
        List<Libro2> lista = new ArrayList<>(libriDaMostrare);
        lista.sort(Comparator.comparing(
                Libro2::getTitolo,
                String.CASE_INSENSITIVE_ORDER
        ));

        ObservableList<ObservableList<String>> righe = FXCollections.observableArrayList();

        for (Libro2 libro : lista) {
            ObservableList<String> riga = FXCollections.observableArrayList();
            riga.add(String.valueOf(libro.getIsbn()));              // 0
            riga.add(libro.getTitolo());                            // 1
            riga.add(joinAutori(libro.getAutori()));                // 2
            riga.add(String.valueOf(libro.getAnnoPubblicazione())); // 3
            riga.add(String.valueOf(libro.getCopieTotali()));       // 4
            riga.add(String.valueOf(libro.getCopieDisponibili()));  // 5
            righe.add(riga);
        }

        view.getTabellaLibri().setItems(righe);
    }

    /**
     * @brief Resetta i campi del form e la selezione della tabella.
     */
    private void resetFormESelezione() {
        view.pulisciCampi();
        view.getTabellaLibri().getSelectionModel().clearSelection();
    }

    /**
     * @brief Resetta lo stato di ricerca.
     * Imposta il flag di ricerca a false e ripristina il testo del pulsante a "Cerca".
     */
    private void resetModalitaRicerca() {
        inModalitaRicerca = false;
        view.getBottoneCerca().setText("Cerca");
    }

    /**
     * @brief Verifica se una stringa è nulla o vuota.
     * @param s La stringa da controllare.
     * @return true se la stringa è null o vuota (dopo il trim), false altrimenti.
     */
    private boolean isVuoto(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * @brief Converte una stringa in Integer gestendo le eccezioni.
     * Mostra un errore se il formato non è valido.
     * @param s          La stringa da convertire.
     * @param nomeCampo  Il nome del campo (per il messaggio di errore).
     * @return L'Integer convertito o null se la conversione fallisce.
     */
    private Integer parseInt(String s, String nomeCampo) {
        try {
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere un numero intero.");
            return null;
        }
    }

    /**
     * @brief Converte una stringa in Long gestendo le eccezioni.
     * Mostra un errore se il formato non è valido.
     * @param s          La stringa da convertire.
     * @param nomeCampo  Il nome del campo (per il messaggio di errore).
     * @return Il Long convertito o null se la conversione fallisce.
     */
    private Long parseLong(String s, String nomeCampo) {
        try {
            return Long.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere un numero intero.");
            return null;
        }
    }

    /**
     * @brief Parsa una stringa di autori separati da virgola in una lista.
     * @param testo La stringa contenente gli autori.
     * @return Una lista di stringhe con i nomi degli autori puliti dagli spazi.
     */
    private List<String> parseAutori(String testo) {
        List<String> lista = new ArrayList<>();
        if (testo == null) return lista;

        String[] parti = testo.split(",");
        for (String p : parti) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                lista.add(trimmed);
            }
        }
        if (lista.isEmpty()) {
            lista.add(testo.trim());
        }
        return lista;
    }

    /**
     * @brief Unisce una lista di autori in un'unica stringa separata da virgole.
     * @param autori La lista degli autori.
     * @return Una stringa formattata con i nomi degli autori.
     */
    private String joinAutori(List<String> autori) {
        if (autori == null || autori.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < autori.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(autori.get(i));
        }
        return sb.toString();
    }

    /**
     * @brief Cerca un oggetto Libro2 nel modello tramite il suo ISBN.
     * @param isbn L'ISBN del libro da cercare.
     * @return L'oggetto Libro2 corrispondente, oppure null se non trovato.
     */
    private Libro2 trovaLibroPerIsbn(Long isbn) {
        for (Libro2 l : gestioneLibri.getLibri()) {
            if (l.getIsbn() == isbn) {
                return l;
            }
        }
        return null;
    }

    /**
     * @brief Mostra una finestra di dialogo di errore.
     * @param messaggio Il testo dell'errore da visualizzare.
     */
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Errore");
        alert.showAndWait();
    }

    /**
     * @brief Mostra una finestra di dialogo informativa.
     * @param messaggio Il testo informativo da visualizzare.
     */
    private void mostraInfo(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Informazione");
        alert.showAndWait();
    }

    /**
     * @brief Forza l'aggiornamento della vista recuperando i dati dal modello.
     * Utile per sincronizzare la vista dopo operazioni esterne o cambi di pannello.
     */
    public void aggiornaDaModel() {
        resetModalitaRicerca();
        resetFormESelezione();
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }
}