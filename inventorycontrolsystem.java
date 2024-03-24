import java.io.*;
import java.util.*;

public class InventoryManagementSystem {
    private static final String FILE_NAME = "products.csv";
    private static final String PURCHASE_HISTORY_FILE = "purchase_history.csv";
    private static final String DELIMITER = ",";
    private static final String PURCHASE_DELIMITER = "::";

    public static void main(String[] args) {
        List<Product> products = loadProductsFromFile();
        List<Purchase> purchaseHistory = loadPurchaseHistory();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Purchase Product");
            System.out.println("5. View Purchase History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProducts(products);
                    break;
                case 2:
                    addProduct(scanner, products);
                    break;
                case 3:
                    removeProduct(scanner, products);
                    break;
                case 4:
                    purchaseProduct(scanner, products, purchaseHistory);
                    break;
                case 5:
                    viewPurchaseHistory(purchaseHistory);
                    break;
                case 6:
                    saveProductsToFile(products);
                    savePurchaseHistoryToFile(purchaseHistory);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewProducts(List<Product> products) {
        System.out.println("Product List:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void addProduct(Scanner scanner, List<Product> products) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        products.add(new Product(name, price));
        System.out.println("Product added successfully.");
    }

    private static void removeProduct(Scanner scanner, List<Product> products) {
        System.out.print("Enter product name to remove: ");
        String name = scanner.nextLine();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("Product removed successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void purchaseProduct(Scanner scanner, List<Product> products, List<Purchase> purchaseHistory) {
        System.out.print("Enter product name to purchase: ");
        String name = scanner.nextLine();
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter quantity to purchase: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                double totalPrice = product.getPrice() * quantity;
                Purchase purchase = new Purchase(product.getName(), quantity, totalPrice, new Date());
                purchaseHistory.add(purchase);
                System.out.println("Purchase successful.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void viewPurchaseHistory(List<Purchase> purchaseHistory) {
        System.out.println("Purchase History:");
        for (Purchase purchase : purchaseHistory) {
            System.out.println(purchase);
        }
    }

    private static List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                products.add(new Product(data[0], Double.parseDouble(data[1])));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
        return products;
    }

    private static void saveProductsToFile(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product product : products) {
                writer.write(product.getName() + DELIMITER + product.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }

    private static List<Purchase> loadPurchaseHistory() {
        List<Purchase> purchaseHistory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PURCHASE_HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                purchaseHistory.add(new Purchase(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]), new Date(Long.parseLong(data[3])));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading purchase history: " + e.getMessage());
        }
        return purchaseHistory;
    }

    private static void savePurchaseHistoryToFile(List<Purchase> purchaseHistory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PURCHASE_HISTORY_FILE))) {
            for (Purchase purchase : purchaseHistory) {
                writer.write(purchase.getProductName() + DELIMITER + purchase.getQuantity() + DELIMITER + purchase.getTotalPrice() + DELIMITER + purchase.getPurchaseDate().getTime());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving purchase history: " + e.getMessage());
        }
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}

class Purchase {
    private String productName;
    private int quantity;
    private double totalPrice;
    private Date purchaseDate;

    public Purchase(String productName, int quantity, double totalPrice, Date purchaseDate) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    @Override
    public String toString() {
        return "Product: " + productName + ", Quantity: " + quantity + ", Total Price: $" + totalPrice + ", Purchase Date: " + purchaseDate;
    }
}
