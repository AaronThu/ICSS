package nl.han.ica.datastructures;


public class HANStack<T> implements IHANStack<T> {

    IHANLinkedList<T> stackList;

    public HANStack(){
        stackList = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        stackList.addFirst(value);
//        System.out.println("Pushed: " + value);
    }

    @Override
    public T pop() {
        T firstItemOnStack = stackList.getFirst();
        stackList.delete(0);
//        System.out.println("Popped: " + firstItemOnStack);
        return firstItemOnStack;
    }

    @Override
    public T peek() {
//        System.out.println("Peeked: " + stackList.getFirst());
        return stackList.getFirst();

    }
}
