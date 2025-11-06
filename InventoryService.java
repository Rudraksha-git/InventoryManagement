import java.util.ArrayList;

public interface InventoryService {
    void addProduct(Product product);
    void removeProduct(String ProductId);
    void updateStock(String ProductId, int stockQuantity);
    Product getProductDetails(String productId);
    ArrayList<Product> getAllProducts();
}