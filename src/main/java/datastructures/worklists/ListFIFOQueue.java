package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private Node<E> start;
    private Node<E> end;
    private int size;

    public ListFIFOQueue() {
        start = null;
        end = null;
        size = 0;
    }

    @Override
    public void add(E work) {
        Node<E> n = new Node<E>(work);
        if (!hasWork()) {
            start = n;
            end = n;
        } else {
            end.next = n;
            end = end.next;
        }
        size ++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return start.value;
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        E work = start.value;
        if (start.next == null) {
            end = null;
        }
        start = start.next;
        size --;
        return work;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        start = null;
        end = null;
    }

    private class Node<E> {
        public E value;
        public Node<E> next;

        public Node(E value) {
            this.value = value;
            this.next = null;
        }

        public Node(E value, Node n) {
            this.value = value;
            this.next = n;
        }

    }
}
