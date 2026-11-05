package cat.pratfp.servihub.fils;

/**
 * ETAPA 5 · Dimonis i prioritats (forats 13, 14 i 15).
 * <p>
 * El cronòmetre de la central fa «tic» cada 100 ms i no acaba mai. Si fos un fil normal, el programa
 * no podria acabar. Un fil <b>dimoni</b> no reté la JVM: quan l'últim fil normal acaba, el dimoni mor
 * amb el programa. Les prioritats són un consell al planificador (1-10), no una ordre.
 * <p>
 * Teoria: apartat 6 «Prioritats, dimonis i context» i exemple 1, ampliacions 6 i 7.
 */
public final class Etapa05_Dimonis {

    private Etapa05_Dimonis() {}

    /**
     * Crea (sense engegar) el fil del cronòmetre: fa tic cada 100 ms per sempre, però és dimoni.
     *
     * @param tic què s'ha de fer a cada tic
     */
    public static Thread creaCronometre(Runnable tic) {
        Thread cronometre = new Thread(() -> {
            while (true) {
                tic.run();
                Etapa01_Engegar.dorm(100);
            }
        }, "cronometre");
        // TODO FORAT 13 · Fes que el cronòmetre sigui un fil dimoni (s'ha de fer ABANS de start()).
        Forat.n(13);
        return cronometre;
    }

    /**
     * Crea (sense engegar) dos fils: un urgent amb la prioritat màxima i un normal amb la mínima.
     *
     * @return {@code [urgent, tranquil]}
     */
    public static Thread[] creaAmbPrioritats(Runnable tascaUrgent, Runnable tascaTranquilla) {
        Thread urgent = new Thread(tascaUrgent, "urgent");
        Thread tranquil = new Thread(tascaTranquilla, "tranquil");
        // TODO FORAT 14 · Dona al fil urgent la prioritat màxima (fes servir la constant de Thread, no el número).
        Forat.n(14);
        // TODO FORAT 15 · Dona al fil tranquil la prioritat mínima.
        Forat.n(15);
        return new Thread[] {urgent, tranquil};
    }

    public static void main(String[] args) throws InterruptedException {
        Thread cronometre = creaCronometre(() -> System.out.println("tic"));
        cronometre.start();
        Thread[] fils = creaAmbPrioritats(
                () -> System.out.println("urgent (prioritat " + Thread.currentThread().getPriority() + ")"),
                () -> System.out.println("tranquil (prioritat " + Thread.currentThread().getPriority() + ")"));
        fils[1].start();
        fils[0].start();
        Thread.sleep(350);
        System.out.println("main acaba; el cronòmetre és dimoni? " + cronometre.isDaemon()
                + " → el programa acaba igualment.");
    }
}
