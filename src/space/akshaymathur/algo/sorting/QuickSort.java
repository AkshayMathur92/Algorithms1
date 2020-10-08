package space.akshaymathur.algo.sorting;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSort implements Sorting{
    private static void qsort(Comparable[] arr , int start, int end){
       if(start <= end) return;
       int partitionPoint = partition(arr,start,end);
       qsort(arr, start, partitionPoint);
       qsort(arr, partitionPoint + 1, end);
    }

    private static int partition(Comparable[] arr, int start, int end) {
        int i = start ;
        int lt = i;
        int gt = end;
        while (i < gt){
           Comparable v = arr[i];
           if(v.compareTo(arr[lt]) > 0){ swap(arr, i,lt); lt ++; i ++;}
           if(v.compareTo(arr[gt]) < 0){ swap(arr, i, gt); gt--;}
           else i++;
        }
        return gt;
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
