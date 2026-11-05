# ServiHub · La central de comandes en 10 etapes (MP0490 · RA2 · fils)

**Alumne/a:** _(escriu aquí el teu nom)_

Projecte Maven (Java 21, JUnit 5) que **ja compila** però té **30 forats** (3 per etapa) marcats amb
`// TODO FORAT xx`. Al lloc de cada línia que falta hi ha una crida `Forat.n(xx)`: substitueix-la per la teva línia.

| Etapa | Classe | Forats | Prova |
|---|---|---|---|
| 1 Crear i engegar fils | `Etapa01_Engegar` | 01-03 | `Etapa01Test` |
| 2 Esperar amb join | `Etapa02_Esperar` | 04-06 | `Etapa02Test` |
| 3 Cicle de vida | `Etapa03_Estats` | 07-09 | `Etapa03Test` |
| 4 Aturar amb interrupt | `Etapa04_Aturar` | 10-12 | `Etapa04Test` |
| 5 Dimonis i prioritats | `Etapa05_Dimonis` | 13-15 | `Etapa05Test` |
| 6 Condició de carrera | `Etapa06_Carrera` | 16-18 | `Etapa06Test` |
| 7 Panys sense interbloqueig | `Etapa07_Magatzems` | 19-21 | `Etapa07Test` |
| 8 Productor-consumidor | `Etapa08_Cuina` | 22-24 | `Etapa08Test` |
| 9 Piscina de fils (Callable/Future) | `Etapa09_Piscina` | 25-27 | `Etapa09Test` |
| 10 ServiHub v2: la central sencera | `Etapa10_Central` | 28-30 | `Etapa10Test` |

## Com es treballa

1. Obre la carpeta amb IntelliJ IDEA (projecte Maven). Executa `Etapa01Test`: surt en vermell. És normal.
2. Omple els tres forats de l'etapa 1 i torna a executar la prova fins que surti en verd. Executa també el `main`
   de la classe per veure què fa el programa.
3. Fes `git commit` amb el missatge `Etapa 1` i passa a la següent. **Les etapes van en ordre**: cada una fa servir
   coses de les anteriors.
4. Quan les 10 etapes tinguin la prova verda, `mvn test` ha de dir `Tests run: 30, Failures: 0, Errors: 0`.

Només es toquen les línies dels forats. Les proves (`src/test`) no es modifiquen.
