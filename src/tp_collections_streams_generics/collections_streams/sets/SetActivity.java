package tp_collections_streams_generics.collections_streams.sets;

import java.util.HashSet;

public class SetActivity {
    public static void main(String[] ars){
        HashSet<String> groupA = new HashSet<>();
        HashSet<String> groupB = new HashSet<>();

        groupA.add("Student 1"); groupA.add("Student 2");
        groupB.add("Student 2"); groupB.add("Student 3");
        System.out.println(groupA);

        // Intersection
        HashSet<String> intersect = new HashSet<>(groupA);
        intersect.retainAll(groupB);
        System.out.println("Intersection : " + intersect);

        // Union
        HashSet<String> union = new HashSet<>(groupA);
        union.addAll(groupB);
        System.out.println("Union : " + union);
    }
}
