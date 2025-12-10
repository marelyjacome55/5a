/*
 * Logic.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-09
 * Versión: 2.0
 * Descripción: Gestiona la entrada de datos del usuario y coordina
 *              el refinamiento de la integración usando
 *              SimpsonIntegration.
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Clase Logic con método logic1a()
 */

/*
 * Reuse Instructions:
 *  - Llamada desde App.
 *  - Solicita x, dof y epsilon por consola.
 *  - Controla el refinamiento: num_seg = 10, 20, 40, ...
 *  - Imprime el valor p en cada iteración y el resultado final.
 */

import java.util.Scanner;

public class Logic {

    private int intNumSeg;   /* Número actual de segmentos */
    private double dblE;     /* Error permitido (epsilon) */
    private int intDOF;      /* Grados de libertad de la t-Student */
    private double dblX;     /* Límite superior de integración */

    /*
     * logic1a
     * Propósito:
     *  - Gestionar la entrada de datos del usuario para Program 6
     *    (p objetivo, dof, tolerancia, número de segmentos) y
     *    coordinar la búsqueda del valor de x correspondiente
     *    usando la clase Busqueda.
     *
     * Detalle:
     *  - Se pide por teclado:
     *      p_objetivo, dof, tolerancia_error, num_segmentos.
     *  - Se crean valores iniciales:
     *      x_inicial = 1.0, d_inicial = 0.5.
     *  - Se invoca Busqueda.buscarX(...) para encontrar x.
     *  - Se muestra en consola el valor final de x y p(x).
     */
    public void logic1a() {

        java.util.Scanner objScanner = new java.util.Scanner(System.in);

        double dblPObjetivo;
        int intDOF;
        double dblTolerancia;
        int intNumSeg;

        /* === Entrada de datos === */
        System.out.println("=== Programa 5: Búsqueda de x para t-Student ===");

        System.out.print("Ingresa p objetivo (ejemplo 0.20): ");
        dblPObjetivo = objScanner.nextDouble();

        System.out.print("Ingresa los grados de libertad (dof): ");
        intDOF = objScanner.nextInt();

        System.out.print("Ingresa la tolerancia del error (ejemplo 0.00001): ");
        dblTolerancia = objScanner.nextDouble();

        System.out.print("Ingresa el número de segmentos para Simpson (ejemplo 10): ");
        intNumSeg = objScanner.nextInt();

        /* Valores iniciales del algoritmo (como en el PDF) */
        double dblXInicial = 1.0;
        double dblDInicial = 0.5;

        /* === Ejecución de la búsqueda === */
        Busqueda objBusqueda = new Busqueda();

        double dblXEncontrado = objBusqueda.buscarX(
                dblPObjetivo,
                intDOF,
                dblTolerancia,
                intNumSeg,
                dblXInicial,
                dblDInicial
        );

        double dblPFinal = objBusqueda.getUltimoP();

        /* === Resultado final === */
        System.out.println();
        System.out.println("=== Resultado final ===");
        System.out.printf(
                "x = %.6f    dof = %d    p(x) = %.6f%n",
                dblXEncontrado,
                intDOF,
                dblPFinal
        );

        objScanner.close();
    }

}
