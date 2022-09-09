package Experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BSTvsAVL {
    private static int NUM_WARMUP = 100;
    private static int NUM_TESTS = 10000;

    public static void main(String[] args) throws IOException {

        //CONSTRUCT OR INSERT RUNTIME
        for (int size = 100; size <= 1000; size += 100 ) {
            double totalTimeBST = 0;
            double totalTimeAVL = 0;

            for (int j = 0; j < NUM_TESTS; j++) {
                long startTimeAVL = System.nanoTime();
                AVLTree<Integer, Integer> avl = new AVLTree<>();
                for (int k = 0; k < size; k++) {
                    avl.insert(k, k);
                }
                long endTimeAVL = System.nanoTime();


                long startTimeBST = System.nanoTime();
                BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
                for (int k = 0; k < size; k++) {
                    bst.insert(k, k);
                }
                long endTimeBST = System.nanoTime();

                if (NUM_WARMUP <= j) {
                    totalTimeAVL += (endTimeAVL - startTimeAVL);
                    totalTimeBST += (endTimeBST - startTimeBST);
                }
            }
            double averageRuntimeBST = totalTimeAVL / (NUM_TESTS - NUM_WARMUP);
            double averageRuntimeAVL = totalTimeBST / (NUM_TESTS - NUM_WARMUP);
            System.out.println("Size(x): " + size + " AVL runtime: " + averageRuntimeBST
                    + " BST Runtime: " + averageRuntimeAVL);
        }


//        // FIND() RUNTIME
//        for (int size = 100; size <= 1000; size += 100) {
//            AVLTree<Integer, Integer> avl = new AVLTree<>();
//            for (int k = 0; k < size; k++) {
//                avl.insert(k, k);
//            }
//
//            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
//            for (int k = 0; k < size; k++) {
//                bst.insert(k, k);
//            }
//
//            double totalTimeBST = 0;
//            double totalTimeAVL = 0;
//
//            for (int j = 0; j < NUM_TESTS; j++) {
//                long startTimeAVL = System.nanoTime();
//                avl.find(size);
//                long endTimeAVL = System.nanoTime();
//
//
//                long startTimeBST = System.nanoTime();
//                bst.find(size);
//                long endTimeBST = System.nanoTime();
//
//                if (NUM_WARMUP <= j) {
//                    totalTimeAVL += (endTimeAVL - startTimeAVL);
//                    totalTimeBST += (endTimeBST - startTimeBST);
//                }
//            }
//            double averageRuntimeBST = totalTimeAVL / (NUM_TESTS - NUM_WARMUP);
//            double averageRuntimeAVL = totalTimeBST / (NUM_TESTS - NUM_WARMUP);
//            System.out.println("Size(x): " + size + " AVL runtime: " + averageRuntimeBST
//                    + " BST Runtime: " + averageRuntimeAVL);
//        }

    }
}
