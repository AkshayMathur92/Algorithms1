package space.akshaymathur.algo.sorting;

import java.util.Arrays;

public class MergeSort {

    public static void sort(Comparable[] arr){
        int N = arr.length;
        Comparable[] aux = new Comparable[N];
        for(int sz = 1; sz < N; sz += sz){
            for(int lo = 0; lo < N - sz; lo += sz + sz)
                merge(arr, lo, lo+sz-1, Math.min(lo+sz+sz - 1, N - 1),aux);
        }
    }

    public static void recursiveSort(Comparable[] arr){
        int lo = 0, hi = arr.length - 1;
        Comparable[] aux = Arrays.copyOf(arr, arr.length);
        mergeSort(arr, lo, hi, aux);
    }

    private static void mergeSort(Comparable[] arr, int lo, int hi, Comparable[] aux) {
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(arr, lo, mid, aux);
        mergeSort(arr, mid + 1, hi, aux);
        merge(arr, lo, mid, hi, aux);

    }

    private static void merge(Comparable[] arr, int lo, int mid, int hi, Comparable[] aux) {
        for(int k = lo; k <= hi; k++){
            aux[k] = arr[k];
        }
        int i = lo, j = mid + 1;
        for( int k = lo; k <= hi; k ++){
            if(i > mid ) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else if (less(aux[j], aux[i])) arr[k] = aux[j++];
            else arr[k] = aux[i++];
        }
    }

    private static boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;
    }

    public static boolean isSorted(Comparable[] arr){
        for(int i = 1; i < arr.length; i++){
           if(less(arr[i],arr[i -1])) {
               return false;
           }
        }
        return true;
    }

    public static void main(String... s) {
        Integer[] arr = new Integer[]{5, 4, 6, 3, 7, 2, 8, 1, 9};
        assert (!isSorted(arr));
        recursiveSort(arr);
        assert (isSorted(arr));

        Integer[] arr1 = new Integer[]{5, 4, 6, 3, 7, 2, 8, 1, 9};
        assert (!isSorted(arr1));
        sort(arr1);
        assert (isSorted(arr1));
    }
}
