package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 5 · Dimonis i prioritats")
class Etapa05Test {

    @Test
    @DisplayName("5a · El cronòmetre és un fil dimoni i fa tics")
    void cronometreDimoni() throws InterruptedException {
        AtomicInteger tics = new AtomicInteger();
        Thread c = Etapa05_Dimonis.creaCronometre(tics::incrementAndGet);
        assertTrue(c.isDaemon(), "el cronòmetre ha de ser dimoni");
        assertEquals(Thread.State.NEW, c.getState(), "creaCronometre no l'ha d'engegar");
        c.start();
        Thread.sleep(350);
        assertTrue(tics.get() >= 2, "en 350 ms hauria d'haver fet tics");
    }

    @Test
    @DisplayName("5b · El fil urgent té la prioritat màxima (10)")
    void urgent() {
        Thread[] fils = Etapa05_Dimonis.creaAmbPrioritats(() -> {}, () -> {});
        assertEquals(Thread.MAX_PRIORITY, fils[0].getPriority());
        assertEquals("urgent", fils[0].getName());
    }

    @Test
    @DisplayName("5c · El fil tranquil té la prioritat mínima (1)")
    void tranquil() {
        Thread[] fils = Etapa05_Dimonis.creaAmbPrioritats(() -> {}, () -> {});
        assertEquals(Thread.MIN_PRIORITY, fils[1].getPriority());
        assertFalse(fils[1].isDaemon());
    }
}
