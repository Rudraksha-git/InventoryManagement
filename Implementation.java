import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Implementation {
    public static void main(String[] args) {
        Inventory manager = new Inventory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Inventory Management System!");
        
        // Add shutdown hook to close database on unexpected exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            manager.closeDatabase();
        }));
        while(true) {
            System.out.println("\n-----Inventory Management System-----");
            System.out.println("1. Add New Product");
            System.out.println("2. View All Products");
            System.out.println("3. View Product Details");
            System.out.println("4. Update Stock Quantity");
            System.out.println("5. Remove Product");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number (1-6).");
                scanner.nextLine();
                continue;
            }

            switch(choice) {
                case 1:
                    handleAddProduct(scanner, manager);
                    break;
                case 2:
                    ArrayList<Product> products = manager.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("There are no products in the Inventory");
                    }
                    else {
                        System.out.println("\n--- All Products in Inventory ---");
                        for (Product p: products) {
                            System.out.println(p);
                        }
                        System.out.println("Total products: " + products.size());
                    }
                    break;
                case 3:
                    System.out.print("Enter the Product ID: ");
                    String viewProductId = scanner.nextLine().trim();
                    if (viewProductId.isEmpty()) {
                        System.out.println("Error: Product ID cannot be empty.");
                    } else {
                        manager.getProductDetails(viewProductId);
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Enter the Product ID: ");
                        String productid = scanner.nextLine();
                        if (productid.trim().isEmpty()) {
                            System.out.println("Error: Product ID cannot be empty.");
                            break;
                        }
                        System.out.print("Enter the quantity to add to stock: ");
                        int newSupply = scanner.nextInt();
                        scanner.nextLine();
                        if (newSupply < 0) {
                            System.out.println("Error: Stock quantity cannot be negative.");
                        } else {
                            manager.updateStock(productid, newSupply);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Please enter a valid integer for stock quantity.");
                        scanner.nextLine();
                    }
                    break;
                case 5:
                    System.out.print("Enter the product ID: ");
                    String productID = scanner.nextLine();
                    if (productID.trim().isEmpty()) {
                        System.out.println("Error: Product ID cannot be empty.");
                        break;
                    }
                    System.out.print("Confirm Yes/No, This operation cannot be reversed! ");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("Yes")) {
                        manager.removeProduct(productID);
                    }
                    else {
                        System.out.println("Confirmation Denied! Product not removed.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    manager.closeDatabase();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Please choose the correct option number");
            }
            
        }
    }


    private static void handleAddProduct(Scanner scanner, InventoryService manager) {
        try {
            System.out.println("Select Product Type:");
            System.out.println("1. Electronics");
            System.out.println("2. Grocery");
            System.out.print("Enter choice: ");
            int typeChoice = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Enter Product ID: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("Error: Product ID cannot be empty.");
                return;
            }
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Error: Product name cannot be empty.");
                return;
            }
            System.out.print("Enter Price: ");
            int price = scanner.nextInt();
            if (price < 0) {
                System.out.println("Error: Price cannot be negative.");
                scanner.nextLine();
                return;
            }
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            if (quantity < 0) {
                System.out.println("Error: Quantity cannot be negative.");
                return;
            }

            Product newProduct = null;

            if (typeChoice == 1) {
                System.out.print("Enter Warranty (in months): ");
                int warranty = scanner.nextInt();
                scanner.nextLine();
                if (warranty < 0) {
                    System.out.println("Error: Warranty period cannot be negative.");
                    return;
                }
                newProduct = new ElectronicsProduct(id, name, price, quantity, warranty);

            } else if (typeChoice == 2) {
                System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                String expiry = scanner.nextLine().trim();
                if (expiry.isEmpty()) {
                    System.out.println("Error: Expiry date cannot be empty.");
                    return;
                }
                newProduct = new GroceryProduct(id, name, price, quantity, expiry);
                
            } else {
                System.out.println("Invalid type choice.");
                return;
            }

            if (newProduct != null) {
                manager.addProduct(newProduct);
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter numbers where required.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            scanner.nextLine();
        }
    }
}