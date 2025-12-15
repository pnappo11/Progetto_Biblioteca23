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
 * coordinando l'interazione tra la vista (LibriPanel), il modello (GestioneLibri)
 * e la persistenza dei dati (ArchivioFile).
 */
public class LibriController {

    private final GestioneLibri gestioneLibri;
    private final LibriPanel view;
    private final ArchivioFile archivio;

    private boolean inModalitaRicerca = false;

    /**
     * @brief Costruttore della classe LibriController.
     * Inizializza il controller, popola la tabella con i dati iniziali e collega
     * gli eventi dei pulsanti e della tabella.
     * @param gestioneLibri per la gestione logica dei libri.
     * @param view          La view che contiene il pannello di gestione libri.
     * @param archivio      Il gestore della persistenza su file.
     */
    public LibriController(GestioneLibri gestioneLibri,
                            LibriPanel view,
                            ArchivioFile archivio) {
        this.gestioneLibri = gestioneLibri;
        this.view = view;
        this.archivio = archivio;

        inizializzaTabella();
        collegaEventi();
    }

    private void inizializzaTabella() {
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }

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

        // --- VALIDAZIONE ISBN-13 (inline, senza metodi/attributi) ---
        String isbnClean = isbnStr.replaceAll("[^0-9]", "");
        if (isbnClean.length() != 13) {
            mostraErrore("ISBN non valido: deve avere 13 cifre.");
            return;
        }
        if (!(isbnClean.startsWith("978") || isbnClean.startsWith("979"))) {
            mostraErrore("ISBN non valido: deve iniziare con 978 o 979.");
            return;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int d = isbnClean.charAt(i) - '0';
            sum += (i % 2 == 0) ? d : d * 3;
        }
        int check = (10 - (sum % 10)) % 10;
        int last = isbnClean.charAt(12) - '0';
        if (check != last) {
            mostraErrore("ISBN non valido: cifra di controllo errata.");
            return;
        }
        isbnStr = isbnClean;
        // --- FINE VALIDAZIONE ISBN-13 ---

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

        // --- VALIDAZIONE ISBN-13 (inline, senza metodi/attributi) ---
        String isbnClean = isbnStr.replaceAll("[^0-9]", "");
        if (isbnClean.length() != 13) {
            mostraErrore("ISBN non valido: deve avere 13 cifre.");
            return;
        }
        if (!(isbnClean.startsWith("978") || isbnClean.startsWith("979"))) {
            mostraErrore("ISBN non valido: deve iniziare con 978 o 979.");
            return;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int d = isbnClean.charAt(i) - '0';
            sum += (i % 2 == 0) ? d : d * 3;
        }
        int check = (10 - (sum % 10)) % 10;
        int last = isbnClean.charAt(12) - '0';
        if (check != last) {
            mostraErrore("ISBN non valido: cifra di controllo errata.");
            return;
        }
        isbnStr = isbnClean;
        // --- FINE VALIDAZIONE ISBN-13 ---

        Long isbn = parseLong(isbnStr, "ISBN");
        if (isbn == null) return;

        Libro libro = trovaLibroPerIsbn(isbn);
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

            Collection<Libro> trovati =
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


    private void aggiornaTabella(Collection<Libro> libriDaMostrare) {
        List<Libro> lista = new ArrayList<>(libriDaMostrare);
        lista.sort(Comparator.comparing(
                Libro::getTitolo,
                String.CASE_INSENSITIVE_ORDER
        ));

        ObservableList<ObservableList<String>> righe = FXCollections.observableArrayList();

        for (Libro libro : lista) {
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

    
    private void resetFormESelezione() {
        view.pulisciCampi();
        view.getTabellaLibri().getSelectionModel().clearSelection();
    }

   
    private void resetModalitaRicerca() {
        inModalitaRicerca = false;
        view.getBottoneCerca().setText("Cerca");
    }

    
    private boolean isVuoto(String s) {
        return s == null || s.trim().isEmpty();
    }

    
    private Integer parseInt(String s, String nomeCampo) {
        try {
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere un numero intero.");
            return null;
        }
    }

    
    private Long parseLong(String s, String nomeCampo) {
        try {
            return Long.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            mostraErrore("Il campo \"" + nomeCampo + "\" deve essere un numero intero.");
            return null;
        }
    }

    
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

    
    private String joinAutori(List<String> autori) {
        if (autori == null || autori.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < autori.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(autori.get(i));
        }
        return sb.toString();
    }

    
    private Libro trovaLibroPerIsbn(Long isbn) {
        for (Libro l : gestioneLibri.getLibri()) {
            if (l.getIsbn() == isbn) {
                return l;
            }
        }
        return null;
    }

    
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Errore");
        alert.showAndWait();
    }

    
    private void mostraInfo(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, messaggio, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Informazione");
        alert.showAndWait();
    }

    /**
     * @brief Forza l'aggiornamento della view recuperando i dati dal modello.
     * Utile per sincronizzare la view dopo operazioni esterne o cambi di pannello.
     */
    public void aggiornaDaModel() {
        resetModalitaRicerca();
        resetFormESelezione();
        aggiornaTabella(gestioneLibri.getLibriOrdinatiPerTitolo());
    }
}
