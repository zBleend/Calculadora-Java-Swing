package com.calculadora.service;

/**
 * Construye y valida en vivo la expresion que se muestra en pantalla.
 *
 * <p>Reglas de transicion:
 * <ul>
 *   <li>Un digito solo se agrega tras el inicio, un operador o un '(' (nunca tras ')' o '%').</li>
 *   <li>Un operador consecutivo reemplaza al anterior; no puede iniciar la expresion ni ir tras '('. </li>
 *   <li>El '.' agrega "0." si no hay numero en curso y evita dobles puntos.</li>
 *   <li>Un '(' tras un numero agrega '×' implicito.</li>
 *   <li>El ')' requiere un parentesis abierto y terminar en numero/')'/'%'.</li>
 *   <li>El '%' solo tras un numero; se admite uno por numero.</li>
 *   <li>El '±' conmuta el signo del numero en curso (el '-' unario se distingue del binario).</li>
 *   <li>reemplazarEntrada sustituye el numero en curso (usado por unarias y MR).</li>
 * </ul>
 * </p>
 */
public final class ExpresionBuilder {

    private final StringBuilder expresion = new StringBuilder();

    public void limpiar() {
        expresion.setLength(0);
    }

    public boolean esVacia() {
        return expresion.length() == 0;
    }

    public String getExpresion() {
        return expresion.toString();
    }

