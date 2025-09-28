Este documento te guiará a través de los pasos para poner en marcha la aplicación. 
Elige la opción que mejor se adapte a tu entorno.

## Ejecutar con Java

### Paso 1
Para ejecutar la aplicación, necesitarás tener instalado el JDK 21. <br>
Descarga e instala la versión 21 de Java Development Kit (JDK) desde el sitio oficial:
 + https://jdk.java.net/archive/


### Paso 2
Compilar y Ejecutar <br>
Navega a la raíz del proyecto en tu terminal.<br>
- cd ruta/del/proyecto <br>

**Windows**

*Ejecuta este comando para generar el archivo .jar* <br>
+ ./gradlew jar  

*Luego, inicia la aplicación:* <br>
+ java -jar build/libs/ProductComparisons-1.0.jar

**Linux:**

Ejecuta el siguiente comando para iniciar la aplicación directamente:
+ ./gradlew bootRun


## Ejecutar con Docker

### Paso 1
Instalar docker
+ https://www.docker.com/

### Paso 2
Generar la imagen <br>
+ docker build -t product-comparisons .

### Paso 3
Correr la imagen
+ docker run -p 8081:8081 product-comparisons

