package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 7 · Panys sense interbloqueig")
class Etapa07Test {

    @Test
    @DisplayName("7a · Una transferència mou l'estoc i deixa els dos panys lliures")
    void unaTransferencia() {
        Etapa07_Magatzems.Magatzem a = new Etapa07_Magatzems.Magatzem(1, 100);
        Etapa07_Magatzems.Magatzem b = new Etapa07_Magatzems.Magatzem(2, 100);
        Etapa07_Magatzems.transfereix(a, b, 30);
        assertEquals(70, a.estoc());
        assertEquals(130, b.estoc());
        assertFalse(a.pany.isLocked(), "el pany d'A ha de quedar lliure");
        assertFalse(b.pany.isLocked(), "el pany de B ha de quedar lliure");
    }

    @Test
    @DisplayName("7b · Transferir en sentit contrari (B→A) també funciona")
    void sentitContrari() {
        Etapa07_Magatzems.Magatzem a = new Etapa07_Magatzems.Magatzem(1, 100);
        Etapa07_Magatzems.Magatzem b = new Etapa07_Magatzems.Magatzem(2, 100);
        Etapa07_Magatzems.transfereix(b, a, 25);
        assertEquals(125, a.estoc());
        assertEquals(75, b.estoc());
        assertFalse(a.pany.isLocked());
        assertFalse(b.pany.isLocked());
    }

    @Test
    @DisplayName("7c · 50.000 transferències creuades acaben (sense interbloqueig) i conserven l'estoc")
    void senseInterbloqueig() {
        assertTimeoutPreemptively(Duration.ofSeconds(20), () -> {
            Etapa07_Magatzems.Magatzem a = new Etapa07_Magatzems.Magatzem(1, 1000);
            Etapa07_Magatzems.Magatzem b = new Etapa07_Magatzems.Magatzem(2, 1000);
            Throwable[] error = new Throwable[1];
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> error[0] = e);
            try {
                assertEquals(2000, Etapa07_Magatzems.transferenciesCreuades(a, b, 50_000));
            } finally {
                Thread.setDefaultUncaughtExceptionHandler(null);
            }
            assertNull(error[0], "un dels fils ha mort per una excepció: " + error[0]);
            assertFalse(a.pany.isLocked());
            assertFalse(b.pany.isLocked());
        }, "els dos fils s'han quedat esperant-se: interbloqueig");
    }
}
