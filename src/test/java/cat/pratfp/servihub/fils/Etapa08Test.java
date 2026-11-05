package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 8 · Productor-consumidor")
class Etapa08Test {

    @Test
    @DisplayName("8a · Un forn i un repartidor: les 20 comandes arriben en ordre")
    void unRepartidor() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            List<Comanda> servides = Etapa08_Cuina.serveix(20, 1, 3);
            assertEquals(20, servides.size());
            for (int i = 0; i < 20; i++) assertEquals(i + 1, servides.get(i).id());
        }, "el repartidor no acaba mai: li falta la píndola de FI?");
    }

    @Test
    @DisplayName("8b · Tres repartidors: cada comanda se serveix una sola vegada i tots pleguen")
    void tresRepartidors() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            List<Comanda> servides = Etapa08_Cuina.serveix(100, 3, 5);
            assertEquals(100, servides.size());
            assertEquals(100, new HashSet<>(servides).size(), "cap comanda repetida");
            assertFalse(servides.stream().anyMatch(Comanda::esFi), "les píndoles de FI no es serveixen");
        }, "algun repartidor no ha plegat: cal una píndola de FI per a CADA repartidor");
    }

    @Test
    @DisplayName("8c · Amb un passaplats d'1 lloc i més repartidors que comandes, tot acaba igualment")
    void passaplatsPetit() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            List<Comanda> servides = Etapa08_Cuina.serveix(3, 5, 1);
            assertEquals(3, servides.size());
        });
    }
}
