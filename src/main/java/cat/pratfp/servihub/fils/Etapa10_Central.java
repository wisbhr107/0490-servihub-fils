package cat.pratfp.servihub.fils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ETAPA 10 · ServiHub v2: la central de comandes sencera (forats 28, 29 i 30).
 * <p>
 * Tot junt: milers de comandes, un fil <b>virtual</b> per a cadascuna (Java 21), un mapa concurrent
 * on es compta quantes comandes hi ha de cada plat i un {@link CountDownLatch} perquè el fil principal
 * sàpiga quan s'han processat totes. Quan aquesta etapa passa, ServiHub v2 funciona.
 * <p>
 * Teoria: apartat 9 (fils virtuals, CountDownLatch, ConcurrentHashMap) i apartat 12 (resum).
 */
public final class Etapa10_Central {

    private Etapa10_Central() {}

    /**
     * Processa totes les comandes alhora i retorna quantes n'hi ha de cada plat.
     *
     * @return mapa plat → nombre de comandes
     */
    public static Map<String, Integer> processa(List<Comanda> comandes) throws InterruptedException {
        Map<String, Integer> perPlat = new ConcurrentHashMap<>();
        CountDownLatch pendents = new CountDownLatch(comandes.size());

        // TODO FORAT 28 · Crea un executor que fa un fil virtual nou per a cada tasca (Java 21).
        try (ExecutorService executor = Forat.n(28)) {
            for (Comanda c : comandes) {
                executor.submit(() -> {
                    Etapa01_Engegar.dorm(20); // simula preparar la comanda
                    // TODO FORAT 29 · Suma 1 al plat c.plat() del mapa de manera atòmica (merge amb Integer::sum).
                    Forat.n(29);
                    pendents.countDown();
                });
            }
            // TODO FORAT 30 · Espera que el comptador de pendents arribi a zero.
            Forat.n(30);
        }
        return perPlat;
    }

    /** Genera comandes de prova repartides entre quatre plats. */
    public static List<Comanda> comandesDeProva(int quantes) {
        String[] plats = {"pizza", "amanida", "pasta", "sopa"};
        List<Comanda> comandes = new ArrayList<>();
        for (int i = 1; i <= quantes; i++) {
            comandes.add(new Comanda(i, plats[i % plats.length]));
        }
        return comandes;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Comanda> comandes = comandesDeProva(10_000);
        long inici = System.nanoTime();
        Map<String, Integer> informe = processa(comandes);
        long ms = (System.nanoTime() - inici) / 1_000_000;
        System.out.println("ServiHub v2 · " + comandes.size() + " comandes en " + ms + " ms amb fils virtuals");
        informe.forEach((plat, n) -> System.out.println("  " + plat + ": " + n));
        System.out.println("  total: " + informe.values().stream().mapToInt(Integer::intValue).sum());
    }
}
