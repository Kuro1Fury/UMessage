package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] arr;
    private int end;

    public ArrayStack() {
        end = -1;
        arr = (E[]) new Object[10];
    }

    @Override
    public void add(E work) {
        if (end == arr.length - 1) {
            E[] temp = (E[]) new Object[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                temp[i] = arr[i];
            }
            arr = temp;
        }
        end++;
        arr[end] = work;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return arr[end];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E val = arr[end];
        end--;
        return val;
    }

    @Override
    public int size() {
        return end + 1;
    }

    @Override
    public void clear() {
        end = -1;
        arr = (E[]) new Object[10];
    }
}
