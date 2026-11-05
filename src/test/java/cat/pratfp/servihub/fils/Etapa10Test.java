package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 10 · ServiHub v2: la central sencera")
class Etapa10Test {

    @Test
    @DisplayName("10a · 1.000 comandes: el recompte per plat és exacte")
    void recompte() {
        assertTimeoutPreemptively(Duration.ofSeconds(20), () -> {
            Map<String, Integer> informe = Etapa10_Central.processa(Etapa10_Central.comandesDeProva(1000));
            assertEquals(Map.of("pizza", 250, "amanida", 250, "pasta", 250, "sopa", 250), informe);
        });
    }

    @Test
    @DisplayName("10b · 5.000 comandes de 20 ms triguen molt menys de 100 s: van alhora")
    void vanAlhora() {
        assertTimeoutPreemptively(Duration.ofSeconds(20), () -> {
            long inici = System.nanoTime();
            Map<String, Integer> informe = Etapa10_Central.processa(Etapa10_Central.comandesDeProva(5000));
            long ms = (System.nanoTime() - inici) / 1_000_000;
            assertEquals(5000, informe.values().stream().mapToInt(Integer::intValue).sum());
            assertTrue(ms < 5000, "5.000 fils virtuals haurien de trigar ben poc, no " + ms + " ms");
        });
    }

    @Test
    @DisplayName("10c · Sense comandes, l'informe és buit i el mètode torna de seguida")
    void senseComandes() {
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> assertEquals(Map.of(), Etapa10_Central.processa(List.of())));
    }
}
