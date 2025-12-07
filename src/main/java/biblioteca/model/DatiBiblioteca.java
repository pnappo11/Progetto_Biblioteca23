package biblioteca.model;

import java.io.Serializable;

public class DatiBiblioteca implements Serializable {

    private static final long serialVersionUID = 1L;
    public GestioneLibri gestioneLibri;
    public GestioneUtenti gestioneUtenti;
    public GestionePrestiti gestionePrestiti;
    public Autenticazione autenticazione;

    public DatiBiblioteca() {
    }

    public DatiBiblioteca(GestioneLibri gl,
                           GestioneUtenti gu,
                           GestionePrestiti gp,
                           Autenticazione a) {
    }
}
