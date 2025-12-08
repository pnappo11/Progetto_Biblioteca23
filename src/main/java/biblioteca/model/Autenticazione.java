package biblioteca.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @brief Gestisce la sicurezza e l'autenticazione del bibliotecario.
 *
 * La classe {@code Autenticazione} si occupa di memorizzare e verificare la password
 * di accesso al sistema. Implementa l'interfaccia {@link Serializable} per permettere
 * il salvataggio delle credenziali su file (in modo persistente).
 *
 * <b>Nota sulla sicurezza:</b>
 * La password non viene mai salvata "in chiaro". Viene invece memorizzato
 * solo il suo <i>Hash</i>, calcolato tramite un algoritmo crittografico. Questo rende impossibile risalire alla password originale
 * leggendo il file di salvataggio.
 *
 * @author tommy
 */
public class Autenticazione implements Serializable {

    private static final long serialVersionUID = 1L;

    private String passwordHash;

    /**
     * @brief Costruttore della classe.
     *
     * Inizializza l'oggetto.
     */
    public Autenticazione() {
    }

    /**
     * @brief Verifica se la password inserita è corretta.
     *
     * Il metodo calcola l'hash della {@code passwordInserita} e lo confronta
     * con il {@code passwordHash} memorizzato. Se i due hash coincidono, la password è corretta.
     *
     * @param passwordInserita La password digitata dall'utente nel form di login.
     * @return {@code true} se la password corrisponde, {@code false} altrimenti.
     */
    public boolean login(String passwordInserita) {
         
    }

    /**
     * @brief Permette di modificare la password di accesso.
     *
     * Per sicurezza, richiede di inserire la vecchia password prima di impostarne una nuova.
     *
     * @param vecchiaPassword La password attuale (per verifica di sicurezza).
     * @param nuovaPassword   La nuova password da impostare.
     */
    public void cambiaPassword(String vecchiaPassword, String nuovaPassword) {
    }

    /**
     * @brief Restituisce l'hash memorizzato.
     *
     * @return La stringa contenente l'hash della password.
     */
    public String getPasswordHash() {
        
    }

    /**
     * @brief Metodo statico di utilità per calcolare l'hash della password .
     *
     * @param passwordChiaro La stringa da convertire.
     * @return La rappresentazione esadecimale dell'hash, oppure {@code null} in caso di errore dell'algoritmo.
     */
    public static String calcolaHash(String passwordChiaro) {
     
    }
}
