package com.calculadora.ui;

import com.calculadora.model.CalculadoraModel;
import com.calculadora.service.CalculadoraService;
import com.calculadora.service.ExpresionBuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.DoubleUnaryOperator;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Calculadora orientada a expresiones.
 *
 * <p>Display de dos lineas: expresion en vivo arriba y resultado abajo.
 * Grid 4x6 (C ± % ÷ / 7 8 9 × / 4 5 6 - / 1 2 3 + / 0 . ( ) / √x x² 1/x =),
 * fila de memoria (MC MR M+ M-) con indicador M, historial lateral, tema
 * claro/oscuro persistente y soporte de teclado. La estructura (campos +
 * initComponents) es editable desde WindowBuilder.</p>
 */
public class CalculadoraUI extends JFrame {

    private static final Font FUENTE_NUMEROS = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FUENTE_FUNCIONES = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_OPERADORES = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FUENTE_MEMORIA = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FUENTE_EXPRESION = new Font("Segoe UI", Font.PLAIN, 18);
    private static final Font FUENTE_RESULTADO = new Font("Segoe UI", Font.PLAIN, 40);

    private static final Dimension TAMANO_TECLA = new Dimension(58, 46);
    private static final Dimension TAMANO_MEMORIA = new Dimension(52, 32);
    private static final Dimension TAMANO_SUPERIOR = new Dimension(52, 32);

    private static final Preferences PREFERENCIAS =
            Preferences.userNodeForPackage(CalculadoraUI.class);
    private static final String CLAVE_TEMA = "temaOscuro";

    private final CalculadoraModel model = new CalculadoraModel();
    private final CalculadoraService service = new CalculadoraService();
    private final ExpresionBuilder builder = new ExpresionBuilder();
    private final DefaultListModel<String> modeloHistorial = new DefaultListModel<>();

    // Componentes (campos para WindowBuilder)
    private JLabel etiquetaExpresion;
    private JLabel etiquetaResultado;
    private JLabel etiquetaMemoria;
    private JButton botonTema;
    private JButton botonBorrar;
    private JButton bLimpiar;
    private JButton bSigno;
    private JButton bPorcentaje;
    private JButton bDividir;
    private JButton bSiete;
    private JButton bOcho;
    private JButton bNueve;
    private JButton bMultiplicar;
    private JButton bCuatro;
    private JButton bCinco;
    private JButton bSeis;
    private JButton bResta;
    private JButton bUno;
    private JButton bDos;
    private JButton bTres;
    private JButton bSuma;
    private JButton bCero;
    private JButton bPunto;
    private JButton bParenIzq;
    private JButton bParenDer;
    private JButton bRaiz;
    private JButton bCuadrado;
    private JButton bInverso;
    private JButton bIgual;
    private JButton bMC;
    private JButton bMR;
    private JButton bMmas;
    private JButton bMmenos;
    private JButton bLimpiarHistorial;
    private JList<String> listaHistorial;

    public CalculadoraUI() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Calculadora");
        getContentPane().setBackground(TemaCalculadora.actual().colorFondo(TemaCalculadora.ROL_FONDO));
        getContentPane().setLayout(new BorderLayout(0, 0));

        // ---------------- Display ----------------
        JPanel display = crearPanel(TemaCalculadora.ROL_FONDO, new BorderLayout(0, 0));

        JPanel barraSuperior = crearPanel(TemaCalculadora.ROL_FONDO, new FlowLayout(FlowLayout.RIGHT, 6, 4));
        botonTema = crearBoton("☀", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_SUPERIOR, e -> cambiarTema());
        botonBorrar = crearBoton("⌫", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_SUPERIOR, e -> pulsarBorrar());
        barraSuperior.add(botonTema);
        barraSuperior.add(botonBorrar);
        display.add(barraSuperior, BorderLayout.NORTH);

