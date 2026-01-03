package TP_Strings_Arrays;

import java.util.Arrays;
import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nombre d'étudiants: ");
        int size = scanner.nextInt();

        double[] grades = new double[size];

        for(int i = 0; i < size; i++){
            System.out.print("Entrez la note de l'étudiants N" + (i + 1) + ": " );
            grades[i] = scanner.nextDouble();
        }

//        1. Sort and display the list of grades.
        Arrays.sort(grades);
        String str_grade = Arrays.toString(grades).replace("[", "").replace("]", "");
        System.out.println("Notes: " + str_grade);

//        2. Display the average grade.
        double somme = 0;
        for (double grade: grades){
            somme += grade;
        }
        System.out.printf("Moyenne: %.2f%n", somme / size);

//        3. Display the highest and lowest grades.
//        double min = grades[0], max = grades[0];
//        for (double grade: grades){
//            min = Math.min(min, grade);
//            max = Math.max(max, grade);
//        }
        double min = grades[0], max = grades[size-1];
        System.out.println("La note minimale est: " + min);
        System.out.println("La note maximale est: " + max);

//        4. Display the number of students with a grade entered by the user.
        System.out.print("Quelle note recherchez-vous ? : ");
        double target = scanner.nextDouble();
        int count = 0;
        for (double grade : grades) {
            if (grade == target) count++;
        }
        System.out.println("Nombre d'étudiants ayant " + target + " : " + count);

        scanner.close();
    }
}
