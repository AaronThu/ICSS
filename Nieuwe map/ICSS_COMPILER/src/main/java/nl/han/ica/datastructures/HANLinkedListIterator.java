package nl.han.ica.datastructures;

import java.util.Iterator;

public class HANLinkedListIterator<T> implements Iterator<T> {
    private HANLinkedListNode<T> currentNode;

    public HANLinkedListIterator(HANLinkedListNode<T> headerNode){
        currentNode = headerNode;
    }

    @Override
    public boolean hasNext() {
        return currentNode != null;
    }

    @Override
    public T next() {
        if(!hasNext()) {
            return null;
        }
        HANLinkedListNode<T> oldNode = currentNode;
        currentNode = currentNode.getNextNode();

        return oldNode.getContent();
    }


    public int getSizeOfLinkedList() {
        int currentSize = 0;

        while(hasNext()){
            currentSize++;
            next();
        }
        return currentSize;
    }
}
