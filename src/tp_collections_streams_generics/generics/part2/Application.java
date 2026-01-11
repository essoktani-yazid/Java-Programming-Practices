package tp_collections_streams_generics.generics.part2;

import java.util.Scanner;

public class Application {
    public static void main(String[] args){
        MetierProduitImpl metier = new MetierProduitImpl();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Afficher la liste des produits");
            System.out.println("2. Rechercher un produit par ID");
            System.out.println("3. Ajouter un nouveau produit");
            System.out.println("4. Supprimer un produit par ID");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    metier.getAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("ID à rechercher : ");
                    int idSearch = scanner.nextInt();
                    Product p = metier.findById(idSearch);
                    System.out.println(p != null ? p : "Produit non trouvé.");
                    break;
                case 3:
                    System.out.print("ID : "); int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nom : "); String name = scanner.nextLine();
                    System.out.print("Marque : "); String brand = scanner.nextLine();
                    System.out.print("Prix : "); double price = scanner.nextDouble();
                    metier.add(new Product(id, name, brand, price, "", 0));
                    break;
                case 4:
                    System.out.print("ID à supprimer : ");
                    int idDel = scanner.nextInt();
                    metier.delete(idDel);
                    break;
            }

        }while (choice != 5);
    }
}
