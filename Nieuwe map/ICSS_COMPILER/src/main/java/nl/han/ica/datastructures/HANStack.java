package nl.han.ica.datastructures;


public class HANStack<T> implements IHANStack<T> {

    IHANLinkedList<T> stackList;

    public HANStack(){
        stackList = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        stackList.addFirst(value);
    }

    @Override
    public T pop() {
        T firstItemOnStack = stackList.getFirst();
        stackList.delete(0);
        return firstItemOnStack;
    }

    @Override
    public T peek() {
        return stackList.getFirst();

    }
}
