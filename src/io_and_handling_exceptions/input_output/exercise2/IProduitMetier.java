package io_and_handling_exceptions.input_output.exercise2;

import java.util.List;

public interface IProduitMetier {
    void add(Product p);
    List<Product> getAll();
    Product findById(long id);
    void delete(long id);
    void saveAll();
}
