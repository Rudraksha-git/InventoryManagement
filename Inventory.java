import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements InventoryService {

    private Map<String, Product> inventory = new HashMap<>();
    private DatabaseManager dbManager;

    public Inventory() {
        this.dbManager = DatabaseManager.getInstance();
        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        try {
            ArrayList<Product> products = dbManager.getAllProducts();
            for (Product product : products) {
                inventory.put(product.getProductId(), product);
            }
            if (!products.isEmpty()) {
                System.out.println("Loaded " + products.size() + " product(s) from database.");
            }
        } catch (SQLException e) {
            System.err.println("Error loading products from database: " + e.getMessage());
            System.err.println("Starting with empty inventory.");
        }
    }

    @Override
    public void addProduct(Product product) {
        try {
            inventory.put(product.getProductId(), product);
            dbManager.addProduct(product);
            System.out.println(product.getProductName() + " was added to the inventory");
        } catch (SQLException e) {
            System.err.println("Error saving product to database: " + e.getMessage());
            // Still keep it in memory even if DB save fails
            System.out.println(product.getProductName() + " was added to memory (database save failed)");
        }
    }

    @Override
    public void removeProduct(String productId) {
        Product product = inventory.get(productId);
        if (product != null) {
            try {
                inventory.remove(productId);
                dbManager.removeProduct(productId);
                System.out.println(product.getProductName() + " was removed from the inventory");
            } catch (SQLException e) {
                System.err.println("Error removing product from database: " + e.getMessage());
                // Re-add to memory if DB operation failed
                inventory.put(productId, product);
                System.out.println("Failed to remove product from database. Product still in memory.");
            }
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    @Override
    public void updateStock(String productId, int stockIncrease) {
        Product product = inventory.get(productId);
        if (product != null) {
            int newStockQuantity = product.getStockQuantity() + stockIncrease;
            product.setStockQuantity(newStockQuantity);
            try {
                dbManager.updateStock(productId, newStockQuantity);
                System.out.println("Stock updated for " + product.getProductName() + ": " + newStockQuantity);
            } catch (SQLException e) {
                System.err.println("Error updating stock in database: " + e.getMessage());
                System.out.println("Stock updated in memory: " + product.getProductName() + ": " + newStockQuantity);
            }
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    @Override
    public Product getProductDetails(String productId) {
        Product product = inventory.get(productId);
        if (product != null) {
            System.out.println("\n--- Product Details ---");
            System.out.println(product);
        } else {
            // Try to load from database in case it's not in memory
            try {
                product = dbManager.getProduct(productId);
                if (product != null) {
                    inventory.put(productId, product);
                    System.out.println("\n--- Product Details ---");
                    System.out.println(product);
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            } catch (SQLException e) {
                System.err.println("Error loading product from database: " + e.getMessage());
                System.out.println("Product with ID " + productId + " not found.");
            }
        }
        return product;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

    public void closeDatabase() {
        dbManager.closeConnection();
    }

}