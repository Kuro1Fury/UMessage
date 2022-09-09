package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (array.length == 0) {
            return;
        }
        MinFourHeap h = new MinFourHeap(comparator);
        int i;
        k = Math.min(array.length, k);
        for (i = 0; i < k; i++) {
            h.add(array[i]);
        }
        while (i < array.length) {
            if (comparator.compare(array[i], (E) h.peek()) > 0) {
                h.next();
                h.add(array[i]);
            }
            array[i] = null;
            i++;
        }
        for (int j = 0; j < k; j++) {
            array[j] = (E) h.next();
        }
    }
}
