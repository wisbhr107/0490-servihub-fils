package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 4 · Aturar amb interrupt()")
class Etapa04Test {

    @Test
    @DisplayName("4a · El vigilant fa rondes mentre no el molesten")
    void faRondes() throws InterruptedException {
        Etapa04_Aturar.Vigilant v = new Etapa04_Aturar.Vigilant();
        Thread fil = new Thread(v, "vigilant");
        fil.start();
        Thread.sleep(300);
        assertTrue(fil.isAlive(), "sense interrupció, el vigilant ha de seguir viu");
        assertTrue(v.rondes() >= 3, "en 300 ms hauria d'haver fet unes 6 rondes, no " + v.rondes());
        fil.interrupt();
        fil.join(2000);
    }

    @Test
    @DisplayName("4b · aturaVigilant el fa plegar en menys d'un segon")
    void plegaQuanLiHoDemanen() {
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            Etapa04_Aturar.Vigilant v = new Etapa04_Aturar.Vigilant();
            Thread fil = new Thread(v, "vigilant");
            fil.start();
            Thread.sleep(200);
            assertTrue(Etapa04_Aturar.aturaVigilant(fil), "el vigilant no ha plegat");
            assertFalse(fil.isAlive());
        });
    }

    @Test
    @DisplayName("4c · Interrompre'l mentre dorm també el fa plegar (bandera restaurada al catch)")
    void interrupcioDinsDelSleep() {
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            Etapa04_Aturar.Vigilant v = new Etapa04_Aturar.Vigilant();
            Thread fil = new Thread(v, "vigilant");
            Throwable[] error = new Throwable[1];
            fil.setUncaughtExceptionHandler((t, e) -> error[0] = e);
            fil.start();
            Thread.sleep(75); // enmig d'un sleep(50)
            fil.interrupt();
            fil.join(1000);
            assertNull(error[0], "el vigilant ha mort per una excepció: " + error[0]);
            assertFalse(fil.isAlive(), "després de l'excepció, cal tornar a marcar la interrupció");
            assertTrue(v.rondes() >= 1);
        });
    }
}
