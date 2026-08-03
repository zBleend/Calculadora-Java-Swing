package com.calculadora.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EvaluadorExpresionTest {

    private void verificar(String expresion, double esperado) {
        assertEquals(esperado, evaluar(expresion), 1e-9);
    }

    private double evaluar(String expresion) {
        return EvaluadorExpresion.evaluar(expresion);
    }

    @Test
    public void suma() {
        verificar("2+3", 5);
    }

    @Test
    public void restaAsociativaIzquierda() {
        verificar("10-2-3", 5);
    }

    @Test
    public void multiplicacionAntesDeSuma() {
        verificar("2+3×4", 14);
    }

    @Test
    public void divisionAntesDeSuma() {
        verificar("2+8÷4", 4);
    }

    @Test
    public void parentesis() {
        verificar("(2+3)×4", 20);
    }

    @Test
    public void parentesisAnidados() {
        verificar("2×(3+(4×5))", 46);
    }

    @Test
    public void operadorAsteriscoYBarra() {
        verificar("2+3*4", 14);
        verificar("8/2", 4);
    }

    @Test
    public void operadorUnario() {
        verificar("-5+3", -2);
        verificar("2×-3", -6);
        verificar("-(2+3)", -5);
    }

    @Test
    public void decimales() {
        verificar("1.5+2.5", 4);
    }

    @Test
    public void porcentajeSuma() {
        verificar("200+10%", 220);
    }

    @Test
    public void porcentajeResta() {
        verificar("200-10%", 180);
    }

    @Test
    public void porcentajeRestaNegativa() {
        verificar("100-200%", -100);
    }

    @Test
    public void porcentajeMultiplicacion() {
        verificar("200×10%", 20);
    }

    @Test
    public void porcentajeDivision() {
        verificar("200÷10%", 2000);
    }

    @Test
    public void porcentajeSolo() {
        verificar("50%", 0.5);
    }

    @Test
    public void porcentajeUnario() {
        verificar("-50%", -0.5);
    }

    @Test
    public void porcentajeGrupo() {
        verificar("(200+10)%", 2.1);
    }

    @Test(expected = ArithmeticException.class)
    public void divisionPorCero() {
        evaluar("5÷0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parentesisSinCerrar() {
        evaluar("(5+3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parentesisSobrante() {
        evaluar("5+3)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void operadorFinal() {
        evaluar("5+");
    }

    @Test(expected = IllegalArgumentException.class)
    public void expresionVacia() {
        evaluar("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void numeroInvalido() {
        evaluar("1..2");
    }
}
