package io_and_handling_exceptions.input_output.exercise2;

import io_and_handling_exceptions.input_output.exercise2.MetierProduitImpl;

import java.util.Scanner;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        MetierProduitImpl metier = new MetierProduitImpl();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 6) {
            System.out.println("\n--- PRODUCT MANAGEMENT MENU ---");
            System.out.println("1. Display the list of products");
            System.out.println("2. Search for a product by its ID");
            System.out.println("3. Add a new product to the list");
            System.out.println("4. Delete a product by ID");
            System.out.println("5. Save the products (to products.dat)");
            System.out.println("6. Exit this program");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    List<Product> products = metier.getAll();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        products.forEach(System.out::println);
                    }
                    break;

                case 2:
                    System.out.print("Enter ID to search: ");
                    long searchId = scanner.nextLong();
                    Product found = metier.findById(searchId);
                    System.out.println(found != null ? found : "Product not found.");
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    long id = scanner.nextLong();
                    scanner.nextLine(); // consommer le \n

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();

                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();

                    System.out.print("Enter Stock Quantity: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();

                    metier.add(new Product(id, name, brand, price, desc, stock));
                    System.out.println("Product added successfully.");
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    long delId = scanner.nextLong();
                    metier.delete(delId);
                    System.out.println("Deletion completed.");
                    break;

                case 5:
                    metier.saveAll();
                    break;

                case 6:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}