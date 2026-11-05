package cat.pratfp.servihub.fils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ETAPA 8 · Productor-consumidor amb una cua bloquejant (forats 22, 23 i 24).
 * <p>
 * El forn de ServiHub (productor) posa comandes acabades al passaplats, i els repartidors
 * (consumidors) les recullen. El passaplats és una {@link BlockingQueue} amb capacitat limitada:
 * {@code put} s'espera si és ple i {@code take} s'espera si és buit, sense cap {@code synchronized}
 * a la vista. Per dir als repartidors que ja no vindrà res més, el forn posa una «píndola de FI»
 * <b>per a cada repartidor</b>.
 * <p>
 * Teoria: apartat 8 (BlockingQueue, productor-consumidor) i exemple 2, ampliació 4 «La cua de comandes».
 */
public final class Etapa08_Cuina {

    private Etapa08_Cuina() {}

    /**
     * Prepara {@code comandes} comandes al forn i les fa repartir entre {@code repartidors} repartidors.
     *
     * @param capacitat  mida del passaplats
     * @return les comandes servides, en l'ordre en què s'han recollit (sense les píndoles de FI)
     */
    public static List<Comanda> serveix(int comandes, int repartidors, int capacitat) throws InterruptedException {
        BlockingQueue<Comanda> passaplats = new ArrayBlockingQueue<>(capacitat);
        List<Comanda> servides = new CopyOnWriteArrayList<>();

        Thread forn = new Thread(() -> {
            try {
                for (int i = 1; i <= comandes; i++) {
                    Comanda c = prepara(i);
                    // TODO FORAT 22 · Posa la comanda al passaplats (si és ple, aquesta crida s'espera sola).
                    Forat.n(22);
                }
                // TODO FORAT 23 · Posa al passaplats una píndola Comanda.FI per a CADA repartidor (bucle d'una línia).
                Forat.n(23);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "forn");

        List<Thread> fils = new ArrayList<>();
        for (int r = 1; r <= repartidors; r++) {
            Thread repartidor = new Thread(() -> {
                try {
                    while (true) {
                        // TODO FORAT 24 · Recull la següent comanda del passaplats (si és buit, espera't).
                        Comanda c = Forat.n(24);
                        if (c.esFi()) {
                            break;
                        }
                        entrega(c, servides);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "repartidor-" + r);
            fils.add(repartidor);
        }

        forn.start();
        fils.forEach(Thread::start);
        forn.join();
        Etapa02_Esperar.esperaTots(fils);
        return List.copyOf(servides);
    }

    /** El forn triga un moment a preparar cada comanda. */
    static Comanda prepara(int numero) throws InterruptedException {
        Thread.sleep(1);
        return new Comanda(numero, numero % 2 == 0 ? "pizza" : "amanida");
    }

    /** El repartidor triga un moment a entregar-la i l'apunta com a servida. */
    static void entrega(Comanda c, List<Comanda> servides) throws InterruptedException {
        Thread.sleep(1);
        servides.add(c);
    }

    public static void main(String[] args) throws InterruptedException {
        List<Comanda> servides = serveix(10, 3, 2);
        servides.forEach(c -> System.out.println("Servida " + c));
        System.out.println("Total servides: " + servides.size());
    }
}