        JPanel numeros = new JPanel();
        numeros.setLayout(new javax.swing.BoxLayout(numeros, javax.swing.BoxLayout.Y_AXIS));
        numeros.putClientProperty("tema", TemaCalculadora.ROL_FONDO);
        etiquetaExpresion = new JLabel(" ", SwingConstants.RIGHT);
        etiquetaExpresion.setAlignmentX(Component.LEFT_ALIGNMENT);
        etiquetaExpresion.setFont(FUENTE_EXPRESION);
        etiquetaExpresion.setPreferredSize(new Dimension(0, 24));
        etiquetaExpresion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        etiquetaExpresion.putClientProperty("tema", TemaCalculadora.ROL_EXPRESION);
        etiquetaResultado = new JLabel("0", SwingConstants.RIGHT);
        etiquetaResultado.setAlignmentX(Component.LEFT_ALIGNMENT);
        etiquetaResultado.setFont(FUENTE_RESULTADO);
        etiquetaResultado.setPreferredSize(new Dimension(0, 60));
        etiquetaResultado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        etiquetaResultado.putClientProperty("tema", TemaCalculadora.ROL_RESULTADO);
        numeros.add(etiquetaExpresion);
        numeros.add(etiquetaResultado);
        display.add(numeros, BorderLayout.CENTER);

        // ---------------- Botonera ----------------
        JPanel botonera = crearPanel(TemaCalculadora.ROL_FONDO, null);
        botonera.setLayout(new javax.swing.BoxLayout(botonera, javax.swing.BoxLayout.Y_AXIS));

        JPanel memoria = crearPanel(TemaCalculadora.ROL_FONDO, new GridLayout(1, 5, 8, 0));
        etiquetaMemoria = new JLabel(" ", SwingConstants.CENTER);
        etiquetaMemoria.setFont(FUENTE_MEMORIA);
        etiquetaMemoria.putClientProperty("tema", TemaCalculadora.ROL_MEMORIA_INDICADOR);
        bMC = crearBoton("MC", TemaCalculadora.ROL_MEMORIA, FUENTE_MEMORIA,
                TAMANO_MEMORIA, e -> memoriaLimpia());
        bMR = crearBoton("MR", TemaCalculadora.ROL_MEMORIA, FUENTE_MEMORIA,
                TAMANO_MEMORIA, e -> memoriaRecupera());
        bMmas = crearBoton("M+", TemaCalculadora.ROL_MEMORIA, FUENTE_MEMORIA,
                TAMANO_MEMORIA, e -> memoriaSuma());
        bMmenos = crearBoton("M-", TemaCalculadora.ROL_MEMORIA, FUENTE_MEMORIA,
                TAMANO_MEMORIA, e -> memoriaResta());
        memoria.add(etiquetaMemoria);
        memoria.add(bMC);
        memoria.add(bMR);
        memoria.add(bMmas);
        memoria.add(bMmenos);

