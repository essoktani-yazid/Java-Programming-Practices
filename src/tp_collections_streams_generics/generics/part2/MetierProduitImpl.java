package tp_collections_streams_generics.generics.part2;

import java.util.ArrayList;
import java.util.List;

public class MetierProduitImpl<T> implements IMetier<Product>{

    List<Product> products = new ArrayList<>();

    @Override
    public void add(Product p) {
        products.add(p);
    }

    @Override
    public List<Product> getAll() {
        return this.products;
    }

    @Override
    public Product findById(long id) {
        for(Product product: products){
            if (product.getId() == id) return product;
        }
        return null;
    }

    @Override
    public void delete(long id) {
        for(Product product: products){
            products.removeIf(p -> p.getId() == id);
        }
    }
}
