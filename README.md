# Inventory Management System

A Java-based inventory management system with SQLite database persistence.

## Features

- Add products (Electronics or Grocery)
- View all products
- View individual product details
- Update stock quantities
- Remove products
- Persistent data storage using SQLite database

## Prerequisites

- Java JDK 8 or higher
- SQLite JDBC Driver

## Setup Instructions

### 1. Download SQLite JDBC Driver

Download the SQLite JDBC driver (JAR file) from:
- **Official Repository**: https://github.com/xerial/sqlite-jdbc/releases
- Download the latest `sqlite-jdbc-X.X.X.jar` file

### 2. Add JDBC Driver to Classpath

#### Option A: Using Command Line

Place the `sqlite-jdbc-X.X.X.jar` file (or `sqlite-jdbc-3.51.0.0.jar` if using that version) in your project directory, then compile and run:

**On Windows (PowerShell or CMD):**
```bash
# Compile all Java files with the JDBC driver
javac -cp "sqlite-jdbc-3.51.0.0.jar" *.java

# Run the application (NOTE: Use semicolon ; not colon :)
java -cp ".;sqlite-jdbc-3.51.0.0.jar" Implementation
```

**On Linux/Mac:**
```bash
# Compile all Java files with the JDBC driver
javac -cp "sqlite-jdbc-X.X.X.jar" *.java

# Run the application (NOTE: Use colon : not semicolon ;)
java -cp ".:sqlite-jdbc-X.X.X.jar" Implementation
```

**Quick Run (Windows):**
Simply double-click `run.bat` or run:
```bash
run.bat
```

#### Option B: Using an IDE (Eclipse, IntelliJ, etc.)

1. Right-click on your project
2. Select "Properties" or "Project Structure"
3. Navigate to "Libraries" or "Dependencies"
4. Click "Add External JARs" or "Add JARs"
5. Select the downloaded `sqlite-jdbc-X.X.X.jar` file
6. Click "OK" or "Apply"

### 3. Compile and Run

**Windows:**
```bash
# Compile all Java files
javac -cp "sqlite-jdbc-3.51.0.0.jar" *.java

# Run the application (use semicolon as separator)
java -cp ".;sqlite-jdbc-3.51.0.0.jar" Implementation
```

**Linux/Mac:**
```bash
# Compile all Java files
javac -cp "sqlite-jdbc-X.X.X.jar" *.java

# Run the application (use colon as separator)
java -cp ".:sqlite-jdbc-X.X.X.jar" Implementation
```

**Or simply use the provided scripts:**
- Windows: Double-click `run.bat`
- Linux/Mac: Run `./compile_and_run.sh`

## Database

The application automatically creates a SQLite database file named `inventory.db` in the project directory on first run. All product data is persisted in this database file.

### Database Schema

The `products` table has the following structure:
- `product_id` (TEXT, PRIMARY KEY)
- `product_name` (TEXT, NOT NULL)
- `price` (INTEGER, NOT NULL)
- `stock_quantity` (INTEGER, NOT NULL)
- `category` (TEXT, NOT NULL) - "Electronics" or "Grocery"
- `warranty_months` (INTEGER) - For Electronics products
- `expiry_date` (TEXT) - For Grocery products

## Usage

1. Run the application
2. Choose from the menu options:
   - **1**: Add a new product (Electronics or Grocery)
   - **2**: View all products in inventory
   - **3**: View details of a specific product
   - **4**: Update stock quantity for a product
   - **5**: Remove a product from inventory
   - **6**: Exit the application

## Project Structure

- `Implementation.java` - Main application entry point
- `Inventory.java` - Inventory management implementation
- `InventoryService.java` - Service interface
- `Product.java` - Abstract Product class and subclasses (ElectronicsProduct, GroceryProduct)
- `DatabaseManager.java` - Database connection and operations
- `ExceptionHandle.java` - Custom exception classes
- `inventory.db` - SQLite database file (created automatically)

## Notes

- The database file (`inventory.db`) is created automatically in the project directory
- Product data persists between application sessions
- The application loads all products from the database on startup
- All add, update, and remove operations are immediately saved to the database

## Troubleshooting

### "SQLite JDBC driver not found" Error

Make sure you have:
1. Downloaded the SQLite JDBC driver JAR file
2. Added it to the classpath when compiling and running

### "Could not find or load main class Implementation" Error

This usually happens when:
1. **Wrong classpath separator**: On Windows, use `;` not `:`
   - Wrong: `java -cp ".:sqlite-jdbc-X.X.X.jar" Implementation`
   - Correct: `java -cp ".;sqlite-jdbc-3.51.0.0.jar" Implementation`
2. **Files not compiled**: Run `javac -cp "sqlite-jdbc-3.51.0.0.jar" *.java` first
3. **Wrong directory**: Make sure you're in the project directory where the .java files are located

### Database Connection Errors

- Ensure you have write permissions in the project directory
- Check if `inventory.db` file is not locked by another process
- Delete `inventory.db` to reset the database (all data will be lost)