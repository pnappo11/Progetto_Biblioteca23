package biblioteca.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Autenticazione implements Serializable {

    private static final long serialVersionUID = 1L;
    private String passwordHash;

    public Autenticazione() {
    }

    public boolean login(String passwordInserita) {
    }

    public void cambiaPassword(String vecchiaPassword, String nuovaPassword) {
    }

    public String getPasswordHash() {
    }

    public static String calcolaHash(String passwordChiaro) {
    }
}
