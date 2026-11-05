package cat.pratfp.servihub.fils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Etapa 6 · Condició de carrera")
class Etapa06Test {

    @Test
    @DisplayName("6a · El comptador sincronitzat no perd cap suma (4 × 100.000)")
    void sincronitzat() throws InterruptedException {
        assertEquals(400_000, Etapa06_Carrera.processa(new Etapa06_Carrera.ComptadorSincronitzat(), 4, 100_000));
    }

    @Test
    @DisplayName("6b · El comptador atòmic no perd cap suma (4 × 100.000)")
    void atomic() throws InterruptedException {
        assertEquals(400_000, Etapa06_Carrera.processa(new Etapa06_Carrera.ComptadorAtomic(), 4, 100_000));
    }

    @Test
    @DisplayName("6c · Amb un sol fil, tots dos comptadors compten bé des de zero")
    void unSolFil() throws InterruptedException {
        assertEquals(1000, Etapa06_Carrera.processa(new Etapa06_Carrera.ComptadorSincronitzat(), 1, 1000));
        Etapa06_Carrera.ComptadorAtomic a = new Etapa06_Carrera.ComptadorAtomic();
        assertEquals(0, a.valor());
        a.incrementa();
        a.incrementa();
        assertEquals(2, a.valor());
    }
}
