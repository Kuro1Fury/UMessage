package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array,0,array.length - 1, comparator);
    }

    private static <E> void swap(E[] array, int a, int b) {
        E temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private static <E> E[] quickSort(E[] array, int l, int h, Comparator<E> comparator) {
        if (l < h) {
            int piv = partition(array, l, h, comparator);
            quickSort(array, l, piv - 1, comparator);
            quickSort(array, piv + 1, h, comparator);
        }
        return array;

    }

    private static <E> int partition(E[] array, int l, int h, Comparator<E> comparator) {
        E pivot = array[h];
        int i = l - 1;
        for (int j = l; j < h; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, h);
        return i + 1;
    }
}
