package cat.pratfp.servihub.fils;

/**
 * ETAPA 3 · El cicle de vida d'un fil (forats 07, 08 i 09).
 * <p>
 * Un fil passa per sis estats: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING i TERMINATED.
 * Aquí observem un fil en tres moments: abans d'engegar-lo, mentre dorm i quan ja ha acabat.
 * <p>
 * Teoria: apartat 4 «Cicle de vida» i exemple 1, ampliació 4 «Els estats dels repartidors».
 */
public final class Etapa03_Estats {

    private Etapa03_Estats() {}

    /**
     * El que hem vist del fil en cada moment.
     *
     * @param abans      estat abans de start()
     * @param durant     estat mentre la tasca dorm
     * @param viuDurant  si el fil era viu mentre dormia
     * @param despres    estat després de join()
     */
    public record Observacio(Thread.State abans, Thread.State durant, boolean viuDurant, Thread.State despres) {}

    /**
     * Engega un fil que dorm {@code dormMs} mil·lisegons i n'observa l'estat en tres moments.
     */
    public static Observacio observa(long dormMs) throws InterruptedException {
        Thread fil = new Thread(() -> Etapa01_Engegar.dorm(dormMs), "observat");

        // TODO FORAT 07 · Desa l'estat del fil ara, que encara no s'ha engegat.
        Thread.State abans = Forat.n(7);

        fil.start();
        Thread.sleep(dormMs / 4); // li donem temps d'arribar al sleep
        Thread.State durant = fil.getState();
        // TODO FORAT 08 · Desa si el fil és viu en aquest moment (hauria de ser-ho).
        boolean viuDurant = Forat.n(8);

        // TODO FORAT 09 · Espera que el fil acabi del tot.
        Forat.n(9);
        Thread.State despres = fil.getState();

        return new Observacio(abans, durant, viuDurant, despres);
    }

    public static void main(String[] args) throws InterruptedException {
        Observacio o = observa(400);
        System.out.println("Abans de start(): " + o.abans());
        System.out.println("Mentre dorm:      " + o.durant() + " (viu: " + o.viuDurant() + ")");
        System.out.println("Després de join(): " + o.despres());
    }
}
