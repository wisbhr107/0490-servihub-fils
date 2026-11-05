package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 3 · Cicle de vida")
class Etapa03Test {

    @Test
    @DisplayName("3a · Abans de start() el fil és NEW")
    void abans() throws InterruptedException {
        assertEquals(Thread.State.NEW, Etapa03_Estats.observa(200).abans());
    }

    @Test
    @DisplayName("3b · Mentre dorm és TIMED_WAITING i és viu")
    void durant() throws InterruptedException {
        Etapa03_Estats.Observacio o = Etapa03_Estats.observa(400);
        assertEquals(Thread.State.TIMED_WAITING, o.durant());
        assertTrue(o.viuDurant());
    }

    @Test
    @DisplayName("3c · Després de join() és TERMINATED")
    void despres() throws InterruptedException {
        assertEquals(Thread.State.TERMINATED, Etapa03_Estats.observa(200).despres());
    }
}
