package tp_collections_streams_generics.generics.part1;

import java.util.ArrayList;

public class GenericStorage<T> {
    ArrayList<T> elements = new ArrayList<>();

    public void addElement(T o){
        elements.add(o);
    }

    public void removeElement(int index){
        elements.remove(index);
    }

    public T getElement(int index){
        return elements.get(index);
    }

    public int getSize(){
        return elements.size();
    }
}
