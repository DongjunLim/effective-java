package item7;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Code1 {
    public static void main(String[] args){
        Stack s = new Stack();
        for(int i = 1; i <=5; i++)
            s.push(new Node());

        s.pop();
        s.pop();
        System.out.println("참조 해제를 안했을 경우");
        System.out.println(Arrays.toString(s.elements));

        newStack ns = new newStack();
        for(int i = 1; i <=5; i++)
            ns.push(new Node());

        ns.pop();
        ns.pop();
        
        System.out.println("참조 해제를 했을 경우");
        System.out.println(Arrays.toString(ns.elements));
    }
}

class Node{
    int x;
    int y;
    public Node(){
        this.x = 1;
        this.y = 2;
    }
}

class Stack {
    public Object[] elements;
    protected int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if(size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length - 1 == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}


class newStack extends Stack{
    public newStack(){
        super();
    }

    @Override
    public Object pop() {
        if(size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }
}