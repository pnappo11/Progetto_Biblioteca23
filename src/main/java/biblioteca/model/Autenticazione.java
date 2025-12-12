package biblioteca.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @brief classe Autenticazione relativa all'accesso da parte del bibliotecario al sistema permette di fare login e cambiare la password di accesso.
 * La nostra autenticazione prevede il confronto tra la password settata(già codificata) e l'hash  eseguito sulla password inserita nella finestra di login al momento dell'accesso.
 * @author tommy
 */
public class Autenticazione implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_HASH =
            "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";


    private String passwordHash;

  /**
   * @brief costruttore di default inizializza l'attributo passwordHash
   */
    public Autenticazione() {
        this.passwordHash = DEFAULT_HASH;
    }

   /**
    * @brief metodo che calcola l'hash sulla password inserita al momento del login e la confronta con quella già presente nel sistema.
    * @param passwordInserita rappresenta la password inserita al momento del login nel campo password.
    * @return l'esito del confronto, true se coincidono altrimenti false.
    */
    public boolean login(String passwordInserita) {
        String hashInserita = calcolaHash(passwordInserita);
        return passwordHash.equals(hashInserita);
    }

    /**
     * @brief metodo per il cambio password, per poter procedere alla modifica della password bisogna comunque ricordare la vecchia
     * @param vecchiaPassword rappresenta la vecchia password 
     * @param nuovaPassword rappresenta la nuova password che si vuole impostare
     */
    public void cambiaPassword(String vecchiaPassword, String nuovaPassword) {
        if (!login(vecchiaPassword)) {
            throw new IllegalArgumentException("Vecchia password non corretta");
        }
        this.passwordHash = calcolaHash(nuovaPassword);
    }

 /**
  * @brief metodo getter sulla passwordhash
  *  @return la password hashata.
  */
    public String getPasswordHash() {
        return passwordHash;
    }

   /**
    * @brief metodo che si occupa di calcolare l'hash di una password, ovvero la versione codificata di quella password.
    * @param passwordChiaro rappresenta la password non codificata (es."supermario23").
    * @return la stringa che contiene la password codificata.
    */
    public static String calcolaHash(String passwordChiaro) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordChiaro.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo di hash non disponibile", e);
        }
    }
}