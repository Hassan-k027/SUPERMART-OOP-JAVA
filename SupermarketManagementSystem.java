import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


class Product {
    String name;
    int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}


class ProductList {
    ArrayList<Product> products;

    public ProductList() {
        products = new ArrayList<>();

        products.add(new Product("Sugar", 100));
        products.add(new Product("Rice", 256));
        products.add(new Product("Sauce", 120));
        products.add(new Product("Flour", 105));
        products.add(new Product("Soap", 120));
        products.add(new Product("Powder", 120));
        products.add(new Product("Shampoo", 130));
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product added: " + product.name);
    }

    public void removeProduct(int index) {
        if (index >= 0 && index < products.size()) {
            System.out.println("Product removed: " + products.get(index).name);
            products.remove(index);
        } else {
            System.out.println("Invalid index. No product removed.");
        }
    }

    public void displayProducts() {
        System.out.println("Available Products:");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println(i + ": " + product.name + " - $" + product.price);
        }
    }
}


class Admin {
    String username;
    String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String inputUsername, String inputPassword) {
        return username.equals(inputUsername) && password.equals(inputPassword);
    }

    public void addProduct(ProductList productList, String name, int price) {
        productList.addProduct(new Product(name, price));
    }

    public void removeProduct(ProductList productList, int index) {
        productList.removeProduct(index);
    }
}


class Customer {
    String name;
    String phoneNumber;
    ArrayList<Product> cart;

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cart = new ArrayList<>();
    }

    public void displayInfo() {
        System.out.println("Customer Name: " + name);
        System.out.println("Phone Number: " + phoneNumber);
    }

    public void addToCart(Product product) {
        cart.add(product);
        System.out.println(product.name + " has been added to your cart.");
    }

    public void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Please add items to your cart before checking out.");
            return;
        }
    
    
        JFrame frame = new JFrame("Customer Cart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
    
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Cart Items"));
    
    
        final int total = cart.stream().mapToInt(product -> product.price).sum();
    
        for (Product product : cart) {
            JLabel productLabel = new JLabel(product.name + " - $" + product.price);
            cartPanel.add(productLabel);
        }
    
        JLabel totalLabel = new JLabel("Total Payment: $" + total);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> {
    
            cart.clear();
            frame.dispose();
    
    
            showThankYouGUI(total);
        });
    
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(cartPanel, BorderLayout.CENTER);
        frame.add(totalLabel, BorderLayout.SOUTH);
        frame.add(payButton, BorderLayout.NORTH);
    
        frame.setVisible(true);
    }
    
    
    private void showThankYouGUI(int total) {
        JFrame thankYouFrame = new JFrame("Thank You");
        thankYouFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        thankYouFrame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel thankYouLabel = new JLabel("Thank You for Your Purchase!");
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 16));
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel totalLabel = new JLabel("Total Paid: $" + total);
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> thankYouFrame.dispose());

        panel.add(thankYouLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        panel.add(totalLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); 
        panel.add(closeButton);

        thankYouFrame.add(panel);
        thankYouFrame.setVisible(true);
    }
}


public class SupermarketManagementSystem {
    private ProductList productList = new ProductList();
    private Admin admin;

    public SupermarketManagementSystem() {
        
        admin = new Admin("admin", "admin123");
    }

    public static void main(String[] args) {
        SupermarketManagementSystem system = new SupermarketManagementSystem();
        Scanner scanner = new Scanner(System.in);

        
        while (true) {
            System.out.println("\nWelcome to the Supermarket Management System!");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int mainChoice = scanner.nextInt();

            if (mainChoice == 1) {
                // Admin login
                System.out.print("Enter admin username: ");
                String username = scanner.next();
                System.out.print("Enter admin password: ");
                String password = scanner.next();

                if (!system.admin.login(username, password)) {
                    System.out.println("Invalid credentials. Exiting.");
                    return;
                }

                
                while (true) {
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. Add Product");
                    System.out.println("2. Remove Product");
                    System.out.println("3. View Products");
                    System.out.println("0. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            scanner.nextLine(); 
                            System.out.print("Enter product name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter product price: ");
                            int price = scanner.nextInt();
                            system.admin.addProduct(system.productList, name, price);
                            break;
                        case 2:
                            System.out.print("Enter product index to remove: ");
                            int index = scanner.nextInt();
                            system.admin.removeProduct(system.productList, index);
                            break;
                        case 3:
                            system.productList.displayProducts();
                            break;
                        case 0:
                            System.out.println("Logging out.");
                            break;
                        default:
                            System.out.println("Invalid choice! Please try again.");
                    }
                    if (choice == 0) break; 
                }
            } else if (mainChoice == 2) {
                
                scanner.nextLine(); 
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();
                System.out.print("Enter your phone number: ");
                String phoneNumber = scanner.nextLine();
                Customer customer = new Customer(name, phoneNumber);
                customer.displayInfo();

                
                while (true) {
                    System.out.println("\nCustomer Menu:");
                    System.out.println("1. View Products");
                    System.out.println("2. Add Product to Cart");
                    System.out.println("3. Checkout");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    int customerChoice = scanner.nextInt();

                    if (customerChoice == 1) {
                        system.productList.displayProducts();
                    } else if (customerChoice == 2) {
                        System.out.print("Enter product index to add to cart: ");
                        int productIndex = scanner.nextInt();
                        if (productIndex >= 0 && productIndex < system.productList.products.size()) {
                            customer.addToCart(system.productList.products.get(productIndex));
                        } else {
                            System.out.println("Invalid product index.");
                        }
                    } else if (customerChoice == 3) {
                        customer.checkout();
                    } else if (customerChoice == 4) {
                        System.out.println("Thank you for visiting the supermarket!");
                        break;
                    } else {
                        System.out.println("Invalid choice! Please try again.");
                    }
                }
            } else if (mainChoice == 0) {
                System.out.println("Exiting the program.");
                break; 
            } else {
                System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
}