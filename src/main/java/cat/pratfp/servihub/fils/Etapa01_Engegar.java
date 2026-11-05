package cat.pratfp.servihub.fils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ETAPA 1 · Crear i engegar fils (forats 01, 02 i 03).
 * <p>
 * La central de ServiHub té repartidors. Cada repartidor és una tasca ({@link Runnable}) que
 * entrega uns quants paquets i deixa constància de cada entrega en un registre compartit.
 * Per fer-los treballar alhora, cada repartidor ha d'anar dins del seu propi {@link Thread}.
 * <p>
 * Teoria: apartat 3 «Crear i engegar» i exemple 1 «La cursa de repartidors».
 */
public final class Etapa01_Engegar {

    private Etapa01_Engegar() {}

    /** La tasca d'un repartidor: entrega paquets i ho apunta al registre. */
    public static final class Repartidor implements Runnable {

        private final int paquets;
        private final List<String> registre;

        public Repartidor(int paquets, List<String> registre) {
            this.paquets = paquets;
            this.registre = registre;
        }

        @Override
        public void run() {
            for (int i = 1; i <= paquets; i++) {
                // TODO FORAT 01 · Desa a la variable qui el nom del fil que està executant aquest codi ara mateix.
                String qui = Thread.currentThread().getName();
                registre.add(qui + ": paquet " + i);
                dorm(10);
            }
        }
    }

    /**
     * Crea un fil per a cada repartidor, els anomena "repartidor-1", "repartidor-2"... i els engega.
     *
     * @param quants   nombre de repartidors
     * @param paquets  paquets que entrega cadascun
     * @param registre llista compartida (ja és segura per a diversos fils) on apunten les entregues
     * @return els fils, ja engegats, en el mateix ordre que els noms
     */
    public static List<Thread> engegaRepartidors(int quants, int paquets, List<String> registre) {
        List<Thread> fils = new ArrayList<>();
        for (int i = 1; i <= quants; i++) {
            Repartidor repartidor = new Repartidor(paquets, registre);
            // TODO FORAT 02 · Crea el fil que executarà aquest repartidor, amb el nom "repartidor-" + i.
            Thread fil = new Thread(repartidor, "repartidor-" + i);
            // TODO FORAT 03 · Engega el fil (compte: no és el mateix que cridar run()).
            fil.start();
            fils.add(fil);
        }
        return fils;
    }

    /** Crea un registre que diversos fils poden omplir alhora sense perdre línies. */
    public static List<String> nouRegistre() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    static void dorm(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> registre = nouRegistre();
        List<Thread> fils = engegaRepartidors(3, 4, registre);
        System.out.println("main continua mentre els repartidors treballen...");
        for (Thread fil : fils) {
            fil.join();
        }
        registre.forEach(System.out::println);
        System.out.println("Entregues: " + registre.size());
    }
}