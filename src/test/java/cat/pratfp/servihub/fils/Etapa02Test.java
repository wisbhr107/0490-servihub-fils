package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 2 · Esperar amb join")
class Etapa02Test {

    @Test
    @DisplayName("2a · esperaTots torna quan tots els fils han acabat")
    void esperaTots() throws InterruptedException {
        AtomicInteger acabats = new AtomicInteger();
        List<Thread> fils = List.of(
                new Thread(() -> { Etapa01_Engegar.dorm(150); acabats.incrementAndGet(); }),
                new Thread(() -> { Etapa01_Engegar.dorm(300); acabats.incrementAndGet(); }),
                new Thread(() -> { Etapa01_Engegar.dorm(50); acabats.incrementAndGet(); }));
        fils.forEach(Thread::start);
        Etapa02_Esperar.esperaTots(fils);
        assertEquals(3, acabats.get(), "en tornar, els tres fils havien d'haver acabat");
        fils.forEach(f -> assertFalse(f.isAlive()));
    }

    @Test
    @DisplayName("2b · esperaAmbLimit: false si el fil encara treballa, true si ha acabat")
    void esperaAmbLimit() throws InterruptedException {
        Thread lent = new Thread(() -> Etapa01_Engegar.dorm(1000), "lent");
        lent.start();
        long inici = System.nanoTime();
        assertFalse(Etapa02_Esperar.esperaAmbLimit(lent, 100), "als 100 ms el fil encara era viu");
        long ms = (System.nanoTime() - inici) / 1_000_000;
        assertTrue(ms < 800, "esperaAmbLimit(100) no pot esperar fins al final (ha trigat " + ms + " ms)");
        assertTrue(Etapa02_Esperar.esperaAmbLimit(lent, 5000), "amb 5 s de marge, el fil ha d'haver acabat");
    }

    @Test
    @DisplayName("2c · Tres tasques de 300 ms en paral·lel triguen menys de 700 ms")
    void enParallel() throws InterruptedException {
        Runnable t = () -> Etapa01_Engegar.dorm(300);
        long ms = Etapa02_Esperar.cronometraEnParallel(List.of(t, t, t));
        assertTrue(ms >= 280, "cal esperar que acabin (han trigat " + ms + " ms)");
        assertTrue(ms < 700, "en paral·lel haurien de trigar uns 300 ms, no " + ms);
    }
}
