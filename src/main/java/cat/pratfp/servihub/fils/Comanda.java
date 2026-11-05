package cat.pratfp.servihub.fils;

/**
 * Una comanda de ServiHub: un número i el plat demanat.
 * <p>
 * És un {@code record}: immutable, amb {@code equals}, {@code hashCode} i {@code toString} fets.
 * Com que cap fil no la pot modificar, es pot compartir entre fils sense cap perill.
 *
 * @param id   número de comanda
 * @param plat nom del plat
 */
public record Comanda(int id, String plat) {

    /** Comanda especial que vol dir «ja no en vindran més»: la píndola de FI de l'etapa 8. */
    public static final Comanda FI = new Comanda(-1, "FI");

    public boolean esFi() {
        return id == -1;
    }
}
