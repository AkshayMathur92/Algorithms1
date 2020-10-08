package space.akshaymathur.algo.sorting;

public interface Sorting {
    String name();
     void sort(Comparable[] arr);

    static boolean isSorted(Comparable[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }
}
