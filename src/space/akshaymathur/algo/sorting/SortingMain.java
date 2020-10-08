package space.akshaymathur.algo.sorting;

import edu.princeton.cs.algs4.StdRandom;

public class SortingMain {
    public static void main(String... s) {
        Sorting[] sortingAlgos = new Sorting[]{new MergeSort(), new QuickSort()};
        for (var sortingAlgo : sortingAlgos) {
            System.out.println("Sorting with " + sortingAlgo.name());
            Integer[] testcases = new Integer[]{10,100,1000,10000,100000,1000000,10000000,100000000};
            for(var testcase : testcases){
                Integer[] arr = generateRandomIntegerArrayOfSize(testcase);
                System.out.println("Running test case " + testcase);
                assert (!Sorting.isSorted(arr));
                long start = System.currentTimeMillis();
                sortingAlgo.sort(arr);
                long end = System.currentTimeMillis();
                System.out.println("Time taken " +
                        (end - start) + "ms");
                assert (Sorting.isSorted(arr));
            }
        }
    }

    static Integer[] generateRandomIntegerArrayOfSize(int n){
        Integer[] arr = new Integer[n];
        for(int i = 0 ; i < n ; i ++){
            arr[i] = StdRandom.uniform(n);
        }
        return arr;
    }
}
