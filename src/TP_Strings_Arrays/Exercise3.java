package TP_Strings_Arrays;

import java.util.Scanner;

public class Exercise3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String currentString = "";
        int choice;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Saisir la chaîne");
            System.out.println("2. Afficher la chaîne");
            System.out.println("3. Inverser la chaîne");
            System.out.println("4. Compter les mots");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Entrez une chaîne : ");
                    currentString = scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Chaîne actuelle : " + currentString);
                    break;
                case 3:
                    String reversed = new StringBuilder(currentString).reverse().toString();
                    System.out.println("Chaîne inversée : " + reversed);
                    break;
                case 4:
                    if (currentString.trim().isEmpty()) {
                        System.out.println("Nombre de mots : 0");
                    } else {
                        String[] words = currentString.trim().split("\\s+");
                        System.out.println("Nombre de mots : " + words.length);
                    }
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

            if (choice != 0) {
                System.out.println("Appuyez sur Entrée pour revenir au menu...");
                scanner.nextLine();
            }

        } while (choice != 0);

        scanner.close();
    }
}