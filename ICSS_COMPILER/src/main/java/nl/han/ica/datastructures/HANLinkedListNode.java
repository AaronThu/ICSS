package nl.han.ica.datastructures;

public class HANLinkedListNode <T>{

    private HANLinkedListNode<T> nextNode;
    private T content;

    public HANLinkedListNode(T content){
        this.content = content;
    }

    public HANLinkedListNode<T> getNextNode(){
        return nextNode;
    }

    public void setNextNode(HANLinkedListNode<T> nextNode){
        this.nextNode = nextNode;
    }

    public T getContent() {
        return content;
    }

}
