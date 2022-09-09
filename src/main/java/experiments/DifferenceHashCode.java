package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.worklists.CircularArrayFIFOQueue;

import java.io.IOException;
import java.util.Random;
import java.util.function.Supplier;


public class DifferenceHashCode {


    public static void main (String[] args) throws IOException {
        int NUM_WARMUP = 500;
        int NUM_TESTS = 100000;
        double HashTime = 0;

        for (int size = 100; size <= 12800; size *= 2) {

            // Runtime of find method:
            ChainingHashTable<AlphabeticString, Integer> cht = new ChainingHashTable(BinarySearchTree::new);

            for (int j = 0; j < size; j++) {

                // Create a new random CircularArray by given size:
                CircularArrayFIFOQueue q = new CircularArrayFIFOQueue<Character>(300);

                for (int k = 0; k < 300; k++) {
                    // Get a random char:
                    Random r = new Random();
                    char c = (char) (r.nextInt(26) + 'a');
                    q.add(c);
                }

                    // Create a random AlphabeticString:
                AlphabeticString str = new AlphabeticString(q);
                cht.insert(str, 0);
                }

            // Starting test the runtime of find:
            for (int i = 0; i < NUM_TESTS; i++) {

                // Create a random str as a random key with the same fixed length = 300
                CircularArrayFIFOQueue newq = new CircularArrayFIFOQueue<Character>(300);
                for (int k = 0; k < 300; k++) {
                    // Get a random char:
                    Random r = new Random();
                    char c = (char) (r.nextInt(26) + 'a');
                    newq.add(c);
                }
                AlphabeticString key = new AlphabeticString(newq);

                long startTime = System.nanoTime();
                cht.find(key);
                long endTime = System.nanoTime();

                if (NUM_WARMUP <= i) {
                    HashTime += (endTime - startTime);
                }
            }

            double averageDecentRuntime = HashTime / (NUM_TESTS - NUM_WARMUP);
            System.out.println("Size(x) " + size + "    Run time of Simple Hashcode "
                    + averageDecentRuntime);





//            for (int i = 0; i < NUM_TESTS; i++) {

//                ChainingHashTable<AlphabeticString, Integer> cht = new ChainingHashTable(BinarySearchTree::new);
//
//                // Average-Case of ChainHashTable:
//                long startTime = System.nanoTime();
//
//                for (int j = 0; j < size; j++) {
//
//                    // Create a new random CircularArray with size:
//                    CircularArrayFIFOQueue q = new CircularArrayFIFOQueue<Character>(300);
//                    for (int k = 0; k < 300; k++) {
//                        // Get a random char:
//                        Random r = new Random();
//                        char c = (char) (r.nextInt(26) + 'a');
//                        q.add(c);
//                    }
//
//                    // Create a random AlphabeticString:
//                    AlphabeticString str = new AlphabeticString(q);
//                    cht.insert(str, 0);
//                }
//                long endTime = System.nanoTime();
//
//                if (NUM_WARMUP <= i) {
//                    HashTime += (endTime - startTime);
//                }
//            }
//
//            double averageDecentRuntime = HashTime / (NUM_TESTS - NUM_WARMUP);
//            System.out.println("Size(x) " + size + "    Run time of Simple Hashcode "
//                    + averageDecentRuntime);
        }

//        for (int i = 0; i < NUM_TESTS; i++) {
//
//            ChainingHashTable<AlphabeticString, Integer> cht = new ChainingHashTable(AVLTree::new);
//
//            // Average-Case of ChainHashTable for inserting elements:
//            long startTime = System.nanoTime();
//            for (int j = 0; j < size; j++) {
//
//                // Create a new random CircularArray with size:
//                CircularArrayFIFOQueue q = new CircularArrayFIFOQueue<Character>(300);
//                for (int k = 0; k < 300; k++) {
//                    // Get a random char:
//                    Random r = new Random();
//                    char c = (char) (r.nextInt(26) + 'a');
//                    q.add(c);
//                }
//
//                // Create a random AlphabeticString as a key:
//                AlphabeticString str = new AlphabeticString(q);
//                cht.insert(str, 0);
//            }
//            long endTime = System.nanoTime();
//
//            if (NUM_WARMUP <= i) {
//                HashTime += (endTime - startTime);
//            }
//        }
//
//        double averageDecentRuntime = HashTime / (NUM_TESTS - NUM_WARMUP);
//        System.out.println("Size(x) " + size + "    Run time of hash function: "
//                + averageDecentRuntime);
    }
}

