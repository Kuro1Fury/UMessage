package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private final int capacity[] = {29, 61, 127, 257, 541, 1039, 2111, 4229, 8887, 16927, 35393, 71597, 144271, 289657};
    private int resizeIndex;
    private Dictionary<K, V>[] array;
    private double lambda;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        resizeIndex = 0;
        array = new Dictionary[29];

        // initialize what Dictionary used in array.
        for (int i = 0; i < array.length; i++) {
            array[i] = newChain.get();
        }

        resizeIndex = 0;
        lambda = 0.0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // Check if we need to reSize array
        if(lambda >= 1) {
            this.resize();
        }

        int HashKey = key.hashCode();           //Get hashcode of Key
        // insert abs
        int index = Math.abs(HashKey % array.length);

        if (array[index] == null) {
            array[index] = newChain.get();
        }

        V preValue = array[index].insert(key, value);
        if (preValue == null) {
            size++;
        }
        this.lambda = (double) size / array.length;     // update lambda

        return preValue;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        int HashKey = key.hashCode();         // 32bit int if overflow hashcode can be negative.
        int index = Math.abs(HashKey % array.length);

        if (array[index] == null) {
            return null;
        }
        return array[index].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        if (array[0] == null) {
            array[0] = newChain.get();
        }
        Iterator<Item<K, V>> it = new Iterator<Item<K, V>>() {
            int idx = 0;
            Iterator<Item<K, V>> curIt = array[0].iterator();
            int numLeft = size;

            @Override
            public boolean hasNext() {
                return numLeft > 0;
            }

            @Override
            public Item<K, V> next() {
                if (numLeft <= 0) {
                    throw new NoSuchElementException("No Item Left!!!");
                }
                if (curIt.hasNext()) {
                    numLeft--;
                    return curIt.next();
                }
                idx++;
                while (array[idx] == null || array[idx].isEmpty()) {
                    idx++;
                }
                curIt = array[idx].iterator();
                numLeft--;
                return curIt.next();
            }
        };
        return it;
    }


    private void resize() {
        resizeIndex++;     // Move index to next in capacity[]
        Dictionary<K,V>[] newArray;
        if (resizeIndex >= capacity.length) {
            newArray = new Dictionary[array.length * 2];
        } else {
            newArray = new Dictionary[capacity[resizeIndex]];
        }

        this.array = reHash(newArray);      // Change field
        this.lambda = (double) size / array.length;     // update lambda
    }

    private Dictionary<K,V>[] reHash(Dictionary<K,V>[] newArray) {
        int capacity = newArray.length;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                for (Item<K, V> item : array[i]) {
                    int HashKey = item.key.hashCode();
                    int index = Math.abs(HashKey % capacity);
                    if (newArray[index] == null) {
                        newArray[index] = newChain.get();
                    }
                    newArray[index].insert(item.key, item.value);
                }
            }

        }
        return newArray;
    }


    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
