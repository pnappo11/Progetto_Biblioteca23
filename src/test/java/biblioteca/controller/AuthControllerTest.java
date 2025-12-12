package biblioteca.controller;

import biblioteca.model.Autenticazione;
import biblioteca.view.LoginView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private Autenticazione bibliotecario;
    private LoginView loginView;
    private MainController mainController;
    private Runnable onLogin;

    @BeforeEach
    public void setUp() {
        bibliotecario = mock(Autenticazione.class);
        loginView = mock(LoginView.class);
        mainController = mock(MainController.class);

        ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
        new AuthController(bibliotecario, loginView, mainController);
        verify(loginView).setOnLogin(captor.capture());
        onLogin = captor.getValue();
    }

    @Test
    public void testLoginSuccessMostraMenuEPulisceCampi() {
        when(loginView.getPassword()).thenReturn("  segreta  ");
        when(bibliotecario.login("segreta")).thenReturn(true);

        onLogin.run();

        InOrder ordine = inOrder(loginView, mainController);
        ordine.verify(loginView).mostraErrore("");
        ordine.verify(loginView).pulisciCampi();
        ordine.verify(mainController).mostraMenu();

        verify(bibliotecario).login("segreta");
        verifyNoMoreInteractions(mainController);
    }

    @Test
    public void testLoginFallitoMostraErroreEPulisceCampi() {
        when(loginView.getPassword()).thenReturn("sbagliata");
        when(bibliotecario.login("sbagliata")).thenReturn(false);

        onLogin.run();

        InOrder ordine = inOrder(loginView);
        ordine.verify(loginView).mostraErrore("Password errata.");
        ordine.verify(loginView).pulisciCampi();

        verify(bibliotecario).login("sbagliata");
        verify(mainController, never()).mostraMenu();
    }

    @Test
    public void testLoginPasswordNullTrattataComeVuota() {
        when(loginView.getPassword()).thenReturn(null);
        when(bibliotecario.login("")).thenReturn(false);

        onLogin.run();

        verify(bibliotecario).login("");
        verify(loginView).mostraErrore("Password errata.");
        verify(loginView).pulisciCampi();
        verify(mainController, never()).mostraMenu();
    }

    @Test
    public void testLoginPasswordSoloSpaziTrattataComeVuota() {
        when(loginView.getPassword()).thenReturn("   ");
        when(bibliotecario.login("")).thenReturn(false);

        onLogin.run();

        verify(bibliotecario).login("");
        verify(loginView).mostraErrore("Password errata.");
        verify(loginView).pulisciCampi();
        verify(mainController, never()).mostraMenu();
    }
}
