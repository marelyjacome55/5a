/*
 * SimpsonIntegration.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 2.0
 * Descripción: Implementa la regla de Simpson para la distribución
 *              t-Student, siguiendo el procedimiento del Excel:
 *              cálculo de p1, p2, coeficiente, f(Xi), términos y suma.
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Clase SimpsonIntegration con métodos:
 *      integrate, computeW, computeXi,
 *      computeFirstBaseTerms, computeExponent,
 *      computeCoefficient, computeFxi,
 *      computeFinalTerms, computeFinalValue, getFinalValue.
 */

/*
 * Reuse Instructions:
 *  - Llamar integrate(intNumSeg, dblX, intDOF) para obtener el valor
 *    de la integral acumulada P(T <= x).
 *  - Internamente se inicializan y usan los arreglos Xi, p1, f(Xi), etc.
 */

public class SimpsonIntegration {

    private int intNumSeg;        /* Número de segmentos (n) */
    private double dblW;          /* Ancho de cada subintervalo (w) */
    private int intDOF;           /* Grados de libertad */
    private double dblX;          /* Límite superior de integración */

    private double[] dblTotXi;          /* Xi: puntos de integración */
    private double[] dblFirstBaseTerms; /* p1 = 1 + (Xi^2 / dof) */
    private double dblExponent;         /* p2 = (dof + 1) / 2 */
    private double dblCoeff;            /* Coeficiente C de la t-Student */
    private double[] dblFxi;            /* f(Xi) = C * (p1^-p2) */
    private double[] dblFinalTerms;     /* término = multiplicador * f(Xi) */
    private double dblFinalValue;       /* Valor final de la integral */

    /*
     * integrate
     * Propósito: ejecutar el flujo completo de Simpson definido en el UML.
     * Parámetros:
     *  - intNumSeg: número de segmentos
     *  - dblX: límite superior de integración
     *  - intDOF: grados de libertad
     * Retorna:
     *  - valor final de la integral P(T <= x).
     */
    public double integrate(int intNumSeg, double dblX, int intDOF) {

        this.intNumSeg = intNumSeg;
        this.dblX = dblX;
        this.intDOF = intDOF;

        /* Secuencia de métodos según el UML */
        computeW(intNumSeg, dblX);
        computeXi(intNumSeg);
        computeFirstBaseTerms(intNumSeg, dblTotXi, intDOF);
        computeExponent(intDOF);
        computeCoefficient(intDOF);
        computeFxi(intNumSeg);
        computeFinalTerms(intNumSeg);
        computeFinalValue(intNumSeg);

        return dblFinalValue;
    }

    /*
     * computeW
     * Propósito: calcular el ancho w = x / num_seg.
     */
    public void computeW(int intNumSeg, double dblX) {
        dblW = dblX / intNumSeg;
    }

    /*
     * computeXi
     * Propósito: calcular los puntos Xi = i * w, para i = 0..n.
     */
    public void computeXi(int intNumSeg) {
        dblTotXi = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            dblTotXi[i] = i * dblW;
        }
    }

    /*
     * computeFirstBaseTerms
     * Propósito: calcular p1 = 1 + (Xi^2 / dof) para cada Xi.
     */
    public void computeFirstBaseTerms(
            int intNumSeg,
            double[] dblTotXi,
            int intDOF
    ) {
        dblFirstBaseTerms = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            double xi = dblTotXi[i];
            dblFirstBaseTerms[i] = 1.0 + (xi * xi / intDOF);
        }
    }

    /*
     * computeExponent
     * Propósito: calcular p2 = (dof + 1) / 2.
     */
    public void computeExponent(int intDOF) {
        dblExponent = (intDOF + 1.0) / 2.0;
    }

    /*
     * computeCoefficient
     * Propósito: calcular la constante C de la distribución t-Student:
     *   C = Gamma((dof+1)/2) / ( sqrt(dof*PI) * Gamma(dof/2) ).
     */
    public void computeCoefficient(int intDOF) {
        GammaFunction gamma = new GammaFunction();

        double num = gamma.computeDblGamma((intDOF + 1.0) / 2.0);
        double den = Math.sqrt(intDOF * Math.PI)
                     * gamma.computeDblGamma(intDOF / 2.0);

        dblCoeff = num / den;
    }

    /*
     * computeFxi
     * Propósito: calcular f(Xi) = C * (p1 ^ -p2) para cada Xi.
     */
    public void computeFxi(int intNumSeg) {
        dblFxi = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            dblFxi[i] = dblCoeff
                        * Math.pow(dblFirstBaseTerms[i], -dblExponent);
        }
    }

    /*
     * computeFinalTerms
     * Propósito: aplicar los multiplicadores de Simpson:
     *            1, 4, 2, 4, 2, ..., 4, 1
     *            término = multiplicador * f(Xi).
     */
    public void computeFinalTerms(int intNumSeg) {
        dblFinalTerms = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            int mult;

            if (i == 0 || i == intNumSeg) {
                mult = 1;
            } else if (i % 2 == 0) {
                mult = 2;
            } else {
                mult = 4;
            }

            dblFinalTerms[i] = mult * dblFxi[i];
        }
    }

    /*
     * computeFinalValue
     * Propósito: sumar los términos y aplicar la fórmula de Simpson:
     *            integral ≈ (w / 3) * Σ(término).
     */
    public void computeFinalValue(int intNumSeg) {
        double sum = 0.0;

        for (int i = 0; i <= intNumSeg; i++) {
            sum = sum + dblFinalTerms[i];
        }

        dblFinalValue = (dblW / 3.0) * sum;
    }

    /*
     * getFinalValue
     * Propósito: devolver el valor final de la integral.
     */
    public double getFinalValue() {
        return dblFinalValue;
    }
}
