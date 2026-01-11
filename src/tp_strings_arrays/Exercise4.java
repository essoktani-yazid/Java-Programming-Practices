package tp_strings_arrays;

import java.util.Scanner;

public class Exercise4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] nb_occurrences = new int[26];

        System.out.print("Entrez une ligne de texte (max. 100 caractères) : ");
        String input = scanner.nextLine();

        String upperInput = input.toUpperCase();

        for (int i = 0; i < upperInput.length(); i++) {
            char c = upperInput.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                nb_occurrences[c - 'A']++;
            }
        }

        System.out.println("La chaîne \"" + input + "\" contient :");
        for (int i = 0; i < nb_occurrences.length; i++) {
            if (nb_occurrences[i] > 0) {
                char letter = (char) ('A' + i);
                System.out.println(nb_occurrences[i] + " fois la lettre '" + letter + "'");
            }
        }

        scanner.close();
    }
}