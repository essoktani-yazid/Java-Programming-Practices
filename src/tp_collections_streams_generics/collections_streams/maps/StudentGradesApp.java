package tp_collections_streams_generics.collections_streams.maps;

import java.util.HashMap;

public class StudentGradesApp {
    public static void main(String[] args){
        HashMap<String, Double> grades = new HashMap<>();

        // 1. Insérer des notes
        grades.put("Student 1", 15.5);
        grades.put("Student 2", 18.0);
        grades.put("Student 3", 12.0);

        // 2 & 3. Augmenter une note
        grades.put("Student 1", grades.get("Student 1") + 2.0);

        // 4. Supprimer une note
        grades.remove("Student 2");

        // 5. Taille de la map
        System.out.println("Nombre d'étudiants : " + grades.size());

        // 6. Moyenne, Max, Min
        double sum = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        for(double grade: grades.values()){
            sum += grade;
            max = Math.max(max, grade);
            min = Math.min(min, grade);
        }
        System.out.println("Moyenne: " + (sum / grades.size()) + ", Max: " + max + ", Min: " + min);

        // 7. Vérifier si une note est égale à 20
        System.out.println("Note 20 présente ? " + grades.containsValue(20.0));

        // 8. Affichage forEach + Lambda
        grades.forEach((name, grade) -> System.out.println(name + ": "+ grade));
    }
}
