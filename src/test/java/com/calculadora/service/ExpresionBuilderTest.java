package com.calculadora.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExpresionBuilderTest {

    private ExpresionBuilder b = new ExpresionBuilder();

    private void teclear(String texto) {
        for (char c : texto.toCharArray()) {
            if (Character.isDigit(c)) {
                b.agregarDigito(c);
            } else {
                switch (c) {
                    case '.': b.agregarPunto(); break;
                    case '(': b.agregarParentesisIzquierdo(); break;
                    case ')': b.agregarParentesisDerecho(); break;
                    case '%': b.agregarPorcentaje(); break;
                    default: b.agregarOperador(c); break;
                }
            }
        }
    }

    @Test
    public void digitosConsecutivos() {
        teclear("35+20");
        assertEquals("35+20", b.getExpresion());
        assertEquals("20", b.getEntradaActual());
    }

    @Test
    public void operadorNoIniciaExpresion() {
        assertFalse(b.agregarOperador('+'));
        assertEquals("", b.getExpresion());
    }

    @Test
    public void operadorConsecutivoSeReemplaza() {
        teclear("5");
        teclear("+");
        teclear("-");
        assertEquals("5-", b.getExpresion());
        b.agregarOperador('*');
        assertEquals("5×", b.getExpresion());
    }

    @Test
    public void operadorInmediatoTrasParentesisAbiertoSeIgnora() {
        teclear("(");
        assertFalse(b.agregarOperador('+'));
        assertEquals("(", b.getExpresion());
        assertTrue(b.agregarDigito('5'));
        assertTrue(b.agregarOperador('+'));
        assertEquals("(5+", b.getExpresion());
    }

    @Test
    public void parentesisImplicitoDeMultiplicacion() {
        teclear("2");
        teclear("(");
        assertEquals("2×(", b.getExpresion());
        teclear("3");
        assertEquals("2×(3", b.getExpresion());
    }

    @Test
    public void parentesisNoSeCierraSinAbrir() {
        assertFalse(b.agregarParentesisDerecho());
    }

    @Test
    public void parentesisBalanceados() {
        teclear("(2+3)×(4+1");
        assertFalse(b.esCompleta());
        assertTrue(b.tieneParentesisAbiertos());
        b.agregarParentesisDerecho();
        assertTrue(b.esCompleta());
        assertFalse(b.tieneParentesisAbiertos());
    }

    @Test
    public void porcentajeSoloTrasNumero() {
        assertFalse(b.agregarPorcentaje());
        teclear("200+10");
        assertTrue(b.agregarPorcentaje());
        assertEquals("200+10%", b.getExpresion());
        assertFalse(b.agregarPorcentaje());
        assertEquals("200+10%", b.getExpresion());
    }

    @Test
    public void digitoTrasPorcentajeSeIgnora() {
        teclear("200+10%");
        assertFalse(b.agregarDigito('5'));
        assertEquals("200+10%", b.getExpresion());
    }

    @Test
    public void puntoConduceNumero() {
        teclear("0.5");
        assertEquals("0.5", b.getExpresion());
    }

    @Test
    public void puntoTrasOperadorAgregaCero() {
        teclear("5+");
        assertTrue(b.agregarPunto());
        assertEquals("5+0.", b.getExpresion());
        assertFalse(b.agregarPunto());
    }

    @Test
    public void puntoDobleSeEvita() {
        teclear("1.5");
        assertFalse(b.agregarPunto());
        assertEquals("1.5", b.getExpresion());
    }

    @Test
    public void cerosInicialesSeEvitan() {
        teclear("007");
        assertEquals("7", b.getExpresion());
        teclear("+0");
        assertEquals("7+0", b.getExpresion());
        assertTrue(b.agregarDigito('0'));
        assertEquals("7+0", b.getExpresion());
        assertTrue(b.agregarDigito('4'));
        assertEquals("7+4", b.getExpresion());
    }

    @Test
    public void borrar() {
        teclear("355");
        assertTrue(b.borrar());
        assertEquals("35", b.getExpresion());
        teclear("+");
        assertTrue(b.borrar());
        assertEquals("35", b.getExpresion());
        assertTrue(b.borrar());
        assertEquals("3", b.getExpresion());
    }

    @Test
    public void cambiarSignoEntrada() {
        teclear("5");
        assertTrue(b.cambiarSignoEntrada());
        assertEquals("-5", b.getExpresion());
        assertEquals("-5", b.getEntradaActual());
        assertTrue(b.cambiarSignoEntrada());
        assertEquals("5", b.getExpresion());
    }

    @Test
    public void cambiarSignoTrasOperador() {
        teclear("5+3");
        assertTrue(b.cambiarSignoEntrada());
        assertEquals("5+-3", b.getExpresion());
        assertEquals("-3", b.getEntradaActual());
    }

    @Test
    public void cambiarSignoDistingueBinario() {
        teclear("5-3");
        assertEquals("3", b.getEntradaActual());
        assertTrue(b.cambiarSignoEntrada());
        assertEquals("5--3", b.getExpresion());
    }

    @Test
    public void cambiarSignoDeCeroEsInerte() {
        teclear("5+0");
        assertTrue(b.cambiarSignoEntrada());
        assertEquals("5+0", b.getExpresion());
    }

    @Test
    public void cambiarSignoSiembraMenos() {
        teclear("5+");
        assertTrue(b.cambiarSignoEntrada());
        assertEquals("5+-", b.getExpresion());
        assertTrue(b.agregarDigito('3'));
        assertEquals("5+-3", b.getExpresion());
    }

    @Test
    public void reemplazarEntrada() {
        teclear("5+3");
        assertTrue(b.reemplazarEntrada("9"));
        assertEquals("5+9", b.getExpresion());
    }

    @Test
    public void reemplazarEntradaTrasOperador() {
        teclear("5+");
        assertTrue(b.reemplazarEntrada("7"));
        assertEquals("5+7", b.getExpresion());
    }

    @Test
    public void reemplazarEntradaTrasCierreSeRechaza() {
        teclear("(5+3)");
        assertFalse(b.reemplazarEntrada("9"));
        assertEquals("(5+3)", b.getExpresion());
    }

    @Test
    public void reemplazarEntradaConSigno() {
        teclear("5+3");
        b.cambiarSignoEntrada();
        assertEquals("5+-3", b.getExpresion());
        assertTrue(b.reemplazarEntrada("4"));
        assertEquals("5+4", b.getExpresion());
    }

    @Test
    public void expresionCompleta() {
        teclear("5+3");
        assertTrue(b.esCompleta());
        teclear("+");
        assertFalse(b.esCompleta());
        teclear("2");
        assertTrue(b.esCompleta());
    }

    @Test
    public void expresionConPorcentajeCompleta() {
        teclear("200+10%");
        assertTrue(b.esCompleta());
    }

    @Test
    public void expresionVaciaNoCompleta() {
        assertFalse(b.esCompleta());
    }

    @Test
    public void formateadaConEspacios() {
        teclear("35+20");
        assertEquals("35 + 20", b.getFormateada());
    }

    @Test
    public void formateadaConParentesis() {
        teclear("(12×4)");
        assertEquals("(12 × 4)", b.getFormateada());
    }

    @Test
    public void formateadaConPorcentaje() {
        teclear("200+10%");
        assertEquals("200 + 10%", b.getFormateada());
    }

    @Test
    public void setExpresion() {
        b.setExpresion("5+3");
        assertEquals("5+3", b.getExpresion());
        assertTrue(b.esCompleta());
        b.setExpresion("");
        assertTrue(b.esVacia());
    }

    @Test
    public void limpiar() {
        teclear("35+20");
        b.limpiar();
        assertTrue(b.esVacia());
        assertFalse(b.esCompleta());
    }
}
