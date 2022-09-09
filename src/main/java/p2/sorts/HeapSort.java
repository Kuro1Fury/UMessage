package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap h = new MinFourHeap(comparator);
        for (E i : array) {
            h.add(i);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = (E) h.next();
        }
    }
}