        JPanel teclas = crearPanel(TemaCalculadora.ROL_FONDO, new GridLayout(6, 4, 10, 10));
        bLimpiar = crearBoton("C", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarLimpiar());
        bSigno = crearBoton("±", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarSigno());
        bPorcentaje = crearBoton("%", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarPorcentaje());
        bDividir = crearBoton("÷", TemaCalculadora.ROL_OPERADOR,
                FUENTE_OPERADORES, TAMANO_TECLA, e -> pulsarOperador('÷'));
        bSiete = crearBoton("7", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('7'));
        bOcho = crearBoton("8", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('8'));
        bNueve = crearBoton("9", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('9'));
        bMultiplicar = crearBoton("×", TemaCalculadora.ROL_OPERADOR,
                FUENTE_OPERADORES, TAMANO_TECLA, e -> pulsarOperador('×'));
        bCuatro = crearBoton("4", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('4'));
        bCinco = crearBoton("5", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('5'));
        bSeis = crearBoton("6", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('6'));
        bResta = crearBoton("-", TemaCalculadora.ROL_OPERADOR,
                FUENTE_OPERADORES, TAMANO_TECLA, e -> pulsarOperador('-'));
        bUno = crearBoton("1", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('1'));
        bDos = crearBoton("2", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('2'));
        bTres = crearBoton("3", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('3'));
        bSuma = crearBoton("+", TemaCalculadora.ROL_OPERADOR,
                FUENTE_OPERADORES, TAMANO_TECLA, e -> pulsarOperador('+'));
        bCero = crearBoton("0", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarDigito('0'));
        bPunto = crearBoton(".", TemaCalculadora.ROL_NUMERO,
                FUENTE_NUMEROS, TAMANO_TECLA, e -> pulsarPunto());
        bParenIzq = crearBoton("(", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarParentesis('('));
        bParenDer = crearBoton(")", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarParentesis(')'));
        bRaiz = crearBoton("√x", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarRaiz());
        bCuadrado = crearBoton("x²", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarCuadrado());
        bInverso = crearBoton("1/x", TemaCalculadora.ROL_FUNCION,
                FUENTE_FUNCIONES, TAMANO_TECLA, e -> pulsarInverso());
        bIgual = crearBoton("=", TemaCalculadora.ROL_OPERADOR,
                FUENTE_OPERADORES, TAMANO_TECLA, e -> pulsarIgual());

        teclas.add(bLimpiar);
        teclas.add(bSigno);
        teclas.add(bPorcentaje);
        teclas.add(bDividir);
        teclas.add(bSiete);
        teclas.add(bOcho);
        teclas.add(bNueve);
        teclas.add(bMultiplicar);
        teclas.add(bCuatro);
        teclas.add(bCinco);
        teclas.add(bSeis);
        teclas.add(bResta);
        teclas.add(bUno);
        teclas.add(bDos);
        teclas.add(bTres);
        teclas.add(bSuma);
        teclas.add(bCero);
        teclas.add(bPunto);
        teclas.add(bParenIzq);
        teclas.add(bParenDer);
        teclas.add(bRaiz);
        teclas.add(bCuadrado);
        teclas.add(bInverso);
        teclas.add(bIgual);

        botonera.add(memoria);
        botonera.add(javax.swing.Box.createVerticalStrut(10));
        botonera.add(teclas);

