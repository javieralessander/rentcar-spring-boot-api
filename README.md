# RentCar API - Sistema de Renta de Vehículos

**Proyecto Final - Universidad APEC**
**Profesor:** Juan P. Valdez
**Año:** 2020

## Descripción

API REST desarrollada con Spring Boot para un sistema completo de renta de vehículos que incluye:

- ✅ Gestión de Tipos de Vehículos, Marcas, Modelos y Combustibles
- ✅ Gestión de Vehículos completa
- ✅ Gestión de Clientes (Físicos y Jurídicos)
- ✅ Gestión de Empleados con turnos y comisiones
- ✅ Sistema de Inspección detallado
- ✅ Proceso completo de Renta y Devolución
- ✅ Consultas por criterios múltiples
- ✅ Reportes de rentas configurables

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Base de datos H2** (en memoria para desarrollo)
- **Maven** para gestión de dependencias

## Requisitos

- Java 17 o superior
- Maven 3.6+

## Instalación y Ejecución

1. **Clonar o descargar el proyecto**
2. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```
3. **La API estará disponible en:** `http://localhost:8080`
4. **Base de datos H2 Console:** `http://localhost:8080/h2-console`
   - URL: `jdbc:h2:mem:rentcardb`
   - Usuario: `sa`
   - Password: (vacío)

## Endpoints Principales

### Clientes
- `GET /api/clientes` - Listar todos los clientes
- `POST /api/clientes` - Crear nuevo cliente
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente (soft delete)

### Vehículos
- `GET /api/vehiculos` - Listar todos los vehículos
- `GET /api/vehiculos/disponibles` - Vehículos disponibles para renta
- `POST /api/vehiculos` - Crear nuevo vehículo
- `PUT /api/vehiculos/{id}` - Actualizar vehículo

### Rentas
- `GET /api/rentas` - Listar todas las rentas
- `POST /api/rentas` - Crear nueva renta
- `PUT /api/rentas/{id}/devolver` - Marcar vehículo como devuelto
- `GET /api/rentas/reporte?fechaInicio=2023-01-01&fechaFin=2023-12-31` - Reporte de rentas

### Inspecciones
- `GET /api/inspecciones` - Listar inspecciones
- `POST /api/inspecciones` - Crear nueva inspección
- `GET /api/inspecciones/vehiculo/{id}` - Inspecciones por vehículo

### Catálogos
- `GET /api/tipos-vehiculos` - Tipos de vehículos
- `GET /api/marcas` - Marcas disponibles
- `GET /api/modelos` - Modelos de vehículos
- `GET /api/tipos-combustibles` - Tipos de combustible

## Datos de Prueba

La aplicación incluye datos de ejemplo que se cargan automáticamente:
- 4 tipos de vehículos
- 4 marcas con sus modelos
- 4 tipos de combustible
- 3 clientes de ejemplo
- 2 empleados
- 3 vehículos disponibles

## Estructura del Proyecto

```
src/main/java/com/universidad/apec/rentcar/
├── entity/          # Entidades JPA (modelos de datos)
├── repository/      # Repositorios de datos
├── controller/      # Controladores REST
└── RentCarApiApplication.java
```

## Conectar desde Flutter

Ver la guía detallada en el archivo `FLUTTER_GUIDE.md` para instrucciones específicas de integración con tu aplicación Flutter.