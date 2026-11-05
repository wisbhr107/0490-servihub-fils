package cat.pratfp.servihub.fils;

/**
 * Marca un forat que encara no està fet.
 * <p>
 * A cada etapa hi ha tres forats, cadascun una crida {@code Forat.n(numero)} amb un comentari
 * {@code TODO FORAT xx} just a sobre que diu què ha de fer la línia. El projecte compila des del primer
 * moment, però quan el programa (o la prova) arriba a un forat buit llança una excepció que diu quin és.
 * Substituïu cada {@code Forat.n(...)} per la vostra línia de codi. Quan els 30 estiguin fets, aquesta
 * classe ja no la farà servir ningú.
 * <p>
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public final class Forat {

    private Forat() {}

    /**
     * Forat buit: llança sempre una excepció amb el número del forat.
     *
     * @param numero número del forat (1-30)
     * @param <T>    qualsevol tipus, perquè el forat pugui anar on calgui un valor
     * @return no retorna mai
     */
    public static <T> T n(int numero) {
        throw new UnsupportedOperationException(String.format("FORAT %02d per completar", numero));
    }
}
