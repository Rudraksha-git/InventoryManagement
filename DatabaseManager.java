import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:inventory.db";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        initializeDatabase();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found. Please add sqlite-jdbc.jar to your classpath.");
            System.err.println("Download from: https://github.com/xerial/sqlite-jdbc/releases");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        String createTableSQL = 
            "CREATE TABLE IF NOT EXISTS products (" +
            "product_id TEXT PRIMARY KEY, " +
            "product_name TEXT NOT NULL, " +
            "price INTEGER NOT NULL, " +
            "stock_quantity INTEGER NOT NULL, " +
            "category TEXT NOT NULL, " +
            "warranty_months INTEGER, " +
            "expiry_date TEXT" +
            ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public void addProduct(Product product) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not available.");
        }
        String sql = "INSERT OR REPLACE INTO products (product_id, product_name, price, stock_quantity, category, warranty_months, expiry_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductId());
            pstmt.setString(2, product.getProductName());
            pstmt.setInt(3, product.getPrice());
            pstmt.setInt(4, product.getStockQuantity());
            
            if (product instanceof ElectronicsProduct) {
                pstmt.setString(5, "Electronics");
                ElectronicsProduct ep = (ElectronicsProduct) product;
                pstmt.setInt(6, ep.getWarrantyMonths());
                pstmt.setNull(7, Types.VARCHAR);
            } else if (product instanceof GroceryProduct) {
                pstmt.setString(5, "Grocery");
                GroceryProduct gp = (GroceryProduct) product;
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setString(7, gp.getExpiryDate());
            } else {
                pstmt.setString(5, "General");
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.VARCHAR);
            }
            
            pstmt.executeUpdate();
        }
    }

    public void removeProduct(String productId) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not available.");
        }
        String sql = "DELETE FROM products WHERE product_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            pstmt.executeUpdate();
        }
    }

    public void updateStock(String productId, int newStockQuantity) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not available.");
        }
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newStockQuantity);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();
        }
    }

    public Product getProduct(String productId) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not available.");
        }
        String sql = "SELECT * FROM products WHERE product_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createProductFromResultSet(rs);
            }
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not available.");
        }
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(createProductFromResultSet(rs));
            }
        }
        return products;
    }

    private Product createProductFromResultSet(ResultSet rs) throws SQLException {
        String productId = rs.getString("product_id");
        String productName = rs.getString("product_name");
        int price = rs.getInt("price");
        int stockQuantity = rs.getInt("stock_quantity");
        String category = rs.getString("category");
        
        if ("Electronics".equals(category)) {
            int warrantyMonths = rs.getInt("warranty_months");
            return new ElectronicsProduct(productId, productName, price, stockQuantity, warrantyMonths);
        } else if ("Grocery".equals(category)) {
            String expiryDate = rs.getString("expiry_date");
            return new GroceryProduct(productId, productName, price, stockQuantity, expiryDate);
        } else {
            // For general products, we'll create a basic Product wrapper
            // Since Product is abstract, we'll need to handle this differently
            // For now, return null or throw exception
            throw new SQLException("Unsupported product category: " + category);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
