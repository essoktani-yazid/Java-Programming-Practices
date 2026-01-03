package TP_Strings_Arrays;

import java.util.Scanner;

public class Exercise2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez un verbe du premier groupe : ");
        String verb = scanner.nextLine().trim().toLowerCase();

        if (!verb.endsWith("er")) {
            System.out.println("Ce n'est pas un verbe terminant par 'er'.");
        } else {
            String radical = verb.substring(0, verb.length() - 2);

            System.out.println("je " + radical + "e");
            System.out.println("tu " + radical + "es");
            System.out.println("il/elle " + radical + "e");

            if (radical.endsWith("g")) {
                System.out.println("nous " + radical + "eons");
            } else {
                System.out.println("nous " + radical + "ons");
            }

            System.out.println("vous " + radical + "ez");
            System.out.println("ils/elles " + radical + "ent");
        }

        scanner.close();
    }
}