package space.akshaymathur.algo.sorting;

import edu.princeton.cs.algs4.StdRandom;
import space.akshaymathur.algo.priorityQ.HeapSort;

public class SortingMain {
    public static void main(String... s) {
        Sorting[] sortingAlgos = new Sorting[]{new MergeSort(), new QuickSort(), new HeapSort()};
        Integer[] testcases = new Integer[]{10,100,1000,10000,100000,1000000, 10000000 };//,100000000};
        for (var testcase : testcases) {
            System.out.print("Sorting "  + testcase + "items    ");
            for(var sortingAlgo : sortingAlgos){
                Integer[] arr = generateRandomIntegerArrayOfSize(testcase);
                assert (!Sorting.isSorted(arr));
                long start = System.currentTimeMillis();
                sortingAlgo.sort(arr);
                long end = System.currentTimeMillis();
                System.out.printf("%-10s", (end - start) + "ms" + "     ");
                assert (Sorting.isSorted(arr));
            }
            System.out.println();
        }
    }

   public static Integer[] generateRandomIntegerArrayOfSize(int n){
        Integer[] arr = new Integer[n];
        for(int i = 0 ; i < n ; i ++){
            arr[i] = StdRandom.uniform(n);
        }
        return arr;
    }
}
