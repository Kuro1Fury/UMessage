package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] arr;
    public int start;
    public int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        start = 0;
        size = 0;
        arr = (E[]) new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException("The worklist is full!!!!");
        }
        arr[(start + size) % arr.length] = work;
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException("The worklist is empty!!!!");
        }
        return arr[start];
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException("The worklist is empty!!!!");
        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("No such work!!!!");
        }
        return arr[(start + i) % arr.length];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException("The worklist is empty!!!!");
        }
        E temp = arr[start];
        start++;
        if (start == arr.length) {
            start = 0;
        }
        size--;
        return temp;
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException("The worklist is empty!!!!");
        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("No such work!!!!");
        }
        arr[(start + i) % arr.length] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        start = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        if (this.equals(other)) {
            return 0;
        }
        int size = Math.min(this.size(), other.size());
        for (int i = 0; i < size; i++) {
            if (!this.peek(i).equals(other.peek(i))) {
                return this.peek(i).compareTo(other.peek(i));
            }
        }
        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (other.size() != this.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!this.peek(i).equals(other.peek(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
//        return 17;
        int c = 0;
        for (int i = start; i < size() + start; i++) {
            c *= 37;
            if (arr[i] != null) {
                c += arr[i].hashCode() * i + arr.length * start * 73;
            }
        }
        return c;
    }
}
