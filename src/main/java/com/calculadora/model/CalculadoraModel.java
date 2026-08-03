package com.calculadora.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estado de la calculadora orientada a expresiones.
 *
 * <p>Guarda la memoria (MC/MR/M+/M-), el historial de operaciones (maximo
 * {@value #MAX_HISTORIAL} entradas), el estado de repeticion de "=" (ultimo
 * operador binario y su operando) y la bandera de que la pantalla muestra un
 * resultado terminado en lugar de la entrada en curso.</p>
 */
@Data
@NoArgsConstructor
public class CalculadoraModel {

    public static final int MAX_HISTORIAL = 100;

    private double memoria;
    private boolean hayMemoria;

    private String ultimoOperador;
    private double ultimoOperando;
    private boolean puedeRepetir;

    private boolean mostrandoResultado;
    private String ultimoResultado;

    private final List<String> historial = new ArrayList<>();

    public void agregarHistorial(String entrada) {
        historial.add(entrada);
        if (historial.size() > MAX_HISTORIAL) {
            historial.remove(0);
        }
    }

    public void limpiarHistorial() {
        historial.clear();
    }

    public List<String> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public void memoriaSuma(double valor) {
        memoria += valor;
        hayMemoria = true;
    }

    public void memoriaResta(double valor) {
        memoria -= valor;
        hayMemoria = true;
    }

    public void memoriaLimpia() {
        memoria = 0;
        hayMemoria = false;
    }
}
