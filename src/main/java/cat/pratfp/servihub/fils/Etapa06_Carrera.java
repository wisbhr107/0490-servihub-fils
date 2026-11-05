package cat.pratfp.servihub.fils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ETAPA 6 · La condició de carrera i com evitar-la (forats 16, 17 i 18).
 * <p>
 * Quatre caixes de ServiHub compten comandes sobre un mateix comptador. {@code valor++} sembla una
 * sola operació, però són tres (llegir, sumar, desar): si dues caixes ho fan alhora, es perden sumes.
 * Dues solucions: un bloc {@code synchronized} (només un fil alhora) o un {@link AtomicInteger}
 * (una operació atòmica de maquinari).
 * <p>
 * Teoria: apartats 7 «Compartir i perills» i 8 «Sincronitzar»; exemple 2, ampliacions 1-3.
 */
public final class Etapa06_Carrera {

    private Etapa06_Carrera() {}

    /** El que fa qualsevol comptador de comandes. */
    public interface Comptador {
        void incrementa();

        int valor();
    }

    /** Tal com estava a classe: perd sumes. NO el toquis: serveix per veure el problema. */
    public static final class ComptadorInsegur implements Comptador {
        private int valor;

        @Override
        public void incrementa() {
            valor++;
        }

        @Override
        public int valor() {
            return valor;
        }
    }

    /** Solució 1: només un fil alhora pot entrar al bloc. */
    public static final class ComptadorSincronitzat implements Comptador {
        private int valor;

        @Override
        public void incrementa() {
            // TODO FORAT 16 · Incrementa valor dins d'un bloc synchronized sobre this (una sola línia).
            Forat.n(16);
        }

        @Override
        public int valor() {
            synchronized (this) {
                return valor;
            }
        }
    }

    /** Solució 2: una operació atòmica, sense claus. */
    public static final class ComptadorAtomic implements Comptador {
        private final AtomicInteger valor = new AtomicInteger();

        @Override
        public void incrementa() {
            // TODO FORAT 17 · Incrementa l'AtomicInteger de manera atòmica.
            Forat.n(17);
        }

        @Override
        public int valor() {
            // TODO FORAT 18 · Retorna el valor actual de l'AtomicInteger com a int.
            return Forat.n(18);
        }
    }

    /**
     * Engega {@code caixes} fils que fan {@code perCaixa} increments cadascun i espera que acabin.
     *
     * @return el valor final del comptador (hauria de ser caixes × perCaixa)
     */
    public static int processa(Comptador comptador, int caixes, int perCaixa) throws InterruptedException {
        List<Thread> fils = new ArrayList<>();
        for (int i = 1; i <= caixes; i++) {
            Thread caixa = new Thread(() -> {
                for (int j = 0; j < perCaixa; j++) {
                    comptador.incrementa();
                }
            }, "caixa-" + i);
            fils.add(caixa);
            caixa.start();
        }
        Etapa02_Esperar.esperaTots(fils);
        return comptador.valor();
    }

    public static void main(String[] args) throws InterruptedException {
        int esperat = 4 * 100_000;
        System.out.println("Esperat:       " + esperat);
        System.out.println("Insegur:       " + processa(new ComptadorInsegur(), 4, 100_000));
        System.out.println("Sincronitzat:  " + processa(new ComptadorSincronitzat(), 4, 100_000));
        System.out.println("Atòmic:        " + processa(new ComptadorAtomic(), 4, 100_000));
    }
}
