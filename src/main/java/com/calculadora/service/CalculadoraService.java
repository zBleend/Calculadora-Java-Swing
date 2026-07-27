package com.calculadora.service;

import com.calculadora.model.CalculadoraModel;

public class CalculadoraService {

    public double calcular(CalculadoraModel model) {
        String operacion = model.getOperacion();
        Double segundoNumero = model.getSegundoNumero();
        double num2 = segundoNumero != null ? segundoNumero : 0.0;

        if (operacion == null || operacion.isEmpty()) {
            return num2;
        }

        Double primerNumero = model.getPrimerNumero();
        double num1 = primerNumero != null ? primerNumero : 0.0;

        switch (operacion) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": case "×": return num1 * num2;
            case "/": case "÷":
                if (num2 == 0) throw new ArithmeticException("Syntax Error");
                return num1 / num2;
            default: throw new IllegalArgumentException("Invalid operation: " + operacion);
        }
    }

    public double cambiarSigno(double numero) {
        return numero * -1;
    }

    public double calcularRaizCuadrada(double numero) {
        if (numero < 0) {
            throw new ArithmeticException("Invalid input for square root");
        }
        return Math.sqrt(numero);
    }
}