        JPanel contenido = crearPanel(TemaCalculadora.ROL_FONDO, new BorderLayout(0, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(4, 14, 14, 14));
        contenido.add(display, BorderLayout.NORTH);
        contenido.add(botonera, BorderLayout.CENTER);

        // ---------------- Historial ----------------
        JPanel historial = crearPanel(TemaCalculadora.ROL_SUPERFICIE, new BorderLayout(0, 6));
        historial.setPreferredSize(new Dimension(190, 0));

        JPanel cabeceraHistorial = crearPanel(TemaCalculadora.ROL_SUPERFICIE, new BorderLayout(8, 0));
        cabeceraHistorial.setBorder(BorderFactory.createEmptyBorder(10, 10, 4, 10));
        JLabel tituloHistorial = new JLabel("Historial");
        tituloHistorial.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tituloHistorial.putClientProperty("tema", TemaCalculadora.ROL_TITULO);
        bLimpiarHistorial = new JButton("Borrar");
        bLimpiarHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bLimpiarHistorial.putClientProperty("tema", TemaCalculadora.ROL_MEMORIA);
        bLimpiarHistorial.setBorderPainted(false);
        bLimpiarHistorial.setFocusPainted(false);
        bLimpiarHistorial.setFocusable(false);
        bLimpiarHistorial.addActionListener(e -> {
            model.limpiarHistorial();
            recargarHistorial();
        });
        cabeceraHistorial.add(tituloHistorial, BorderLayout.WEST);
        cabeceraHistorial.add(bLimpiarHistorial, BorderLayout.EAST);

        listaHistorial = new JList<>(modeloHistorial);
        listaHistorial.putClientProperty("tema", TemaCalculadora.ROL_HISTORIAL);
        listaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    recordarHistorial(listaHistorial.getSelectedIndex());
                }
            }
        });

        JScrollPane scrollHistorial = new JScrollPane(listaHistorial);
        scrollHistorial.setBorder(BorderFactory.createEmptyBorder());
        scrollHistorial.getViewport().putClientProperty("tema", TemaCalculadora.ROL_HISTORIAL);

        historial.add(cabeceraHistorial, BorderLayout.NORTH);
        historial.add(scrollHistorial, BorderLayout.CENTER);

        getContentPane().add(contenido, BorderLayout.CENTER);
        getContentPane().add(historial, BorderLayout.EAST);

        pack();
        setMinimumSize(getSize());
        configurarTeclado();
        aplicarColoresTema();
        actualizarIndicadorMemoria();
    }

    private JPanel crearPanel(String rol, java.awt.LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.putClientProperty("tema", rol);
        return panel;
    }

    private JButton crearBoton(String texto, String rol, Font fuente, Dimension tamano,
            ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setFocusable(false);
        boton.putClientProperty("tema", rol);
        if (tamano != null) {
            boton.setPreferredSize(tamano);
        }
        boton.addActionListener(accion);
        return boton;
    }

    // ---------------- Tema ----------------

    private void cambiarTema() {
        aplicarTema(!TemaCalculadora.actual().esOscuro());
    }

    private void aplicarTema(boolean oscuro) {
        TemaCalculadora.aplicar(oscuro);
        PREFERENCIAS.putBoolean(CLAVE_TEMA, oscuro);
        aplicarColoresTema();
    }

    private void aplicarColoresTema() {
        TemaCalculadora.Paleta paleta = TemaCalculadora.actual();
        botonTema.setText(paleta.esOscuro() ? "☀" : "☾");
        aplicarTemaRecursivo(getContentPane(), paleta);
        actualizarDisplay();
        getContentPane().repaint();
    }

    private void aplicarTemaRecursivo(Component componente, TemaCalculadora.Paleta paleta) {
        Object rolObj = null;
        if (componente instanceof JComponent) {
            rolObj = ((JComponent) componente).getClientProperty("tema");
        }
        if (rolObj != null) {
            aplicarRol(componente, rolObj.toString(), paleta);
        }
        if (componente instanceof Container) {
            for (Component hijo : ((Container) componente).getComponents()) {
                aplicarTemaRecursivo(hijo, paleta);
            }
        }
    }

    private void aplicarRol(Component componente, String rol, TemaCalculadora.Paleta paleta) {
        if (componente instanceof JButton) {
            Color fondo = paleta.colorFondo(rol);
            Color texto = paleta.colorTexto(rol);
            if (fondo != null) {
                componente.setBackground(fondo);
            }
            if (texto != null) {
                componente.setForeground(texto);
            }
        } else if (componente instanceof JList) {
            Color fondo = paleta.colorFondo(rol);
            Color texto = paleta.colorTexto(rol);
            Color seleccion = paleta.colorTexto(TemaCalculadora.ROL_SELECCION);
            if (fondo != null) {
                componente.setBackground(fondo);
            }
            if (texto != null) {
                componente.setForeground(texto);
            }
            if (seleccion != null) {
                ((JList<?>) componente).setSelectionBackground(seleccion);
            }
        } else if (componente instanceof JLabel) {
            Color texto = paleta.colorTexto(rol);
            if (texto != null) {
                componente.setForeground(texto);
            }
        } else {
            Color fondo = paleta.colorFondo(rol);
            if (fondo != null) {
                componente.setBackground(fondo);
            }
        }
    }

    // ---------------- Entrada ----------------

    private void pulsarDigito(char digito) {
        if (model.isMostrandoResultado()) {
            builder.limpiar();
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        builder.agregarDigito(digito);
        actualizarDisplay();
    }

    private void pulsarPunto() {
        if (model.isMostrandoResultado()) {
            builder.limpiar();
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        builder.agregarPunto();
        actualizarDisplay();
    }

    private void pulsarOperador(char operador) {
        if (model.isMostrandoResultado()) {
            if ("Error".equals(model.getUltimoResultado())) {
                model.setMostrandoResultado(false);
                model.setPuedeRepetir(false);
                actualizarDisplay();
                return;
            }
            builder.limpiar();
            builder.reemplazarEntrada(model.getUltimoResultado());
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        builder.agregarOperador(operador);
        actualizarDisplay();
    }

    private void pulsarParentesis(char parentesis) {
        if (model.isMostrandoResultado()) {
            if ("Error".equals(model.getUltimoResultado())) {
                return;
            }
            builder.limpiar();
            builder.reemplazarEntrada(model.getUltimoResultado());
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        if (parentesis == '(') {
            builder.agregarParentesisIzquierdo();
        } else {
            builder.agregarParentesisDerecho();
        }
        actualizarDisplay();
    }

    private void pulsarPorcentaje() {
        if (model.isMostrandoResultado()) {
            if ("Error".equals(model.getUltimoResultado())) {
                return;
            }
            builder.limpiar();
            builder.reemplazarEntrada(model.getUltimoResultado());
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        builder.agregarPorcentaje();
        actualizarDisplay();
    }

    private void pulsarSigno() {
        if (model.isMostrandoResultado()) {
            if ("Error".equals(model.getUltimoResultado())) {
                return;
            }
            builder.limpiar();
            builder.reemplazarEntrada(model.getUltimoResultado());
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        builder.cambiarSignoEntrada();
        actualizarDisplay();
    }

    private void pulsarBorrar() {
        if (model.isMostrandoResultado()) {
            if ("Error".equals(model.getUltimoResultado())) {
                model.setMostrandoResultado(false);
                model.setPuedeRepetir(false);
                model.setUltimoResultado(null);
            } else {
                builder.limpiar();
                builder.reemplazarEntrada(model.getUltimoResultado());
                model.setMostrandoResultado(false);
                model.setPuedeRepetir(false);
            }
        } else {
            builder.borrar();
        }
        actualizarDisplay();
    }

    private void pulsarLimpiar() {
        builder.limpiar();
        model.setMostrandoResultado(false);
        model.setPuedeRepetir(false);
        model.setUltimoOperador(null);
        model.setUltimoResultado(null);
        actualizarDisplay();
    }

    private void pulsarRaiz() {
        aplicarUnaria(service::calcularRaizCuadrada);
    }

    private void pulsarCuadrado() {
        aplicarUnaria(service::calcularCuadrado);
    }

    private void pulsarInverso() {
        aplicarUnaria(service::calcularInverso);
    }

    private void aplicarUnaria(DoubleUnaryOperator operacion) {
        String valor = model.isMostrandoResultado()
                ? model.getUltimoResultado() : builder.getEntradaActual();
        if (valor == null || valor.isEmpty() || "Error".equals(valor)) {
            return;
        }
        try {
            double resultado = operacion.applyAsDouble(Double.parseDouble(valor));
            String texto = formatearResultado(resultado);
            if (model.isMostrandoResultado()) {
                builder.limpiar();
                builder.reemplazarEntrada(texto);
                model.setMostrandoResultado(false);
                model.setPuedeRepetir(false);
            } else {
                builder.reemplazarEntrada(texto);
            }
            actualizarDisplay();
        } catch (ArithmeticException e) {
            mostrarError();
        }
    }

    private void pulsarIgual() {
        if (builder.esVacia() && !model.isMostrandoResultado()) {
            return;
        }
        if (model.isMostrandoResultado() && model.isPuedeRepetir() && model.getUltimoOperador() != null) {
            builder.limpiar();
            builder.reemplazarEntrada(model.getUltimoResultado());
            builder.agregarOperador(model.getUltimoOperador().charAt(0));
            builder.reemplazarEntrada(formatearResultado(model.getUltimoOperando()));
        }
        if (!builder.esCompleta()) {
            mostrarError();
            return;
        }
        try {
            String expresion = builder.getExpresion();
            double resultado = service.evaluar(expresion);
            String res = formatearResultado(resultado);
            model.agregarHistorial(builder.getFormateada() + " =");
            model.agregarHistorial(res);
            model.setPuedeRepetir(guardarRepeticion(expresion));
            model.setUltimoResultado(res);
            model.setMostrandoResultado(true);
            recargarHistorial();
            actualizarDisplay();
        } catch (ArithmeticException | IllegalArgumentException e) {
            mostrarError();
        }
    }

    private void mostrarError() {
        builder.limpiar();
        model.setMostrandoResultado(true);
        model.setPuedeRepetir(false);
        model.setUltimoOperador(null);
        model.setUltimoResultado("Error");
        actualizarDisplay();
    }

    // ---------------- Memoria ----------------

    private void memoriaSuma() {
        model.memoriaSuma(valorActual());
        actualizarIndicadorMemoria();
    }

    private void memoriaResta() {
        model.memoriaResta(valorActual());
        actualizarIndicadorMemoria();
    }

    private void memoriaRecupera() {
        if (!model.isHayMemoria()) {
            return;
        }
        if (model.isMostrandoResultado() && "Error".equals(model.getUltimoResultado())) {
            return;
        }
        String texto = formatearResultado(model.getMemoria());
        if (model.isMostrandoResultado()) {
            builder.limpiar();
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
        }
        builder.reemplazarEntrada(texto);
        actualizarDisplay();
    }

    private void memoriaLimpia() {
        model.memoriaLimpia();
        actualizarIndicadorMemoria();
    }

    private double valorActual() {
        if (model.isMostrandoResultado()) {
            String res = model.getUltimoResultado();
            if ("Error".equals(res)) {
                return 0;
            }
            return Double.parseDouble(res);
        }
        String entrada = builder.getEntradaActual();
        if (entrada.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(entrada);
    }

    private void actualizarIndicadorMemoria() {
        etiquetaMemoria.setText(model.isHayMemoria() ? "M" : " ");
    }

    // ---------------- Historial ----------------

    private void recargarHistorial() {
        modeloHistorial.clear();
        for (String entrada : model.getHistorial()) {
            modeloHistorial.addElement(entrada);
        }
    }

    private void recordarHistorial(int indice) {
        if (indice < 0 || indice >= modeloHistorial.size()) {
            return;
        }
        String entrada = modeloHistorial.get(indice);
        String canonica = entrada.replace(" ", "");
        if (esEntradaExpresion(entrada)) {
            builder.setExpresion(canonica.replace("=", ""));
            model.setMostrandoResultado(false);
            model.setPuedeRepetir(false);
            pulsarIgual();
        } else {
            builder.setExpresion(canonica);
            model.setMostrandoResultado(true);
            model.setPuedeRepetir(false);
            model.setUltimoResultado(canonica);
            actualizarDisplay();
        }
    }

    private boolean esEntradaExpresion(String entrada) {
        for (char c : entrada.toCharArray()) {
            if (c == '+' || c == '-' || c == '×' || c == '÷' || c == '(' || c == ')') {
                return true;
            }
        }
        return false;
    }

    // ---------------- Repeticion de "=" ----------------

    private boolean guardarRepeticion(String expresion) {
        int ultimoOp = ultimoOperadorBinario(expresion);
        if (ultimoOp < 0) {
            return false;
        }
        String derecho = expresion.substring(ultimoOp + 1);
        if (derecho.isEmpty() || derecho.indexOf('%') >= 0) {
            return false;
        }
        if (ultimoOperadorBinario(derecho) >= 0) {
            return false;
        }
        try {
            model.setUltimoOperador(String.valueOf(expresion.charAt(ultimoOp)));
            model.setUltimoOperando(service.evaluar(derecho));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int ultimoOperadorBinario(String expresion) {
        int nivel = 0;
        int ultimo = -1;
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (c == '(') {
                nivel++;
            } else if (c == ')') {
                nivel--;
            } else if (nivel == 0 && esOperadorBinario(expresion, i)) {
                ultimo = i;
            }
        }
        return ultimo;
    }

    private boolean esOperadorBinario(String expresion, int indice) {
        char c = expresion.charAt(indice);
        if (c != '+' && c != '-' && c != '×' && c != '÷') {
            return false;
        }
        if (indice == 0) {
            return false;
        }
        char anterior = expresion.charAt(indice - 1);
        return anterior != '(' && !esOperador(anterior);
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷';
    }

    // ---------------- Display ----------------

    private void actualizarDisplay() {
        String expresion = builder.getFormateada();
        if (model.isMostrandoResultado()) {
            etiquetaExpresion.setText(expresion.isEmpty() ? " " : expresion + " =");
            etiquetaResultado.setText(model.getUltimoResultado());
        } else {
            etiquetaExpresion.setText(expresion.isEmpty() ? " " : expresion);
            String entrada = builder.getEntradaActual();
            etiquetaResultado.setText(entrada.isEmpty() ? "0" : entrada);
        }
        etiquetaResultado.setForeground("Error".equals(etiquetaResultado.getText())
                ? TemaCalculadora.actual().colorTexto(TemaCalculadora.ROL_ERROR)
                : TemaCalculadora.actual().colorTexto(TemaCalculadora.ROL_RESULTADO));
    }

    private String formatearResultado(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            return "Error";
        }
        if (valor == Math.rint(valor) && valor >= Long.MIN_VALUE && valor <= Long.MAX_VALUE) {
            return String.format("%d", (long) valor);
        }
        return String.valueOf(valor);
    }

    // ---------------- Teclado ----------------

    private void configurarTeclado() {
        InputMap mapaEntrada = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap mapaAcciones = getRootPane().getActionMap();

        for (char c = '0'; c <= '9'; c++) {
            final char digito = c;
            vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke(c), e -> pulsarDigito(digito));
        }
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('.'), e -> pulsarPunto());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke(','), e -> pulsarPunto());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('+'), e -> pulsarOperador('+'));
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('-'), e -> pulsarOperador('-'));
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('*'), e -> pulsarOperador('*'));
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('/'), e -> pulsarOperador('/'));
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('('), e -> pulsarParentesis('('));
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke(')'), e -> pulsarParentesis(')'));
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('%'), e -> pulsarPorcentaje());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('^'), e -> pulsarCuadrado());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('r'), e -> pulsarRaiz());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('R'), e -> pulsarRaiz());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('i'), e -> pulsarInverso());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('I'), e -> pulsarInverso());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('n'), e -> pulsarSigno());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('N'), e -> pulsarSigno());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke("ENTER"), e -> pulsarIgual());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke('='), e -> pulsarIgual());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke("BACK_SPACE"), e -> pulsarBorrar());
        vincular(mapaEntrada, mapaAcciones, KeyStroke.getKeyStroke("ESCAPE"), e -> pulsarLimpiar());
    }

    private void vincular(InputMap mapaEntrada, ActionMap mapaAcciones, KeyStroke tecla,
            ActionListener accion) {
        String clave = "accion_" + tecla.toString();
        mapaEntrada.put(tecla, clave);
        mapaAcciones.put(clave, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                accion.actionPerformed(evt);
            }
        });
    }

    // ---------------- Arranque ----------------

    public static void main(String[] args) {
        boolean oscuro = PREFERENCIAS.getBoolean(CLAVE_TEMA, true);
        TemaCalculadora.aplicar(oscuro);
        if (oscuro) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
        UIManager.put("Button.arc", 18);
        java.awt.EventQueue.invokeLater(() -> new CalculadoraUI().setVisible(true));
    }
}

