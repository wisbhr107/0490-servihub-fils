package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 9 · Piscina de fils amb Callable i Future")
class Etapa09Test {

    @Test
    @DisplayName("9a · Els totals són correctes i en el mateix ordre que les caixes")
    void totals() throws Exception {
        List<List<Integer>> caixes = List.of(List.of(1, 2, 3), List.of(10), List.of(), List.of(5, 5));
        assertEquals(List.of(6, 10, 0, 10), Etapa09_Piscina.totalsPerCaixa(caixes, 2));
    }

    @Test
    @DisplayName("9b · 6 caixes de 100 ms amb 3 fils triguen uns 200 ms (no 600)")
    void enParallel() throws Exception {
        List<List<Integer>> caixes = List.of(List.of(1), List.of(1), List.of(1), List.of(1), List.of(1), List.of(1));
        long inici = System.nanoTime();
        Etapa09_Piscina.totalsPerCaixa(caixes, 3);
        long ms = (System.nanoTime() - inici) / 1_000_000;
        assertTrue(ms < 500, "amb 3 fils haurien de ser dues tandes de 100 ms, no " + ms + " ms");
    }

    @Test
    @DisplayName("9c · El mètode no deixa fils de la piscina vius")
    void piscinaTancada() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            int abans = Thread.activeCount();
            Etapa09_Piscina.totalsPerCaixa(List.of(List.of(1), List.of(2)), 4);
            Thread.sleep(200);
            assertTrue(Thread.activeCount() <= abans, "han quedat fils de la piscina vius");
        });
    }
}
