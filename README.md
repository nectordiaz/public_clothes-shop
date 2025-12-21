# Prices Service

Servicio Spring Boot para consultar precios.

Endpoints:

- GET /api/v1/prices?productId={productId}&brandId={brandId}&applicationDate={ISO-8601}

Ejemplo:
GET /api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00

Cómo ejecutar:

- Requiere Java 21 y Maven
- Nota: Asegúrate de tener JAVA_HOME apuntando a un JDK 21 válido cuando compiles localmente.
- Ejecutar tests: mvn clean test
- Empaquetar: mvn package
- Ejecutar jar: java -jar target/prices-service-1.0.0.jar

Docker:

- `docker build -t prices-service:1.0 .`
- `docker run -p 8080:8080 prices-service:1.0`

H2 console: http://localhost:8080/h2-console

- Reemplazar la base de datos con `jdbc:h2:mem:pricesdb` en el cliente de H2

Swagger UI: http://localhost:8080/swagger-ui.html

En algunas partes del código fuente hay comentarios con dudas, dilemas, inquietudes y propuestas.
