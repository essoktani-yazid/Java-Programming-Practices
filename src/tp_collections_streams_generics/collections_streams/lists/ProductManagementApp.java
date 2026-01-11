package tp_collections_streams_generics.collections_streams.lists;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductManagementApp {
    public static void main(String[] args) {
        // Créer l'ArrayList
        ArrayList<Product> products = new ArrayList<>();

        // Ajouter des produits [cite: 160]
        products.add(new Product(1, "Clavier", 25.0));
        products.add(new Product(2, "Souris", 15.0));
        products.add(new Product(3, "Ecran", 150.0));

        // Supprimer un produit par index
        products.remove(1);

        // Modifier un produit par index
        products.set(0, new Product(1, "Clavier Gamer", 45.0));

        // Afficher la liste 
        System.out.println("Liste des produits :");
        products.forEach(System.out::println);

        // Rechercher un produit dont le nom est saisi par l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEntrez le nom du produit à rechercher : ");
        String searchName = scanner.nextLine();

        boolean trouve = false;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(searchName)) {
                System.out.println("Produit trouvé : " + p);
                trouve = true;
                break;
            }
        }

        if (!trouve) {
            System.out.println("Aucun produit trouvé avec ce nom.");
        }

        scanner.close();
    }
}