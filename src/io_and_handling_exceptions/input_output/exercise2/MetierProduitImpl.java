package io_and_handling_exceptions.input_output.exercise2;

import java.io.*;
import java.util.*;

public class MetierProduitImpl implements IProduitMetier {
    private List<Product> products = new ArrayList<>();
    private final String FILE_NAME = "products.dat";

    @Override
    public void add(Product p) {
        products.add(p);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAll() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (List<Product>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error loading file: " + e.getMessage());
            }
        }
        return products;
    }

    @Override
    public Product findById(long id) {
        for (Product product : products) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    @Override
    public void delete(long id) {
        products.removeIf(p -> p.getId() == id);
    }

    @Override
    public void saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}