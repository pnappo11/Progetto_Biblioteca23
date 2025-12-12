
package biblioteca.model;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PrestitoTest {

    private Prestito prestito;
    private Utente utente;
    private Libro libro;
    private LocalDate dataInizio;
    private LocalDate dataPrevista;
    @BeforeEach
    void setUp() {
        utente = new Utente("Mario", "Verdi", "0612709541", "m.verdi@unisa.it");
        libro = new Libro(123456L, "Libro Test", new ArrayList<>(), 2020, 5, 5);
        dataInizio = LocalDate.of(2023, 1, 1);
        dataPrevista = LocalDate.of(2023, 2, 1);
        
        prestito = new Prestito(utente, libro, dataInizio, dataPrevista);
    } @Test
    void testCostruttore() {
        assertNotNull(prestito);
        assertEquals(utente, prestito.getUtente());
        assertEquals(libro, prestito.getLibro());
        assertEquals(dataInizio, prestito.getDataInizio());
        assertEquals(dataPrevista, prestito.getDataPrevistaRestituzione());
        assertNull(prestito.getDataRestituzione());
    } @Test
    void testIsAttivoAppenaCreato() {
        assertTrue(prestito.isAttivo());
    }
@Test
    void testSetDataRestituzione() {
        LocalDate dataRestituzione = LocalDate.of(2023, 1, 20);
        prestito.setDataRestituzione(dataRestituzione);
        
        assertEquals(dataRestituzione, prestito.getDataRestituzione());
        assertFalse(prestito.isAttivo());
    } @Test
    void testIsInRitardoFalsoSeNonScaduto() {
        LocalDate primaDellaScadenza = LocalDate.of(2023, 1, 15);
        assertFalse(prestito.isInRitardo(primaDellaScadenza));
    } @Test
    void testIsInRitardoFalsoSeGiornoEsatto() {
        assertFalse(prestito.isInRitardo(dataPrevista));
    } @Test
    void testIsInRitardoVeroSeScaduto() {
        LocalDate dopoScadenza = LocalDate.of(2023, 2, 2);
        assertTrue(prestito.isInRitardo(dopoScadenza));
    } @Test
    void testIsInRitardoFalsoSeRestituito() {
        prestito.setDataRestituzione(LocalDate.of(2023, 2, 5)); 
        LocalDate controllo = LocalDate.of(2023, 2, 10);
        
        assertFalse(prestito.isInRitardo(controllo));
    } @Test
    void testIsInRitardoInputNull() {
        Prestito prestitoScaduto = new Prestito(utente, libro, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1));
        assertTrue(prestitoScaduto.isInRitardo(null));

        Prestito prestitoNonScaduto = new Prestito(utente, libro, LocalDate.now(), LocalDate.now().plusDays(10));
        assertFalse(prestitoNonScaduto.isInRitardo(null));
    } @Test
    void testEquals() {
        Prestito stessoPrestito = new Prestito(utente, libro, dataInizio, LocalDate.of(2023, 3, 1));
        
        Utente altroUtente = new Utente("Giuseppe", "Luongo", "0612709547", "g.luongo@unisa.it");
        Prestito prestitoUtenteDiverso = new Prestito(altroUtente, libro, dataInizio, dataPrevista);
        
        Libro altroLibro = new Libro(999L, "B", new ArrayList<>(), 2000, 1);
        Prestito prestitoLibroDiverso = new Prestito(utente, altroLibro, dataInizio, dataPrevista);
        
        Prestito prestitoDataDiversa = new Prestito(utente, libro, LocalDate.of(2023, 1, 2), dataPrevista);

        assertEquals(prestito, stessoPrestito); 
        assertNotEquals(prestito, prestitoUtenteDiverso);
        assertNotEquals(prestito, prestitoLibroDiverso);
        assertNotEquals(prestito, prestitoDataDiversa);
        assertNotEquals(prestito, null);
        assertNotEquals(prestito, new Object());
    } @Test
    void testHashCode() {
        Prestito stessoPrestito = new Prestito(utente, libro, dataInizio, LocalDate.of(2099, 12, 31));
        assertEquals(prestito.hashCode(), stessoPrestito.hashCode());
    } }
