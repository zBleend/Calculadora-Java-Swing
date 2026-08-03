package com.calculadora.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculadoraServiceTest {

    private final CalculadoraService service = new CalculadoraService();

    @Test
    public void evaluarDelegaEnEvaluador() {
        assertEquals(14.0, service.evaluar("2+3×4"), 1e-9);
        assertEquals(220.0, service.evaluar("200+10%"), 1e-9);
    }

    @Test
    public void cuadrado() {
        assertEquals(16.0, service.calcularCuadrado(4.0), 0.0);
        assertEquals(4.0, service.calcularCuadrado(-2.0), 0.0);
    }

    @Test
    public void raizCuadrada() {
        assertEquals(3.0, service.calcularRaizCuadrada(9.0), 0.0);
    }

    @Test(expected = ArithmeticException.class)
    public void raizDeNegativo() {
        service.calcularRaizCuadrada(-9.0);
    }

    @Test
    public void inverso() {
        assertEquals(0.5, service.calcularInverso(2.0), 0.0);
        assertEquals(-0.25, service.calcularInverso(-4.0), 0.0);
    }

    @Test(expected = ArithmeticException.class)
    public void inversoDeCero() {
        service.calcularInverso(0.0);
    }
}
