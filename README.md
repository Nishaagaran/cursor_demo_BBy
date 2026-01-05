# Inventory Management REST API

A Spring Boot REST API for managing product inventory with H2 in-memory database.

## Features

- CRUD operations for products
- Search products by name
- Filter products by category
- Low stock alerts
- Product quantity management
- H2 database with web console

## Technology Stack

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Java 17
- Maven

## Project Structure

```
src/main/java/com/inventory/
├── InventoryManagementApplication.java  # Main Spring Boot application
├── entity/
│   └── Product.java                     # Product entity
├── repository/
│   └── ProductRepository.java           # Product repository interface
├── service/
│   └── ProductService.java              # Product business logic
└── controller/
    └── ProductController.java           # REST API endpoints
```

## API Endpoints

### Product Management

- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/sku/{sku}` - Get product by SKU
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?name={name}` - Search products by name
- `GET /api/products/low-stock?threshold={threshold}` - Get low stock products (default threshold: 10)
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `PATCH /api/products/{id}/quantity?quantity={quantity}` - Update product quantity
- `DELETE /api/products/{id}` - Delete a product

## Product Entity Fields

- `id` - Auto-generated unique identifier
- `name` - Product name (required)
- `description` - Product description
- `price` - Product price (required, must be >= 0)
- `quantity` - Stock quantity (required, must be >= 0)
- `sku` - Stock Keeping Unit (unique)
- `category` - Product category
- `createdAt` - Creation timestamp (auto-generated)
- `updatedAt` - Last update timestamp (auto-generated)

## Running the Application

1. Make sure you have Java 17+ and Maven installed
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`
5. H2 Console will be available at `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:inventorydb`
   - Username: `sa`
   - Password: (leave empty)

## Example API Requests

### Create a Product
```bash
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "quantity": 50,
  "sku": "LAP-001",
  "category": "Electronics"
}
```

### Get All Products
```bash
GET http://localhost:8080/api/products
```

### Update Product Quantity
```bash
PATCH http://localhost:8080/api/products/1/quantity?quantity=45
```

### Search Products
```bash
GET http://localhost:8080/api/products/search?name=laptop
```

## Database

The application uses H2 in-memory database. Data will be lost when the application stops. To persist data, you can:

1. Change the datasource URL in `application.properties` to use a file-based H2 database:
   ```
   spring.datasource.url=jdbc:h2:file:./data/inventorydb
   ```

2. Or configure a different database (PostgreSQL, MySQL, etc.) by updating the dependencies and datasource configuration.

