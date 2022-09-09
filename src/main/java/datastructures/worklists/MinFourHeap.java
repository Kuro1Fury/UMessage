package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int height;
    private int size;
    private Comparator<E> c;

    public MinFourHeap(Comparator<E> c) {
        data = (E[]) new Object[1];
        height = 0;
        size = 0;
        this.c = c;
    }

    @Override
    public boolean hasWork() {
        return size != 0;
    }

    @Override
    public void add(E work) {
        if (work == null) {
            throw new IllegalArgumentException("Don't add null work!!");
        }
        if (size == data.length) {
            resize();
        }
        size++;

        int i = percolateUp(size - 1, work);
        data[i] = work;
    }

    private void resize() {
        height++;
        int newSize = (int) (1 - Math.pow(4, height + 1)) / (-3);
        E[] array = (E[]) new Object[newSize];
        for(int i = 0; i < data.length; i++) {
            array[i] = data[i];
        }
        data = array;
    }

    private int percolateUp(int hole, E val) {
        while (hole > 0 && c.compare(val, data[(int) Math.floor((hole - 1) / 4)]) < 0 )  {
            data[hole] = data[(int) Math.floor((hole - 1) / 4)];
            hole = (int) Math.floor((hole - 1) / 4);
        }
        return hole;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException("don't have work!!!");
        }
        return (E) data[0];
    }

    @Override
    public E next() {
        if(!hasWork()) {
            throw new NoSuchElementException("don't have work!!!");
        }
        E ans = data[0];
        int hole = percolateDown(0, data[size - 1]);
        data[hole] = data[size - 1];
        size--;
        return ans;
    }

    private int percolateDown(int hole, E val) {
        while (4 * hole + 1 < size) {
            int target = minChild(hole);
            if (c.compare(data[target], val) < 0) {
                data[hole] = data[target];
                hole = target;
            } else {
                break;
            }
        }
        return hole;
    }

    private int minChild(int hole) {
        int minIdx = 4 * hole + 1;
        for (int i = 4 * hole + 2; i < 4 * hole + 5; i++) {
            if (i >= size) {
                break;
            }
            if (c.compare(data[i], data[minIdx])< 0) {
                minIdx = i;
            }
        }
        return minIdx;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = (E[]) new Object[1];
        height = -1;
        size = 0;
    }
}
