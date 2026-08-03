package com.calculadora.service;

public class CalculadoraService {

    public double evaluar(String expresion) {
        return EvaluadorExpresion.evaluar(expresion);
    }

    public double calcularCuadrado(double numero) {
        return numero * numero;
    }

    public double calcularRaizCuadrada(double numero) {
        if (numero < 0) {
            throw new ArithmeticException("Invalid input for square root");
        }
        return Math.sqrt(numero);
    }

    public double calcularInverso(double numero) {
        if (numero == 0) {
            throw new ArithmeticException("Division por cero");
        }
        return 1.0 / numero;
    }
}
