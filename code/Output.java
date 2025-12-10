/*
 * Output.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 2.0
 * Descripción: Clase auxiliar para escribir texto en archivos externos.
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Clase Output con método writeData()
 */

/*
 * Reuse Instructions:
 *  - Llamar writeData(nombreArchivo, texto) para guardar resultados.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    /*
     * writeData
     * Propósito: escribir el texto recibido en un archivo.
     * Parámetros:
     *  - outFile: nombre del archivo de salida.
     *  - outText: contenido a escribir.
     */
    public void writeData(String outFile, String outText) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            bw.write(outText);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error al escribir archivo: " + outFile,
                    e
            );
        }
    }
}
