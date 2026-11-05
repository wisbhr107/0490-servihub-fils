package cat.pratfp.servihub.fils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ETAPA 7 · Panys explícits sense interbloqueig (forats 19, 20 i 21).
 * <p>
 * ServiHub mou estoc entre dos magatzems. Cada magatzem té el seu {@link ReentrantLock}. Si un fil
 * transfereix d'A a B i un altre de B a A, i cadascun agafa primer el seu pany d'origen, es poden
 * quedar esperant-se l'un a l'altre per sempre: <b>interbloqueig</b>. La regla d'or: tots els fils
 * agafen els panys <b>en el mateix ordre</b> (aquí, pel número de magatzem) i els alliberen sempre
 * dins d'un {@code finally}.
 * <p>
 * Teoria: apartat 7 (interbloqueig), apartat 8 (ReentrantLock) i exemple 2, ampliació 6.
 */
public final class Etapa07_Magatzems {

    private Etapa07_Magatzems() {}

    /** Un magatzem amb estoc i el seu pany. */
    public static final class Magatzem {
        final int numero;
        final ReentrantLock pany = new ReentrantLock();
        private int estoc;

        public Magatzem(int numero, int estocInicial) {
            this.numero = numero;
            this.estoc = estocInicial;
        }

        public int estoc() {
            pany.lock();
            try {
                return estoc;
            } finally {
                pany.unlock();
            }
        }
    }

    /**
     * Mou {@code quantitat} unitats d'un magatzem a l'altre, agafant els dos panys en ordre fix.
     */
    public static void transfereix(Magatzem origen, Magatzem desti, int quantitat) {
        // TODO FORAT 19 · Tria com a "primer" el magatzem amb el número més petit (i l'altre serà "segon").
        Magatzem primer = Forat.n(19);
        Magatzem segon = primer == origen ? desti : origen;

        primer.pany.lock();
        try {
            // TODO FORAT 20 · Agafa també el pany del segon magatzem.
            Forat.n(20);
            try {
                origen.estoc -= quantitat;
                desti.estoc += quantitat;
            } finally {
                // TODO FORAT 21 · Allibera el pany del segon magatzem (passi el que passi).
                Forat.n(21);
            }
        } finally {
            primer.pany.unlock();
        }
    }

    /**
     * Dos fils fan transferències creuades (A→B i B→A) moltes vegades alhora.
     *
     * @return l'estoc total al final (s'ha de conservar)
     */
    public static int transferenciesCreuades(Magatzem a, Magatzem b, int vegades) throws InterruptedException {
        Thread capAB = new Thread(() -> {
            for (int i = 0; i < vegades; i++) {
                transfereix(a, b, 1);
            }
        }, "A-cap-a-B");
        Thread capBA = new Thread(() -> {
            for (int i = 0; i < vegades; i++) {
                transfereix(b, a, 1);
            }
        }, "B-cap-a-A");
        capAB.start();
        capBA.start();
        capAB.join();
        capBA.join();
        return a.estoc() + b.estoc();
    }

    public static void main(String[] args) throws InterruptedException {
        Magatzem a = new Magatzem(1, 1000);
        Magatzem b = new Magatzem(2, 1000);
        int total = transferenciesCreuades(a, b, 50_000);
        System.out.println("A: " + a.estoc() + " · B: " + b.estoc() + " · total: " + total + " (ha de ser 2000)");
    }
}
