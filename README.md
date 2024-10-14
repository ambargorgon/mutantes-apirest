# 🧬 APIREST de Detección de Mutantes
## 📋 Descripción
Esta API analiza secuencias de ADN determinando si corresponden a un Mutante o Humano, gracias a una serie de patrones que se deben cumplir.

## 🌐 URL de Producción
La API está hosteada en Render:
- URL: `https://mutantes-apirest-1.onrender.com`

## 🗺️ Diagrama de Secuencia
![diagramasecuencia](https://github.com/user-attachments/assets/cffbbd37-8464-43f5-bf6e-756a01fef01d)


## Construida con:
- SpringBoot 
- Java 17
- Gradle
- Hibernate
- Open API
- H2 Database
- Render

- ### Cómo acceder a la api Hosteada:
   Acceder a la API en producción: La API está hosteada en Render, y puedes acceder a ella en para ver la documentación visual e interactiva de la API. [Swagger UI](https://mutantesapirestprogiii.onrender.com/swagger-ui/index.html)
   - Tambien puedes enviar una request atraves de Postman.
   - **POST**: https://mutantes-apirest-1.onrender.com/mutant
      - Ejemplo de POST:
        ```
        {
            "dna": ["CCCC","AAAA","TAAG","GGTC"]
        }
        ```
  - **GET**: https://mutantes-apirest-1.onrender.com/stats

- ### Cómo ejecutar el proyecto localmente:
    - **Clonar el repositorio**:
      ```bash
      git clone "https://github.com/ambargorgon/mutantes-apirest"
      cd mutantes-apirest
      ```
   
    - **Ejecutar la aplicación**: Ejecutar la clase principal Spring Boot "MutantesApplication"
   
    - **Acceder a la API localmente**: Si ejecutas el proyecto en tu máquina local, 8080 es el puerto especificado en el archivo application.properties (puedes modificarlo si es necesario).
      - Puedes hacer las solicitudes a la API en http://localhost:8080/swagger-ui/index.html.
      - Puedes enviar una request atraves de Postman.
         - **POST**: http://localhost:8080/mutant
         - **GET**:  http://localhost:8080/stats
      - Puedes acceder a la base de datos h2  http://localhost:8080/h2-console/ (revisar en el archivo application.properties: contraseña, usuario, URL de JDBC que es en memoria).
        

## 📬 Uso de la API

| Endpoint        | Método | Descripción                   |
|-----------------|--------|-------------------------------|
| `/mutant`      | POST   | Detecta si un ADN es mutante |
| `/mutant/stats`        | GET    | Muestra estadísticas de ADN  |

#### 🔎 Endpoint `/mutant/`
Este endpoint detecta si una secuencia de ADN pertenece a un mutante según los criterios de Magneto. La solicitud debe enviarse como un POST en formato JSON con el siguiente esquema:
   
#### Criterios de aceptacion
- **Clave `dna`**: Debe ser un arreglo de strings representando cada fila de la matriz de ADN.
- #### **Restricciones de Matriz**:
  - **Tamaño mínimo**: 4x4.
  - **Formato NxN**: El número de filas y columnas debe ser igual.
  - **Bases Nitrogenadas Válidas**: Cada string debe contener solo las letras A, C, T, o G.
  - **Datos Completos**: No debe haber valores nulos ni vacíos.
  
#### 🔄 Respuestas del Endpoint
- **Mutante detectado**: Devuelve HTTP 200 OK.
- **No es mutante**: Devuelve HTTP 403 Forbidden.
- **Ingreso inválido**: Devuelve un HTTP 400 Bad Request
  
## 🧪 Pruebas
### Pruebas Untarias y de Integración
La API cuenta con pruebas unitarias y de integración para asegurar su correcto funcionamiento, estas `se pueden ejecutar` para probar el funcionamiento de la Api de Manera independiente sin tener que ejecutar el servidor.

### Pruebas de Servicio (`MutantServiceTest`) 
 **Ubicacion**: `src/test/java/com/example/mutantes/MutantesApplicationTests.java`.Esta clase se encarga de validar el funcionamiento del servicio.
