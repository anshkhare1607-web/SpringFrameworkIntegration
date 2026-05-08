# Spring Framework Integration

A comprehensive collection of Spring Framework applications demonstrating best practices and enterprise-level development patterns.

## Projects

### Quantity Measurement Application

A RESTful API service for performing unit conversions and quantity comparisons across multiple measurement types including length, weight, volume, and temperature.

## Overview

The Quantity Measurement Application provides a robust backend service for unit conversion and comparison operations. Built with Spring Boot, it supports real-time data persistence with H2 database and comprehensive API documentation through OpenAPI/Swagger.

## Features

- Unit Conversion: Convert between different units of the same measurement type
- Quantity Comparison: Compare quantities across different units
- Arithmetic Operations: Add, subtract, multiply, and divide quantities
- Cross-Origin Resource Sharing: Configured for frontend integration
- Comprehensive Error Handling: Global exception handling with meaningful error messages
- Database Persistence: Track all operations with timestamps
- OpenAPI Documentation: Interactive API documentation via Swagger UI
- Logging: Request and operation tracking

## Technology Stack

- Java 21
- Spring Boot 4.0.4
- Spring Data JPA
- H2 Database (in-memory)
- Spring Web MVC
- SpringDoc OpenAPI 3.0.2 (Swagger UI)
- JUnit & Spring Boot Test

## Supported Measurements

### Length
- KM (Kilometer)
- M (Meter)
- MILE (Mile)

### Weight
- TON (Metric Ton)
- KG (Kilogram)
- G (Gram)

### Volume
- GALLON (US Gallon)
- L (Liter)
- ML (Milliliter)

### Temperature
- C (Celsius)
- F (Fahrenheit)
- K (Kelvin)

## API Endpoints

### Convert Quantity
**POST** `/api/quantity/convert`

Convert a value from one unit to another within the same measurement type.

Request Body:
```json
{
  "value1": 100,
  "unit1": "KM",
  "unit2": "M",
  "measurementType": "LENGTH"
}
```

Response:
```json
{
  "value1": 100,
  "unit1": "KM",
  "result": 100000,
  "resultUnit": "M",
  "operation": "CONVERT",
  "measurementType": "LENGTH",
  "error": false
}
```

### Compare Quantities
**POST** `/api/quantity/compare`

Compare two quantities to determine their relationship.

Request Body:
```json
{
  "value1": 100,
  "unit1": "M",
  "value2": 0.1,
  "unit2": "KM",
  "measurementType": "LENGTH"
}
```

Response:
```json
{
  "value1": 100,
  "unit1": "M",
  "value2": 0.1,
  "unit2": "KM",
  "comparisonResult": "EQUAL",
  "operation": "COMPARE",
  "measurementType": "LENGTH",
  "error": false
}
```

### Calculate (Add, Subtract, Multiply, Divide)
**POST** `/api/quantity/calculate`

Perform arithmetic operations on quantities.

Request Body:
```json
{
  "value1": 50,
  "unit1": "KG",
  "value2": 25,
  "unit2": "KG",
  "operation": "ADD",
  "measurementType": "WEIGHT"
}
```

## Project Structure

```
Quantity-Measurement-App/
├── src/
│   ├── main/java/com/app/quantitymeasurement/
│   │   ├── QuantityMeasurementAppApplication.java     (Entry point)
│   │   ├── controller/
│   │   │   └── QuantityController.java               (REST endpoints)
│   │   ├── service/
│   │   │   └── QuantityService.java                  (Business logic)
│   │   ├── repository/
│   │   │   └── QuantityMeasurementRepository.java   (Data access)
│   │   ├── model/
│   │   │   ├── dto/
│   │   │   │   ├── QuantityDto.java
│   │   │   │   └── QuantityResponseDto.java
│   │   │   ├── entity/
│   │   │   │   └── QuantityMeasurementEntity.java
│   │   │   └── enums/
│   │   │       ├── Unit.java
│   │   │       └── OperationType.java
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java
│   ├── test/java/com/app/quantitymeasurement/
│   │   ├── controller/
│   │   │   └── QuantityControllerTest.java
│   │   ├── service/
│   │   │   └── QuantityServiceTest.java
│   │   └── repository/
│   │       └── QuantityMeasurementRepositoryTest.java
│   └── resources/
│       └── application.properties
├── pom.xml
├── mvnw / mvnw.cmd
└── HELP.md
```


### Access the Application

- Application URL: `http://localhost:8080`
- API Base URL: `http://localhost:8080/api/quantity`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (empty)

## Database

The application uses an H2 in-memory database for demonstration and testing purposes.

**Database Schema:**
- Table: `quantity_measurements`
- Stores operation history with timestamps
- Tracks errors and comparison results
- Automatically created by Hibernate on application startup


## Configuration

Key application properties are configured in `src/main/resources/application.properties`:

- Spring Application Name: `Quantity-Measurement-App`
- Server Port: `8080` (default)
- H2 Database: In-memory database with console enabled
- JPA/Hibernate: Automatic schema generation enabled (ddl-auto=update)

## Error Handling

The application provides comprehensive error handling with meaningful error messages:

- Invalid unit combinations
- Unsupported measurement types
- Invalid operation requests
- Validation errors on request parameters

All errors are returned with appropriate HTTP status codes and detailed error messages in the response.

## CORS Configuration

The application is configured to accept requests from `http://localhost:5173` for frontend development and integration.

