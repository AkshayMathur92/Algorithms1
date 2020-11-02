package space.akshaymathur.algo.sorting;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSort implements Sorting{
    private static void qsort(Comparable[] arr , int start, int end){
       if(start >= end) return;
//       int partitionPoint = partition(arr,start,end);
        Comparable v = arr[start];
        int i = start ;
        int lt = start;
        int gt = end;
        while (i <= gt){
            int cmp = arr[i].compareTo(v);
            if(cmp < 0){
                swap(arr, i,lt);
                lt++ ; i ++;
            }
            else if(cmp > 0){
                swap(arr, i, gt);
                gt--;
            }
            else i ++;
        }
       qsort(arr, start, lt- 1);
       qsort(arr, gt + 1, end);
    }

    //naive partition
    private static int partition(Comparable[] arr, int start, int end){
       int i = start + 1;
       int j = end;
       while(i < j){
           while (i < end && arr[i].compareTo(arr[start]) < 0) i++;
           while (arr[j].compareTo(arr[start]) > 0) j--;
           if(i >= j) break;
           swap(arr, i , j);
       }
       swap(arr, start, j);
       return j;
    }

    private static void swap(Comparable[] arr, int i, int lt) {
        Comparable temp = arr[i];
        arr[i] = arr[lt];
        arr[lt] = temp;
    }

    @Override
    public String name() {
        return "Quick sort";
    }

    @Override
    public void sort(Comparable[] arr) {
        StdRandom.shuffle(arr);
        qsort(arr,0, arr.length - 1);
    }
}
