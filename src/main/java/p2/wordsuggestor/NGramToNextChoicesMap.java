package p2.wordsuggestor;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.sorts.InsertionSort;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.sorts.TopKSort;
import p2.sorts.HeapSort;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Supplier;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    public void seenWordAfterNGram(NGram ngram, String word) {
        Dictionary<AlphabeticString, Integer> counter = map.find(ngram);
        if (counter == null) {
            counter = newInner.get();
            map.insert(ngram, counter);
        }

        Integer prev = counter.find(new AlphabeticString(word));
        if (prev == null) {
            prev = 0;
        }
        counter.insert(new AlphabeticString(word), prev + 1);

    }

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram the ngram we want the counts for
     * @return An array of all the Items for the requested ngram.
     */
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        if (ngram == null) {
            return (Item<String, Integer>[]) new Item[0];
        }
        Dictionary<AlphabeticString, Integer> counter = map.find(ngram);
        Item<String, Integer>[] result = (Item<String, Integer>[]) new Item[counter != null
                ? counter.size() : 0];
        if (counter != null) {
            Iterator<Item<AlphabeticString, Integer>> it = counter.iterator();

            for (int i = 0; i < result.length; i++) {
                Item<AlphabeticString, Integer> item = it.next();
                result[i] = new Item<String, Integer>(item.key.toString(),
                        item.value);
            }
        }
        return result;

    }

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        Comparator<Item<String, Integer>> comp_small = new SmallValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            //InsertionSort.sort(afterNGrams, comp);
            HeapSort.sort(afterNGrams, comp);
        } else {
            // You must fix this line toward the end of the project
            TopKSort.sort(afterNGrams, k, comp_small);
            if (afterNGrams.length != 0) {
                for (int i = 0; i < k/2; i++) {
                    if (i < afterNGrams.length && k - i - 1 < afterNGrams.length) {
                        Item<String, Integer> temp = afterNGrams[i];
                        afterNGrams[i] = afterNGrams[k - i - 1];
                        afterNGrams[k - i - 1] = temp;
                    }
                }
            }
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
//        String[] temp = new String[nextWords.length];
//        for (int i = 0; i < temp.length; i++) {
//            temp[i] = nextWords[temp.length - 1 - i];
//        }
//        nextWords = temp;
        return nextWords;
    }

    private class SmallValueFirstItemComparator<K extends Comparable<K>, V extends Comparable<V>>
            implements Comparator<Item<K, V>> {
        @Override
        public int compare(Item<K, V> e1, Item<K, V> e2) {
            int result = e1.value.compareTo(e2.value);
            if (result != 0) {
                return result;
            }
            return e2.key.compareTo(e1.key);
        }
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
