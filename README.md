# Payment Document Processor

Este documento explica, paso a paso y desde cero, cómo instalar y probar la aplicación desarrollada. No se asume ningún software previamente instalado en el computador. Siga cada sección en orden.

---

## 1. Requisitos del sistema
- Sistema operativo: Windows, macOS o Linux
- Acceso a internet
- Permisos para instalar software

## 2. Instalación de Java (JDK 17 o superior)

### Windows
1. Descargue el instalador de Java desde: https://adoptium.net/es/temurin/releases/?version=17
2. Ejecute el instalador y siga las instrucciones.
3. Reinicie su PC si se le solicita.
4. Verifique la instalación abriendo una terminal (cmd o PowerShell) y ejecutando:
   ```sh
   java -version
   ```
   Debe mostrar una versión igual o superior a 17.

### macOS
1. Instale Homebrew si no lo tiene: https://brew.sh/
2. En la terminal, ejecute:
   ```sh
   brew install temurin@17
   ```
3. Verifique con:
   ```sh
   java -version
   ```

### Linux (Ubuntu/Debian)
1. Abra una terminal y ejecute:
   ```sh
   sudo apt update
   sudo apt install openjdk-17-jdk
   java -version
   ```

## 3. Instalación de Maven (opcional)
No es necesario instalar Maven manualmente, ya que el proyecto incluye Maven Wrapper (`mvnw`).

## 4. Descargar el proyecto
1. Abra una terminal o consola.
2. Descargue el repositorio usando git (si tiene git instalado):
   ```sh
   git clone <URL_DEL_REPOSITORIO>
   cd vaadin-processor
   ```
   Si no tiene git, puede descargar el proyecto como archivo ZIP desde la página del repositorio y descomprimirlo.

## 5. Compilar y ejecutar la aplicación
1. En la terminal, dentro de la carpeta del proyecto (`vaadin-processor`), ejecute:
   ```sh
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```
   - En Windows, use `mvnw.cmd` en vez de `./mvnw` si tiene problemas.
2. Espere a ver un mensaje indicando que la aplicación está corriendo en `http://localhost:8080/`.

## 6. Probar la aplicación
1. Abra su navegador web y vaya a:
   - [http://localhost:8080/](http://localhost:8080/)
2. Use el botón de carga para subir un archivo `.json` con documentos de pago.
3. El reporte HTML se mostrará automáticamente en la página.

### Formatos de JSON aceptados
- **Arreglo de documentos:**
  ```json
  [
    { "DocType": "F02", "NroDocInterno": "30000000", "ContentBase64": "..." }
  ]
  ```
- **Objeto único:**
  ```json
  { "DocType": "F02", "NroDocInterno": "30000000", "ContentBase64": "..." }
  ```
- **Objeto con campo `documents`:**
  ```json
  {
    "documents": [
      { "DocType": "F02", "NroDocInterno": "30000000", "ContentBase64": "..." }
    ]
  }
  ```

## 7. Ejecutar pruebas automáticas
Para validar el correcto funcionamiento, ejecute:
```sh
./mvnw test
```
Esto compilará y ejecutará todos los tests del proyecto. Debe mostrar que todas las pruebas pasan.

## 8. Estructura del proyecto
```
vaadin-processor/
├── src/main/java/cl/felipe/processor/...
├── src/test/java/cl/felipe/processor/...
├── pom.xml
├── README.md
├── mvnw / mvnw.cmd
```

## 9. Problemas frecuentes
- **Puerto 8080 ocupado:** Cierre cualquier otro programa que use ese puerto.
- **Permisos denegados:** Ejecute la terminal como administrador o use `sudo` en Linux/macOS.
- **No reconoce `./mvnw`:** Use `mvnw.cmd` en Windows.

## 10. Contacto
Para dudas o problemas, contactar al desarrollador o revisar el código fuente.

---

**Fin de la guía de instalación y prueba.**
