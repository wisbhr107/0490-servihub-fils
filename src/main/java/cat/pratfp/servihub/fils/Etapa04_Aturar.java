package cat.pratfp.servihub.fils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ETAPA 4 · Aturar un fil amb educació: interrupt() (forats 10, 11 i 12).
 * <p>
 * El vigilant de la central fa rondes sense parar. No hi ha cap botó «mata'l» (stop() està
 * deprecat i és perillós): el que fem és demanar-li que plegui amb {@link Thread#interrupt()},
 * i el vigilant ha de ser prou educat per mirar la bandera d'interrupció i acabar sol.
 * <p>
 * Teoria: apartat 5 «Aturar un fil» i exemple 1, ampliació 5 «En Biel punxa».
 */
public final class Etapa04_Aturar {

    private Etapa04_Aturar() {}

    /** Fa rondes fins que li demanen que plegui. */
    public static final class Vigilant implements Runnable {

        private final AtomicInteger rondes = new AtomicInteger();

        public int rondes() {
            return rondes.get();
        }

        @Override
        public void run() {
            // TODO FORAT 10 · Continua fent rondes mentre el fil actual NO estigui interromput.
            while (Forat.<Boolean>n(10)) {
                rondes.incrementAndGet();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // sleep() ha esborrat la bandera d'interrupció en llançar l'excepció.
                    // TODO FORAT 11 · Torna a marcar el fil actual com a interromput perquè el while acabi.
                    Forat.n(11);
                }
            }
            System.out.println("Vigilant: plego després de " + rondes.get() + " rondes.");
        }
    }

    /**
     * Demana al vigilant que plegui i espera (com a màxim un segon) que ho faci.
     *
     * @return {@code true} si el fil ha acabat dins del segon
     */
    public static boolean aturaVigilant(Thread filDelVigilant) throws InterruptedException {
        // TODO FORAT 12 · Demana al fil del vigilant que s'interrompi.
        Forat.n(12);
        return Etapa02_Esperar.esperaAmbLimit(filDelVigilant, 1000);
    }

    public static void main(String[] args) throws InterruptedException {
        Vigilant vigilant = new Vigilant();
        Thread fil = new Thread(vigilant, "vigilant");
        fil.start();
        Thread.sleep(300);
        System.out.println("main: vigilant, ja pots plegar.");
        System.out.println("Ha plegat? " + aturaVigilant(fil) + " · rondes: " + vigilant.rondes());
    }
}
