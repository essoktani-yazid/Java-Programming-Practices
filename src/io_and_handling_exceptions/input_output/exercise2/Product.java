package io_and_handling_exceptions.input_output.exercise2;

import java.io.Serializable;

public class Product implements Serializable {
    private long id;
    private String name, brand, description;
    private double price;
    private int stock;

    public Product(long id, String name, String brand, double price, String description, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public long getId() { return id; }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Name: " + this.name + " | Brand: " + this.brand + " | Price: " + this.price + " | Description: " + this.description + " | Stock: " + this.stock;
    }
}