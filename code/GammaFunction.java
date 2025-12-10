/*
 * GammaFunction.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 2.0
 * Descripción: Calcula la función Gamma para enteros y medios enteros,
 *              usando recurrencia, de acuerdo a las necesidades de la
 *              distribución t-Student.
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Clase GammaFunction con:
 *      computeIntGamma, computeDblGamma, getGammaValue.
 */

/*
 * Reuse Instructions:
 *  - Usar computeDblGamma(doubleValue) para valores tipo n/2.
 *  - Para enteros usar computeIntGamma(intValue).
 */

public class GammaFunction {

    private double gammaValue; /* Último valor calculado de Gamma */

    /*
     * computeIntGamma
     * Propósito: calcular Gamma(n) para n entero positivo.
     * Nota: Gamma(n) = (n - 1)!.
     */
    public double computeIntGamma(int intValue) {

        if (intValue <= 1) {
            gammaValue = 1.0;
        } else {
            double result = 1.0;

            for (int i = 2; i <= intValue; i++) {
                result = result * i;
            }

            gammaValue = result;
        }

        return gammaValue;
    }

    /*
     * computeDblGamma
     * Propósito: calcular Gamma(z) para valores dobles, usando
     *            casos base y recurrencia Gamma(z) = (z-1)*Gamma(z-1).
     */
    public double computeDblGamma(double doubleValue) {
        gammaValue = gamma(doubleValue);
        return gammaValue;
    }

    /*
     * gamma
     * Propósito: función recursiva interna para evaluar Gamma(z).
     */
    private double gamma(double z) {

        /* Casos base: Gamma(1) = 1, Gamma(1/2) = sqrt(pi) */
        if (Math.abs(z - 1.0) < 1e-12) {
            return 1.0;
        }

        if (Math.abs(z - 0.5) < 1e-12) {
            return Math.sqrt(Math.PI);
        }

        /* Caso entero: usar factorial (Gamma(n) = (n-1)!) */
        if (Math.abs(z - Math.rint(z)) < 1e-12) {
            int n = (int) Math.round(z);
            return computeIntGamma(n - 1);
        }

        /* Caso general: recurrencia Gamma(z) = (z - 1) * Gamma(z - 1) */
        return (z - 1.0) * gamma(z - 1.0);
    }

    /*
     * getGammaValue
     * Propósito: devolver el último valor calculado de Gamma.
     */
    public double getGammaValue() {
        return gammaValue;
    }
}
