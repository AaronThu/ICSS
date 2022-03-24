package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {

    private HANLinkedListNode<T> firstNode;

    @Override
    public void addFirst(T value) {
        if(firstNode == null) {
            firstNode = new HANLinkedListNode<>(value);

        }
        else{
            HANLinkedListNode<T> newNode = new HANLinkedListNode<>(value);
            newNode.setNextNode(firstNode);
            firstNode = newNode;
        }

//        System.out.print(value);

    }

    @Override
    public void clear() {
        firstNode = null;
    }

    @Override
    public void insert(int index, T value) {
        HANLinkedListNode<T> newNode = new HANLinkedListNode<>(value);
        HANLinkedListNode<T> currentNode = firstNode;

        if(getFirst() == null) {
            firstNode = new HANLinkedListNode<>(value);
        }

        newNode.setNextNode(currentNode.getNextNode());
        currentNode.setNextNode(newNode);

    }

    @Override
    public void delete(int pos) {
        if(pos ==0){
            removeFirst();
        } else {

            HANLinkedListNode<T> currentNode = firstNode;
            int counter = 0;

            while (counter != pos - 1) {
                currentNode = currentNode.getNextNode();
                counter++;
            }
            HANLinkedListNode<T> nodeToBeDeleted = currentNode.getNextNode();
            currentNode.setNextNode(nodeToBeDeleted.getNextNode());
        }
    }

    @Override
    public T get(int pos) {
        HANLinkedListIterator<T> iterator = linkedListIterator();
        for (int i = 0; i < pos; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    @Override
    public void removeFirst() {
    firstNode = firstNode.getNextNode();
    }

    @Override
    public T getFirst() {
        return firstNode.getContent();
    }

    @Override
    public int getSize() {
        return linkedListIterator().getSizeOfLinkedList();
    }


    private HANLinkedListIterator<T> linkedListIterator(){
        return new HANLinkedListIterator<>(firstNode);
    }

}
