import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Inventory implements InventoryService {

    private Map<String, Product> inventory = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot add null product.");
            return;
        }
        if (inventory.containsKey(product.getProductId())) {
            System.out.println("Warning: Product with ID " + product.getProductId() + " already exists. Updating existing product.");
        }
        inventory.put(product.getProductId(), product);
        System.out.println(product.getProductName() + " was added to the inventory");
    }

    @Override
    public void removeProduct(String productId) {
        if (inventory.containsKey(productId)) {
            Product removed = inventory.remove(productId);
            System.out.println(removed.getProductName() + " was removed from the inventory");
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
            System.out.println("Stock updated for " + product.getProductName() + ": " + newStockQuantity);
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
            System.out.println("Product with ID " + productId + " not found.");
        }
        return product;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

}