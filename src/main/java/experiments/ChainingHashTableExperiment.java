package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;

import java.io.IOException;
import java.util.Random;

public class ChainingHashTableExperiment {
    private static int NUM_WARMUP = 50;
    private static int NUM_TESTS = 200;
    private static int[] arr_insert;
    private static int[] arr_find;

    public static void main(String[] args) throws IOException {

        for (int size = 1000; size <= 256000; size *= 2) {

            double totalConstructTimeMTF = 0;
            double totalFindTimeMTF = 0;
            double totalConstructTimeBST = 0;
            double totalFindTimeBST = 0;
            double totalConstructTimeAVL = 0;
            double totalFindTimeAVL = 0;

            int find_size = 100000;

//            int randomRuns = 1=;

//            for (int j = 0; j < randomRuns; j++) {
//                arr_insert = new int[size];
//                for (int i = 0; i < size; i++) {
//                    arr_insert[i] = new Random().nextInt(Integer.MAX_VALUE);
//                }
//                arr_find = new int[find_size];
//                for (int i = 0; i < find_size; i++) {
//                    arr_find[i] = arr_insert[new Random().nextInt(size)];
//                }
//
//                for (int i = 0; i < NUM_TESTS; i++) {
//                    double[] MTF = timeTest(size, new ChainingHashTable<>(MoveToFrontList::new));
//                    if (NUM_WARMUP <= i) {
//                        totalConstructTimeMTF += MTF[0];
//                        totalFindTimeMTF += MTF[1];
//                    }
//                }
//
//                for (int i = 0; i < NUM_TESTS; i++) {
//                    double[] BST = timeTest(size, new ChainingHashTable<>(BinarySearchTree::new));
//                    if (NUM_WARMUP <= i) {
//                        totalConstructTimeBST += BST[0];
//                        totalFindTimeBST += BST[1];
//                    }
//                }
//
//                for (int i = 0; i < NUM_TESTS; i++) {
//                    double[] AVL = timeTest(size, new ChainingHashTable<>(AVLTree::new));
//                    if (NUM_WARMUP <= i) {
//                        totalConstructTimeAVL += AVL[0];
//                        totalFindTimeAVL += AVL[1];
//                    }
//                }
//
//            }
//            System.out.println("MTF: " + "size: " + size + " averageInsertRuntime: " + totalConstructTimeMTF / (randomRuns * (NUM_TESTS - NUM_WARMUP))
//                    + " averageFindRuntime: " + totalFindTimeMTF / (randomRuns * (NUM_TESTS - NUM_WARMUP)));
//            System.out.println("BST: " + "size: " + size + " averageInsertRuntime: " + totalConstructTimeBST / (randomRuns * (NUM_TESTS - NUM_WARMUP))
//                    + " averageFindRuntime: " + totalFindTimeBST / (randomRuns * (NUM_TESTS - NUM_WARMUP)));
//            System.out.println("AVL: " + "size: " + size + " averageInsertRuntime: " + totalConstructTimeAVL / (randomRuns * (NUM_TESTS - NUM_WARMUP))
//                    + " averageFindRuntime: " + totalFindTimeAVL / (randomRuns * (NUM_TESTS - NUM_WARMUP)));

            for (int j = 0; j < NUM_TESTS; j++) {
                arr_insert = new int[size];
                for (int i = 0; i < size; i++) {
                    arr_insert[i] = new Random().nextInt(Integer.MAX_VALUE);
                }
                arr_find = new int[find_size];
                for (int i = 0; i < find_size; i++) {
                    arr_find[i] =  new Random().nextInt(Integer.MAX_VALUE);
                    //arr_find[i] = arr_insert[new Random().nextInt(size)];
                    //arr_find[i] = arr_insert[i];
                }

                double[] MTF = timeTest(size, new ChainingHashTable<>(MoveToFrontList::new));
                double[] BST = timeTest(size, new ChainingHashTable<>(BinarySearchTree::new));
                double[] AVL = timeTest(size, new ChainingHashTable<>(AVLTree::new));

                if (NUM_WARMUP <= j) {
                    totalConstructTimeMTF += MTF[0];
                    totalFindTimeMTF += MTF[1];
                    totalConstructTimeBST += BST[0];
                    totalFindTimeBST += BST[1];
                    totalConstructTimeAVL += AVL[0];
                    totalFindTimeAVL += AVL[1];
                }
            }
            System.out.println("MTF: " + "size: " + size + " averageInsertRuntime: " + totalConstructTimeMTF / (NUM_TESTS - NUM_WARMUP)
                + " averageFindRuntime: " + totalFindTimeMTF / (NUM_TESTS - NUM_WARMUP));
            System.out.println("BST: " + "size: " + size + " averageInsertRuntime: " + totalConstructTimeBST / (NUM_TESTS - NUM_WARMUP)
                    + " averageFindRuntime: " + totalFindTimeBST /(NUM_TESTS - NUM_WARMUP));
            System.out.println("AVL: " + "size: " + size + " averageInsertRuntime: " + totalConstructTimeAVL / (NUM_TESTS - NUM_WARMUP)
                    + " averageFindRuntime: " + totalFindTimeAVL / (NUM_TESTS - NUM_WARMUP));

        }

    }

    private static double[] timeTest(int size, ChainingHashTable<Integer, Integer> table) {
        long startTimeInsert = System.nanoTime();
        for (int j = 0; j < arr_insert.length; j++) {
            table.insert(arr_insert[j], arr_insert[j]);
        }
        long endTimeInsert = System.nanoTime();

        long startTimeFind = System.nanoTime();
        for (int k = 0; k < arr_find.length; k++) {
            table.find(arr_find[k]);
        }
        long endTimeFind = System.nanoTime();
        return new double[]{endTimeInsert - startTimeInsert, endTimeFind - startTimeFind};
    }


//    private static double[] timeTest(int size, ChainingHashTable<Integer, Integer> t) {
//        double totalTimeInsert = 0;
//        double totalTimeFind = 0;
//        ChainingHashTable<Integer, Integer> table = t;
//        for (int i = 0; i < NUM_TESTS; i++) {
//            long startTimeInsert = System.nanoTime();
//            for (int j = 0; j < arr_insert.length; j++) {
//                table.insert(arr_insert[j], arr_insert[j]);
//            }
//            long endTimeInsert = System.nanoTime();
//
//            long startTimeFind = System.nanoTime();
//            for (int k = 0; k < arr_find.length; k++) {
//                table.find(arr_find[k]);
//            }
//            long endTimeFind = System.nanoTime();
//
//            if (NUM_WARMUP <= i) {
//                totalTimeInsert += (endTimeInsert - startTimeInsert);
//                totalTimeFind += (endTimeFind - startTimeFind);
//            }
//            table = t;
//        }
//        double averageInsertRuntime = totalTimeInsert / (NUM_TESTS - NUM_WARMUP);
//        double averageFindRuntime = totalTimeFind / (NUM_TESTS - NUM_WARMUP);
////        System.out.println("size: " + size + " averageInsertRuntime: " + averageInsertRuntime
////                + " averageFindRuntime: " + averageFindRuntime);
//        return new double[]{averageInsertRuntime, averageFindRuntime};
//    }


}
