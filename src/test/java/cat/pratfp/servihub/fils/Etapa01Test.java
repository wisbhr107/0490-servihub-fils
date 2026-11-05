package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 1 · Crear i engegar fils")
class Etapa01Test {

    @Test
    @DisplayName("1a · Es creen tants fils com repartidors, amb els noms repartidor-1, -2, -3")
    void nomsDelsFils() throws InterruptedException {
        List<String> registre = Etapa01_Engegar.nouRegistre();
        List<Thread> fils = Etapa01_Engegar.engegaRepartidors(3, 1, registre);
        assertEquals(3, fils.size());
        assertEquals(List.of("repartidor-1", "repartidor-2", "repartidor-3"), fils.stream().map(Thread::getName).toList());
        for (Thread f : fils) f.join(2000);
    }

    @Test
    @DisplayName("1b · Els fils estan engegats de debò (no s'ha cridat run() des de main)")
    void elsFilsEstanEngegats() throws InterruptedException {
        List<String> registre = Etapa01_Engegar.nouRegistre();
        List<Thread> fils = Etapa01_Engegar.engegaRepartidors(2, 5, registre);
        for (Thread f : fils) {
            assertNotEquals(Thread.State.NEW, f.getState(), "el fil " + f.getName() + " no s'ha engegat");
        }
        for (Thread f : fils) f.join(3000);
        assertEquals(10, registre.size(), "cada repartidor ha d'apuntar totes les entregues");
    }

    @Test
    @DisplayName("1c · Cada entrega porta el nom del fil que l'ha fet")
    void elRegistrePortaElNomDelFil() throws InterruptedException {
        List<String> registre = Etapa01_Engegar.nouRegistre();
        List<Thread> fils = Etapa01_Engegar.engegaRepartidors(2, 2, registre);
        for (Thread f : fils) f.join(3000);
        assertTrue(registre.contains("repartidor-1: paquet 1"), registre.toString());
        assertTrue(registre.contains("repartidor-2: paquet 2"), registre.toString());
        assertFalse(registre.stream().anyMatch(l -> l.startsWith("main")), "les entregues no les fa el fil main");
    }
}
