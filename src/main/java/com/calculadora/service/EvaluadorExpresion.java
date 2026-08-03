package com.calculadora.service;

/**
 * Evalua una expresion aritmetica con precedencia de operadores.
 *
 * <p>Soporta numeros (con punto decimal), operadores + - × ÷ (tambien * y /),
 * parentesis, operador unario y el porcentaje postfijo con semantica clasica:
 * a + b% = a + (a*b/100), a - b% = a - (a*b/100), a × b% = a × (b/100),
 * a ÷ b% = a ÷ (b/100). Un % sin operando izquierdo (o tras un grupo entre
 * parentesis) equivale a dividir entre 100.</p>
 */
public final class EvaluadorExpresion {

    private final String expresion;
    private int pos;

    private EvaluadorExpresion(String expresion) {
        this.expresion = expresion;
        this.pos = 0;
    }

    public static double evaluar(String expresion) {
        if (expresion == null || expresion.trim().isEmpty()) {
            throw new IllegalArgumentException("Expresion vacia");
        }
        EvaluadorExpresion parser = new EvaluadorExpresion(expresion.replaceAll("\\s+", ""));
        double resultado = parser.evaluarExpresion().valor;
        if (parser.pos != parser.expresion.length()) {
            throw new IllegalArgumentException("Expresion invalida en posicion " + parser.pos);
        }
        return resultado;
    }

    private static final class Valor {
        double valor;
        boolean porcentaje;

        Valor(double valor, boolean porcentaje) {
            this.valor = valor;
            this.porcentaje = porcentaje;
        }
    }

    private Valor evaluarExpresion() {
        Valor izquierda = evaluarTermino();

        while (hay('+') || hay('-')) {
            char op = siguiente();
            Valor derecha = evaluarTermino();
            if (izquierda.porcentaje) {
                izquierda.valor /= 100.0;
            }
            if (derecha.porcentaje) {
                derecha.valor = izquierda.valor * derecha.valor / 100.0;
            }
            izquierda.valor = aplicar(izquierda.valor, op, derecha.valor);
            izquierda.porcentaje = false;
        }

        if (izquierda.porcentaje) {
            izquierda.valor /= 100.0;
        }
        return new Valor(izquierda.valor, false);
    }

    private Valor evaluarTermino() {
        Valor izquierda = evaluarFactor();

        while (hay('×') || hay('÷') || hay('*') || hay('/')) {
            char op = siguiente();
            Valor derecha = evaluarFactor();
            if (izquierda.porcentaje) {
                izquierda.valor /= 100.0;
            }
            if (derecha.porcentaje) {
                derecha.valor /= 100.0;
            }
            izquierda.valor = aplicar(izquierda.valor, op, derecha.valor);
            izquierda.porcentaje = false;
        }
        return izquierda;
    }

    private Valor evaluarFactor() {
        if (hay('-')) {
            siguiente();
            Valor v = evaluarFactor();
            v.valor = -v.valor;
            return v;
        }
        if (hay('+')) {
            siguiente();
            return evaluarFactor();
        }
        return evaluarPrimario();
    }

    private Valor evaluarPrimario() {
        if (hay('(')) {
            siguiente();
            Valor v = evaluarExpresion();
            esperar(')');
            if (hay('%')) {
                siguiente();
                return new Valor(v.valor / 100.0, false);
            }
            return new Valor(v.valor, false);
        }
        double numero = leerNumero();
        if (hay('%')) {
            siguiente();
            return new Valor(numero, true);
        }
        return new Valor(numero, false);
    }

    private double leerNumero() {
        int inicio = pos;
        while (pos < expresion.length() && (Character.isDigit(expresion.charAt(pos)) || expresion.charAt(pos) == '.')) {
            pos++;
        }
        if (inicio == pos) {
            throw new IllegalArgumentException("Numero invalido");
        }
        String token = expresion.substring(inicio, pos);
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numero invalido: " + token);
        }
    }

    private boolean hay(char c) {
        return pos < expresion.length() && expresion.charAt(pos) == c;
    }

    private char siguiente() {
        if (pos >= expresion.length()) {
            throw new IllegalArgumentException("Expresion incompleta");
        }
        return expresion.charAt(pos++);
    }

    private void esperar(char c) {
        if (!hay(c)) {
            throw new IllegalArgumentException("Se esperaba '" + c + "'");
        }
        pos++;
    }

    private double aplicar(double a, char op, double b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '×':
            case '*': return a * b;
            case '÷':
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division por cero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Operador invalido: " + op);
        }
    }
}
