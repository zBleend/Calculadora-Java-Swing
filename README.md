# Calculadora con Java Swing

Una aplicación de calculadora de escritorio simple pero funcional, desarrollada en Java utilizando la biblioteca Swing para la interfaz gráfica de usuario.

## Descripción

Este proyecto implementa una calculadora estándar con operaciones aritméticas básicas y algunas funciones adicionales. La aplicación está estructurada para separar la lógica de la interfaz de usuario, facilitando su mantenimiento y escalabilidad.

## Características

- **Operaciones básicas**: Suma (`+`), Resta (`-`), Multiplicación (`×`) y División (`÷`).
- **Operaciones unarias**:
  - Raíz cuadrada (`²√x`)
  - Cuadrado (`x²`)
  - Inverso (`1/x`)
  - Porcentaje (`%`)
  - Cambio de signo (`+/-`)
- **Funciones de control**:
  - **C**: Borra todo el cálculo y reinicia el estado.
  - **CE**: Borra la entrada actual.
  - **DEL**: Elimina el último dígito introducido.
- **Soporte para decimales**.
- **Manejo de errores**: Muestra "Error" para operaciones no válidas (ej. división por cero).

## Estructura del Proyecto

El código fuente está organizado en paquetes que siguen un patrón similar a MVC (Modelo-Vista-Controlador):

```
src/main/java/com/calculadora/
├── model/
│   └── CalculadoraModel.java   # (Modelo) Almacena el estado de la calculadora.
├── service/
│   └── CalculadoraService.java # (Controlador/Servicio) Contiene la lógica de negocio.
└── ui/
    └── CalculadoraUI.java      # (Vista) Gestiona la interfaz de usuario y la interacción.
```

- `CalculadoraModel.java`: Utiliza Lombok para un código más limpio y conciso, representando los datos de la calculadora.
- `CalculadoraService.java`: Centraliza todos los cálculos matemáticos, asegurando que la lógica de negocio esté desacoplada de la interfaz.
- `CalculadoraUI.java`: Construida con Swing, esta clase maneja todos los eventos de los botones y actualiza la pantalla.

## Cómo Ejecutar

Este proyecto utiliza Maven para gestionar las dependencias y el proceso de construcción.

1.  **Prerrequisitos**:
    - Tener instalado el JDK (Java Development Kit, versión 17 o superior).
    - Tener instalado Apache Maven.

2.  **Compilación**:
    - Abre una terminal en el directorio raíz del proyecto (`calculadora-app`).
    - Ejecuta el siguiente comando para compilar el proyecto y generar el archivo JAR:
      ```bash
      mvn clean install
      ```

3.  **Ejecución**:
    - Una vez compilado, ejecuta la aplicación con el siguiente comando:
      ```bash
      java -cp target/calculadora-app-1.0-SNAPSHOT.jar com.calculadora.ui.CalculadoraUI
      ```
    - La ventana de la calculadora debería aparecer en tu pantalla.
