/*
 * App.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 2.0
 * Descripción: Punto de entrada del programa. Crea un objeto Logic
 *              e inicia el proceso de integración.
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Clase App con método main()
 */

/*
 * Reuse Instructions:
 *  - Ejecutar con: java App
 *  - La clase App solo delega el control a Logic.logic1a().
 */

public class App {

    /*
     * main
     * Propósito: iniciar la ejecución del programa.
     */
    public static void main(String[] args) {
        Logic objLogic = new Logic();   /* Controlador principal del flujo */
        objLogic.logic1a();             /* Ejecuta el proceso de integración */
    }
}
