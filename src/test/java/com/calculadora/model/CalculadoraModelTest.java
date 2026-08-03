package com.calculadora.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

public class CalculadoraModelTest {

    @Test
    public void memoriaSumaAcumula() {
        CalculadoraModel m = new CalculadoraModel();
        m.memoriaSuma(5);
        m.memoriaSuma(3);
        assertEquals(8.0, m.getMemoria(), 0.0);
        assertTrue(m.isHayMemoria());
    }

    @Test
    public void memoriaRestaAcumula() {
        CalculadoraModel m = new CalculadoraModel();
        m.memoriaSuma(10);
        m.memoriaResta(4);
        assertEquals(6.0, m.getMemoria(), 0.0);
    }

    @Test
    public void memoriaLimpia() {
        CalculadoraModel m = new CalculadoraModel();
        m.memoriaSuma(5);
        m.memoriaLimpia();
        assertEquals(0.0, m.getMemoria(), 0.0);
        assertFalse(m.isHayMemoria());
    }

    @Test
    public void memoriaConValoresNegativos() {
        CalculadoraModel m = new CalculadoraModel();
        m.memoriaSuma(-5);
        assertTrue(m.isHayMemoria());
        assertEquals(-5.0, m.getMemoria(), 0.0);
    }

    @Test
    public void historialAgregaEnOrden() {
        CalculadoraModel m = new CalculadoraModel();
        m.agregarHistorial("5 + 3 =");
        m.agregarHistorial("8");
        assertEquals(Arrays.asList("5 + 3 =", "8"), m.getHistorial());
    }

    @Test
    public void historialMantieneTope() {
        CalculadoraModel m = new CalculadoraModel();
        for (int i = 0; i < CalculadoraModel.MAX_HISTORIAL + 10; i++) {
            m.agregarHistorial(String.valueOf(i));
        }
        assertEquals(CalculadoraModel.MAX_HISTORIAL, m.getHistorial().size());
        assertEquals(String.valueOf(10), m.getHistorial().get(0));
    }

    @Test
    public void historialNoModificable() {
        CalculadoraModel m = new CalculadoraModel();
        m.agregarHistorial("a");
        try {
            m.getHistorial().clear();
            fail("El historial no debe ser modificable desde fuera");
        } catch (UnsupportedOperationException esperado) {
            // ok
        }
    }

    @Test
    public void limpiarHistorialVacia() {
        CalculadoraModel m = new CalculadoraModel();
        m.agregarHistorial("a");
        m.agregarHistorial("b");
        m.limpiarHistorial();
        assertTrue(m.getHistorial().isEmpty());
    }

    @Test
    public void estadoRepeticionPorDefecto() {
        CalculadoraModel m = new CalculadoraModel();
        assertNull(m.getUltimoOperador());
        assertEquals(0.0, m.getUltimoOperando(), 0.0);
        assertFalse(m.isPuedeRepetir());
        assertFalse(m.isMostrandoResultado());
        assertNull(m.getUltimoResultado());
    }
}
