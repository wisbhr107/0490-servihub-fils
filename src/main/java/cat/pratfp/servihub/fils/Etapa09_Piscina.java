package cat.pratfp.servihub.fils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ETAPA 9 · Una piscina de fils amb Callable i Future (forats 25, 26 i 27).
 * <p>
 * Al tancament del dia, cada caixa ha de sumar els seus tiquets. En lloc de crear un fil per caixa a mà,
 * fem servir un {@link ExecutorService} amb un nombre fix de fils: li enviem tasques
 * ({@link Callable}, que retornen un valor) i ens dona un {@link Future} per recollir cada resultat.
 * <p>
 * Teoria: apartat 9 «Llibreries» i exemple 2, ampliació 5 «Les caixes amb un executor».
 */
public final class Etapa09_Piscina {

    private Etapa09_Piscina() {}

    /**
     * Suma els tiquets de cada caixa en paral·lel amb una piscina de {@code fils} fils.
     *
     * @param caixes els tiquets de cada caixa
     * @return el total de cada caixa, en el mateix ordre
     */
    public static List<Integer> totalsPerCaixa(List<List<Integer>> caixes, int fils)
            throws InterruptedException, ExecutionException {
        // TODO FORAT 25 · Crea una piscina amb un nombre fix de fils.
        ExecutorService piscina = Forat.n(25);
        try {
            List<Future<Integer>> futurs = new ArrayList<>();
            for (List<Integer> tiquets : caixes) {
                Callable<Integer> sumaCaixa = () -> {
                    Etapa01_Engegar.dorm(100); // simula la feina
                    return tiquets.stream().mapToInt(Integer::intValue).sum();
                };
                // TODO FORAT 26 · Envia la tasca a la piscina i desa el Future que et retorna.
                Future<Integer> futur = Forat.n(26);
                futurs.add(futur);
            }

            List<Integer> totals = new ArrayList<>();
            for (Future<Integer> futur : futurs) {
                // TODO FORAT 27 · Recull el resultat del Future (si encara no està, aquesta crida s'espera).
                int total = Forat.n(27);
                totals.add(total);
            }
            return totals;
        } finally {
            // Sense això, els fils de la piscina seguirien vius i el programa no acabaria mai.
            piscina.shutdown();
            piscina.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws Exception {
        List<List<Integer>> caixes = List.of(
                List.of(12, 8, 30), List.of(5, 5, 5, 5), List.of(100), List.of(7, 3), List.of(1, 2, 3, 4), List.of(50, 50));
        long inici = System.nanoTime();
        List<Integer> totals = totalsPerCaixa(caixes, 3);
        System.out.println("Totals per caixa: " + totals);
        System.out.println("6 caixes amb 3 fils: " + (System.nanoTime() - inici) / 1_000_000 + " ms (≈ 200)");
    }
}
