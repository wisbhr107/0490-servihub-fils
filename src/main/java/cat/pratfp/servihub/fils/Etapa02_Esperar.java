package cat.pratfp.servihub.fils;

import java.util.List;

/**
 * ETAPA 2 · Esperar que els fils acabin (forats 04, 05 i 06).
 * <p>
 * El fil principal no pot fer el recompte d'entregues fins que tots els repartidors hagin acabat.
 * L'eina és {@link Thread#join()}: «espera't aquí fins que aquest fil mori». També existeix
 * {@link Thread#join(long)}, que espera com a màxim un temps i després continua, hagi acabat o no.
 * <p>
 * Teoria: apartat 3 (taula de mètodes) i exemple 1, ampliació 3 «join».
 */
public final class Etapa02_Esperar {

    private Etapa02_Esperar() {}

    /**
     * Espera que tots els fils de la llista hagin acabat.
     *
     * @param fils fils ja engegats
     */
    public static void esperaTots(List<Thread> fils) throws InterruptedException {
        for (Thread fil : fils) {
            // TODO FORAT 04 · Espera que aquest fil acabi abans de passar al següent.
            Forat.n(4);
        }
    }

    /**
     * Espera un fil com a màxim {@code maximMs} mil·lisegons.
     *
     * @return {@code true} si el fil ha acabat dins del temps; {@code false} si encara era viu
     */
    public static boolean esperaAmbLimit(Thread fil, long maximMs) throws InterruptedException {
        // TODO FORAT 05 · Espera el fil, però com a màxim maximMs mil·lisegons.
        Forat.n(5);
        // TODO FORAT 06 · Retorna true només si el fil ja NO és viu.
        return Forat.n(6);
    }

    /**
     * Cronometra quant triguen unes tasques executades totes alhora, cadascuna al seu fil.
     *
     * @return mil·lisegons des que s'engeguen fins que l'última acaba
     */
    public static long cronometraEnParallel(List<Runnable> tasques) throws InterruptedException {
        long inici = System.nanoTime();
        List<Thread> fils = tasques.stream().map(Thread::new).toList();
        fils.forEach(Thread::start);
        esperaTots(fils);
        return (System.nanoTime() - inici) / 1_000_000;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable tascaDeMigSegon = () -> Etapa01_Engegar.dorm(500);
        List<Runnable> tres = List.of(tascaDeMigSegon, tascaDeMigSegon, tascaDeMigSegon);

        long inici = System.nanoTime();
        for (Runnable t : tres) {
            t.run();
        }
        System.out.println("Seqüencial: " + (System.nanoTime() - inici) / 1_000_000 + " ms");
        System.out.println("Paral·lel:  " + cronometraEnParallel(tres) + " ms");

        Thread lent = new Thread(() -> Etapa01_Engegar.dorm(2000), "lent");
        lent.start();
        System.out.println("Ha acabat en 200 ms? " + esperaAmbLimit(lent, 200));
        System.out.println("Ha acabat en 3000 ms? " + esperaAmbLimit(lent, 3000));
    }
}
