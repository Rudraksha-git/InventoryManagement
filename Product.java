abstract class Product{
    private String productId;
    private String productName;
    private int price;
    private int stockQuantity;

    public Product(String productId, String productName, int price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getProductId() {
        return this.productId;
    }
    public String getProductName() {
        return this.productName;
    } 
    public int getPrice() {
        return this.price;
    }
    public int getStockQuantity() {
        return this.stockQuantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setProductName (String productName) {
        this.productName = productName;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) {
            this.stockQuantity = stockQuantity;
        }
        else {
            System.out.println("Stock quantity cannot be negative");
        }
    }
    @Override
    public String toString() {
        return "Product[ID=" + productId + 
            ", Name=" + productName + 
            ", Price=₹" + price + 
            ", Quantity=" + stockQuantity + "]";
    }
} 

class ElectronicsProduct extends Product {

    private int warrantyMonths;

    public ElectronicsProduct(String productId, String productName, int price, int stockQuantity, int warrantyMonths) {
        super(productId, productName, price, stockQuantity);
        this.warrantyMonths = warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return this.warrantyMonths;
    }
    @Override
    public String toString() {
        return super.toString().replace("]", "") + 
            ", Category=Electronics" + 
            ", Warranty Period=" + warrantyMonths + " months]";
    }
}

class GroceryProduct extends Product {

    private String ExpiryDate;

    public GroceryProduct(String productId, String productName, int price, int stockQuantity, String ExpiryDate) {
        super(productId, productName, price, stockQuantity);
        this.ExpiryDate = ExpiryDate;
    }

    public void setExpiryDate(String ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }
    public String getExpiryDate() {
        return ExpiryDate;
    }

    @Override
    public String toString() {
        return super.toString().replace("]", "") + 
            ", Category=Grocery" + 
            ", Expiry Date=" + ExpiryDate + "]";
    }
}

