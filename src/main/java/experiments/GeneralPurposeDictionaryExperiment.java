package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.*;

public class GeneralPurposeDictionaryExperiment {
    private static int NUM_TESTS = 20000;
    private static int NUM_WARMUP = 3000;

    public static void main(String[] args) throws IOException {

        List<AlphabeticString> lst = new ArrayList<>();

        Scanner scn = new Scanner(new File("corpus/alice.txt"));
        while (scn.hasNext()) {
            AlphabeticString str = new AlphabeticString(scn.next());
            lst.add(str);
        }

        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            Random r = new Random(i);

            // Types of Dictionaries
            Dictionary<AlphabeticString, Integer> d = new BinarySearchTree<>();
            //Dictionary<AlphabeticString, Integer> d = new AVLTree<>();
            //Dictionary<AlphabeticString, Integer> d = new ChainingHashTable<>(MoveToFrontList::new);
            //Dictionary<AlphabeticString, Integer> d = new HashTrieMap<>(AlphabeticString.class);

            // Unsorted
            long startTime = System.nanoTime();
            for (AlphabeticString str : lst) {
                if (d.find(str) == null) {
                    d.insert(str, 1);
                } else {
                    d.insert(str, d.find(str) + 1);
                }
            }

            long endTime = System.nanoTime();

            // Sorted
            PriorityQueue<AlphabeticString> pr = new PriorityQueue<>((a, b) -> a.compareTo(b));
            for (AlphabeticString str : lst) {
                pr.add(str);
            }

            //long startTime = System.nanoTime();
            while (!pr.isEmpty()) {
                AlphabeticString str = pr.poll();
                if (d.find(str) == null) {
                    d.insert(str, 1);
                } else {
                    d.insert(str, d.find(str) + 1);
                }
            }
            //long endTime = System.nanoTime();


            // Find
            int start = r.nextInt(lst.size() - 100);
            //long startTime = System.nanoTime();

            for (int j = 0; j < 100; j++) {
                d.find(lst.get(start + j));
            }

            //long endTime = System.nanoTime();
            if (NUM_WARMUP <= i) {
                totalTime += endTime - startTime;
            }

        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(averageRuntime);
    }


}