    /**
     * Expresion con espacios alrededor de los operadores, para mostrar en el display
     * sin espacios internos entre '(' y ')'.
     */
    public String getFormateada() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (esOperador(c)) {
                sb.append(' ').append(c).append(' ');
            } else {
                sb.append(c);
            }
        }
        String s = sb.toString().replace("( ", "(").replace(" )", ")").trim();
        return s.replaceAll("\\s+", " ");
    }

    /**
     * Numero en curso editable (con su signo unario si aplica), o "" si no hay
     * ninguno (tras operador, '(', ')' o '%').
     */
    public String getEntradaActual() {
        int s = inicioEntrada();
        return s < 0 ? "" : expresion.substring(s);
    }

    /** La expresion es evaluable: no vacia, balanceada y termina en numero/')'/'%'. */
    public boolean esCompleta() {
        int len = expresion.length();
        if (len == 0 || balanceParentesis() != 0) {
            return false;
        }
        char ult = expresion.charAt(len - 1);
        return Character.isDigit(ult) || ult == '.' || ult == ')' || ult == '%';
    }

    public boolean tieneParentesisAbiertos() {
        return balanceParentesis() > 0;
    }

    public boolean agregarDigito(char d) {
        if (!Character.isDigit(d)) {
            return false;
        }
        int s = inicioEntrada();
        if (s < 0) {
            char ult = ultimoChar();
            if (ult == 0 || esOperador(ult) || ult == '(') {
                expresion.append(d);
                return true;
            }
            return false;
        }
        String entrada = expresion.substring(s);
        if (entrada.equals("0") || entrada.equals("-0")) {
            if (d == '0') {
                return true;
            }
            expresion.setLength(expresion.length() - 1);
            expresion.append(d);
        } else {
            expresion.append(d);
        }
        return true;
    }

    public boolean agregarPunto() {
        int s = inicioEntrada();
        if (s < 0) {
            char ult = ultimoChar();
            if (ult == 0 || esOperador(ult) || ult == '(') {
                expresion.append("0.");
                return true;
            }
            return false;
        }
        String entrada = expresion.substring(s);
        if (entrada.contains(".")) {
            return false;
        }
        expresion.append('.');
        return true;
    }

    public boolean agregarOperador(char op) {
        char o = op;
        if (o == '*') {
            o = '×';
        } else if (o == '/') {
            o = '÷';
        }
        if (!esOperador(o)) {
            return false;
        }
        int len = expresion.length();
        if (len == 0) {
            return false;
        }
        char ult = expresion.charAt(len - 1);
        if (esOperador(ult)) {
            expresion.setCharAt(len - 1, o);
            return true;
        }
        if (ult == '(') {
            return false;
        }
        expresion.append(o);
        return true;
    }

    public boolean agregarParentesisIzquierdo() {
        int len = expresion.length();
        if (len == 0) {
            expresion.append('(');
            return true;
        }
        char ult = expresion.charAt(len - 1);
        if (esOperador(ult) || ult == '(') {
            expresion.append('(');
            return true;
        }
        if (Character.isDigit(ult) || ult == '.' || ult == ')' || ult == '%') {
            expresion.append('×').append('(');
            return true;
        }
        return false;
    }

    public boolean agregarParentesisDerecho() {
        if (balanceParentesis() <= 0) {
            return false;
        }
        char ult = ultimoChar();
        if (Character.isDigit(ult) || ult == '.' || ult == ')' || ult == '%') {
            expresion.append(')');
            return true;
        }
        return false;
    }

    public boolean agregarPorcentaje() {
        int s = inicioEntrada();
        if (s < 0) {
            return false;
        }
        if (expresion.charAt(expresion.length() - 1) == '.') {
            return false;
        }
        expresion.append('%');
        return true;
    }

    public boolean borrar() {
        if (expresion.length() == 0) {
            return false;
        }
        expresion.setLength(expresion.length() - 1);
        return true;
    }

    /** Conmuta el signo del numero en curso; si no hay numero, siembra un '-' tras operador/'(' o al inicio. */
    public boolean cambiarSignoEntrada() {
        int s = inicioEntrada();
        if (s < 0) {
            char ult = ultimoChar();
            if (ult == 0 || esOperador(ult) || ult == '(') {
                expresion.append('-');
                return true;
            }
            return false;
        }
        String entrada = expresion.substring(s);
        String sinSigno = entrada.startsWith("-") ? entrada.substring(1) : entrada;
        if (sinSigno.equals("0") || sinSigno.equals("0.")) {
            return true;
        }
        if (expresion.charAt(s) == '-') {
            expresion.deleteCharAt(s);
        } else {
            expresion.insert(s, '-');
        }
        return true;
    }

    /** Sustituye el numero en curso por otro texto (unarias, MR, etc.). */
    public boolean reemplazarEntrada(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        int s = inicioEntrada();
        if (s < 0) {
            char ult = ultimoChar();
            if (ult == 0 || esOperador(ult) || ult == '(') {
                expresion.append(texto);
                return true;
            }
            return false;
        }
        expresion.replace(s, expresion.length(), texto);
        return true;
    }

    /** Carga una expresion canonica existente (uso: recordar historial o continuar desde un resultado). */
    public void setExpresion(String expresion) {
        this.expresion.setLength(0);
        this.expresion.append(expresion == null ? "" : expresion);
    }

    private int balanceParentesis() {
        int balance = 0;
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
        }
        return balance;
    }

    /**
     * Indice de inicio del numero en curso editable, o -1 si no existe.
     * Incluye el '-' unario cuando precede a un numero y esta precedido por un
     * operador, '(' o el inicio de la expresion (asi "5-3" trata el '-' como binario).
     */
    private int inicioEntrada() {
        int i = expresion.length() - 1;
        if (i < 0) {
            return -1;
        }
        if (!Character.isDigit(expresion.charAt(i)) && expresion.charAt(i) != '.') {
            return -1;
        }
        while (i >= 0 && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
            i--;
        }
        if (i >= 0 && expresion.charAt(i) == '-'
                && (i == 0 || esOperador(expresion.charAt(i - 1)) || expresion.charAt(i - 1) == '(')) {
            i--;
        }
        return i + 1;
    }

    private char ultimoChar() {
        return expresion.length() == 0 ? 0 : expresion.charAt(expresion.length() - 1);
    }

    private static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷';
    }
}
