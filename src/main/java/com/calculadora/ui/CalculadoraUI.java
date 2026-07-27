package com.calculadora.ui;

import com.calculadora.model.CalculadoraModel;
import com.calculadora.service.CalculadoraService;

public class CalculadoraUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CalculadoraUI.class.getName());

    private final CalculadoraModel model = new CalculadoraModel();
    private final CalculadoraService service = new CalculadoraService();

    public CalculadoraUI() {
        initComponents();
        resultado.setEditable(false);
        setResizable(false);
    }

    private void agregarNumero(String digito){
        if (model.isLimpiarPantalla() || resultado.getText().equals("0") || resultado.getText().equals("Error")){
            resultado.setText(digito);
            model.setLimpiarPantalla(false);
        } else {
            resultado.setText(resultado.getText() + digito);
        }
    }

    private void agregarComa(String coma){
        if (model.isLimpiarPantalla()) {
            resultado.setText("0.");
            model.setLimpiarPantalla(false);
        } else if (!resultado.getText().contains(".")) {
            resultado.setText(resultado.getText() + ".");
        }
    }

    private void seleccionarOperacion(String op) {
        try {
            double valorActual = Double.parseDouble(resultado.getText());
            boolean hayOperacionPendiente = model.getOperacion() != null
                    && !model.getOperacion().isEmpty()
                    && !model.isLimpiarPantalla();

            if (hayOperacionPendiente) {
                model.setSegundoNumero(valorActual);
                double parcial = service.calcular(model);
                model.setPrimerNumero(parcial);
            } else {
                model.setPrimerNumero(valorActual);
            }

            model.setOperacion(op);
            model.setLimpiarPantalla(true);
        } catch (NumberFormatException | ArithmeticException e) {
            resultado.setText("Error");
            model.setOperacion("");
            model.setLimpiarPantalla(true);
        }
    }

    private void calcularResultado() {
        try {
            double valorActual = Double.parseDouble(resultado.getText());
            model.setSegundoNumero(valorActual);
            double res = service.calcular(model);

            resultado.setText(res == (long) res ? String.format("%d", (long) res) : String.valueOf(res));

            model.setOperacion("");
            model.setPrimerNumero(res);
            model.setLimpiarPantalla(true);
        } catch (Exception e) {
            resultado.setText("Error");
            model.setOperacion("");
            model.setLimpiarPantalla(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        porcentaje = new javax.swing.JButton();
        borrarTodoCalculo = new javax.swing.JButton();
        borrarTodo = new javax.swing.JButton();
        retroceso = new javax.swing.JButton();
        dividir = new javax.swing.JButton();
        inverso = new javax.swing.JButton();
        cuadrado = new javax.swing.JButton();
        raizCuadrada = new javax.swing.JButton();
        numNueve = new javax.swing.JButton();
        numOcho = new javax.swing.JButton();
        multiplicar = new javax.swing.JButton();
        numSiete = new javax.swing.JButton();
        resta = new javax.swing.JButton();
        numCuatro = new javax.swing.JButton();
        numCinco = new javax.swing.JButton();
        numSeis = new javax.swing.JButton();
        numDos = new javax.swing.JButton();
        numTres = new javax.swing.JButton();
        suma = new javax.swing.JButton();
        numUno = new javax.swing.JButton();
        igual = new javax.swing.JButton();
        coma = new javax.swing.JButton();
        cambiarSigno = new javax.swing.JButton();
        numCero = new javax.swing.JButton();
        resultado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        porcentaje.setText("%");
        porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porcentajeActionPerformed(evt);
            }
        });

        borrarTodoCalculo.setText("CE");
        borrarTodoCalculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarTodoCalculoActionPerformed(evt);
            }
        });

        borrarTodo.setText("C");
        borrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarTodoActionPerformed(evt);
            }
        });

        retroceso.setText("DEL");
        retroceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrocesoActionPerformed(evt);
            }
        });

        dividir.setText("÷");
        dividir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dividirActionPerformed(evt);
            }
        });

        inverso.setText("1/x");
        inverso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inversoActionPerformed(evt);
            }
        });

        cuadrado.setText("X²");
        cuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuadradoActionPerformed(evt);
            }
        });

        raizCuadrada.setText("²√x");
        raizCuadrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raizCuadradaActionPerformed(evt);
            }
        });

        numNueve.setText("9");
        numNueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numNueveActionPerformed(evt);
            }
        });

        numOcho.setText("8");
        numOcho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numOchoActionPerformed(evt);
            }
        });

        multiplicar.setText("×");
        multiplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiplicarActionPerformed(evt);
            }
        });

        numSiete.setText("7");
        numSiete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numSieteActionPerformed(evt);
            }
        });

        resta.setText("-");
        resta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restaActionPerformed(evt);
            }
        });

        numCuatro.setText("4");
        numCuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numCuatroActionPerformed(evt);
            }
        });

        numCinco.setText("5");
        numCinco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numCincoActionPerformed(evt);
            }
        });

        numSeis.setText("6");
        numSeis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numSeisActionPerformed(evt);
            }
        });

        numDos.setText("2");
        numDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numDosActionPerformed(evt);
            }
        });

        numTres.setText("3");
        numTres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numTresActionPerformed(evt);
            }
        });

        suma.setText("+");
        suma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumaActionPerformed(evt);
            }
        });

        numUno.setText("1");
        numUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numUnoActionPerformed(evt);
            }
        });

        igual.setText("=");
        igual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                igualActionPerformed(evt);
            }
        });

        coma.setText(",");
        coma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comaActionPerformed(evt);
            }
        });

        cambiarSigno.setText("+/-");
        cambiarSigno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarSignoActionPerformed(evt);
            }
        });

        numCero.setText("0");
        numCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numCeroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(borrarTodoCalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(borrarTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(retroceso, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(inverso, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(raizCuadrada, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dividir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(numSiete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numOcho, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(numNueve, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(multiplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(numCuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numCinco, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(numSeis, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(resta, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(numUno, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numDos, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(numTres, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(suma, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cambiarSigno, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numCero, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(coma, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(igual, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrarTodoCalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrarTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(retroceso, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inverso, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(raizCuadrada, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dividir, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numSiete, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numOcho, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numNueve, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(multiplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numCuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numCinco, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numSeis, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resta, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numUno, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numDos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numTres, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suma, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cambiarSigno, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numCero, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coma, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(igual, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        resultado.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        resultado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        resultado.setText("0");
        resultado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultado))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultado, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void porcentajeActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            double valorActual = Double.parseDouble(resultado.getText());
            double valorCalculado;

            String operacion = model.getOperacion();
            boolean hayOperacionPendiente = operacion != null && !operacion.isEmpty();
            boolean esMultiplicacionODivision = "*".equals(operacion) || "×".equals(operacion)
                    || "/".equals(operacion) || "÷".equals(operacion);

            if (hayOperacionPendiente && !esMultiplicacionODivision) {
                valorCalculado = (model.getPrimerNumero() * valorActual) / 100.0;
            } else {
                valorCalculado = valorActual / 100.0;
            }

            resultado.setText(valorCalculado == (long) valorCalculado
                    ? String.format("%d", (long) valorCalculado)
                    : String.valueOf(valorCalculado));

            model.setLimpiarPantalla(true);
        } catch (NumberFormatException e) {
            resultado.setText("Error");
        }
    }

    private void retrocesoActionPerformed(java.awt.event.ActionEvent evt) {
        if (model.isLimpiarPantalla() || resultado.getText().equals("Error")) {
            resultado.setText("0");
            model.setLimpiarPantalla(false);
            return;
        }

        String texto = resultado.getText();
        if (texto.length() > 1) {
            resultado.setText(texto.substring(0, texto.length() - 1));
        } else {
            resultado.setText("0");
        }
    }

    private void inversoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            double valor = Double.parseDouble(resultado.getText());
            if (valor == 0) {
                resultado.setText("Error");
            } else {
                double res = 1.0 / valor;
                resultado.setText(res == (long) res ? String.format("%d", (long) res) : String.valueOf(res));
            }
            model.setLimpiarPantalla(true);
        } catch (NumberFormatException e) {
            resultado.setText("Error");
        }
    }

    private void resultadoActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void borrarTodoCalculoActionPerformed(java.awt.event.ActionEvent evt) {
        resultado.setText("0");
    }

    private void borrarTodoActionPerformed(java.awt.event.ActionEvent evt) {
        resultado.setText("0");
        model.setPrimerNumero(0.0);
        model.setSegundoNumero(0.0);
        model.setOperacion("");
        model.setLimpiarPantalla(false);
    }

    private void cuadradoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            double valor = Double.parseDouble(resultado.getText());
            double res = valor * valor;
            resultado.setText(res == (long) res ? String.format("%d", (long) res) : String.valueOf(res));
            model.setLimpiarPantalla(true);
        } catch (NumberFormatException e) {
            resultado.setText("Error");
        }
    }

    private void raizCuadradaActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            double valor = Double.parseDouble(resultado.getText());
            if (valor < 0) {
                resultado.setText("Error");
            } else {
                double res = service.calcularRaizCuadrada(valor);
                resultado.setText(res == (long) res ? String.format("%d", (long) res) : String.valueOf(res));
            }
            model.setLimpiarPantalla(true);
        } catch (Exception e) {
            resultado.setText("Error");
        }
    }

    private void dividirActionPerformed(java.awt.event.ActionEvent evt) {
        seleccionarOperacion("/");
    }

    private void numSieteActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("7");
    }

    private void numOchoActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("8");
    }

    private void numNueveActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("9");
    }

    private void multiplicarActionPerformed(java.awt.event.ActionEvent evt) {
        seleccionarOperacion("*");
    }

    private void numCuatroActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("4");
    }

    private void numCincoActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("5");
    }

    private void numSeisActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("6");
    }

    private void restaActionPerformed(java.awt.event.ActionEvent evt) {
        seleccionarOperacion("-");
    }

    private void numUnoActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("1");
    }

    private void numDosActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("2");
    }

    private void numTresActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("3");
    }

    private void sumaActionPerformed(java.awt.event.ActionEvent evt) {
        seleccionarOperacion("+");
    }

    private void cambiarSignoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            double valor = Double.parseDouble(resultado.getText());
            double res = service.cambiarSigno(valor);
            if (res == (long) res) {
                resultado.setText(String.format("%d", (long) res));
            } else {
                resultado.setText(String.valueOf(res));
            }
        } catch (NumberFormatException e) {
            resultado.setText("Error");
        }
    }

    private void numCeroActionPerformed(java.awt.event.ActionEvent evt) {
        agregarNumero("0");
    }

    private void comaActionPerformed(java.awt.event.ActionEvent evt) {
        agregarComa(".");
    }

    private void igualActionPerformed(java.awt.event.ActionEvent evt) {
        calcularResultado();
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new CalculadoraUI().setVisible(true));
    }

    private javax.swing.JButton borrarTodo;
    private javax.swing.JButton borrarTodoCalculo;
    private javax.swing.JButton cambiarSigno;
    private javax.swing.JButton coma;
    private javax.swing.JButton cuadrado;
    private javax.swing.JButton dividir;
    private javax.swing.JButton igual;
    private javax.swing.JButton inverso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton multiplicar;
    private javax.swing.JButton numCero;
    private javax.swing.JButton numCinco;
    private javax.swing.JButton numCuatro;
    private javax.swing.JButton numDos;
    private javax.swing.JButton numNueve;
    private javax.swing.JButton numOcho;
    private javax.swing.JButton numSeis;
    private javax.swing.JButton numSiete;
    private javax.swing.JButton numTres;
    private javax.swing.JButton numUno;
    private javax.swing.JButton porcentaje;
    private javax.swing.JButton raizCuadrada;
    private javax.swing.JButton resta;
    private javax.swing.JTextField resultado;
    private javax.swing.JButton retroceso;
    private javax.swing.JButton suma;
}
