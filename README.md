# Inventory Service

Microservicio de gestión de inventario para el ecosistema Linktic. Proporciona APIs RESTful para el manejo de inventarios y ventas de productos.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Data JPA**
- **PostgreSQL**
- **Spring Cloud OpenFeign**
- **Resilience4j**
- **MapStruct**
- **OpenAPI 3**

## 📋 Funcionalidades

### Gestión de Inventario
- Actualización de inventario por producto
- Consulta de inventario por ID de producto

### Gestión de Ventas
- Registro de ventas con actualización automática de inventario
- Validación de stock disponible

### Características Técnicas
- Circuit breaker y retry patterns con Resilience4j
- Integración con servicio de productos via OpenFeign
- Documentación automática con OpenAPI
- Health checks y métricas con Actuator
- Cobertura de pruebas con JaCoCo (mínimo 80%)

## 🔧 Configuración

### Variables de Entorno
```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/microdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
INVENTORY_API_KEY=key-inventory
PRODUCTS_API_KEY=key
PRODUCTS_HOST=http://localhost:8080
```

### Puerto
- **Puerto por defecto:** 8081

## 📡 APIs

### Inventario
- `PUT /api/v1/inventory` - Actualizar inventario
- `GET /api/v1/inventory/{productId}` - Consultar inventario

### Ventas
- `POST /api/v1/sales` - Registrar venta

## 🏃‍♂️ Ejecución

```bash
# Con Gradle
./gradlew bootRun

# Con Docker
docker build -t inventory-service .
docker run -p 8081:8081 inventory-service
```

## 🧪 Pruebas

```bash
./gradlew test
./gradlew jacocoTestReport
```

## 📚 Documentación

- **OpenAPI UI:** http://localhost:8081/swagger-ui.html
- **Health Check:** http://localhost:8081/actuator/health