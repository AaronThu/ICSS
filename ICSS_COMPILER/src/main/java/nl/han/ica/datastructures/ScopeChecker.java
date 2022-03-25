package nl.han.ica.datastructures;

import java.util.HashMap;

public class ScopeChecker<K, V>{
    private IHANLinkedList<HashMap<K,V>> hashes;

    public ScopeChecker(){
        this.hashes = new HANLinkedList<>();
    }

    public void push(){
        hashes.addFirst(new HashMap<>());
    }

    public void pop(){
        hashes.removeFirst();
    }

    public void put(K key, V value){
        hashes.getFirst().put(key,value);
    }

    public V getVariableByKey(K key){
        for(int i = 0; i < hashes.getSize(); i++){
            if(hashes.get(i) != null){
                V variable = hashes.get(i).get(key);
                return variable;
            }
        }


        return null;
    }
}
