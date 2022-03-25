package nl.han.ica.datastructures;

import java.util.HashMap;

public class ScopeChecker<K, V>{
    private IHANLinkedList<HashMap<K,V>> allScopes;

    public ScopeChecker(){
        this.allScopes = new HANLinkedList<>();
    }

    public void push(){
        allScopes.addFirst(new HashMap<>());
    }

    public void pop(){
        allScopes.removeFirst();
    }

    public void put(K key, V value){
        allScopes.getFirst().put(key,value);
    }

    public void printAllScopes(){
        for(int i = 0; i< allScopes.getSize(); i++) {
            System.out.println(allScopes.get(i).toString());
        }
    }

    public V getVariableByKey(K key){
        for(int i = 0; i < allScopes.getSize(); i++){
            if(allScopes.get(i) != null){
                V variable = allScopes.get(i).get(key);
                return variable;
            }
        }
        return null;
    }
}
