package com.calculadora.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculadoraModel {

    private double primerNumero;
    private double segundoNumero;
    private String operacion;
    private double resultado;
    private boolean limpiarPantalla;

}
