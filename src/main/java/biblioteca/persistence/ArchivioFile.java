package biblioteca.persistence;

import biblioteca.model.Autenticazione;
import biblioteca.model.GestioneLibri;
import biblioteca.model.GestioneUtenti;
import biblioteca.model.GestionePrestiti;

import java.io.*;

public class ArchivioFile {

    private final String percorsoBase;

    private static final String FILE_LIBRI      = "libri.dat";
    private static final String FILE_UTENTI     = "utenti.dat";
    private static final String FILE_PRESTITI   = "prestiti.dat";
    private static final String FILE_LOGIN      = "login.dat";

    public ArchivioFile(String percorsoBase) {
    }

    public GestioneLibri caricaLibri() {
    }

    public void salvaLibri(GestioneLibri gl) {
    }

    public GestioneUtenti caricaUtenti() {
    }

    public void salvaUtenti(GestioneUtenti gu) {
    }

    public GestionePrestiti caricaPrestiti() {
    }

    public void salvaPrestiti(GestionePrestiti gp) {
    }

    public Autenticazione caricaAutenticazione() {
    }

    public void salvaAutenticazione(Autenticazione a) {
    }

    private <T> T caricaOggetto(String nomeFile, Class<T> tipo) {
    }

    private void salvaOggetto(String nomeFile, Object obj) {
    }
}
