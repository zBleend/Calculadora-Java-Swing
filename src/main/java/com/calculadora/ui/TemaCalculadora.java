package com.calculadora.ui;

import java.awt.Color;

/**
 * Tema de colores de la calculadora (estilo iOS), conmutable entre oscuro y claro.
 *
 * <p>Cada componente de la UI tiene una propiedad "tema" (client property) que
 * identifica su rol; {@link Paleta#colorFondo(String)} y
 * {@link Paleta#colorTexto(String)} resuelven el color segun la paleta activa.</p>
 */
public final class TemaCalculadora {

    private TemaCalculadora() {
    }

    public static final String ROL_FONDO = "fondo";
    public static final String ROL_SUPERFICIE = "superficie";
    public static final String ROL_HISTORIAL = "historial";
    public static final String ROL_NUMERO = "numero";
    public static final String ROL_FUNCION = "funcion";
    public static final String ROL_OPERADOR = "operador";
    public static final String ROL_MEMORIA = "memoria";
    public static final String ROL_EXPRESION = "expresion";
    public static final String ROL_RESULTADO = "resultado";
    public static final String ROL_ERROR = "error";
    public static final String ROL_MEMORIA_INDICADOR = "memoriaIndicador";
    public static final String ROL_TITULO = "titulo";
    public static final String ROL_SELECCION = "seleccion";

    public static final Paleta OSCURO = new Paleta(
            true,
            new Color(0x000000), new Color(0x1C1C1E), new Color(0x111113),
            new Color(0x333333), new Color(0xA5A5A5), new Color(0xFF9F0A), new Color(0x2C2C2E),
            Color.WHITE, new Color(0x000000), Color.WHITE, Color.WHITE,
            new Color(0x8E8E93), Color.WHITE, new Color(0xFF453A),
            new Color(0xD1D1D6), new Color(0x3A3A3C));

    public static final Paleta CLARO = new Paleta(
            false,
            new Color(0xFFFFFF), new Color(0xF2F2F7), new Color(0xECECEF),
            new Color(0xE5E5EA), new Color(0xD1D1D6), new Color(0xFF9F0A), new Color(0xE5E5EA),
            new Color(0x000000), new Color(0x000000), Color.WHITE, new Color(0x000000),
            new Color(0x6E6E73), new Color(0x000000), new Color(0xFF3B30),
            new Color(0x3A3A3C), new Color(0xC7C7CC));

    private static Paleta actual = OSCURO;

    public static Paleta actual() {
        return actual;
    }

    public static void aplicar(boolean oscuro) {
        actual = oscuro ? OSCURO : CLARO;
    }

    public static final class Paleta {

        private final boolean oscuro;
        private final Color fondo;
        private final Color fondoSuperficie;
        private final Color fondoHistorial;
        private final Color teclaNumero;
        private final Color teclaFuncion;
        private final Color teclaOperador;
        private final Color teclaMemoria;
        private final Color textoNumero;
        private final Color textoFuncion;
        private final Color textoOperador;
        private final Color textoMemoria;
        private final Color textoExpresion;
        private final Color textoResultado;
        private final Color textoError;
        private final Color textoHistorial;
        private final Color seleccionHistorial;

        Paleta(boolean oscuro, Color fondo, Color fondoSuperficie, Color fondoHistorial,
                Color teclaNumero, Color teclaFuncion, Color teclaOperador, Color teclaMemoria,
                Color textoNumero, Color textoFuncion, Color textoOperador, Color textoMemoria,
                Color textoExpresion, Color textoResultado, Color textoError,
                Color textoHistorial, Color seleccionHistorial) {
            this.oscuro = oscuro;
            this.fondo = fondo;
            this.fondoSuperficie = fondoSuperficie;
            this.fondoHistorial = fondoHistorial;
            this.teclaNumero = teclaNumero;
            this.teclaFuncion = teclaFuncion;
            this.teclaOperador = teclaOperador;
            this.teclaMemoria = teclaMemoria;
            this.textoNumero = textoNumero;
            this.textoFuncion = textoFuncion;
            this.textoOperador = textoOperador;
            this.textoMemoria = textoMemoria;
            this.textoExpresion = textoExpresion;
            this.textoResultado = textoResultado;
            this.textoError = textoError;
            this.textoHistorial = textoHistorial;
            this.seleccionHistorial = seleccionHistorial;
        }

        public boolean esOscuro() {
            return oscuro;
        }

        public Color colorFondo(String rol) {
            switch (rol) {
                case ROL_FONDO: return fondo;
                case ROL_SUPERFICIE: return fondoSuperficie;
                case ROL_HISTORIAL: return fondoHistorial;
                case ROL_NUMERO: return teclaNumero;
                case ROL_FUNCION: return teclaFuncion;
                case ROL_OPERADOR: return teclaOperador;
                case ROL_MEMORIA: return teclaMemoria;
                default: return null;
            }
        }

        public Color colorTexto(String rol) {
            switch (rol) {
                case ROL_NUMERO: return textoNumero;
                case ROL_FUNCION: return textoFuncion;
                case ROL_OPERADOR: return textoOperador;
                case ROL_MEMORIA:
                case ROL_MEMORIA_INDICADOR: return textoMemoria;
                case ROL_EXPRESION: return textoExpresion;
                case ROL_RESULTADO: return textoResultado;
                case ROL_ERROR: return textoError;
                case ROL_TITULO:
                case ROL_HISTORIAL: return textoHistorial;
                case ROL_SELECCION: return seleccionHistorial;
                default: return null;
            }
        }
    }
}
