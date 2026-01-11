package tp_collections_streams_generics.generics.part2;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private String description;
    private int stock;

    public Product(int id, String name, String brand, double price, String description, int stock){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public int getId(){
        return this.id;
    }
}
