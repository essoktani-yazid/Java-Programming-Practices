package tp_collections_streams_generics.generics.part1;

public class Application {
    public static void main(String[] args) {
        // Test avec Integer
        GenericStorage<Integer> intStorage = new GenericStorage<>();
        intStorage.addElement(10);
        intStorage.addElement(20);
        System.out.println("Taille Integer: " + intStorage.getSize());

        // Test avec String
        GenericStorage<String> stringStorage = new GenericStorage<>();
        stringStorage.addElement("Java");
        stringStorage.addElement("Generics");
        System.out.println("Élément 0: " + stringStorage.getElement(0));

        // Test avec Double
        GenericStorage<Double> doubleStorage = new GenericStorage<>();
        doubleStorage.addElement(15.5);
        doubleStorage.removeElement(0);
        System.out.println("Taille Double après suppression: " + doubleStorage.getSize());
    }
}
