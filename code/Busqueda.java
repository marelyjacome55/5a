/*
 * Busqueda.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-09
 * Versión: 1.0
 * Descripción: Implementa el algoritmo de búsqueda del valor de x
 *              dado un p objetivo para la distribución t-Student,
 *              reutilizando SimpsonIntegration.
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Clase Busqueda con:
 *      buscarX, getUltimoP.
 */

/*
 * Reuse Instructions:
 *  - Esta clase se puede reutilizar en cualquier programa que requiera
 *    encontrar el valor de x tal que P(T <= x) = p_objetivo usando la
 *    distribución t-Student y la regla de Simpson.
 *  - Depende de:
 *      - SimpsonIntegration: para calcular p(x) = integral(0..x) t-Student.
 *  - No realiza lecturas de teclado ni escrituras de archivo; solo
 *    calcula y devuelve resultados numéricos.
 */

public class Busqueda {

    /* Objeto para ejecutar la integración de Simpson */
    private SimpsonIntegration objSimpson;

    /* Último valor de p(x) calculado durante la búsqueda */
    private double dblUltimoP;

    /*
     * Constructor
     * Propósito: inicializar el objeto SimpsonIntegration y el valor
     *            inicial de dblUltimoP.
     */
    public Busqueda() {
        objSimpson = new SimpsonIntegration();
        dblUltimoP = 0.0;
    }

    /*
     * buscarX
     * Propósito:
     *  - Aplicar el algoritmo de búsqueda del PDF:
     *    dado un p objetivo, encontrar el valor de x tal que la
     *    integral de la t-Student desde 0 hasta x sea aproximadamente
     *    p_objetivo, dentro de una tolerancia dada.
     *
     * Parámetros:
     *  - dblPObjetivo: valor objetivo de la probabilidad p.
     *  - intDOF: grados de libertad de la distribución t-Student.
     *  - dblTolerancia: error máximo permitido |p_objetivo - p(x)|.
     *  - intNumSeg: número de segmentos para la regla de Simpson.
     *  - dblXInicial: valor inicial de x para iniciar la búsqueda
     *                 (por ejemplo 1.0).
     *  - dblDInicial: tamaño inicial del paso d (por ejemplo 0.5).
     *
     * Retorna:
     *  - El valor de x encontrado que cumple con la tolerancia.
     *
     * Notas:
     *  - Durante la búsqueda se imprime en consola el detalle de
     *    cada iteración (x, p(x), error), lo cual ayuda a verificar
     *    el comportamiento igual que en el Excel.
     *  - El último p(x) calculado se guarda en dblUltimoP y se puede
     *    consultar con getUltimoP().
     */
    public double buscarX(double dblPObjetivo,
                          int intDOF,
                          double dblTolerancia,
                          int intNumSeg,
                          double dblXInicial,
                          double dblDInicial) {

        /* Variables de estado de la búsqueda */
        double dblX = dblXInicial;
        double dblD = dblDInicial;

        double dblPActual;
        double dblError;
        double dblAbsError;

        int intSignoAnterior = 0;
        int intSignoActual = 0;

        int intIter = 0;
        int intIterMax = 1000;  /* Límite de seguridad */

        while (intIter < intIterMax) {

            /* 1. Calcular p(x) con Simpson */
            dblPActual = objSimpson.integrate(intNumSeg, dblX, intDOF);
            dblUltimoP = dblPActual;  /* Guardar el último p(x) */

            /* 2. Calcular error y su valor absoluto */
            dblError = dblPObjetivo - dblPActual;
            dblAbsError = Math.abs(dblError);

            /* Mostrar la iteración (para depuración) */
            System.out.printf(
                    "Iter %2d: x = %.6f   p(x) = %.6f   error = %.10f%n",
                    intIter,
                    dblX,
                    dblPActual,
                    dblError
            );

            /* 3. Criterio de parada: |error| <= tolerancia */
            if (dblAbsError <= dblTolerancia) {
                break;
            }

            /* 4. Calcular signo del error */
            if (dblError > 0.0) {
                intSignoActual = 1;
            } else if (dblError < 0.0) {
                intSignoActual = -1;
            } else {
                intSignoActual = 0;
            }

            /* 5. Ajustar el paso d según cambio o no de signo */
            double dblDUsado;

            if (intIter == 0) {
                /* Primera iteración: usamos directamente dInicial */
                dblDUsado = dblDInicial;
            } else {
                if (intSignoActual == intSignoAnterior) {
                    /* Mismo signo que antes: dejamos d igual */
                    dblDUsado = dblD;
                } else {
                    /* Cambió el signo: dividimos d entre 2 */
                    dblDUsado = dblD / 2.0;
                }
            }

            /* 6. Actualizar x según el signo del error */
            if (dblError > 0.0) {
                /* p(x) es muy baja -> aumentar x */
                dblX = dblX + dblDUsado;
            } else if (dblError < 0.0) {
                /* p(x) es muy alta -> disminuir x */
                dblX = dblX - dblDUsado;
            } else {
                /* Error exactamente 0 */
                break;
            }

            /* 7. Preparar siguiente iteración */
            dblD = dblDUsado;
            intSignoAnterior = intSignoActual;
            intIter++;
        }

        return dblX;
    }

    /*
     * getUltimoP
     * Propósito: devolver el último valor p(x) calculado durante la
     *            ejecución de buscarX().
     */
    public double getUltimoP() {
        return dblUltimoP;
    }
}
